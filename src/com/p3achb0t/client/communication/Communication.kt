package com.p3achb0t.client.communication

import java.util.concurrent.ConcurrentHashMap

class Communication {

    private val channels = ConcurrentHashMap<String, Channel>()

    init {
        //channels["1234"] = Channel()
    }

    fun subscribeChannel(id: String, channelInterface: ChannelInterface) {

        if (!channels.containsKey(id)) {
            addChannel(id)
        }

        val chan: Channel = channels[id]!!
        println("subscribeChannel: $id $chan")
        channelInterface.setChannel(chan)
        chan.subscribe(channelInterface)


    }

    fun addChannel(id: String) {
        channels[id] = Channel()
    }

    // TODO remove when channel is empty
    fun unsubscribeChannel(channelInterface: ChannelInterface) {

    }
}