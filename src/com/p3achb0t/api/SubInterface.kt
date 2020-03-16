package com.p3achb0t.api

import com.p3achb0t.client.communication.Room

interface SubInterface {
    fun setChannel(id: String, room: Room)
}