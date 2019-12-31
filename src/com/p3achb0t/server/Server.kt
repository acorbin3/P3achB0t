package com.p3achb0t.server

import com.p3achb0t.client.test.BotWindow
import java.net.ServerSocket
import kotlin.concurrent.thread

class Server {

    private val tabBotWindow = BotWindow()

    fun start() {
        val server = ServerSocket(7000)
        println("Server is running on port ${server.localPort}")

        while (true) {
            val client = server.accept()
            println("Client connected: ${client.inetAddress.hostAddress}")
            // Run client in it's own thread.
            thread { ClientHandler(client, tabBotWindow).run() }
        }
    }
}

fun main() {
    val server = Server()
    server.start()
}