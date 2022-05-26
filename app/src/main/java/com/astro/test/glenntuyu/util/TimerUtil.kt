package com.astro.test.glenntuyu.util

import java.util.*
import kotlin.concurrent.timerTask

/**
 * Created by glenntuyu on 26/05/2022.
 */
object TimerUtil{
    @JvmStatic
    private var timer: Timer = Timer()

    fun scheduleCanceler(delay: Long = 300, runnable: Runnable = timerTask {  }){
        timer.cancel()
        timer = Timer()
        timer.schedule(delay, action = { runnable.run() })
    }
}

private inline fun Timer.schedule(delay: Long, crossinline action: TimerTask.() -> Unit): TimerTask {
    val task = timerTask(action)
    schedule(task, delay)
    return task
}