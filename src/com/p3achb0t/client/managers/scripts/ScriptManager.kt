package com.p3achb0t.client.managers.scripts

import com.p3achb0t.client.managers.Manager

class ScriptManager(val manager: Manager) {

    fun setScript(id: Int) {
        val bot = manager.tabManager.lookupBot(id)
        //bot.setScript()
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

}