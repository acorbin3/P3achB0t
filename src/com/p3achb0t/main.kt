package com.p3achb0t

import com.p3achb0t.client.ux.BotManager
import com.p3achb0t.client.ux.lookAndFeel
import com.p3achb0t.client.ux.setup


object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        setup()
        lookAndFeel()
        BotManager()
    }
}
