package com.p3achb0t.api.interfaces

interface WorldMapArchiveLoader {
    fun getArchive(): AbstractArchive
    fun getCacheName(): String
    fun getIsLoaded0(): Boolean
    fun getPercentLoaded0(): Int
}
