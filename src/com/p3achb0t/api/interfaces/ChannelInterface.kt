package com.p3achb0t.api.interfaces

interface ChannelInterface {
    fun subscribe(subscriberId: String, channelCallback: (String, String) -> Unit)
    fun unsubscribe(subscriberId: String)
    fun notifySubscribers(message: String)
}