package com.p3achb0t.injection.Replace

import com.p3achb0t.interfaces.ScriptManager
import java.awt.*
import java.awt.image.BufferedImage


open class RsCanvas(val manager: ScriptManager) : Canvas() {

    private val gameCanvas: BufferedImage = BufferedImage(manager.x, manager.y, BufferedImage.TYPE_INT_RGB)
    private val screen: BufferedImage = BufferedImage(manager.x, manager.y, BufferedImage.TYPE_INT_RGB)

    private var count = 0

    init {
        super.setFocusable(true)
        manager.setGameImage(screen)
    }

    override fun getGraphics() : Graphics {
        val g = gameCanvas.createGraphics()
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        manager.paintScript(g)
        manager.paintDebug(g)

        //g.color = Color.CYAN
        //g.drawString("P3achB0t & Løhde - BOT Alpha 0.1.0", 30 ,20)



        // screen shot logic
        if (manager.captureScreen && count > manager.captureScreenFrame) {
            val noob = screen.createGraphics()
            noob.drawImage(gameCanvas, 0, 0, null);
            count = 0
        }
        count++

        super.getGraphics().drawImage(gameCanvas, 0, 0, null)

        return g
    }
}