package com.p3achb0t.client.communication.ipc

import com.p3achb0t.api.interfaces.ChannelInterface
import java.util.*

class Channel(val id: String) : ChannelInterface {

    private val mutex = Any()
    private val observers  = ArrayList<(String, String) -> Unit>()

    override fun subscribe(channelCallback: (String, String) -> Unit) {
        synchronized (mutex) {
            observers.add(channelCallback)
        }
    }

    override fun unsubscribe(channelCallback: (String, String) -> Unit) {
        synchronized (mutex) {
            observers.remove(channelCallback)
        }
    }

    override fun notifySubscribers(message: String) {
        val copyChannelInterfaces: ArrayList<(String, String) -> Unit>

        synchronized(mutex) {
            copyChannelInterfaces = ArrayList<(String, String) -> Unit>(observers)
        }

        for (observer in copyChannelInterfaces) {
            observer(id, message)
        }
    }
}