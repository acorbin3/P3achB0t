package com.p3achb0t.api

import com.p3achb0t.api.interfaces.BrokerInterface
import com.p3achb0t.api.interfaces.ChannelInterface
import kotlin.collections.HashMap

class Channels() {

    private val channels = HashMap<String, ChannelInterface>()
    lateinit var broker: BrokerInterface
    //private lateinit var callback: ((String, String) -> Unit)

    /**
     * subscribe to IPC
     */
    fun subscribe(id: String, callback: (id: String, message: String) -> Unit) {
        broker.subscribeChannel(id, ::setChannel, callback)
        //this.callback = callback
    }

    /**
     * unsubscribe from IPC
     */
    fun unsubscribe(id: String) {
        //communication.unsubscribeChannel(id, channels[id]!!)
    }

    fun send(id: String, message: String) {
        channels[id]?.notifySubscribers(message)
    }

     private fun setChannel(id: String, channel: ChannelInterface) {
        channels[id] = channel
    }

}