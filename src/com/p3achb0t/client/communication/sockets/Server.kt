package com.p3achb0t.client.communication.sockets

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Thread.sleep
import java.net.InetSocketAddress
import java.net.URI


class Server(port: Int) : WebSocketServer(InetSocketAddress(port)) {
    var callbacks: ArrayList<(String) -> Unit> = ArrayList()

    init {
        this.start()
    }

    fun addCallback(callback: (String) -> Unit) {
        callbacks.add(callback)
    }

    override fun onOpen(p0: WebSocket?, p1: ClientHandshake?) {
        println("Server onOpen")
    }

    override fun onClose(p0: WebSocket?, p1: Int, p2: String?, p3: Boolean) {
        println("onClose")
    }

    override fun onMessage(socket: WebSocket?, message: String?) {
        println("Server onMessage $message")
        callbacks.forEach {
            it("$message")
        }
    }

    override fun onStart() {

    }

    override fun onError(p0: WebSocket?, p1: Exception?) {
        println("onError")
    }


}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val port = 8274
        val server = Server(port)
        sleep(1000)

        val client = CommunicationClient(URI("ws://localhost:$port"))
        client.connect()
        sleep(1000)
        client.send("Hello world")

    }
}