package com.p3achb0t._runelite_interfaces

interface Script : DualNode {
    fun getIntArgumentCount(): Int
    fun getIntOperands(): Array<Int>
    fun getLocalIntCount(): Int
    fun getLocalStringCount(): Int
    fun getOpcodes(): Array<Int>
    fun getStringArgumentCount(): Int
    fun getStringOperands(): Array<String>
    fun getSwitches(): Array<IterableNodeHashTable>
}
