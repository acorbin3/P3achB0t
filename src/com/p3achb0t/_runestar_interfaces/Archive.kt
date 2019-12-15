package com.p3achb0t._runestar_interfaces

interface Archive : AbstractArchive {
    fun getArchiveDisk(): ArchiveDisk
    fun getIndex(): Int
    fun getIndexCrc(): Int
    fun getIndexVersion(): Int
    fun getMasterDisk(): ArchiveDisk
    fun getValidGroups(): BooleanArray
    fun get__af(): Boolean
    fun get__ao(): Int
    fun get__j(): Boolean
}
