package com.p3achb0t.api.interfaces

interface BrokerInterface {

    fun subscribeChannel(id: String, roomCallback: (String, ChannelInterface) -> Unit, callback: (String, String) -> Unit)
    fun unsubscribeChannel(id: String, roomCallback: (String, ChannelInterface) -> Unit, callback: (String, String) -> Unit)
}