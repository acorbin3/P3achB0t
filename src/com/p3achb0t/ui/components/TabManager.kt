package com.p3achb0t.ui.components

import com.p3achb0t.ui.Keyboard
import com.p3achb0t.ui.Mouse
import javax.swing.JTabbedPane

class TabManager private constructor() : JTabbedPane() {

    private val clients = mutableListOf<GamePanel>()

    init {
        println("This ($this) is a singleton")
    }

    private object Holder { val INSTANCE = TabManager() }

    companion object {
        val instance: TabManager by lazy { Holder.INSTANCE }
    }

    fun addInstance() {

        val g = GamePanel()
        clients.add(g)
        addTab("Game", g)
        g.validate()
        g.setContext()
        // setup mouse and keyboard under here <---------------
        Thread.sleep(500)
        val keyboard = Keyboard(g.client.getApplet().getComponent(0))
        g.client.getApplet().addKeyListener(keyboard)
        g.client.keyboard = keyboard
        val mouse = Mouse(g.client.getApplet().getComponent(0))
        g.client.getApplet().addMouseListener(mouse)

/*
        Thread({
            var i = 0
            while (true) {
                println("${g.client.getApplet().componentCount}")
                Thread.sleep(1000)
                mouse.click(400, 290, true)
                if (i++ < 5) {
                    keyboard.sendKeys("Kasp", false)
                    //mouse.click(100, 100, true)
                }


            }
        }).run()*/
    }

    fun removeInstance(id: Int) { // DO SOME CHECKS
        remove(id)
    }

    fun getInstance(id: Int): GamePanel {
        return clients[id]
    }
}
