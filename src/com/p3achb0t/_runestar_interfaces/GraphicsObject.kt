package com.p3achb0t._runestar_interfaces

interface GraphicsObject: Entity{
	fun getCycleStart(): Int
	fun getFrame(): Int
	fun getFrameCycle(): Int
	fun getGraphicsObject_height(): Int
	fun getId(): Int
	fun getIsFinished(): Boolean
	fun getPlane(): Int
	fun getSeqType(): SeqType
	fun getX(): Int
	fun getY(): Int
}
