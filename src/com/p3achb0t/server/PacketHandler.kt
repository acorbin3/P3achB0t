package com.p3achb0t.server

import com.p3achb0t.client.test.BotWindow
import com.p3achb0t.server.packets.PacketTypes
import java.io.DataInputStream
import kotlin.experimental.and

class PacketHandler {

    init {

    }

    fun handle(bytes: DataInputStream, window: BotWindow) {


        val type = bytes.readByte()
        val config = bytes.readByte()
        print("$type $config ")
        val bb = decodeByteInteger(bytes)

        for (i in bb) {
            print("$i ")
        }

        when (type) {
            PacketTypes.PING -> handelPing()
            PacketTypes.ADD_BOT -> handleAddBot(window)
            else -> {

            }
        }

        println()
    }


    private fun decodeByteInteger(array: DataInputStream) : ByteArray {
        var multiplier = 1

        var value = 0
        var encodedByte: Byte

        do {
            encodedByte = array.readByte()

            value += (encodedByte and 127.toByte()) * multiplier

            if (multiplier > 128*128*128) {
                throw Error("Malformed Variable Byte Integer")
            }

            multiplier *= 128

        } while ((encodedByte and 128.toByte()) != 0.toByte())

        val b = ByteArray(value)

        array.read(b)

        return b
    }

    private fun handelPing() {

    }

    private fun handleAddBot(window: BotWindow) {
//        window.manager.tabManager.create()
    }
}