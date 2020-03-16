package com.p3achb0t.client.communication

import com.p3achb0t.api.ChannelInterface1
import com.p3achb0t.api.SubInterface
import java.util.concurrent.ConcurrentHashMap

class Communication {

    private val mutex = Any()
    private val channels = ConcurrentHashMap<String, Room>()

    fun subscribeChannel(id: String, channelInterface: SubInterface, callback: ChannelInterface1) {

        synchronized(mutex) {
            if (!channels.containsKey(id)) {
                println("added channel")
                channels[id] = Room(id)
            }
        }

        val room: Room = channels[id]!!
        channelInterface.setChannel(id, room)
        room.subscribe(callback)
        println("subscribeChannel: $id $room")
    }


    // TODO remove when channel is empty
    fun unsubscribeChannel(id: String, channelInterface: SubInterface) {

    }
}

