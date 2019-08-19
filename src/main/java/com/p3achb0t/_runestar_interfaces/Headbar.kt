package com.p3achb0t._runestar_interfaces

interface Headbar: Node{
	fun getType(): HeadbarType
	fun getUpdates(): IterableNodeDeque
}
