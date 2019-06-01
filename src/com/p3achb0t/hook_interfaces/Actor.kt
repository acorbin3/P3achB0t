package com.p3achb0t.hook_interfaces

interface Actor : Renderable {
    fun get_animation(): Int
    fun get_animationDelay(): Int
    fun get_combatTime(): Int
    fun get_frameOne(): Int
    fun get_frameTwo(): Int
    //    fun get_healthBars(): Iterable
    fun get_hitCycles(): Array<Int>

    fun get_hitDamages(): Array<Int>
    fun get_hitTypes(): Array<Int>
    fun get_interacting(): Int
    fun get_localX(): Int
    fun get_localY(): Int
    fun get_message(): Array<Int>
    fun get_orientation(): Int
    fun get_queueSize(): Int
    //    fun get_queueTraversed(): ArrayList<Byte>
    fun get_queueX(): Array<Int>

    fun get_queueY(): Array<Int>
    fun get_runtimeAnimation(): Int
    fun get_standAnimation(): Int
}
