package com.p3achb0t._runestar_interfaces

interface ChatChannel {
	fun getCount(): Int
	fun getMessages(): Array<Message>
}
