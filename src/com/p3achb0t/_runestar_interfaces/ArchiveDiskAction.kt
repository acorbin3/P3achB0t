package com.p3achb0t._runestar_interfaces

interface ArchiveDiskAction: Node{
	fun getArchive(): Archive
	fun getArchiveDisk(): ArchiveDisk
	fun getData(): ByteArray
	fun getType(): Int
}
