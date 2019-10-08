package com.p3achb0t.server.packets

class PacketTypes {

    companion object {
        const val PING: Byte = 0x00

        const val ADD_BOT: Byte = 0x06
        const val REMOVE_BOT: Byte = 0x07

    }
}