package com.p3achb0t.client.managers

import com.p3achb0t.client.Bot
import com.p3achb0t.client.managers.accounts.AccountManager
import com.p3achb0t.client.managers.tabs.TabManager
import com.test.SingleWindow
import java.util.*
import kotlin.collections.ArrayList

class Manager {

    val accountManager = AccountManager()
    val tabManager = TabManager(this)
    val bots = mutableMapOf<String, Bot>()

    init {

    }

    fun addBot() {
        val uuid = UUID.randomUUID().toString()
        bots[uuid] = Bot(80)
        tabManager.display(uuid, bots[uuid]!!)

    }

    fun getBots(): List<Bot> {
        return ArrayList(bots.values)
    }

    fun printBots() {
        for (bot in bots.keys) {
            println("Bot id: $bot")
        }
    }



    fun removeBot() {

    }

    fun startScript() {

    }

    fun detach() {

    }

    fun attach() {

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