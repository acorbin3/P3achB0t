package com.p3achb0t.server.packets

class AddBot(val bot: Byte = 1.toByte()) : Packet() {
    override fun config(): Byte {
        return 0x0
    }

    override fun type(): Byte {
        return 0x06
    }

    override fun payload(): Collection<Byte> {
        val payload = mutableListOf<Byte>()
        payload.add(bot)
        return payload
    }
}

fun main() {
    val ping = AddBot()

    for(i in ping.build()) {
        print("${i.toUByte()} ")
    }
}