package com.p3achb0t.api

import com.p3achb0t.api.interfaces.BrokerInterface
import com.p3achb0t.api.interfaces.ChannelInterface
import java.util.*
import kotlin.collections.HashMap

class Channels() {

    lateinit var uuid: String // UUID of the instance
    lateinit var broker: BrokerInterface
    val channels = HashMap<String, ChannelInterface>()
    val scriptUUID: String = UUID.randomUUID().toString()

    /**
     * subscribe to IPC
     */
    fun subscribe(id: String, callback: (id: String, message: String) -> Unit) {
        broker.subscribeChannel(id, ::setChannelCallback, scriptUUID, callback)
    }

    /**
     * unsubscribe from IPC
     */
    fun unsubscribe(id: String) {
        broker.unsubscribeChannel(id, scriptUUID)
    }

    /**
     * Send a message to the channel
     */
    fun send(id: String, message: String) {
        channels[id]?.notifySubscribers("$scriptUUID;$message")
    }

    private fun setChannelCallback(id: String, channel: ChannelInterface) {
        channels[id] = channel
    }

}