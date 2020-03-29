package com.p3achb0t.api.interfaces

interface DualNode : Node {
    fun getKeyDual(): Long
    fun getNextDual(): DualNode
    fun getPreviousDual(): DualNode
}
