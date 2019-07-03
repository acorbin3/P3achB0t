package com.p3achb0t.api.wrappers

import com.p3achb0t.Main

class Players {
    companion object {
        fun getLocal(): Player {
            return Player(Main.clientData.getLocalPlayer())
        }

    }
}