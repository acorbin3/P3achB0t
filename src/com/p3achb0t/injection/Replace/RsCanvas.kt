package com.p3achb0t.injection.Replace

import com.p3achb0t.client.injection.InstanceManager
import java.awt.Canvas
import java.awt.Graphics
import java.awt.RenderingHints
import java.awt.image.BufferedImage


open class RsCanvas(val instanceManager: InstanceManager) : Canvas() {

    private val gameCanvas: BufferedImage = BufferedImage(instanceManager.canvasWidth, instanceManager.canvasHeight, BufferedImage.TYPE_INT_RGB)
    private val screen: BufferedImage = BufferedImage(instanceManager.canvasWidth, instanceManager.canvasHeight, BufferedImage.TYPE_INT_RGB)

    private var count = 0

    init {
        super.setFocusable(true)
        instanceManager.setGameImage(screen)
    }

    override fun getGraphics() : Graphics {
        val g = gameCanvas.createGraphics()
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)


        if (instanceManager.isContextLoaded) {
            //scriptManager.paintScript(g)
            instanceManager.paintDebugScripts(g)
            //g.drawString("o", instanceManager.ctx.mouse.getX(), instanceManager.ctx.mouse.getY())
        }

        // screen shot logic
        if (instanceManager.captureScreen && count > instanceManager.captureScreenFrame) {
            val noob = screen.createGraphics()
            noob.drawImage(gameCanvas, 0, 0, null)
            count = 0
        }
        count++

        Thread.sleep(1000/ instanceManager.fps.toLong())
        try {
            super.getGraphics().drawImage(gameCanvas, 0, 0, null)
        }catch (e: Exception){ }

        return g
    }
}