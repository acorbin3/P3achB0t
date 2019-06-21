package com.p3achb0t._runelite_interfaces

interface GraphicsObject : Entity {
    fun getCycleStart(): Int
    fun getFrame(): Int
    fun getFrameCycle(): Int
    fun getGraphicsObject_height(): Int
    fun getId(): Int
    fun getIsFinished(): Boolean
    fun getPlane(): Int
    fun getSequenceDefinition(): SequenceDefinition
    fun getX(): Int
    fun getY(): Int
}
