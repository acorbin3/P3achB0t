package com.p3achb0t._runelite_interfaces

interface Animation {
    fun getHasAlphaTransform(): Boolean
    fun getSkeleton(): Skeleton
    fun getTransformCount(): Int
    fun getTransformSkeletonLabels(): Array<Int>
    fun getTransformXs(): Array<Int>
    fun getTransformYs(): Array<Int>
    fun getTransformZs(): Array<Int>
}
