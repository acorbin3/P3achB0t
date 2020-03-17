package com.p3achb0t.client.communication

import com.p3achb0t.api.ChannelInterface1
import com.p3achb0t.api.SubInterface
import com.p3achb0t.api.interfaces.CommunicationInterface
import java.util.concurrent.ConcurrentHashMap

class Communication : CommunicationInterface {

    private val mutex = Any()
    private val channels = ConcurrentHashMap<String, Room>()

    override fun subscribeChannel(id: String, channelInterface: SubInterface, callback: ChannelInterface1) {

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
    override fun unsubscribeChannel(id: String, channelInterface: SubInterface) {

    }
}

