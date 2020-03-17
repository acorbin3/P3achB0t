package com.p3achb0t.api.interfaces

import com.p3achb0t.api.ChannelInterface1
import com.p3achb0t.api.SubInterface

interface CommunicationInterface {

    fun subscribeChannel(id: String, channelInterface: SubInterface, callback: ChannelInterface1)
    fun unsubscribeChannel(id: String, channelInterface: SubInterface)
}