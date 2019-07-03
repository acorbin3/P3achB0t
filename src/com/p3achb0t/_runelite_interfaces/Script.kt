package com.p3achb0t._runelite_interfaces

interface Script : DualNode {
    fun getIntArgumentCount(): Int
    fun getIntOperands(): IntArray
    fun getLocalIntCount(): Int
    fun getLocalStringCount(): Int
    fun getOpcodes(): IntArray
    fun getStringArgumentCount(): Int
    fun getStringOperands(): Array<String>
    fun getSwitches(): Array<IterableNodeHashTable>
}
