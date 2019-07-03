package com.p3achb0t._runelite_interfaces

interface Animation {
    fun getHasAlphaTransform(): Boolean
    fun getSkeleton(): Skeleton
    fun getTransformCount(): Int
    fun getTransformSkeletonLabels(): IntArray
    fun getTransformXs(): IntArray
    fun getTransformYs(): IntArray
    fun getTransformZs(): IntArray
}
