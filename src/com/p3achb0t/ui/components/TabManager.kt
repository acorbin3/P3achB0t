package com.p3achb0t.ui.components

import com.p3achb0t.ui.Keyboard
import com.p3achb0t.ui.Mouse
import javax.swing.JTabbedPane

class TabManager private constructor() : JTabbedPane() {

    private val clients = mutableListOf<GamePanel>()
    var index = 0

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
        addTab("Game ${tabCount+1}", g)
        g.validate()
        g.setContext()
        Thread.sleep(500)
        // setup mouse and keyboard under here <---------------
        val keyboard = Keyboard(g.client.getApplet().getComponent(0))
        g.client.getApplet().addKeyListener(keyboard)
        g.client.keyboard = keyboard
        val mouse = Mouse(g.client.getApplet().getComponent(0))
        g.client.getApplet().addMouseListener(mouse)
        selectedIndex = index
        index++



    }

    fun removeInstance(id: Int) { // DO SOME CHECKS
        remove(id)
    }

    fun getInstance(id: Int): GamePanel {
        return clients[id]
    }

    fun getSelected() : GamePanel {
        println("Get selected index: $selectedIndex")
        return clients[selectedIndex]
    }

    fun getSelectedIndexx(): Int {
        return selectedIndex
    }

    fun getSelected2() : GamePanel {
        println("Get selected index2: $selectedIndex")
        return clients[index]
    }
}
