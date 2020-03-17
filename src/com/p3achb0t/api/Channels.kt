package com.p3achb0t.api

import com.p3achb0t.api.interfaces.CommunicationInterface
import com.p3achb0t.api.interfaces.RoomInterface
import kotlin.collections.HashMap

class Channels() : SubInterface {

    private val channels = HashMap<String, RoomInterface>()
    private lateinit var communication: CommunicationInterface
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

    fun send(id: String, message: String) {
        channels[id]?.notifySubscribers(message)
    }

    override fun setChannel(id: String, room: RoomInterface) {
        channels[id] = room
    }

    fun setComScript(communication: CommunicationInterface, script: ChannelInterface1) {
        this.communication = communication
        this.script = script
    }
}