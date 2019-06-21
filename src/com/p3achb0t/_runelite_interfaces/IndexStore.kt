package com.p3achb0t._runelite_interfaces

interface IndexStore {
    fun getDataFile(): BufferedFile
    fun getIndex(): Int
    fun getIndexFile(): BufferedFile
    fun getMaxEntrySize(): Int
}
