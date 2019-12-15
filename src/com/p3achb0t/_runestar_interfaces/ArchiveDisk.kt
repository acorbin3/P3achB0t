package com.p3achb0t._runestar_interfaces

interface ArchiveDisk {
    fun getArchive(): Int
    fun getDatFile(): BufferedFile
    fun getIdxFile(): BufferedFile
    fun getMaxEntrySize(): Int
}
