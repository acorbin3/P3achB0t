package com.p3achb0t._runelite_interfaces

interface DynamicObject : Entity {
    fun getCycleStart(): Int
    fun getFrame(): Int
    fun getId(): Int
    fun getOrientation(): Int
    fun getPlane(): Int
    fun getSequenceDefinition(): SequenceDefinition
    fun getType(): Int
    fun getX(): Int
    fun getY(): Int
}
