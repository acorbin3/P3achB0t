package com.p3achb0t._runelite_interfaces

interface Skeleton : Node {
    fun getCount(): Int
    fun getId(): Int
    fun getLabels(): Array<Array<Int>>
    fun getTransformTypes(): Array<Int>
}
