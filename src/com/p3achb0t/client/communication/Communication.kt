package com.p3achb0t.client.communication

import com.p3achb0t.api.interfaces.CommunicationInterface
import com.p3achb0t.api.interfaces.RoomInterface
import java.util.concurrent.ConcurrentHashMap

class Communication : CommunicationInterface {

    private val mutex = Any()
    private val channels = ConcurrentHashMap<String, Room>()

    override fun subscribeChannel(id: String, roomCallback: (id: String, message: RoomInterface) -> Unit, callback: (id: String, message: String) -> Unit) {

        synchronized(mutex) {
            if (!channels.containsKey(id)) {
                println("added channel")
                channels[id] = Room(id)
            }
        }

        val room: Room = channels[id]!!
        roomCallback(id, room)
        room.subscribe(callback)
        println("subscribeChannel: $id $room")
    }


    // TODO remove when channel is empty
    override fun unsubscribeChannel(id: String, roomCallback: (id: String, message: RoomInterface) -> Unit, callback: (String, String) -> Unit) {

    }
}

