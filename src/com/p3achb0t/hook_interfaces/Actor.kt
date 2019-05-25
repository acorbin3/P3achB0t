package com.p3achb0t.hook_interfaces

interface Actor : Renderable {
    fun get_animation(): Int
    fun get_animationDelay(): Int
    fun get_combatTime(): Int
    fun get_frameOne(): Int
    fun get_frameTwo(): Int
    fun get_healthBars(): Any
    fun get_hitCycles(): Any
    fun get_hitDamages(): Any
    fun get_hitTypes(): Any
    fun get_interacting(): Int
    fun get_localX(): Int
    fun get_localY(): Int
    fun get_message(): Any
    fun get_orientation(): Int
    fun get_queueSize(): Int
    fun get_queueTraversed(): Any
    fun get_queueX(): Any
    fun get_queueY(): Any
    fun get_runtimeAnimation(): Int
    fun get_standAnimation(): Int
}
