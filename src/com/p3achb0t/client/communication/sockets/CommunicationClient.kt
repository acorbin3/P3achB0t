package com.p3achb0t.client.communication.sockets

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.*

class CommunicationClient(serverUri: URI?) : WebSocketClient(serverUri) {
    var callbacks: ArrayList<(String) -> Unit> = ArrayList()

    fun addCallback(callback: (String) -> Unit) {
        callbacks.add(callback)
    }

    override fun onOpen(p0: ServerHandshake?) {
        println("Client onOpen")
    }

    override fun onClose(p0: Int, p1: String?, p2: Boolean) {
        println("Client onClose: $p0")
    }

    override fun onMessage(p0: String?) {
        println("Client recieved message: $p0")
        callbacks.forEach {
            it("$p0")
        }
    }

    override fun onError(p0: Exception?) {
        println("Client onError: $p0")
    }
}