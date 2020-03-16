package com.p3achb0t.client.communication

interface ChannelInterface {

    fun setChannel(id: String, room: Room)
    fun receive(id: String, message: String)
    fun send(message: String)
}