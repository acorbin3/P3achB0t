package com.p3achb0t.api.wrappers


class Players {
    companion object {
        fun getLocal(): Player {
            return Player(Client.client.getLocalPlayer())
        }

    }
}