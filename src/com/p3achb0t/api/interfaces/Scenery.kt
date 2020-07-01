package com.p3achb0t.api.interfaces

interface Scenery {
    fun getCenterX(): Int
    fun getCenterY(): Int
    fun getEndX(): Int
    fun getEndY(): Int
    fun getEntity(): Entity
    fun getFlags(): Int
    fun getHeight(): Int
    fun getLastDrawn(): Int
    fun getOrientation(): Int
    fun getPlane(): Int
    fun getStartX(): Int
    fun getStartY(): Int
    fun getTag(): Long
    fun get__w(): Int
}
