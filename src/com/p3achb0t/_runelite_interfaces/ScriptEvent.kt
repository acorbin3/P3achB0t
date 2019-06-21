package com.p3achb0t._runelite_interfaces

interface ScriptEvent : Node {
    fun getArgs0(): Any
    fun getBoolean1(): Boolean
    fun getDragTarget(): Widget
    fun getKeyPressed(): Int
    fun getKeyTyped(): Int
    fun getMouseX(): Int
    fun getMouseY(): Int
    fun getOpIndex(): Int
    fun getTargetName(): String
    fun getType0(): Int
    fun getWidget(): Widget
    fun get__d(): Int
}
