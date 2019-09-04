package com.p3achb0t.ui.components

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.scripts.PrintScript
import com.p3achb0t.scripts.ScriptManager
import com.p3achb0t.scripts.TestScript
import com.p3achb0t.scripts.TutorialIsland
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

        val player = JMenuItem("Run Loop")
        player.addActionListener {
            GlobalScope.launch {
                val game = TabManager.instance.getInstance(TabManager.instance.getSelectedIndexx())
                game.client!!.script?.loop()
                //poll?.loop()
                println("sfsdfdsf")
            }
        }

        val other = JMenuItem("Stop")
        other.addActionListener {
            ScriptManager.instance.stop()
        }

        menu.add(drawing)
        menu.add(npc)
        menu.add(player)
        menu.add(other)
        menu.add(tutorialIslandMenuItem)

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

        menu.add(remove)
        menu.add(drawing)

        menu.popupMenu.isLightWeightPopupEnabled = false

        return menu
    }

}