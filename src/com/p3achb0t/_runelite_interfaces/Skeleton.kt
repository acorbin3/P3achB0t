package com.p3achb0t._runelite_interfaces

interface Skeleton : Node {
    fun getCount(): Int
    fun getId(): Int
    fun getLabels(): Array<IntArray>
    fun getTransformTypes(): IntArray
}
