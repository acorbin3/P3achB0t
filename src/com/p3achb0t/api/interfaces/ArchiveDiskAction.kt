package com.p3achb0t.api.interfaces

interface ArchiveDiskAction : Node {
    fun getArchive(): Archive
    fun getArchiveDisk(): ArchiveDisk
    fun getData(): ByteArray
    fun getType(): Int
}
