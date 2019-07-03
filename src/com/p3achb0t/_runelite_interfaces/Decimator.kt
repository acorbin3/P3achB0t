package com.p3achb0t._runelite_interfaces

interface Decimator {
    fun getInputRate(): Int
    fun getOutputRate(): Int
    fun getTable(): Array<IntArray>
}
