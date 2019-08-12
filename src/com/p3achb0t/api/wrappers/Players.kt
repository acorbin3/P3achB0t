package com.p3achb0t.api.wrappers

import com.p3achb0t.MainApplet

class Players {
    companion object {
        fun getLocal(): Player {
            return Player(MainApplet.clientData.getLocalPlayer())
        }

    }
}