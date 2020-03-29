package com.p3achb0t.api.interfaces

interface DynamicObject : Entity {
    fun getCycleStart(): Int
    fun getFrame(): Int
    fun getId(): Int
    fun getOrientation(): Int
    fun getPlane(): Int
    fun getSeqType(): SeqType
    fun getType(): Int
    fun getX(): Int
    fun getY(): Int
}
