package com.p3achb0t.client.communication

interface ChannelInterface {

    fun setChannel(channel: Channel)
    fun receive(message: String)
    fun send(message: String)
}