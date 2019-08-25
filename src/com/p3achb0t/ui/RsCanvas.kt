package com.p3achb0t.ui

import com.p3achb0t.ui.components.TabManager
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage


open class RsCanvas : Canvas() {

    private val gameCanvas = BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB)
    val gamePanel = TabManager.instance.getSelected()


    override fun getGraphics() : Graphics {

        val g = gameCanvas.graphics
        g.color = Color.CYAN
        g.drawString("P3achB0t & Unoplex - BOT Alpha 0.1.0", 50 ,20)

        gamePanel.client.script?.draw(g)

        if (gamePanel.client.name != "") {
            g.drawString("Script loaded: ${gamePanel.client.name}, author: ${gamePanel.client.author}, category: ${gamePanel.client.category}", 50 ,260)
        }

        super.getGraphics().drawImage(gameCanvas, 0, 0, null)
        return g

    }
}