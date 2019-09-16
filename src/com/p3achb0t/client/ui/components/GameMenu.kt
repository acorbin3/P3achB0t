package com.p3achb0t.client.ui.components

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.scripts.*
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JTabbedPane
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameMenu(val tabs: JTabbedPane, var index: Int) : JMenuBar() {
    var poll: AbstractScript? = null
    init {
        add(debugMenu())
        add(addClientMenu())
    }

    private fun debugMenu() : JMenu {
        val menu = JMenu("Debug")


        val injection = JMenuItem("Run Goblin script")
        injection.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.scriptHook?.getManager()
            manager?.setScriptHookAbs(GoblinKiller())
        }


        menu.add(injection)

        menu.popupMenu.isLightWeightPopupEnabled = false

        return menu
    }

    private fun addClientMenu() : JMenu {
        val menu = JMenu("Client")

        val drawing = JMenuItem("Add")
        drawing.addActionListener {

            TabManager.instance.addInstance()

        }

        val remove = JMenuItem("Remove")
        remove.addActionListener {

            TabManager.instance.removeInstance()

        }

        val start = JMenuItem("Start")
        start.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.scriptHook?.getManager()
            manager?.start()

        }

        val stop = JMenuItem("Stop")
        stop.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.scriptHook?.getManager()
            manager?.stop()

        }

        val resume = JMenuItem("Resume")
        resume.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.scriptHook?.getManager()
            manager?.resume()

        }

        val suspend = JMenuItem("Suspend")
        suspend.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.scriptHook?.getManager()
            manager?.suspend()

        }

        val keyboardEnable = JMenuItem("Toggle Keyboard")
        keyboardEnable.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.scriptHook?.getKeyboard()
            manager?.inputBlocked(!manager.inputBlocked())
            println(manager?.inputBlocked())

        }

        val mouseDisable= JMenuItem("Toggle Mouse")
        mouseDisable.addActionListener {
            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.scriptHook?.getMouse()
            manager?.inputBlocked(!manager.inputBlocked())
            //println("${manager?.x}, ${manager?.y}")
        }

        val getMouseCoords = JMenuItem("Mouse POS")
        getMouseCoords.addActionListener {
            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.scriptHook?.getMouse()
            //manager?.inputBlocked(!manager.inputBlocked())
            println("${manager?.getX()}, ${manager?.getY()}")
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