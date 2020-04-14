package com.p3achb0t.client.ux.prefs

import java.awt.Dimension
import java.awt.Point
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.util.prefs.Preferences
import javax.swing.JFrame

object FrameMonitor {

    fun registerFrame(frame: JFrame, frameUniqueId: String, defaultX: Int, defaultY: Int, defaultW: Int, defaultH: Int) {
        val prefs = Preferences.userRoot()
            .node(FrameMonitor::class.java.simpleName + "-" + frameUniqueId)

        frame.location = getFrameLocation(prefs, defaultX, defaultY)
        frame.size = getFrameSize(prefs, defaultW, defaultH)

        val updater = CoalescedEventUpdater(2000,
            Runnable { updatePref(frame, prefs) }
        )

        frame.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent) {
                updater.update()
            }

            override fun componentMoved(e: ComponentEvent) {
                updater.update()
            }
        })
    }

    private fun updatePref(frame: JFrame, prefs: Preferences) {
        //println("Updating window preferences.")
        val location = frame.location
        prefs.putInt("x", location.x)
        prefs.putInt("y", location.y)
        val size = frame.size
        prefs.putInt("w", size.width)
        prefs.putInt("h", size.height)
    }

    private fun getFrameSize(pref: Preferences, defaultW: Int, defaultH: Int): Dimension {
        val w = pref.getInt("w", defaultW)
        val h = pref.getInt("h", defaultH)
        return Dimension(w, h)
    }

    private fun getFrameLocation(pref: Preferences, defaultX: Int, defaultY: Int): Point {
        val x = pref.getInt("x", defaultX)
        val y = pref.getInt("y", defaultY)
        return Point(x, y)
    }
}