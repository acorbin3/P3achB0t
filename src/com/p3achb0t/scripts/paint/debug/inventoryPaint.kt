package com.p3achb0t.scripts.paint.debug

import com.p3achb0t.api.PaintScript
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.wrappers.tabs.Tabs
import java.awt.Color
import java.awt.Graphics

@ScriptManifest("Debug","Inventory Helper","Bot Team", "0.1")
class PaintInventory : PaintScript() {
    override fun draw(g: Graphics) {
        if (ctx.client.getGameState() == 30) {
            if(ctx.tabs.getOpenTab() == Tabs.Tab_Types.Inventory) {
                val items = ctx.inventory.getAll()
                // Look at inventory
                if (items.size > 0) {
                    items.forEach {
                        g.color = Color.YELLOW
                        g.drawString("${it.id}", it.getBasePoint().x, it.getBasePoint().y-1)
                        g.color = Color.yellow
                        g.drawString("${it.stackSize}", it.getBasePoint().x , it.getBasePoint().y + 20)

                        g.color = Color.RED
                        g.drawRect(it.area.x, it.area.y, it.area.width, it.area.height)
                    }
                }
            }
        }
    }

    override fun start() {
    }

    override fun stop() {
    }

}
