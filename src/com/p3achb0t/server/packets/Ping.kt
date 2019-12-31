package com.p3achb0t.server.packets

class Ping : Packet() {

    override fun config(): Byte {
        return 0x0
    }

    override fun type(): Byte {
        return 0x01
    }

    override fun payload(): Collection<Byte> {
        return mutableListOf()
    }
}

fun main() {
    val ping = Ping()

    for(i in ping.build()) {
        print("${i.toUByte()} ")
    }
}