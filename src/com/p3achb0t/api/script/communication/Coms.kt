package com.p3achb0t.api.script.communication

import com.p3achb0t.client.communication.sockets.CommunicationClient
import com.p3achb0t.client.configs.GlobalStructs
import java.net.URI

object Coms {
    private val communicationClient = CommunicationClient(URI("ws://localhost:${GlobalStructs.commPort}"))


    init {
        communicationClient.connect()
    }


    fun sendMessage(message: String) {
        communicationClient.send(message)
    }
}