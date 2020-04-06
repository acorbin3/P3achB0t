package com.p3achb0t.client.injection


import com.p3achb0t.api.PaintScript
import java.awt.Canvas
import java.awt.Graphics
import java.awt.RenderingHints
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.image.BufferedImage


open class RsCanvas(val instanceManager: InstanceManager) : Canvas(), MouseListener, KeyListener {

    private val gameCanvas: BufferedImage = BufferedImage(instanceManager.canvasWidth, instanceManager.canvasHeight, BufferedImage.TYPE_INT_RGB)

    init {
        super.setFocusable(true)
        this.addMouseListener(this)
        this.addKeyListener(this)
    }

    override fun getGraphics() : Graphics {
        val g = gameCanvas.createGraphics()
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        if (instanceManager.isContextLoaded) {
            //if (!instanceManager.isActionScriptPaused)
            // priority painting is lowest to highest. Where the action scripts have the highest priority of painting
            instanceManager.drawPaintScripts(g)
            instanceManager.actionScript.draw(g)
        }

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

    override fun keyTyped(e: KeyEvent?) {
        if (instanceManager.isContextLoaded) {
            instanceManager.paintScripts.forEach { t: String, u: PaintScript ->
                if(u is KeyListener){
                    (u as KeyListener).keyTyped(e)
                }
            }
            if(instanceManager.actionScript is KeyListener){
                (instanceManager.actionScript as KeyListener).keyTyped(e)
            }
        }
    }

    override fun keyPressed(e: KeyEvent?) {
        if (instanceManager.isContextLoaded) {
            instanceManager.paintScripts.forEach { t: String, u: PaintScript ->
                if(u is KeyListener){
                    (u as KeyListener).keyPressed(e)
                }
            }
            if(instanceManager.actionScript is KeyListener){
                (instanceManager.actionScript as KeyListener).keyPressed(e)
            }
        }
    }

    override fun keyReleased(e: KeyEvent?) {
        if (instanceManager.isContextLoaded) {
            instanceManager.paintScripts.forEach { t: String, u: PaintScript ->
                if(u is KeyListener){
                    (u as KeyListener).keyReleased(e)
                }
            }
            if(instanceManager.actionScript is KeyListener){
                (instanceManager.actionScript as KeyListener).keyReleased(e)
            }
        }
    }
}

/*

        private val screen: BufferedImage = BufferedImage(instanceManager.canvasWidth, instanceManager.canvasHeight, BufferedImage.TYPE_INT_RGB)
        private var count = 0
        instanceManager.setGameImage(screen)
// screen shot logic
if (instanceManager.captureScreen && count > instanceManager.captureScreenFrame) {
    val noob = screen.createGraphics()
    noob.drawImage(gameCanvas, 0, 0, null)
    count = 0
}
count++
 */