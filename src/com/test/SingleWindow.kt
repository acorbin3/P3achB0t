package com.test

import com.p3achb0t.client.Bot
import java.awt.Dimension
import javax.swing.JFrame

class SingleWindow(val bot: Bot) {
    val frame: JFrame
    init {
        frame = JFrame()
        frame.isFocusable = true
        frame.focusTraversalKeysEnabled = true
        frame.size = Dimension(800, 800)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
        frame.add(bot.applet)
    }

    fun destroy() {
        val g = SingleWindow2(bot)
        g.start()
        frame.isVisible = false
        Thread.sleep(1000)
        frame.dispose()
    }
}