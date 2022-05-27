package com.astro.test.glenntuyu.domain

import com.astro.test.glenntuyu.util.launchCatchError
import kotlinx.coroutines.*

/**
 * Created by glenntuyu on 27/05/2022.
 */
abstract class UseCase<out T : Any, Params>(
    private val defaultDispatchers: CoroutineDispatcher = Dispatchers.Default,
    mainDispatchers: CoroutineDispatcher = Dispatchers.Main
) {
    protected var parentJob = SupervisorJob()
    private val localScope = CoroutineScope(mainDispatchers + parentJob)

    abstract suspend fun executeOnBackground(param: Params): T

    private suspend fun executeCatchError(param: Params): Result<T> =
        withContext(defaultDispatchers) {
            try {
                Success(executeOnBackground(param))
            } catch (throwable: Throwable) {
                Fail(throwable)
            }
        }

    fun execute(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit, param: Params) {
        cancelJobs()
        localScope.launchCatchError(block = {
            when (val result = executeCatchError(param)) {
                is Success -> onSuccess(result.data)
                is Fail -> onError(result.throwable)
            }
        }) {
            if (it !is CancellationException)
                onError(it)
        }
    }

    private fun cancelJobs() {
        localScope.coroutineContext.cancelChildren()
    }
}