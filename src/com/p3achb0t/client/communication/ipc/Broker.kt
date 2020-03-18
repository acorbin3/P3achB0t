package com.p3achb0t.client.communication.ipc

import com.p3achb0t.api.interfaces.BrokerInterface
import com.p3achb0t.api.interfaces.ChannelInterface
import com.p3achb0t.client.communication.peer.PeerClient
import java.util.concurrent.ConcurrentHashMap

class Broker : BrokerInterface {

    private val mutex = Any()
    val channels = ConcurrentHashMap<String, Channel>()

    override fun subscribeChannel(id: String, roomCallback: (id: String, message: ChannelInterface) -> Unit, uuid: String, callback: (id: String, message: String) -> Unit) {

        synchronized(mutex) {
            if (!channels.containsKey(id)) {
                println("added channel")
                val channel = Channel(id) // add broker to channel for cross com
                channel.subscribe("Broker", ::brokerCallback)
                channels[id] = channel
            }
        }

        val channel: Channel = channels[id]!!
        roomCallback(id, channel)
        channel.subscribe(uuid, callback)
        println("subscribeChannel: $id $channel")
    }

    // TODO remove when channel is empty
    override fun unsubscribeChannel(id: String, uuid: String) {
        val channel = channels[id]
        channel?.unsubscribe(uuid)
    }

    private fun brokerCallback(id: String, message: String) {
        PeerClient.callback(id, message)
    }
}

