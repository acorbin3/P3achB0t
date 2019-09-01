package com.p3achb0t._runestar_interfaces

interface Archive: AbstractArchive{
	fun getArchiveDisk(): ArchiveDisk
	fun getIndex(): Int
	fun getIndexCrc(): Int
	fun getIndexVersion(): Int
	fun getMasterDisk(): ArchiveDisk
	fun getValidGroups(): BooleanArray
	fun get__av(): Boolean
	fun get__aj(): Int
	fun get__o(): Boolean
}
