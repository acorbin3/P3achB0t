package com.p3achb0t.client.ui.components

import com.p3achb0t.client.managers.Manager
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class BotMenu(val manager: Manager): JMenuBar()  {

    init {

        focusTraversalKeysEnabled = true
        add(accountMenu())
        add(testMenu())

    }

    private fun accountMenu() : JMenu {
        val menu = JMenu("Bots")

        val accountManager = JMenuItem("Add BOT")
        accountManager.addActionListener {
            manager.addBot()
        }

        menu.add(accountManager)

        menu.popupMenu.isLightWeightPopupEnabled = false
        return menu
    }

    private fun testMenu() : JMenu {
        val menu = JMenu("Test")

        val test1 = JMenuItem("Detach bot")
        test1.addActionListener {
            val selectedBot = manager.tabManager.getSelectedTabBot()
            manager.tabManager.detach(selectedBot.bot)
        }

        val test2 = JMenuItem("Remove")
        test2.addActionListener {
            manager.tabManager.destroy("sdertegr")
            //println("$count")
        }

        val test3 = JMenuItem("Resize to 900x800")
        test3.addActionListener {
            manager.tabManager.resizeView(900,800)
        }

        val test4 = JMenuItem("Running bots")
        test4.addActionListener {
            manager.printBots()
        }

        menu.add(test1)
        menu.add(test2)
        menu.add(test3)
        menu.add(test4)

        menu.popupMenu.isLightWeightPopupEnabled = false
        return menu
    }
}