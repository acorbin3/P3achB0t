package com.p3achb0t._runestar_interfaces

interface ClientScript : DualNode {
    fun getIntArgumentCount(): Int
    fun getIntOperands(): IntArray
    fun getLocalIntCount(): Int
    fun getLocalStringCount(): Int
    fun getOpcodes(): IntArray
    fun getStringArgumentCount(): Int
    fun getStringOperands(): Array<String>
    fun getSwitches(): Array<IterableNodeHashTable>
}
