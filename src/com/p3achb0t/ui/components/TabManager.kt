package com.p3achb0t.ui.components

import com.p3achb0t.ui.Keyboard
import com.p3achb0t.ui.Mouse
import javax.swing.JComponent
import javax.swing.JTabbedPane

class TabManager private constructor() : JTabbedPane() {

    val clients = mutableListOf<GamePanel>()

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
        
        if (tabCount == 1) {
            selectedIndex = 0
        } else {
            selectedIndex = tabCount-1
        }

        setTabComponentAt(selectedIndex, NewTab(this))
        g.validate()
        validate() // very important
        g.run()

        Thread.sleep(500)

        // setup mouse and keyboard under here <---------------
        val keyboard = Keyboard(g.client.getApplet().getComponent(0))
        g.client.getApplet().addKeyListener(keyboard)
        g.client.keyboard = keyboard
        val mouse = Mouse(g.client.getApplet().getComponent(0))
        g.client.getApplet().addMouseListener(mouse)



    }

    fun removeInstance(id: Int) { // DO SOME CHECKS
        remove(id)
    }

    fun removeInstance() { // DO SOME CHECKS
        //clients.get(selectedIndex).client.getApplet().destroy()
        //clients.removeAt(selectedIndex)
        //remove(selectedIndex)

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

    fun getSelected2(hash: Int) : GamePanel {
        println("Get selected index2: $selectedIndex hash: $hash")

        return clients[selectedIndex]
    }
}
