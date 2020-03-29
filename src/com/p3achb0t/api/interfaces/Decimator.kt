package com.p3achb0t.api.interfaces

interface Decimator {
    fun getInputRate(): Int
    fun getOutputRate(): Int
    fun getTable(): Array<IntArray>
}
