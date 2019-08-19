package com.p3achb0t.api


class Timer(private var period: Long) {
    private var start: Long = 0
    private var end: Long = 0

    init {
        this.start = System.currentTimeMillis()
        this.end = start + period
    }

    fun isRunning(): Boolean {
        return System.currentTimeMillis() < end
    }

    fun getElapsed(): Long {
        return System.currentTimeMillis() - start
    }

    fun getRemaining(): Long {
        return (if (isRunning()) end - System.currentTimeMillis() else 0).toLong()
    }

    fun reset() {
        this.end = System.currentTimeMillis() + period
    }

    fun setPeriod(period: Int) {
        this.period = period.toLong()
    }

}