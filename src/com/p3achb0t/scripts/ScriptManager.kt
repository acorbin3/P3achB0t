package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.ui.components.TabManager

class ScriptManager private constructor() {

    var scriptThreads = mutableListOf<Thread>()

    private object Holder { val INSTANCE = ScriptManager() }

    companion object {
        val instance: ScriptManager by lazy { Holder.INSTANCE }
    }

    fun start(script: AbstractScript) {



        val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())

        var x = script.javaClass.getAnnotation(ScriptManifest::class.java)
        if(x!=null){
            game.client.category = x?.category
            game.client.name = x?.name
            game.client.author = x?.author
        }

        game.client.script = script

        val thread = Thread({
            while (true) {
                game.client.script?.loop()
            }
        })

        scriptThreads.add(thread)
        thread.start()
    }



    fun stop() {

        val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
        game.client.script = null
        game.client.name = ""
        game.client.category = ""
        game.client.author = ""
        scriptThreads[TabManager.instance.getSelectedIndexx()].stop()
        scriptThreads.removeAt(TabManager.instance.getSelectedIndexx())

    }

    fun pause(id: Int) {

    }
}