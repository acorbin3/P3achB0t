package com.p3achb0t.client.ui.components

import com.p3achb0t.api.Context
import com.p3achb0t.client.managers.Manager
import com.p3achb0t.client.managers.accounts.Account
import com.p3achb0t.client.new_ui.GlobalStructs
import com.p3achb0t.client.util.JarLoader
import com.p3achb0t.scripts.NullScript
import com.p3achb0t.scripts_debug.WidgetExplorerDebug
import com.p3achb0t.scripts_debug.paint_debug.PaintDebug
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.awt.Dimension
import javax.swing.JTabbedPane
import kotlin.concurrent.thread

class TabManager(var manager: Manager) : JTabbedPane() {

    val clients = mutableListOf<GameTab>()

    init {
        focusTraversalKeysEnabled = true
        size = Dimension(900,800)
        println("This ($this) is a singleton")
        validate()

    }

    fun addInstance(account: Account = Account()) {
        println("RUNNING")
        val gameTab = GameTab(account = account, manager = manager)

        clients.add(gameTab)
        if(JarLoader.proxy.length > 9) {
            addTab(account.script + "-" +JarLoader.proxy.substring(7), gameTab)
        }
        if(JarLoader.proxy.length <= 9) {
            addTab("${account.script}-None", gameTab)
        }

        if (tabCount == 1) {
            selectedIndex = 0
        } else {
            selectedIndex = tabCount-1
        }
        gameTab.revalidate()
        setTabComponentAt(selectedIndex, NewTab(this,account))
        gameTab.revalidate()

        gameTab.client.getScriptManager().ctx = Context(gameTab.client.getScriptManager().client)
        GlobalScope.launch {
            gameTab.client.getScriptManager().ctx.cache.updateCache()
        }

        //Here is a place to add some debug script since the client has been loaded
        gameTab.client.addDebugScript(WidgetExplorerDebug.scriptName)
        gameTab.client.addDebugScript(PaintDebug.scriptName)

        //Load the script based on config
        if(account.script.isNotEmpty()) {
            println("Account: ${account.username} setting script: ${account.script}" )
            //gameTab.client.setScript(manager.loadedScripts.getScript(account.script))
        }else{
            gameTab.client.setScript(NullScript())
        }
    }

    fun removeInstance(id: Int) { // DO SOME CHECKS
        remove(id)
    }

    fun removeInstance() { // DO SOME CHECKS
        val s = selectedIndex
        //TODO - error when tab is not selected but the X button was pressed. It closes the selected tab
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
