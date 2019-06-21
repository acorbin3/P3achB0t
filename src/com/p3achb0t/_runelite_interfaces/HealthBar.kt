package com.p3achb0t._runelite_interfaces

interface HealthBar : Node {
    fun getDefinition(): HealthBarDefinition
    fun getUpdates(): IterableNodeDeque
}
