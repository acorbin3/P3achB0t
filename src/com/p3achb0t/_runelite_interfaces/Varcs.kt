package com.p3achb0t._runelite_interfaces

interface Varcs {
    fun getIntsPersistence(): BooleanArray
    fun getLastWriteTimeMs(): Long
    fun getMap(): Any
    fun getStrings(): Array<String>
    fun getUnwrittenChanges(): Boolean
}
