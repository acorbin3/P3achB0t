package com.p3achb0t.client.communication.peer

class PeerClient {

    // TODO add active channels from broker

    companion object {
        fun callback(id: String, message: String) {
            println("From PeerClient callback --> $id, $message")
        }
    }

}