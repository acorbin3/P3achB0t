package com.p3achb0t.api.wrappers

class Players(val client: com.p3achb0t._runestar_interfaces.Client) {
    fun getLocal(): Player {
        return Player(client.getLocalPlayer(), client)
    }
}