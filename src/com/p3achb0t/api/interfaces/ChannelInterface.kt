package com.p3achb0t.api.interfaces

interface ChannelInterface {
    fun subscribe(channelCallback: (String, String) -> Unit)
    fun unsubscribe(channelCallback: (String, String) -> Unit)
    fun notifySubscribers(message: String)
}