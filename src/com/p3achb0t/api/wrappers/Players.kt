package com.p3achb0t.api.wrappers

import com.p3achb0t.api.user_inputs.Mouse

class Players(val client: com.p3achb0t._runestar_interfaces.Client, val mouse: Mouse?= null) {
    fun getLocal(): Player {
        return Player(client.getLocalPlayer(), client, mouse)
    }
}