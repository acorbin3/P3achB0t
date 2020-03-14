package com.p3achb0t.api.interfaces

interface Archive : AbstractArchive {
    fun getArchiveDisk(): ArchiveDisk
    fun getIndex(): Int
    fun getIndexCrc(): Int
    fun getIndexVersion(): Int
    fun getMasterDisk(): ArchiveDisk
    fun getValidGroups(): BooleanArray
    fun get__ae(): Boolean
    fun get__au(): Int
    fun get__u(): Boolean
}
