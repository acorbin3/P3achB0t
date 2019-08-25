package com.p3achb0t.ui.components

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.scripts.PrintScript
import com.p3achb0t.scripts.TestScript
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JTabbedPane

class GameMenu(val tabs: JTabbedPane, var index: Int) : JMenuBar() {
    var poll: AbstractScript? = null
    init {
        add(debugMenu())
        add(addClientMenu())
    }

    private fun debugMenu() : JMenu {
        val menu = JMenu("Debug")

        val drawing = JMenuItem("TEST 1")
        drawing.addActionListener {
            println("SEND KEYS")
            val game = TabManager.instance.getInstance(0)
            poll = TestScript()

        }

        val npc = JMenuItem("Test 2")
        npc.addActionListener {
            poll = PrintScript()

            //println("SEND KEYS 2")
            //val game = TabManager.instance.getInstance(1)
            //game.client.keyboard?.sendKeys("Kasper")
        }

        val player = JMenuItem("3")
        player.addActionListener {
            poll?.loop()
            println("sfsdfdsf")
        }

        val other = JMenuItem("Object")
        other.addActionListener { println("sfsdfdsf")}

        menu.add(drawing)
        menu.add(npc)
        menu.add(player)
        menu.add(other)

        menu.popupMenu.isLightWeightPopupEnabled = false

        return menu
    }

    private fun addClientMenu() : JMenu {
        val menu = JMenu("Client")

        val drawing = JMenuItem("Add")
        drawing.addActionListener {

            TabManager.instance.addInstance()

        }


        //menu.add(player)
        menu.add(drawing)

        menu.popupMenu.isLightWeightPopupEnabled = false

        return menu
    }

}