package com.p3achb0t.ui

import com.p3achb0t.ui.components.TabManager
import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.text.html.CSS.Attribute.FONT_SIZE
import java.awt.Font.PLAIN
import java.awt.AWTEventMulticaster.getListeners




open class RsCanvas : Canvas() {

    private val gameCanvas = BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB)

    val gamePanel = TabManager.instance.getSelected()


    override fun getGraphics() : Graphics {

        val g = gameCanvas.createGraphics()
        //g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.color = Color.CYAN
        g.drawString("P3achB0t & Unoplex - BOT Alpha 0.1.0", 50 ,20)

        gamePanel.client!!.script?.draw(g)

        if (gamePanel.client!!.name != "") {
            g.drawString("Script loaded: ${gamePanel.client!!.name}, author: ${gamePanel.client!!.author}, category: ${gamePanel.client!!.category}", 50 ,260)
        }

        super.getGraphics().drawImage(gameCanvas, 0, 0, null)
        return g

    }
}