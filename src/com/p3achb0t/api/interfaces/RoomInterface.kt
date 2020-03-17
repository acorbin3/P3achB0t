package com.p3achb0t.api.interfaces

import com.p3achb0t.api.ChannelInterface1

interface RoomInterface {
    fun subscribe(channelInterface: ChannelInterface1)
    fun unsubscribe(channelInterface: ChannelInterface1)
    fun notifySubscribers(message: String)
}