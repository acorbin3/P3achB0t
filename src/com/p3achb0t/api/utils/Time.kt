package com.p3achb0t.api.utils

object Time {
    fun getHoursInMils(hours: Int): Long{
        return hours * 60 * 60 * 1000L
    }

    fun getMinInMils(min: Int): Long{
        return min * 60 * 1000L
    }

    fun getSecInMils(sec: Int): Long{
        return sec * 1000L
    }

    fun getTimeInMils(hour: Int, min: Int, sec: Int): Long{
        return getHoursInMils(hour) + getMinInMils(min) + getSecInMils(sec)
    }

    fun getRuntimeString(elapsed: Long): String {
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

    suspend fun sleep(time: Long) {
        kotlinx.coroutines.delay(time)
    }
}