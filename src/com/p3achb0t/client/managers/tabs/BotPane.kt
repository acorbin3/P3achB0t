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
        add(bot.getApplet())
        revalidate()
    }

    fun display() {
        println("Iam a bot panel")
    }

    fun destroy() {
        bot.getApplet().destroy()
    }

    fun resizeView(x: Int, y: Int) {
        bot.getApplet().preferredSize = Dimension(x, y)
        //bot.applet.revalidate()
        val scriptManager = bot.getScriptManager()
        scriptManager.x = x
        scriptManager.y = y
        bot.getApplet().revalidate()
        bot.getApplet().repaint()
    }
}