package com.p3achb0t.api

class StopWatch {
    var starTime = 0L
    init {
        starTime = System.currentTimeMillis()
    }
    fun start(){ starTime = System.currentTimeMillis() }
    fun reset(){ starTime = System.currentTimeMillis()}
    val elapsed: Long get() { return System.currentTimeMillis() - starTime}
    val elapsedSec: Int get() { return ((System.currentTimeMillis() - starTime) / 1000).toInt()}

}