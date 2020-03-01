package com.p3achb0t.client.ui.components

import com.p3achb0t.api.Context
import com.p3achb0t.client.managers.Manager
import com.p3achb0t.scripts.TutorialIsland
import com.p3achb0t.scripts_debug.varbitexplorer.VarBitExplorer
import com.p3achb0t.scripts_debug.widgetexplorer.WidgetExplorerV3
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class GameMenu(var manager: Manager) : JMenuBar() {

    init {
        focusTraversalKeysEnabled = true
        add(accountMenu())
        add(debugMenu())
        add(addClientMenu())
    }

    private fun accountMenu() : JMenu {
        val menu = JMenu("Accounts")

        val accountManager = JMenuItem("Manage Accounts")
        accountManager.addActionListener {
            AccountUI(manager.accountManager)
        }

        menu.add(accountManager)

        return menu
    }

    private fun debugMenu() : JMenu {
        val menu = JMenu("Scripts")


        val menuItem = JMenuItem("Run Tutorial Island")
        menuItem.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.setUpScript(TutorialIsland())

        }
        menu.add(menuItem)



        val widgetExplorer = JMenuItem("Open Widget Explorer")
        widgetExplorer.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            //widget exporer should always be the first in the list of debug scripts
            WidgetExplorerV3.createWidgetExplorer(game.client.getScriptManager().debugScripts[0].ctx)
        }

        val varbitExplorer = JMenuItem("Open Varbit Explorer")
        varbitExplorer.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())

            VarBitExplorer(Context(game.client.getScriptManager().client))
        }



        menu.add(widgetExplorer)
        menu.add(varbitExplorer)

        menu.popupMenu.isLightWeightPopupEnabled = false

        return menu
    }

    private fun addClientMenu() : JMenu {
        val menu = JMenu("Client")

        val drawing = JMenuItem("Add")
        drawing.addActionListener {

            manager.tabManager.addInstance()

        }

        val remove = JMenuItem("Remove")
        remove.addActionListener {

            manager.tabManager.removeInstance()

        }

        val start = JMenuItem("Start")
        start.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.start()
        }

        val stop = JMenuItem("Stop")
        stop.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.stop()

        }

        val resume = JMenuItem("Resume")
        resume.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.resume()

        }

        val suspend = JMenuItem("Suspend")
        suspend.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getScriptManager()
            manager.pause()

        }

        val keyboardEnable = JMenuItem("Toggle Keyboard")
        keyboardEnable.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getKeyboard()
            manager.inputBlocked(!manager.inputBlocked())
            println(manager.inputBlocked())

        }

        val mouseDisable= JMenuItem("Toggle Mouse")
        mouseDisable.addActionListener {
            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getMouse()
            manager.inputBlocked(!manager.inputBlocked())
            //println("${manager?.x}, ${manager?.y}")
        }

        val getMouseCoords = JMenuItem("Mouse POS")
        getMouseCoords.addActionListener {
            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            val manager = game.client.getMouse()
            //manager?.inputBlocked(!manager.inputBlocked())
            println("${manager.getX()}, ${manager.getY()}")
        }

        menu.add(remove)
        menu.add(drawing)
        menu.add(start)
        menu.add(stop)
        menu.add(suspend)
        menu.add(resume)
        menu.add(keyboardEnable)
        menu.add(mouseDisable)
        menu.add(getMouseCoords)

        menu.popupMenu.isLightWeightPopupEnabled = false

        return menu
    }

}
