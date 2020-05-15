package com.p3achb0t.api.script.communication

import com.p3achb0t.client.communication.sockets.CommunicationClient
import com.p3achb0t.client.communication.sockets.Server
import com.p3achb0t.client.configs.GlobalStructs
import java.net.URI

object Coms {
    private val communicationClient = CommunicationClient(URI("ws://localhost:${GlobalStructs.commPort}"))
    private var server: Server? = null
    private var lastMessage = ""

    init {
//        communicationClient.connect()
//        try {
//            server = Server(GlobalStructs.commPort)
//            addCallback(::broadcast)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            println("ERROR - probably Server is already created")
//        }
    }

    fun addCallback(callback: (String) -> Unit) {
        server?.addCallback(callback)
        communicationClient.addCallback(callback)
    }

    fun sendMessage(message: String) {
        try {
            communicationClient.send(message)
        } catch (e: Exception) {
        e.printStackTrace()
        println("ERROR - probably Server is already created")
    }
    }

    fun broadcast(message: String) {
        println("Sending broadcast back")
        if(lastMessage != message) {
            server?.connections?.forEach {
                it.send("$message")
            }
            lastMessage = message
        }
    }


}