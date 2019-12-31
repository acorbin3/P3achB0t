package com.p3achb0t.client.managers

import com.p3achb0t.client.Bot
import com.p3achb0t.client.managers.accounts.AccountManager
import com.p3achb0t.client.managers.tabs.TabManager
import com.p3achb0t.interfaces.ScriptManager
import com.test.SingleWindow
import java.util.*
import kotlin.collections.ArrayList

class Manager {

    val accountManager = AccountManager()
    val tabManager = TabManager(this)
    val scriptManager = com.p3achb0t.client.managers.scripts.ScriptManager(this)
    val bots = mutableListOf<Bot>()

    init {

    }

    fun addBot() {
        val bot = Bot(80)
        bots.add(bot)
        tabManager.display(bot)
    }

    fun printBots() {
        for (bot in bots) {
            println("Bot id: ${bot.id}")
        }
    }



    fun removeBot() {

    }

    fun startScript() {

    }


    // Test
    val bots_old = mutableListOf<Bot>()
    var bot_old: Bot? = null
    var newWindow_old: SingleWindow? = null

    fun changeWindow() {
        newWindow_old?.destroy()
    }

    fun addBot_old() {
        UUID.randomUUID().toString()
        bot_old = Bot(80)
        bots_old.add(bot_old!!)
        println("len ${bots_old.size}")
        newWindow_old = SingleWindow(bot_old!!)

    }

}