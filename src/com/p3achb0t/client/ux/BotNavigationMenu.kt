package com.p3achb0t.client.ux

import ComScript
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.scripts.loading.ScriptType
import com.p3achb0t.client.test.ComScript2
import com.p3achb0t.scripts.scripts_loader.ScriptLoaderUI
import com.p3achb0t.scripts_debug.widgetexplorer.WidgetExplorerV3
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*

class BotNavigationMenu: JMenuBar() {

    // only because they needs to be refreshed
    val debug = JMenu("Debug")
    val abstract = JMenu("Abstract")
    val background = JMenu("Background")
    var scriptLoaderUI: ScriptLoaderUI? = null
    init {
        instanceMenu()
        scriptMenu()
        botUltra()
//        updateAbstractMenu()
//        updateDebugMenu()
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

        val startBackgroundScript = JMenuItem("Start background")
        startBackgroundScript.addActionListener {
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().runBackgroundScripts()

        }

        val addScript = JMenuItem("Add ComScript")
        addScript.addActionListener {
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addAbstractScript(ComScript())
            println("ComScript")

        }
        val addScript2 = JMenuItem("Add ComScript2")
        addScript2.addActionListener {
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addDebugScript("comScript2", ComScript2())
            println("ComScript2")

        }

        val ipcHelper = JMenuItem("ipcHelper")
        ipcHelper.addActionListener {
            IpcHelper(GlobalStructs.communication)
        }

        menu.add(add)
        menu.add(remove)
        menu.add(reloadScripts)
        menu.add(startBackgroundScript)
        menu.add(addScript)
        menu.add(addScript2)
        menu.add(ipcHelper)
        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    fun refreshScriptMenu() {

        debug.removeAll()
        abstract.removeAll()
        background.removeAll()


        for (script in GlobalStructs.scripts.scripts.values) {
            val currentItem = JMenuItem("${script.name} ${script.version}")
            if (script.type == ScriptType.DebugScript) {
                currentItem.addActionListener {
                    GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().toggleDebugScript(script.fileName)
                }
                debug.add(currentItem)
            } else if (script.type == ScriptType.AbstractScript) {
                currentItem.addActionListener {
                    println(script.fileName)
                    GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addAbstractScript(script.fileName)
                }
                abstract.add(currentItem)
            } else if (script.type == ScriptType.BackgroundScript) {
                currentItem.addActionListener {
                    GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addBackgroundScript(script.fileName)
                }
                background.add(currentItem)
            }

        }
    }


    private fun scriptMenu() {


        refreshScriptMenu()

        debug.popupMenu.isLightWeightPopupEnabled = false
        abstract.popupMenu.isLightWeightPopupEnabled = false
        background.popupMenu.isLightWeightPopupEnabled = false

        add(debug)
        add(abstract)
        add(background)


    }

    private fun backgroundMenu(): JMenu {
        val menu = JMenu("Background")

        for (dscript in GlobalStructs.scripts.scripts.keys) {

            val mouseDebug = JMenuItem(GlobalStructs.scripts.scripts[dscript]!!.name)
            mouseDebug.addActionListener {
                println(dscript)
                GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().toggleDebugScript(dscript)
            }
            menu.add(mouseDebug)
        }

        menu.popupMenu.isLightWeightPopupEnabled = false
        return menu
    }
    /*
        private fun updateDebugMenu(){
            val classes: ArrayList<Class<*>> = ArrayList()
            classes.addAll(findAllDebugClasses("com/p3achb0t/scripts_debug"))
            classes.forEach { clazz ->
                val scriptName = clazz.name.split(".").last()
                if(scriptName.contains("PaintDebug")){
                    GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addDebugScript(scriptName, clazz.newInstance() as DebugScript)
                }else {
                    println("Adding menu Item: $scriptName")
                    val menuItem1 = JMenuItem("$scriptName")
                    val localClazz = clazz
                    menuItem1.addActionListener {

                        GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addDebugScript(scriptName, localClazz.newInstance() as DebugScript)
                    }
                    debug.add(menuItem1)
                }
            }
        }
    */
    private fun botUltra() {
        val menu = JMenu("Ultra")

        val add = JMenuItem("Add")
        add.addActionListener {
            for (x in 0..5) {
                val bot = BotInstance()
                bot.initBot()
                bot.getInstanceManager().addDebugScript("SexyMouse.jar")
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
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().stopScript()
            pauseScriptButton.text = "Pause"
            startScriptButton.isEnabled = true
            this.grabFocus() //Weird behaviour with buttons in a Jmenu, so we need to remove the focus
        }

        pauseScriptButton.addActionListener{
            if(GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().isScriptRunning) {
                GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().pauseScript()
                pauseScriptButton.text = if (GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().isPaused()) "UnPause" else "Pause"
            }
            this.grabFocus() //Weird behaviour with buttons in a Jmenu, so we need to remove the focus
        }


        startScriptButton.addActionListener{
            if(scriptLoaderUI == null){
                scriptLoaderUI = ScriptLoaderUI(startScriptButton)
                scriptLoaderUI?.addWindowListener(object : WindowAdapter() {
                    override fun windowClosing(e: WindowEvent?) {
                        scriptLoaderUI?.DisposeAndRemoveReferences()
                        scriptLoaderUI = null
                    }
                })
                val parentWindowLocation = this.locationOnScreen
                scriptLoaderUI?.setLocation(parentWindowLocation.x ,parentWindowLocation.y )
                scriptLoaderUI?.isVisible = true
                scriptLoaderUI?.isAlwaysOnTop = true
            }

            this.grabFocus() //Weird behaviour with buttons in a Jmenu, so we need to remove the focus
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
            this.grabFocus()
        }
    }
}