package com.p3achb0t.client.ui.components

import com.p3achb0t.analyser.ScriptClasses
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.Context
import com.p3achb0t.client.managers.Manager
import com.p3achb0t.scripts_debug.varbitexplorer.VarBitExplorer
import com.p3achb0t.scripts_debug.widgetexplorer.WidgetExplorerV3
import java.util.*
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class GameMenu(var manager: Manager) : JMenuBar() {

    init {
        focusTraversalKeysEnabled = true
        add(accountMenu())
        add(debugMenu())
        add(scriptsMenu())
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


    private fun scriptsMenu(): JMenu{
        val menu = JMenu("Scripts")

        //////////////Adding private scripts///////////////////
        addScriptsMenus("com/p3achb0t/scripts_private", menu)
        addScriptsMenus("com/p3achb0t/scripts", menu)
        menu.popupMenu.isLightWeightPopupEnabled = false
        return menu
    }

    private fun addScriptsMenus(scriptsPath: String, menu: JMenu) {
        val classes: ArrayList<Class<*>> = ArrayList()

        classes.addAll(ScriptClasses.findAllClasses(scriptsPath))
        //        println("Classes: ")
        classes.forEach { clazz ->
            val nameSplit = clazz.name.split(".")
            val name = nameSplit[nameSplit.size - 2]
            val menuItem1 = JMenuItem("Run $name")
            println("Adding menu Item: $name")
            menuItem1.addActionListener {

                val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
                val manager = game.client.getScriptManager()
                manager.setUpScript(clazz.newInstance() as AbstractScript)

            }
            menu.add(menuItem1)

        }
    }

    private fun debugMenu() : JMenu {
        val menu = JMenu("Debug")



        val widgetExplorer = JMenuItem("Open Widget Explorer")
        widgetExplorer.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
            //widget exporer should always be the first in the list of debug scripts
            WidgetExplorerV3.createWidgetExplorer(game.client.getScriptManager().debugScripts[0].ctx)
        }
        menu.add(widgetExplorer)

        val varbitExplorer = JMenuItem("Open Varbit Explorer")
        varbitExplorer.addActionListener {

            val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())

            VarBitExplorer(Context(game.client.getScriptManager().client))
        }
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
