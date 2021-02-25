package com.p3achb0t.api.interfaces

interface Archive : AbstractArchive {
    fun getArchiveDisk(): ArchiveDisk
    fun getIndex(): Int
    fun getIndexCrc(): Int
    fun getIndexVersion(): Int
    fun getMasterDisk(): ArchiveDisk
    fun getValidGroups(): BooleanArray
    fun get__ay(): Boolean
    fun get__aq(): Int
    fun get__j(): Boolean
}
