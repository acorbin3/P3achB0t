package com.p3achb0t.client.injection


import java.awt.Canvas
import java.awt.Graphics
import java.awt.RenderingHints
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.image.BufferedImage


open class RsCanvas(val instanceManager: InstanceManager) : Canvas(), MouseListener {

    private val gameCanvas: BufferedImage = BufferedImage(instanceManager.canvasWidth, instanceManager.canvasHeight, BufferedImage.TYPE_INT_RGB)
    private val screen: BufferedImage = BufferedImage(instanceManager.canvasWidth, instanceManager.canvasHeight, BufferedImage.TYPE_INT_RGB)

    private var count = 0

    init {
        super.setFocusable(true)
        instanceManager.setGameImage(screen)
        this.addMouseListener(this)
    }

    override fun getGraphics() : Graphics {
        val g = gameCanvas.createGraphics()
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)


        if (instanceManager.isContextLoaded) {
            if (instanceManager.isScriptRunning)
                instanceManager.script.draw(g)

            instanceManager.paintScript(g)
            instanceManager.paintDebugScripts(g)
        }

        // screen shot logic
        if (instanceManager.captureScreen && count > instanceManager.captureScreenFrame) {
            val noob = screen.createGraphics()
            noob.drawImage(gameCanvas, 0, 0, null)
            count = 0
        }
        count++

        //Thread.sleep(1000/ instanceManager.fps.toLong())
        try {
            super.getGraphics().drawImage(gameCanvas, 0, 0, null)
        }catch (e: Exception){ }

        return g
    }

    override fun mouseReleased(p0: MouseEvent?) {
    }

    override fun mouseEntered(p0: MouseEvent?) {
        requestFocusInWindow()
    }

    override fun mouseClicked(p0: MouseEvent?) {
    }

    override fun mouseExited(p0: MouseEvent?) {
    }

    override fun mousePressed(p0: MouseEvent?) {
    }

}