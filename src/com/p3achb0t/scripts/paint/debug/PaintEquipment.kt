package com.p3achb0t.scripts.paint.debug

import com.p3achb0t.api.PaintScript
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.wrappers.tabs.Equipment
import java.awt.Color
import java.awt.Graphics

@ScriptManifest("Debug","Equipment Helper","Bot Team", "0.1")
class PaintEquipment: PaintScript() {
    override fun draw(g: Graphics) {
        if (ctx.client.getGameState() == 30) {
            if (ctx.equipment.isOpen()) {
                Equipment.Companion.Slot.values().iterator().forEach { slot ->
                    val widget = ctx.equipment.getItemAtSlot(slot)
                    if (widget != null) {
                        g.color = Color.PINK
                        g.drawRect(widget.area.x, widget.area.y, widget.area.width, widget.area.height)
                        if (widget.id != -1) {
                            g.color = Color.YELLOW
                            g.drawString("${widget.id}", widget.getBasePoint().x, widget.getBasePoint().y)
                            g.color = Color.GREEN
                            g.drawString(
                                    "${widget.stackSize}",
                                    widget.getBasePoint().x + 10,
                                    widget.getBasePoint().y + 10
                            )
                        }
                    }
                }
            }
        }
    }
}
