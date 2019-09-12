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

        val drawing = JMenuItem("TestScript Run")
        drawing.addActionListener {

            //val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            //game.client.script = TestScript()
            ScriptManager.instance.start(TestScript())
            println("AFTER")
            //poll = TestScript()

        }

        val npc = JMenuItem("PrintScript Run")
        npc.addActionListener {
            //poll = PrintScript()
            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            game.client!!.script = PrintScript()
            //println("SEND KEYS 2")
            //val game = TabManager.instance.getInstance(1)
            //game.client.keyboard?.sendKeys("Kasper")
        }

        val tutorialIslandMenuItem = JMenuItem("Tutorial Island")
        tutorialIslandMenuItem.addActionListener{
            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            game.client!!.script = TutorialIsland()
            GlobalScope.launch {
                println("Kicking off the Loop")
                while(true)
                    game.client!!.script?.loop()
            }
        }

        val player = JMenuItem("GoblinKiller")
        player.addActionListener {
            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            game.client!!.script = GoblinKiller()
            GlobalScope.launch {
                println("Kicking off the Loop")
                while(true)
                    game.client!!.script?.loop()
            }
        }

        val other = JMenuItem("Stop")
        other.addActionListener {
            ScriptManager.instance.stop()
        }

        val injection = JMenuItem("Set INJECT")
        injection.addActionListener {

            val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
            val manager = game.client?.scriptHook?.getManager()
            manager?.setScriptHook(com.p3achb0t.interfaces.PrintScript())
        }


        menu.add(drawing)
        menu.add(npc)
        menu.add(player)
        menu.add(other)
        menu.add(tutorialIslandMenuItem)
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

        menu.add(remove)
        menu.add(drawing)
        menu.add(start)
        menu.add(stop)
        menu.add(suspend)
        menu.add(resume)

        menu.popupMenu.isLightWeightPopupEnabled = false

        return menu
    }

}