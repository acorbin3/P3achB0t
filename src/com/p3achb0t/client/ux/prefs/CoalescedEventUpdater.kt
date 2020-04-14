package com.p3achb0t.client.ux.prefs

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.SwingUtilities
import javax.swing.Timer


class CoalescedEventUpdater(delay: Int, callback: Runnable) {

    private val timer: Timer

    init {
        timer = Timer(delay, ActionListener { e: ActionEvent? ->
            stopTimer()
            callback.run()
        })
    }

    fun update() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater { timer.restart() }
        } else {
            timer.restart()
        }
    }

    private fun stopTimer() {
        timer.stop()
    }
}