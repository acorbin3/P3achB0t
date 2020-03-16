package com.p3achb0t.client.communication

import java.util.concurrent.ConcurrentHashMap

class Communication {

    private val mutex = Any()
    private val channels = ConcurrentHashMap<String, Room>()

    fun subscribeChannel(id: String, channelInterface: ChannelInterface) {

        synchronized(mutex) {
            if (!channels.containsKey(id)) {
                println("added channel")
                channels[id] = Room(id)
            }
        }

        val room: Room = channels[id]!!
        channelInterface.setChannel(id, room)
        room.subscribe(channelInterface)
        println("subscribeChannel: $id $room")
    }


    // TODO remove when channel is empty
    fun unsubscribeChannel(id: String, channelInterface: ChannelInterface) {

    }
}