package com.p3achb0t

import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.interfaces.PaintListener
import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.ActionListener
import java.awt.image.BufferedImage

class CustomCanvas(var oldCanvasHash: Int) : Canvas() {

    var counter = 0

    companion object {
        var dimension: Dimension = Dimension(800, 600)
        var image = BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB)
        fun updateImageSize() {
            println("dim: ${dimension}")
            image = BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB)

        }
    }


    @Transient
    var paintListener = ArrayList<PaintListener>()

    fun addPaintListener(listener: ActionListener) {
        this.paintListener.add(listener as PaintListener)
    }


    override fun getGraphics(): Graphics {
        val g = image.graphics
        g.color = Color.GREEN

        paintListener.forEach { it.onPaint(g) }
        try {
            g.color = Color.GREEN
            if (MainApplet.selectedWidget != null) {
                if (MainApplet.selectedWidget!!.getType() == 2) {
                    val retcs = Widget.getItemsRects(MainApplet.selectedWidget!!)
                    retcs.iterator().forEach { rect ->
                        g.drawRect(rect.x, rect.y, rect.width, rect.height)
                    }
                } else {
                    val rect = Widget.getDrawableRect(MainApplet.selectedWidget!!)
                    rect.let { g.drawRect(rect.x, rect.y, rect.width, rect.height) }
                    try {
                        g.color = Color.MAGENTA
                        for (child in MainApplet.selectedWidget?.getChildren()!!) {
                            val rect2 = Widget.getDrawableRect(child)
                            g.drawRect(rect2.x, rect2.y, rect2.width, rect2.height)
                        }
                    } catch (e: Exception) {
                    }
                }
            }

            super.getGraphics().drawImage(image, 0, 0, null)
        } catch (e: Exception) {
        }

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

        return true
    }

}