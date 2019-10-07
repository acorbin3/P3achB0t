package com.p3achb0t.client.test

import com.p3achb0t.client.Bot
import com.p3achb0t.client.managers.Manager
import java.awt.Dimension
import javax.swing.*

class ManagerUX : JFrame() {

    var tabBotWindow: BotWindow? = null

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
        val addBot = JButton("add bot")

        addBot.addActionListener {
            if (tabBotWindow == null) {
                tabBotWindow = BotWindow()
            }
            tabBotWindow?.manager?.addBot()
        }

        pane.add(addBot)



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
    System.setProperty("user.home", "cache")
    val manager = ManagerUX()

}