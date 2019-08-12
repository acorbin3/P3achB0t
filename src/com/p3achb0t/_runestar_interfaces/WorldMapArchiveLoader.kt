package com.p3achb0t._runestar_interfaces

interface WorldMapArchiveLoader {
	fun getArchive(): AbstractArchive
	fun getCacheName(): String
	fun getIsLoaded0(): Boolean
	fun getPercentLoaded0(): Int
}
