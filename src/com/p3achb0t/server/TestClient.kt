package com.p3achb0t.server

import com.p3achb0t.server.packets.AddBot
import com.p3achb0t.server.packets.Ping
import java.net.Socket

class TestClient {

    private val socket: Socket = Socket("localhost", 7000)

    fun sendPacket() {
        val packet = AddBot().build()
        println("Size: ${packet.size}")
        socket.getOutputStream().write(packet)
        socket.getOutputStream().flush()

        socket.getOutputStream().write(Ping().build())
        socket.getOutputStream().flush()
        //Thread.sleep(50)
    }

}

fun main() {
    val client = TestClient()
    client.sendPacket()
}