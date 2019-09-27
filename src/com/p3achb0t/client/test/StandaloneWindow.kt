package com.p3achb0t.client.test

import com.p3achb0t.client.Bot
import com.p3achb0t.client.managers.tabs.TabManager
import java.awt.Dimension
import javax.swing.JFrame

class StandaloneWindow(val bot: Bot, val tabManager: TabManager): JFrame("Bot") {
    val frame: JFrame
    init {
        frame = JFrame()
        frame.isFocusable = true
        frame.focusTraversalKeysEnabled = true
        frame.size = Dimension(800, 600)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
        frame.revalidate()

    }

    fun start() {
        frame.add(bot.applet)
    }

    fun attach() {
        tabManager.attach(bot)
        frame.isVisible = false
        Thread.sleep(1000)
        frame.dispose()
    }
}