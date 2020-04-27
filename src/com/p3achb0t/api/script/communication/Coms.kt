package com.p3achb0t.api.script.communication

import com.p3achb0t.client.communication.sockets.CommunicationClient
import com.p3achb0t.client.communication.sockets.Server
import com.p3achb0t.client.configs.GlobalStructs
import java.net.URI

object Coms {
    private val communicationClient = CommunicationClient(URI("ws://localhost:${GlobalStructs.commPort}"))
    val callbacks = ArrayList<(String) -> Unit>()
    private var server: Server? = null


    init {
        communicationClient.connect()
        try {
            callbacks.add(::broadcast)
            server = Server(GlobalStructs.commPort, callbacks)
        } catch (e: Exception) {
            e.printStackTrace()
            println("ERROR - probably Server is already created")
        }
    }

    fun addCallback(callback: (String) -> Unit) {
        server?.addCallback(callback)
    }

    fun sendMessage(message: String) {
        communicationClient.send(message)
    }

    private fun broadcast(message: String) {
        println("Sending broadcast back")
        server?.connections?.forEach {
            it.send("$message")
        }
    }
}