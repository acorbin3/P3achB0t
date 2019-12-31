package com.test

import com.p3achb0t.client.Bot
import java.awt.Dimension
import java.awt.event.WindowEvent
import javax.swing.JFrame

class SingleWindow2(val bot: Bot) {
    val frame: JFrame
    init {
        frame = JFrame()
        frame.isFocusable = true
        frame.size = Dimension(765, 510)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
        //frame.add(bot.applet)

    }

    fun start() : Boolean {
        frame.add(bot.getApplet())
        frame.revalidate()
        return true
    }

    fun destroy() {
        frame.dispatchEvent(WindowEvent(frame, WindowEvent.WINDOW_CLOSING))
    }
}