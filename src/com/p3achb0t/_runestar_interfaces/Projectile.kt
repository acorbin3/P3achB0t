package com.p3achb0t._runestar_interfaces

interface Projectile: Entity{
	fun getAccelerationZ(): Double
	fun getCycleEnd(): Int
	fun getCycleStart(): Int
	fun getFrame(): Int
	fun getFrameCycle(): Int
	fun getId(): Int
	fun getInt3(): Int
	fun getInt4(): Int
	fun getInt5(): Int
	fun getIsMoving(): Boolean
	fun getPitch(): Int
	fun getPlane(): Int
	fun getSeqType(): SeqType
	fun getSourceX(): Int
	fun getSourceY(): Int
	fun getSourceZ(): Int
	fun getSpeed(): Double
	fun getSpeedX(): Double
	fun getSpeedY(): Double
	fun getSpeedZ(): Double
	fun getTargetIndex(): Int
	fun getX(): Double
	fun getY(): Double
	fun getYaw(): Int
	fun getZ(): Double
}
