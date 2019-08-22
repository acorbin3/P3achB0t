package com.p3achb0t.ui.components

import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JTabbedPane

class GameMenu(val tabs: JTabbedPane, var index: Int) : JMenuBar() {

    init {
        add(debugMenu())
        add(addClientMenu())
    }

    private fun debugMenu() : JMenu {
        val menu = JMenu("Debug")

        val drawing = JMenuItem("Object")
        drawing.addActionListener { println("sfsdfdsf")}

        val npc = JMenuItem("Object")
        npc.addActionListener { println("sfsdfdsf")}

        val player = JMenuItem("Object")
        player.addActionListener { println("sfsdfdsf")}

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
            val game = GameTab(1,tabs)

            tabs.addTab("Game $index", game)
            tabs.selectedIndex = ++index






            Thread({
                while (!tabs.isPreferredSizeSet) {
                    println("${tabs.isPreferredSizeSet}")
                    Thread.sleep(100)
                }
                game.g()
            }).start()


        }


        //menu.add(player)
        menu.add(drawing)

        menu.popupMenu.isLightWeightPopupEnabled = false

        return menu
    }

}