package com.p3achb0t.api.newApi

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.Player

class Players(val client: Client, val mouse: Mouse) {
    fun getLocal(): Player {
        return Player(client.getLocalPlayer(), client, mouse)
    }
}