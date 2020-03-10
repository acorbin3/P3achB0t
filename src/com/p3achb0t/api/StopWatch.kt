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

    fun getRuntimeString(): String? {
        val days = elapsed.toInt() / 86400000
        var remainder = elapsed % 86400000
        val hours = remainder.toInt() / 3600000
        remainder = elapsed % 3600000
        val minutes = remainder.toInt() / 60000
        remainder = remainder % 60000
        val seconds = remainder.toInt() / 1000
        val dd = if (days < 10) "0$days" else Integer.toString(days)
        val hh = if (hours < 10) "0$hours" else Integer.toString(hours)
        val mm = if (minutes < 10) "0$minutes" else Integer.toString(minutes)
        val ss = if (seconds < 10) "0$seconds" else Integer.toString(seconds)
        return "$dd:$hh:$mm:$ss"
    }

}