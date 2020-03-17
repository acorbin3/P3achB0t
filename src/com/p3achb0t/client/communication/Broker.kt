package com.p3achb0t.client.communication

import com.p3achb0t.api.interfaces.BrokerInterface
import com.p3achb0t.api.interfaces.ChannelInterface
import java.util.concurrent.ConcurrentHashMap

class Broker : BrokerInterface {

    private val mutex = Any()
    private val channels = ConcurrentHashMap<String, Channel>()

    override fun subscribeChannel(id: String, roomCallback: (id: String, message: ChannelInterface) -> Unit, callback: (id: String, message: String) -> Unit) {

        synchronized(mutex) {
            if (!channels.containsKey(id)) {
                println("added channel")
                channels[id] = Channel(id)
            }
        }

        val channel: Channel = channels[id]!!
        roomCallback(id, channel)
        channel.subscribe(callback)
        println("subscribeChannel: $id $channel")
    }

    // TODO remove when channel is empty
    override fun unsubscribeChannel(id: String, roomCallback: (id: String, message: ChannelInterface) -> Unit, callback: (String, String) -> Unit) {

    }
}

