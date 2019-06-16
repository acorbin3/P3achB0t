package com.p3achb0t.hook_interfaces

interface Actor : Renderable {
    fun getAnimation(): Int
    fun getAnimationDelay(): Int
    fun getCombatTime(): Int
    fun getFrameOne(): Int
    fun getFrameTwo(): Int
    fun getHealthBars(): Iterable
    fun getHitCycles(): IntArray
    fun getHitDamages(): IntArray
    fun getHitTypes(): IntArray
    fun getInteracting(): Int
    fun getLocalX(): Int
    fun getLocalY(): Int
    fun getMessage(): String
    fun getOrientation(): Int
    fun getQueueSize(): Int
    fun getQueueTraversed(): ByteArray
    fun getQueueX(): IntArray
    fun getQueueY(): IntArray
    fun getRuntimeAnimation(): Int
    fun getStandAnimation(): Int
}
