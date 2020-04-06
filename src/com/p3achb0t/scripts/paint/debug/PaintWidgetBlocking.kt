package com.p3achb0t.scripts.paint.debug

import com.p3achb0t.api.script.PaintScript
import com.p3achb0t.api.script.ScriptManifest
import java.awt.Color
import java.awt.Graphics

@ScriptManifest("Debug","WidgetBlocking Helper","Bot Team", "0.1")
class PaintWidgetBlocking : PaintScript() {
    override fun draw(g: Graphics) {
        if (ctx.client.getGameState() == 30) {
            g.color = Color.red
            /*drawRect(
                    g,
                    Calculations.chatBoxDimensions
            )
            drawRect(
                    g,
                    Calculations.inventoryBarBottomDimensions
            )
            drawRect(
                    g,
                    Calculations.inventoryDimensions
            )
            drawRect(
                    g,
                    Calculations.inventoryBarTopDimensions
            )
            drawRect(
                    g,
                    Calculations.miniMapDimensio
        */
        }
    }


}
