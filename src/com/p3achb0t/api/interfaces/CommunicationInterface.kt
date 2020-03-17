package com.p3achb0t.api.interfaces

interface CommunicationInterface {

    fun subscribeChannel(id: String, roomCallback: (String, RoomInterface) -> Unit, callback: (String, String) -> Unit)
    fun unsubscribeChannel(id: String, roomCallback: (String, RoomInterface) -> Unit, callback: (String, String) -> Unit)
}