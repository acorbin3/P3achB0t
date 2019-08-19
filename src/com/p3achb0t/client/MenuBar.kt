package com.p3achb0t.client

import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem


class MenuBar : JMenuBar() {
    init {
        var fileMenu = JMenu("Debug")
        var showNPCsItem = JMenuItem("Show NPC's")

        fileMenu.add(showNPCsItem)
        this.add(fileMenu)
    }
}