package com.p3achb0t.client.ui.components

import com.p3achb0t.scripts.NullScript
import com.p3achb0t.scripts.WidgetExplorerDebug
import com.p3achb0t.scripts.paint_debug.PaintDebug
import java.awt.Dimension
import javax.swing.JTabbedPane
import kotlin.concurrent.thread

class TabManager private constructor() : JTabbedPane() {

    val clients = mutableListOf<GameTab>()

    init {
        focusTraversalKeysEnabled = true
        size = Dimension(900,800)
        println("This ($this) is a singleton")
        validate()

    }

    private object Holder { val INSTANCE = TabManager() }

    companion object {
        val instance: TabManager by lazy { Holder.INSTANCE }
    }

    fun addInstance() {
        println("RUNNING")
        val gameTab = GameTab()

        clients.add(gameTab)
        addTab("Game ${tabCount+1}", gameTab)

        if (tabCount == 1) {
            selectedIndex = 0
        } else {
            selectedIndex = tabCount-1
        }
        gameTab.revalidate()
        setTabComponentAt(selectedIndex, NewTab(this))
        gameTab.revalidate()

        //Here is a place to add some debug script since the client has been loaded
        gameTab.client.addDebugScript(WidgetExplorerDebug.scriptName)
        gameTab.client.addDebugScript(PaintDebug.scriptName)
        gameTab.client.setScript(NullScript())




    }

    fun removeInstance(id: Int) { // DO SOME CHECKS
        remove(id)
    }

    fun removeInstance() { // DO SOME CHECKS
        val s = selectedIndex
        thread(start = true) {
            println("running from thread(): ${Thread.currentThread()}")
            //clients.get(s).client!!.setApplet()
            clients.get(s).client.getApplet().destroy()
            remove(s)
            clients.removeAt(s)
        }
        if (selectedIndex-1 < 0)
            selectedIndex = 0
        else
            selectedIndex--

    }

    fun getInstance(id: Int): GameTab {
        return clients[id]
    }

    fun getSelected() : GameTab {
        println("Get selected index: $selectedIndex")

            return clients[selectedIndex]
    }

    fun getSelectedIndexx(): Int {
        return selectedIndex
    }

}
