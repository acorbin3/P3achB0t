package com.p3achb0t.server

import com.p3achb0t.client.test.BotWindow
import com.p3achb0t.server.packets.PacketTypes
import com.p3achb0t.server.packets.Ping
import java.io.DataInputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import kotlin.experimental.and


class ClientHandler(client: Socket, window: BotWindow) {
    private val botWindow = window
    private val client: Socket = client
    private val reader: DataInputStream = DataInputStream(client.getInputStream())
    private val writer: OutputStream = client.getOutputStream()
    private var running: Boolean = false

    fun run() {
        running = true
        // Welcome message
        //write(Ping().build())
        while (running) {
            try {

                //if (text[0] == 0.toByte()){
                //    shutdown()
                //    continue
                //}
                handle()

            } catch (ex: Exception) {
                // TODO: Implement exception handling
                shutdown()
            } finally {

            }

        }
    }

    private fun write(message: ByteArray) {
        writer.write(message)
        writer.flush()
    }

    private fun shutdown() {
        running = false
        client.close()
        println("${client.inetAddress.hostAddress} closed the connection")
    }

    fun handle() {


        val type = reader.readByte()
        val config = reader.readByte()
        print("$type $config ")
        val bb = decodeByteInteger(reader)

        when (type) {
            PacketTypes.PING -> handelPing(bb)
            PacketTypes.ADD_BOT -> handleAddBot(bb)
            PacketTypes.REMOVE_BOT -> handlerRemoveBot(bb)
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

    private fun handelPing(bytes: ByteArray) {

    }

    private fun handleAddBot(bytes: ByteArray) {
        for (i in 1..bytes[0])
            botWindow.manager.tabManager.create()
    }

    private fun handlerRemoveBot(bytes: ByteArray) {
        if (bytes.size > 1) {
            botWindow.manager.tabManager.destroy(bytes)
        } else {
            botWindow.manager.tabManager.destroy(bytes[0].toInt())
        }
    }

}
