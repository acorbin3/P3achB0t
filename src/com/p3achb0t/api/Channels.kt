package com.p3achb0t.api

import com.p3achb0t.api.interfaces.CommunicationInterface
import com.p3achb0t.api.interfaces.RoomInterface
import kotlin.collections.HashMap

class Channels() {

    private val channels = HashMap<String, RoomInterface>()
    lateinit var communication: CommunicationInterface
    //private lateinit var callback: ((String, String) -> Unit)

    /**
     * subscribe to IPC
     */
    fun subscribe(id: String, callback: (id: String, message: String) -> Unit) {
        communication.subscribeChannel(id, ::setChannel, callback)
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

     private fun setChannel(id: String, room: RoomInterface) {
        channels[id] = room
    }

}