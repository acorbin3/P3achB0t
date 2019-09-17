package com.test

import com.p3achb0t.client.Bot
import java.awt.Dimension
import java.awt.event.WindowEvent
import javax.swing.JFrame
import java.awt.event.WindowEvent.WINDOW_CLOSING



class SingleWindow(val bot: Bot) {
    val frame: JFrame
    init {
        frame = JFrame()

        frame.size = Dimension(800, 800)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
        frame.add(bot.applet)
    }



    fun destroy2() {
        frame.dispose()
    }

    fun destroy() {
        val g = SingleWindow2(bot)
        g.start()
        frame.isVisible = false
        //Thread.sleep(200)
        destroy2()
    }
}