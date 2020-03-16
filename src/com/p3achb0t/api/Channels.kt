package com.p3achb0t.api


import com.p3achb0t.client.communication.ChannelInterface
import com.p3achb0t.client.communication.Communication
import com.p3achb0t.client.communication.Room
import java.util.*
import kotlin.collections.HashMap

class Channels(val communication: Communication) : SubInterface {

    private val channels = HashMap<String, Room>()

    /**
     * subscribe to IPC
     */
    fun subscribe(id: String) {
        communication.subscribeChannel(id, this)
    }

    /**
     * unsubscribe from IPC
     */
    fun unsubscribe(id: String) {
        communication.unsubscribeChannel(id, this)
    }

    fun send1(id: String, message: String) {
        channels[id]?.notifySubscribers(message)
    }

    override fun setChannel(id: String, room: Room) {
        channels[id] = room
    }

    override fun receive(id: String, message: String) {
        messages[id]?.add(message)
    }

    override fun send(message: String) {
        TODO("Not yet implemented")
    }

}