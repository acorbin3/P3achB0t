package com.p3achb0t.client.communication.ipc

import com.p3achb0t.api.interfaces.ChannelInterface
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class Channel(val id: String) : ChannelInterface {

    //private val mutex = Any()
    val observers  = ConcurrentHashMap<String, (String, String) -> Unit>()

    override fun subscribe(subscriberId: String, channelCallback: (String, String) -> Unit) {
        //synchronized (mutex) {
            observers[subscriberId] = channelCallback
        //}
    }

    override fun unsubscribe(subscriberId: String) {
        //synchronized (mutex) {
            observers.remove(subscriberId)
        //}
    }

    override fun notifySubscribers(message: String) {
        val copyChannelInterfaces: ArrayList<(String, String) -> Unit> = ArrayList<(String, String) -> Unit>(observers.values)

        //synchronized(mutex) {
            //copyChannelInterfaces = ArrayList<(String, String) -> Unit>(observers.values)
        //}

        for (observer in copyChannelInterfaces) {
            observer(id, message)
        }
    }
}

/*
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
 */