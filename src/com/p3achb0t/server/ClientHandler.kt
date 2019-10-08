package com.p3achb0t.server

import com.p3achb0t.client.test.BotWindow
import com.p3achb0t.server.packets.Ping
import java.io.DataInputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket


class ClientHandler(client: Socket, window: BotWindow) {
    private val botWindow = window
    private val handler = PacketHandler()
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
                handler.handle(reader, botWindow)

            } catch (ex: Exception) {
                // TODO: Implement exception handling
                shutdown()
            } finally {

            }

        }
    }

    private fun write(message: ByteArray) {
        writer.write(message)
    }

    private fun shutdown() {
        running = false
        client.close()
        println("${client.inetAddress.hostAddress} closed the connection")
    }

}
