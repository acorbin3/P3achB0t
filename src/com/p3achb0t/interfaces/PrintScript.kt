package com.p3achb0t.interfaces

import com.p3achb0t._runestar_interfaces.Client
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D



class PrintScript(val client: Client, val mk: IScriptManager) : Script {
    val line = BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
    override fun loop() {
    }

    override fun draw(g: Graphics) {
        val g2 = g as Graphics2D
        val x = mk.getMouse().getX()
        val y = mk.getMouse().getY()
        g2.stroke = line
        g2.color = Color.RED
        g2.drawString("Mouse ($x, $y)", 30, 50)

        if (x != -1 && y != -1) {
            g2.drawLine(x-7,y-7,x+7,y+7)
            g2.drawLine(x-7,y+7,x+7,y-7)
        }

    }

}