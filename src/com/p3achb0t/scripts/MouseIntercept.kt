package com.p3achb0t.scripts

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.Calculations
import java.awt.Graphics

class MouseIntercept : AbstractScript() {
    override fun loop() {

    }

    override fun start() {

    }

    override fun stop() {

    }

    override fun draw(g: Graphics) {
        val Ax = ctx.mouse.getX()
        val Ay = ctx.mouse.getY()
        val npc = ctx.npcs.getNearestNPC()
        val Bx = npc.npc.getX()
        val By = npc.npc.getY()

        val ABx = Bx - Ax
        val ABy = By - Ay
        val point = Calculations.worldToScreen(npc.npc.getX(),npc.npc.getY(), npc.npc.getHeight(), ctx)
        g.drawLine(Ax, Ay, point.x, point.y)
    }
}

