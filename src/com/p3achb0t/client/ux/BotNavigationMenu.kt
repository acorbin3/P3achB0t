package com.p3achb0t.client.ux

import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.scripts.loading.ScriptType
import com.p3achb0t.client.ux.scripts_loader.ScriptLoaderUI
import com.p3achb0t.scripts_debug.widgetexplorer.WidgetExplorerV3
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*

class BotNavigationMenu: JMenuBar() {

    // only because they needs to be refreshed
    val paint = JMenu("Paint")
    val action = JMenu("Action")
    val service = JMenu("Services")
    var scriptLoaderUI: ScriptLoaderUI? = null
    init {
        this.layout
        instanceMenu()
        scriptControl()
        scriptMenu()
        botUltra()
        ioHandle()
        test()
        scriptManagerButtons()
    }

    private fun instanceMenu() {
        val menu = JMenu("Client")

        val add = JMenuItem("Add")
        add.addActionListener {
            BotInstance().initBot()
        }

        val remove = JMenuItem("Remove")
        remove.addActionListener {
            GlobalStructs.botTabBar.KillIndex()
        }

        val reloadScripts = JMenuItem("Reload scripts")
        reloadScripts.addActionListener {
            GlobalStructs.scripts.refresh()
            refreshScriptMenu()
        }

        val ipcHelper = JMenuItem("ipcHelper")
        ipcHelper.addActionListener {
            IpcHelper(GlobalStructs.communication)
        }

        menu.add(add)
        menu.add(remove)
        menu.add(reloadScripts)
        menu.add(ipcHelper)

        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    private fun scriptControl() {

        val menu = JMenu("Script")

        val stop = JMenuItem("Stop")
        stop.addActionListener {
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().stopActionScript()
        }

        val pause = JMenuItem("Pause/Resume")
        pause.addActionListener {
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().pauseActionScript()
        }

        menu.add(stop)
        menu.add(pause)

        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    fun refreshScriptMenu() {

        paint.removeAll()
        action.removeAll()
        service.removeAll()

        for (script in GlobalStructs.scripts.scriptsInformation.values) {
            val currentItem = JMenuItem("${script.name} ${script.version}")
            when (script.type) {
                ScriptType.PaintScript -> {
                    currentItem.addActionListener {
                        GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().togglePaintScript(script.fileName)
                    }
                    paint.add(currentItem)
                }
                ScriptType.ActionScript -> {
                    currentItem.addActionListener {
                        GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().startActionScript(script.fileName)
                    }
                    action.add(currentItem)
                }
                ScriptType.ServiceScript -> {
                    currentItem.addActionListener {
                        GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().toggleServiceScript(script.fileName)
                    }
                    service.add(currentItem)
                }
                else -> {}
            }

        }
    }


    private fun scriptMenu() {
        refreshScriptMenu()

        paint.popupMenu.isLightWeightPopupEnabled = false
        action.popupMenu.isLightWeightPopupEnabled = false
        service.popupMenu.isLightWeightPopupEnabled = false

        add(paint)
        add(action)
        add(service)
    }

    private fun botUltra() {
        val menu = JMenu("Monster Spawner")

        val add = JMenuItem("Kidding just adds 5 tabs XD")
        add.addActionListener {
            for (x in 1..5) {
                val bot = BotInstance()
                bot.initBot()
            }
        }

        menu.add(add)

        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    private fun ioHandle() {
        val menu = JMenu("Input")

        val mouse = JMenuItem("Toggle mouse")
        mouse.addActionListener {
            val f = GlobalStructs.botTabBar.getCurrentIndex().instanceManagerInterface!!.getMouse().inputBlocked()
            GlobalStructs.botTabBar.getCurrentIndex().instanceManagerInterface!!.getMouse().inputBlocked(!f)
        }

        val keyboard = JMenuItem("Toggle Keyboard")
        keyboard.addActionListener {
            val f = GlobalStructs.botTabBar.getCurrentIndex().instanceManagerInterface!!.getKeyboard().inputBlocked()
            GlobalStructs.botTabBar.getCurrentIndex().instanceManagerInterface!!.getKeyboard().inputBlocked(!f)
        }

        menu.add(mouse)
        menu.add(keyboard)

        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    private fun test() {
        val menu = JMenu("Tests")

        val widget = JMenuItem("Widget")
        widget.addActionListener {
            val f = GlobalStructs.botTabBar.getCurrentIndex().instanceManagerInterface!!.getManager().ctx
            WidgetExplorerV3(f!!)
        }

        menu.add(widget)

        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    private fun scriptManagerButtons(){
        //This should be only used as a temporary solution till the bot gets good enough
        //Then the UI will need a proper rework, to allow for better looks
        val startScriptButton = JButton("Start")
        val pauseScriptButton = JButton("Pause")
        val stopScriptButton = JButton("Stop")

        stopScriptButton.addActionListener{
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().stopActionScript()
            pauseScriptButton.text = "Pause"
            startScriptButton.isEnabled = true
            //this.grabFocus() //Weird behaviour with buttons in a Jmenu, so we need to remove the focus
            //this.requestFocus(false)
        }

        pauseScriptButton.addActionListener{
            //TODO Replace isactionscript paused with is script running
            if(GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().isActionScriptPaused) {
                GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().pauseActionScript()
                pauseScriptButton.text = if (GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().isActionScriptPaused) "UnPause" else "Pause"
            }
            //this.grabFocus() //Weird behaviour with buttons in a Jmenu, so we need to remove the focus
            //this.requestFocus(false)
        }


        startScriptButton.addActionListener{
            if(scriptLoaderUI == null){
                scriptLoaderUI = ScriptLoaderUI(startScriptButton)
                scriptLoaderUI?.addWindowListener(object : WindowAdapter() {
                    override fun windowClosed(e: WindowEvent?) {
                        scriptLoaderUI?.disposeAndRemoveReferences()
                        scriptLoaderUI = null
                    }
                })
                val parentWindowLocation = this.locationOnScreen
                scriptLoaderUI?.setLocation(parentWindowLocation.x ,parentWindowLocation.y )
                scriptLoaderUI?.isVisible = true
                scriptLoaderUI?.isAlwaysOnTop = true
            }

            //this.grabFocus() //Weird behaviour with buttons in a Jmenu, so we need to remove the focus
            //this.requestFocus(false)
        }
        add(Box.createHorizontalGlue())
        add(startScriptButton)
        add(Box.createHorizontalStrut(3))
        add(pauseScriptButton)
        add(Box.createHorizontalStrut(3))
        add(stopScriptButton)
        add(Box.createHorizontalStrut(2))
        //Weird behaviour with buttons in a Jmenu, so we need to remove the focus, has to be called twice or it still stays in focus
        for(i in 1..2){
            //this.grabFocus()
            //this.requestFocus(false)
        }
    }
}