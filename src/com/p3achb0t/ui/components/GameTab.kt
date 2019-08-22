package com.p3achb0t.ui.components

import javax.swing.JPanel
import javax.swing.JLabel
import java.awt.event.ActionListener
import com.sun.java.accessibility.util.AWTEventMonitor.addActionListener
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JTabbedPane


class GameTab(id: Int, tabs: JTabbedPane) : JPanel() {
    val client = ClientInstance()
    init {
        //val next = JButton("next")
        /*next.addActionListener(object : ActionListener {

            override fun actionPerformed(e: ActionEvent) {
                val selectedIndex = tabs.getSelectedIndex()
                val nextIndex = if (selectedIndex == tabs.getTabCount() - 1) 0 else selectedIndex + 1
                tabs.setSelectedIndex(nextIndex)
            }
        })
        add(JLabel("tab $id"))
        add(next)*/
        setSize(780,600)

        add(client.getApplet())


    }

    fun g() {
        client.run()
    }


}