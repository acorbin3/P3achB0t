package com.p3achb0t.client.managers.tabs

import com.p3achb0t.client.Bot
import java.awt.Color
import java.awt.Dimension
import javax.swing.JPanel

class BotPane(val bot: Bot): JPanel() {

    init {
        focusTraversalKeysEnabled = true
        background = Color.BLACK
        revalidate()
        add(bot.applet)
        revalidate()
    }

    fun display() {
        println("Iam a bot panel")
    }

    fun destroy() {
        bot.applet.destroy()
    }

    fun resizeView(x: Int, y: Int) {
        bot.applet.preferredSize = Dimension(900, 800)
        bot.applet.revalidate()
        bot.manager.getManager().x=x
        bot.manager.getManager().y=y
        bot.applet.repaint()
    }
}