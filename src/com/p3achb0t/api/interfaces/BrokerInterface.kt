package com.p3achb0t.api.interfaces

interface BrokerInterface {

    fun subscribeChannel(id: String, roomCallback: (id: String, message: ChannelInterface) -> Unit, uuid: String, callback: (id: String, message: String) -> Unit)
    fun unsubscribeChannel(id: String, uuid: String)
}