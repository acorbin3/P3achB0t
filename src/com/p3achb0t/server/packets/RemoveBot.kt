package com.p3achb0t.server.packets

class RemoveBot(val bots : Collection<Byte>) : Packet() {
    override fun config(): Byte {
        return 0x0
    }

    override fun type(): Byte {
        return 0x07
    }

    override fun payload(): Collection<Byte> {
        val payload = mutableListOf<Byte>()
        payload.addAll(bots)
        return payload
    }
}