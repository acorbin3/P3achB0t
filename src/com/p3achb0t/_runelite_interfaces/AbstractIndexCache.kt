package com.p3achb0t._runelite_interfaces

interface AbstractIndexCache {
    fun getArchiveCount(): Int
    fun getArchiveCrcs(): Array<Int>
    fun getArchiveIds(): Array<Int>
    fun getArchiveNameHashTable(): IntHashTable
    fun getArchiveNameHashes(): Array<Int>
    fun getArchiveVersions(): Array<Int>
    fun getArchives(): Any
    fun getHash(): Int
    fun getRecordCounts(): Array<Int>
    fun getRecordIds(): Array<Array<Int>>
    fun getRecordNameHashTables(): Array<IntHashTable>
    fun getRecordNameHashes(): Array<Array<Int>>
    fun getRecords(): Any
    fun getReleaseArchives(): Boolean
    fun getShallowRecords(): Boolean
}
