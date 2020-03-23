package com.p3achb0t.client.ux

import ComScript
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.scripts.loading.ScriptType
import com.p3achb0t.client.test.ComScript2
import com.p3achb0t.rewrite.scripts_debug.widgetexplorer.WidgetExplorerV3
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class BotNavigationMenu: JMenuBar() {

    // only because they needs to be refreshed
    val debug = JMenu("Debug")
    val abstract = JMenu("Abstract")
    val background = JMenu("Background")

    init {
        instanceMenu()
        scriptMenu()
        botUltra()
        ioHandle()
        test()
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

        val startScripts = JMenuItem("Run script")
        startScripts.addActionListener {
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().startScript()

        }

        val stopScripts = JMenuItem("Stop script")
        stopScripts.addActionListener {
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().stopScript()

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
            GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().addDebugScript(ComScript2())
            println("ComScript2")

        }

        val ipcHelper = JMenuItem("ipcHelper")
        ipcHelper.addActionListener {
            IpcHelper(GlobalStructs.communication)
        }

        menu.add(add)
        menu.add(remove)
        menu.add(reloadScripts)
        menu.add(startScripts)
        menu.add(stopScripts)
        menu.add(startBackgroundScript)
        menu.add(addScript)
        menu.add(addScript2)
        menu.add(ipcHelper)
        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    private fun refreshScriptMenu() {

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

    private fun abstractMenu(): JMenu {
        val menu = JMenu("Abstract")

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
            val f = GlobalStructs.botTabBar.getCurrentIndex().instanceManager!!.getMouse().inputBlocked()
            GlobalStructs.botTabBar.getCurrentIndex().instanceManager!!.getMouse().inputBlocked(!f)

        }

        val keyboard = JMenuItem("Toggle Keyboard")
        keyboard.addActionListener {
            val f = GlobalStructs.botTabBar.getCurrentIndex().instanceManager!!.getKeyboard().inputBlocked()
            GlobalStructs.botTabBar.getCurrentIndex().instanceManager!!.getKeyboard().inputBlocked(!f)

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
            val f = GlobalStructs.botTabBar.getCurrentIndex().instanceManager!!.getManager().ctx
           WidgetExplorerV3(f!!)

        }

        menu.add(widget)

        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }
}