package com.p3achb0t.hook_interfaces

interface MessageChannel {
    fun getMessageIndex(): Int
    fun getMessages(): Array<MessageNode>
}
