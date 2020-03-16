package com.p3achb0t.client.communication

import java.util.*



class Room(val id: String) {

    private val mutex = Any()

    private val observers  = ArrayList<ChannelInterface>()

    fun subscribe(channelInterface: ChannelInterface) {
        synchronized (mutex) {
            observers.add(channelInterface)
        }
    }

    fun unsubscribe(channelInterface: ChannelInterface) {
        synchronized (mutex) {
            observers.remove(channelInterface)
        }
    }

    fun notifySubscribers(message: String) {

        val copyChannelInterfaces: ArrayList<ChannelInterface>

        synchronized(mutex) {
            copyChannelInterfaces = ArrayList<ChannelInterface>(observers)
        }

        for (observer in copyChannelInterfaces) {
            observer.receive(id, message)
        }
    }

}
