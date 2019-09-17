package com.p3achb0t.client.managers

import com.p3achb0t.client.Bot
import com.test.SingleWindow
import com.test.SingleWindow2

class Manager {

    val bots = mutableListOf<Bot>()
    var bot: Bot? = null
    var newWindow: SingleWindow? = null
    fun addBot() {
        bot = Bot(80)
        bots.add(bot!!)
        println("len ${bots.size}")
        newWindow = SingleWindow(bot!!)

    }

    fun changeWindow() {
        newWindow?.destroy()
    }


    fun addGameTab() {

    }

    fun removeGameTab() {

    }

}