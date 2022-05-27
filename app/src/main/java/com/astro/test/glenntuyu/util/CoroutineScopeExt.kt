package com.astro.test.glenntuyu.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by glenntuyu on 27/05/2022.
 */
fun CoroutineScope.launchCatchError(context: CoroutineContext = coroutineContext,
                                    block: suspend (()->Unit),
                                    onError: suspend (Throwable)-> Unit) =
    launch (context){
        try{
            block()
        } catch (t: Throwable){
            try {
                onError(t)
            } catch (e: Throwable){

            }
        }
    }