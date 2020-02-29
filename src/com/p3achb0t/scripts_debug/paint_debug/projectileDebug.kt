package com.p3achb0t.scripts_debug.paint_debug

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.getConvexHullFromModel
import com.p3achb0t.api.wrappers.utils.getTrianglesFromModel
import java.awt.Color
import java.awt.Graphics

fun projectilePaint(g: Graphics, ctx: Context) {
    ctx.projectiles.projectiles.forEach {
        val positionInfo =
                it.position

        val modelTriangles =
                getTrianglesFromModel(
                        positionInfo,
                        it.raw.getModel(),
                        ctx

                )
        g.color = Color.RED
        modelTriangles.forEach {
            g.drawPolygon(it)
        }
        val hull = getConvexHullFromModel(
                positionInfo,
                it.raw.getModel(),
                ctx

        )
        g.color = Color.CYAN
        g.drawPolygon(hull)
    }
}