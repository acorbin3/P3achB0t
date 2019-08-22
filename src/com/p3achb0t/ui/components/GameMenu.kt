package com.p3achb0t.ui.components

import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class GameMenu : JMenuBar() {

    init {
        add(debugMenu())
    }

    private fun debugMenu() : JMenu {
        val menu = JMenu("Debug")

        val drawing = JMenuItem("Object")
        drawing.addActionListener { println("sfsdfdsf")}

        menu.add(drawing)

        return menu
    }

}