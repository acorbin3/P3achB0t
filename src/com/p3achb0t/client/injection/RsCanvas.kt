package com.p3achb0t.client.injection


import com.p3achb0t.api.script.Drawable
import com.p3achb0t.api.script.GroupTask
import com.p3achb0t.api.script.PaintScript
import com.p3achb0t.api.script.ServiceScript
import com.p3achb0t.client.accounts.Account
import com.p3achb0t.client.configs.Constants
import java.awt.Canvas
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.event.*
import java.awt.image.BufferedImage


open class RsCanvas(val instanceManager: InstanceManager) : Canvas(), MouseListener, MouseWheelListener, KeyListener {

    //private val gameCanvas: BufferedImage = BufferedImage(instanceManager.canvasWidth, instanceManager.canvasHeight, BufferedImage.TYPE_INT_RGB)

    init {
        super.setFocusable(true)
        this.addMouseListener(this)
        this.addKeyListener(this)
    }

    override fun getGraphics(): Graphics? {
        if (instanceManager.drawCanvas) {
            try {
                bufferStrategy?.let {
                    val g2 = it.drawGraphics as Graphics2D
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                    instanceManager.drawPaintScripts(g2)
                    instanceManager.drawsServiceScripts(g2)
                    //Draw all tasks or children of groups tasks if they have drawable
                    instanceManager.actionScript.tasks.forEach { task ->
                        if(task is Drawable){
                            task.draw(g2)
                        }
                        if(task is GroupTask){
                            task.children.forEach { childTask ->
                                if(childTask is Drawable){
                                    childTask.draw(g2)
                                }
                            }
                        }
                    }
                    instanceManager.actionScript.draw(g2)

                    it.show()
                    if(instanceManager.isContextLoaded && instanceManager.ctx.worldHop.isLoggedIn) {
                        Thread.sleep(1000 / instanceManager.account.gameFps.toLong())
                    }
                    return g2
                } ?: createBufferStrategy(2)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    //Replaced implementation
    /*override fun getGraphics() : Graphics {
        val g = gameCanvas.createGraphics()
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        if (instanceManager.isContextLoaded) {
            //if (!instanceManager.isActionScriptPaused)
            // priority painting is lowest to highest. Where the action scripts have the highest priority of painting
            instanceManager.drawPaintScripts(g)
            instanceManager.drawsServiceScripts(g)
            instanceManager.actionScript.draw(g)

        }

        //Thread.sleep(1000/ instanceManager.fps.toLong())
        try {
            super.getGraphics().drawImage(gameCanvas, 0, 0, null)
        }catch (e: Exception){ }

        return g
    }*/

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
            instanceManager.serviceScripts.forEach { t: String, u: ServiceScript ->
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
            instanceManager.serviceScripts.forEach { t: String, u: ServiceScript ->
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
            instanceManager.serviceScripts.forEach { t: String, u: ServiceScript ->
                if(u is KeyListener){
                    (u as KeyListener).keyReleased(e)
                }
            }
            if(instanceManager.actionScript is KeyListener){
                (instanceManager.actionScript as KeyListener).keyReleased(e)
            }
        }
    }

    override fun mouseWheelMoved(e: MouseWheelEvent?) {
        println("mouseWheelMoved $e");
        if (e != null && e.getWheelRotation() < 0)
        {
            println("mouse wheel Up $e");
        }
        else
        {
            println("mouse wheel Down $e");
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