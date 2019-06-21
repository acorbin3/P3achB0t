package com.p3achb0t._runelite_interfaces

interface WorldMapIndexCacheLoader {
    fun getCacheName(): String
    fun getIndexCache(): AbstractIndexCache
    fun getIsLoaded0(): Boolean
    fun getPercentLoaded0(): Int
}
