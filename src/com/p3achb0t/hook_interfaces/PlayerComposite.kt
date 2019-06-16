package com.p3achb0t.hook_interfaces

interface PlayerComposite {
    fun getAnimatedModelID(): Long
    fun getAppearance(): IntArray
    fun getBodyColors(): IntArray
    fun getFemale(): Boolean
    fun getNpcID(): Int
    fun getStaticModelID(): Long
}
