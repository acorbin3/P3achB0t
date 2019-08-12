package com.p3achb0t.api.wrappers

import com.p3achb0t.MainApplet
import com.p3achb0t._runestar_interfaces.Npc
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.getConvexHull
import com.p3achb0t.api.wrappers.interfaces.Interactable
import java.awt.Point
import java.awt.Polygon

class NPC(var npc: Npc) : Actor(npc), Interactable {

    fun getConvexHull(): Polygon {
        return getConvexHull(
            this.npc,
            Client.client.getNPCType_cachedModels(),
            this.npc.getType().getKey().toLong()
        )
    }


    override fun getInteractPoint(): Point {
        return getRandomPoint(getConvexHull())
    }

    override suspend fun clickOnMiniMap(): Boolean {
        return MainApplet.mouse.click(Calculations.worldToMiniMap(npc.getX(), npc.getY()))
    }

    suspend fun talkTo(): Boolean {
        return interact("Talk-to")
    }
}