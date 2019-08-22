package com.p3achb0t.ui.components

import javax.swing.JPanel
import javax.swing.JLabel
import java.awt.event.ActionListener
import com.sun.java.accessibility.util.AWTEventMonitor.addActionListener
import java.awt.Dimension
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
        //tabs.setSelectedIndex(id)
        preferredSize = Dimension(750,560)
        //setSize(800,600)
        isVisible = true

        add(client.getApplet())

        //Thread.sleep(500)
        //client.run()
        //val runnable = SimpleThread(client)
        //val thread1 = Thread(runnable)
        //thread1.start()



    }

    fun g() {
        client.run()
    }

    class SimpleThread(val c: ClientInstance): Thread() {
        public override fun run() {
            println("${Thread.currentThread()} has run.")
            sleep(500)
            c.run()
        }
    }


}

