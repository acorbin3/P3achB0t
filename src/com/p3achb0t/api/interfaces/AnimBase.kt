package com.p3achb0t.api.interfaces

interface AnimBase : Node {
    fun getId(): Int
    fun getTransformCount(): Int
    fun getTransformLabels(): Array<IntArray>
    fun getTransformTypes(): IntArray
}
