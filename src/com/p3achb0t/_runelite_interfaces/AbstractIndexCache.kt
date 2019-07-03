package com.p3achb0t._runelite_interfaces

interface AbstractIndexCache {
    fun getArchiveCount(): Int
    fun getArchiveCrcs(): IntArray
    fun getArchiveIds(): IntArray
    fun getArchiveNameHashTable(): IntHashTable
    fun getArchiveNameHashes(): IntArray
    fun getArchiveVersions(): IntArray
    fun getArchives(): Any
    fun getHash(): Int
    fun getRecordCounts(): IntArray
    fun getRecordIds(): Array<IntArray>
    fun getRecordNameHashTables(): Array<IntHashTable>
    fun getRecordNameHashes(): Array<IntArray>
    fun getRecords(): Any
    fun getReleaseArchives(): Boolean
    fun getShallowRecords(): Boolean
}
