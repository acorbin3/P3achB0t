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

    suspend fun sleep(time: Long) {
        kotlinx.coroutines.delay(time)
    }
}