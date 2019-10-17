package com.p3achb0t.server.packets

class Ok : Packet() {
    override fun config(): Byte {
        return 0x00
    }

    override fun type(): Byte {
        return 0x00
    }

    override fun payload(): Collection<Byte> {
        return emptyList()
    }

}