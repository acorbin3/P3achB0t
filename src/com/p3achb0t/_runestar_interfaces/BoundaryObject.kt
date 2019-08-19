package com.p3achb0t._runestar_interfaces

interface BoundaryObject {
	fun getEntity1(): Entity
	fun getEntity2(): Entity
	fun getFlags(): Int
	fun getOrientationA(): Int
	fun getOrientationB(): Int
	fun getTag(): Long
	fun getTileHeight(): Int
	fun getX(): Int
	fun getY(): Int
}
