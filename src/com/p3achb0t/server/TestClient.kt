package com.p3achb0t.server

import com.p3achb0t.server.packets.AddBot
import com.p3achb0t.server.packets.Ping
import com.p3achb0t.server.packets.RemoveBot
import java.net.Socket

class TestClient {

    private val socket: Socket = Socket("localhost", 7000)

    fun sendPacket() {
        //val packet = AddBot(8).build()
        //println("Size: ${packet.size}")
        //socket.getOutputStream().write(packet)
        //socket.getOutputStream().flush()
        //Thread.sleep(10000)
        val packetRemoveBot = RemoveBot(mutableListOf(5,6)).build()
        println("Size: ${packetRemoveBot.size}")
        socket.getOutputStream().write(packetRemoveBot)
        socket.getOutputStream().flush()
//
        socket.getOutputStream().write(Ping().build())
        socket.getOutputStream().flush()
        //Thread.sleep(50)
    }

}

fun main() {
    val client = TestClient()
    client.sendPacket()
}