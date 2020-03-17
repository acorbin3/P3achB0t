package com.p3achb0t.client.communication

import com.p3achb0t.api.ChannelInterface1
import com.p3achb0t.api.interfaces.RoomInterface
import java.util.*



class Room(val id: String) : RoomInterface {

    private val mutex = Any()

    private val observers  = ArrayList<(String, String) -> Unit>()


    override fun subscribe(channelInterface: (String, String) -> Unit) {
        synchronized (mutex) {
            observers.add(channelInterface)
        }
    }

    override fun unsubscribe(channelInterface: ChannelInterface1) {
        synchronized (mutex) {
            //observers.remove(channelInterface)
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

/*
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
 */
