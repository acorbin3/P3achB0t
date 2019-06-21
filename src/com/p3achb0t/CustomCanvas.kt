package com.p3achb0t

import com.p3achb0t.hook_interfaces.Widget
import com.p3achb0t.interfaces.PaintListener
import java.awt.AWTEventMulticaster
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import java.awt.event.ActionListener
import java.awt.image.BufferedImage

class CustomCanvas(var oldCanvasHash: Int) : Canvas() {

    var counter = 0

    val image = BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB)
    @Transient
    var paintListener: PaintListener? = null

    fun addPaintListener(listener: ActionListener) {
        this.paintListener = AWTEventMulticaster.add(
            this.paintListener,
            listener
        ) as PaintListener
    }

    override fun getGraphics(): Graphics {
        val g = image.graphics
        g.color = Color.GREEN

        paintListener?.onPaint(g)

        g.color = Color.GREEN
        if (Main.selectedWidget != null) {
            if (Main.selectedWidget!!.getType() == 2) {
                val retcs = Widget.getItemsRects(Main.selectedWidget!!)
                retcs.iterator().forEach { rect ->
                    g.drawRect(rect.x, rect.y, rect.width, rect.height)
                }
            } else {
                val rect = Widget.getDrawableRect(Main.selectedWidget!!)
                rect.let { g.drawRect(rect.x, rect.y, rect.width, rect.height) }
            }
        }

        super.getGraphics().drawImage(image, 0, 0, null)
        counter += 1

        return g
    }

    override fun hashCode(): Int {
        return oldCanvasHash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CustomCanvas

        if (oldCanvasHash != other.oldCanvasHash) return false
        if (counter != other.counter) return false
        if (image != other.image) return false

        return true
    }

}