package com.p3achb0t.client.new_ui

import com.p3achb0t.client.managers.scripts.ScriptType
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class NavigationMenu: JMenuBar() {

    // only because they needs to be refreshed
    val debug = JMenu("Debug")
    val abstract = JMenu("Abstract")
    val background = JMenu("Background")

    init {
        instanceMenu()
        scriptMenu()
        botUltra()
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
            GlobalStructs.loadDebugScripts.refresh()
            refreshScriptMenu()

        }

        menu.add(add)
        menu.add(remove)
        menu.add(reloadScripts)
        menu.popupMenu.isLightWeightPopupEnabled = false
        add(menu)
    }

    private fun refreshScriptMenu() {

        debug.removeAll()
        abstract.removeAll()
        background.removeAll()


        for (script in GlobalStructs.loadDebugScripts.scripts.values) {
            val currentItem = JMenuItem("${script.name} ${script.version}")
            if (script.type == ScriptType.DebugScript) {
                currentItem.addActionListener {
                    GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().toggleDebugScript(script.fileName)
                }
                debug.add(currentItem)
            } else if (script.type == ScriptType.AbstractScript) {
                currentItem.addActionListener {
                    GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().toggleDebugScript(script.fileName)
                }
                abstract.add(currentItem)
            } else if (script.type == ScriptType.BackgroundScript) {
                currentItem.addActionListener {
                    GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().toggleDebugScript(script.fileName)
                }
                background.add(currentItem)
            }

        }
    }


    private fun scriptMenu() {


        refreshScriptMenu()

        debug.popupMenu.isLightWeightPopupEnabled = false
        add(debug)
        add(abstract)
        add(background)


    }

    private fun backgroundMenu(): JMenu {
        val menu = JMenu("Background")

        for (dscript in GlobalStructs.loadDebugScripts.scripts.keys) {

            val mouseDebug = JMenuItem(GlobalStructs.loadDebugScripts.scripts[dscript]!!.name)
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

        for (dscript in GlobalStructs.loadDebugScripts.scripts.keys) {

            val mouseDebug = JMenuItem(GlobalStructs.loadDebugScripts.scripts[dscript]!!.name)
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
}