package com.p3achb0t.api

import com.p3achb0t.client.communication.Communication
import com.p3achb0t.client.communication.Room
import kotlin.collections.HashMap

class Channels() : SubInterface {

    private val channels = HashMap<String, Room>()
    private lateinit var communication: Communication
    private lateinit var script: ChannelInterface1

    /**
     * subscribe to IPC
     */
    fun subscribe(id: String) {
        communication.subscribeChannel(id, this, script)
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

    fun setComScript(communication: Communication, script: ChannelInterface1) {
        this.communication = communication
        this.script = script
    }
}