package com.p3achb0t.interfaces

import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

interface PaintListener : ActionListener {
    fun onPaint(g: Graphics)
    override fun actionPerformed(e: ActionEvent?) {
    }
}