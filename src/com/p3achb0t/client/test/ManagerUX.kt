package com.p3achb0t.client.test

import com.p3achb0t.client.Bot
import java.awt.Dimension
import javax.swing.*

class ManagerUX : JFrame() {

    init {
        title = "Bot Manager"
        size = Dimension(600, 500)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        setup()
        isVisible = true

    }

    private fun setup() {
        add(tabs())
    }

    private fun tabs() : JTabbedPane {
        val tabs = JTabbedPane()
        tabs.addTab("Overview", overview())
        tabs.addTab("Scripts", scripts())
        return tabs
    }

    private fun overview() : JPanel{
        val pane = JPanel()

        return pane
    }

    private fun scripts() : JPanel{
        val pane = JPanel()

        val botList = JList<Bot>()
        botList

        return pane
    }
}


fun main() {
    val manager = ManagerUX()

}