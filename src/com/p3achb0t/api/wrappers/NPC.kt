package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.getConvexHull
import com.p3achb0t.api.wrappers.interfaces.Interactable
import com.p3achb0t.hook_interfaces.Npc
import java.awt.Point
import java.awt.Polygon

class NPC(var npc: Npc) : Actor(npc), Interactable {

    fun getConvexHull(): Polygon {
        return getConvexHull(
            this.npc,
            Main.clientData.getNpcModelCache(),
            this.npc.getComposite().getNpcComposite_id().toLong()
        )
    }

    override fun getInteractPoint(): Point {
        return getRandomPoint(getConvexHull())
    }

    override suspend fun clickOnMiniMap(): Boolean {
        return Main.mouse.click(Calculations.worldToMiniMap(npc.getLocalX(), npc.getLocalY()))
    }

    suspend fun talkTo(): Boolean {
        return interact("Talk-to")
    }

    override suspend fun interact(action: String): Boolean {
        //TODO check is player is on scree
        //  TODO - Move camera for player to be on screen
        try {

            val ch = getConvexHull(
                this.npc,
                Main.clientData.getNpcModelCache(),
                this.npc.getComposite().getNpcComposite_id().toLong()
            )

            Interact.interact(ch, action)
        } catch (e: Exception) {
        }
        return true
    }
}