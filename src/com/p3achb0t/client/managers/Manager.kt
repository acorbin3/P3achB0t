package com.p3achb0t.client.managers

import com.p3achb0t.client.Bot
import com.p3achb0t.client.managers.accounts.AccountManager
import com.p3achb0t.client.managers.scripts.LoadDebugScripts
import com.p3achb0t.client.managers.scripts.LoadScripts
//import com.p3achb0t.client.managers.tabs.BotPane
import com.p3achb0t.client.ui.components.TabManager

class Manager {


    val accountManager = AccountManager() //This will load in the accounts
    val tabManager = TabManager(this)
    val bots = mutableListOf<Bot>()
    val loadedDebugScripts = LoadDebugScripts()
    val loadedScripts = LoadScripts()

    init {
    }

    fun addBot() {
        tabManager.addInstance()
    }

    fun printBots() {
        for (bot in bots) {
            println("Bot id: ${bot.id}")
        }
    }

}