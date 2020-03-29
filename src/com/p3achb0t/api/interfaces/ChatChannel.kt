package com.p3achb0t.api.interfaces

interface ChatChannel {
    fun getCount(): Int
    fun getMessages(): Array<Message>
}
