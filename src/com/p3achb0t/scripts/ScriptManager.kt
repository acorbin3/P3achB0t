package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.client.ui.components.TabManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ScriptManager private constructor() {

    var scriptThreads = mutableListOf<Job>()

    private object Holder { val INSTANCE = ScriptManager() }

    companion object {
        val instance: ScriptManager by lazy { Holder.INSTANCE }
    }

    fun start(script: AbstractScript) {



        val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())

        val x = script.javaClass.getAnnotation(ScriptManifest::class.java)
        if(x!=null){
            game.client!!.category = x.category
            game.client!!.name = x.name
            game.client!!.author = x.author
        }

        game.client!!.script = script

        val job = GlobalScope.launch {
            game.client!!.script?.start()
            while (true) {
                game.client!!.script?.loop()
            }
        }

        scriptThreads.add(job)
    }



    fun stop() {

        val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
        game.client!!.script = null
        game.client!!.name = ""
        game.client!!.category = ""
        game.client!!.author = ""
        //TODO - need to appropriately call the script.stop method
        scriptThreads[TabManager.instance.getSelectedIndexx()].cancel()
        scriptThreads.removeAt(TabManager.instance.getSelectedIndexx())

    }

    fun pause(id: Int) {

    }
}