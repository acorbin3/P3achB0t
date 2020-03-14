package com.p3achb0t.client.new_ui

import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class NavigationMenu: JMenuBar() {
    init {
        add(testMenu())
    }


    private fun testMenu(): JMenu {
        val menu = JMenu("Client")

        val add = JMenuItem("Add")
        add.addActionListener {
            BotInstance().initBot()
        }

        val remove = JMenuItem("Remove")
        remove.addActionListener {
            GlobalStructs.botTabBar.KillIndex()
        }

        val start = JMenuItem("Start")
        start.addActionListener {


        }

        val stop = JMenuItem("Stop")
        stop.addActionListener {

        }

        val resume = JMenuItem("Resume")
        resume.addActionListener {

        }

        val suspend = JMenuItem("Suspend")
        suspend.addActionListener {

        }

        val keyboardEnable = JMenuItem("Toggle Keyboard")
        keyboardEnable.addActionListener {



        }

        val mouseDisable= JMenuItem("Toggle Mouse")
        mouseDisable.addActionListener {

        }

        val getMouseCoords = JMenuItem("Mouse POS")
        getMouseCoords.addActionListener {

        }

        menu.add(add)
        menu.add(remove)
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