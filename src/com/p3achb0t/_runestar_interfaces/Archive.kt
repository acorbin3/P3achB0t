package com.p3achb0t._runestar_interfaces

interface Archive: AbstractArchive{
	fun getArchiveDisk(): ArchiveDisk
	fun getIndex(): Int
	fun getIndexCrc(): Int
	fun getIndexVersion(): Int
	fun getMasterDisk(): ArchiveDisk
	fun getValidGroups(): BooleanArray
	fun get__al(): Boolean
	fun get__ag(): Int
	fun get__z(): Boolean
}
