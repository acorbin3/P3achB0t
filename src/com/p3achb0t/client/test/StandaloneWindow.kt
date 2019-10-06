package com.p3achb0t.client.test

import com.p3achb0t.client.Bot
import com.p3achb0t.client.managers.tabs.TabManager
import java.awt.Dimension
import javax.swing.JFrame

class StandaloneWindow(val bot: Bot, val tabManager: TabManager): JFrame(bot.id) {
    //val frame: JFrame
    init {
        //frame = JFrame()
        isFocusable = true
        focusTraversalKeysEnabled = true
        size = Dimension(800, 600)
        setLocationRelativeTo(null)
        isVisible = true
        revalidate()

    }

    fun start() {
        add(bot.getApplet())
    }

    fun attach() {
        tabManager.attach(bot)
        isVisible = false
        Thread.sleep(1000)
        dispose()
    }
}