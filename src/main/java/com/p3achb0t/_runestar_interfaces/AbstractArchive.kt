package com.p3achb0t._runestar_interfaces

interface AbstractArchive {
	fun getFileCounts(): IntArray
	fun getFileIds(): Array<IntArray>
	fun getFileNameHashTables(): Array<IntHashTable>
	fun getFileNameHashes(): Array<IntArray>
	fun getFiles(): Any
	fun getGroupCount(): Int
	fun getGroupCrcs(): IntArray
	fun getGroupIds(): IntArray
	fun getGroupNameHashTable(): IntHashTable
	fun getGroupNameHashes(): IntArray
	fun getGroupVersions(): IntArray
	fun getGroups(): Any
	fun getHash(): Int
	fun getReleaseGroups(): Boolean
	fun getShallowFiles(): Boolean
}
