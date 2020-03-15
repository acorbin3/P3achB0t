package com.p3achb0t.client.managers

import com.p3achb0t.client.Bot
import com.p3achb0t.client.managers.accounts.AccountManager
import com.p3achb0t.client.managers.scripts.LoadAbstractScript
import com.p3achb0t.client.managers.scripts.LoadDebugScripts
import com.p3achb0t.client.managers.tracker.FBDataBase
import com.p3achb0t.client.ui.components.TabManager

class Manager {

    companion object{
        val db : FBDataBase = FBDataBase()

    }

    val accountManager = AccountManager() //This will load in the accounts
    val tabManager = TabManager(this)
    val bots = mutableListOf<Bot>()
    val loadedDebugScripts = LoadDebugScripts()
    val loadedScripts = LoadAbstractScript()

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