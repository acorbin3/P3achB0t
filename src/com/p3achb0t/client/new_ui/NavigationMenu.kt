package com.p3achb0t.client.new_ui

import com.p3achb0t.client.managers.scripts.ScriptInformation
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class NavigationMenu: JMenuBar() {

    init {
        add(testMenu())
        add(debugMenu())
        add(botUltra())
    }

    private fun testMenu(): JMenu {
        val menu = JMenu("Client")

        val add = JMenuItem("Add")
        add.addActionListener {
            BotInstance().initBot()
        }

        val remove = JMenuItem("Remove")
        remove.addActionListener {
            GlobalStructs.botTabBar.KillIndex()
        }

        menu.add(add)
        menu.add(remove)
        menu.popupMenu.isLightWeightPopupEnabled = false
        return menu
    }

    var isMouseDebug = false
    private fun debugMenu(): JMenu {
        val menu = JMenu("Debug")

        for (dscript in GlobalStructs.loadDebugScripts.debugScripts.keys) {

            val mouseDebug = JMenuItem(GlobalStructs.loadDebugScripts.debugScripts[dscript]!!.name)
            mouseDebug.addActionListener {
                GlobalStructs.botTabBar.getCurrentIndex().getInstanceManager().toggleDebugScript(dscript)
            }
            menu.add(mouseDebug)
        }

        menu.popupMenu.isLightWeightPopupEnabled = false
        return menu
    }

    private fun botUltra(): JMenu {
        val menu = JMenu("Ultra")

        val add = JMenuItem("Add")
        add.addActionListener {
            for (x in 0..5) {
                val bot = BotInstance()
                bot.initBot()
                bot.getInstanceManager().addDebugScript("SexyMouse")
            }
        }

        menu.add(add)

        menu.popupMenu.isLightWeightPopupEnabled = false
        return menu
    }
}