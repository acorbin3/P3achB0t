package com.p3achb0t.client.managers.scripts

import com.p3achb0t.client.managers.Manager

class ScriptManager(val manager: Manager) {

    val loadScripts = LoadScripts()

    fun setScript(id: Int, name: String) {
        val bot = manager.tabManager.lookupBot(id)
        bot.setScript(loadScripts.getScript(name))
    }

    fun getScript(id: Int) {
        val bot = manager.tabManager.lookupBot(id)
        bot.getScript()
    }

    fun start(id: Int) {
        val bot = manager.tabManager.lookupBot(id)
        bot.startScript()
    }

    fun stop(id: Int) {
        val bot = manager.tabManager.lookupBot(id)
        bot.stopScript()
    }

    fun pause(id: Int) {
        val bot = manager.tabManager.lookupBot(id)
        bot.pauseScript()
    }

    fun resume(id: Int) {
        val bot = manager.tabManager.lookupBot(id)
        bot.resumeScript()
    }

    fun refreshScripts() {
        loadScripts.refresh()
    }

}