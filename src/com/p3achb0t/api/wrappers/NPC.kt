package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Npc
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.getConvexHull
import com.p3achb0t.api.user_inputs.Mouse
import java.awt.Point
import java.awt.Polygon

class NPC(var npc: Npc, client: com.p3achb0t._runestar_interfaces.Client, mouse: Mouse) : Actor(npc, client, mouse) {
    override fun isMouseOverObj(): Boolean {
        val mousePoint = Point(mouse.mouseEvent?.x ?: -1,mouse.mouseEvent?.y ?: -1)
        return getConvexHull().contains(mousePoint)
    }

    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return client.let { Calculations.worldToScreen(region.x, region.y, npc.getHeight(), it) } ?: Point(-1,-1)
    }
    fun getConvexHull(): Polygon {
        return  getConvexHull(
                    this.npc,
                    client.getNPCType_cachedModels(),
                    this.npc.getType().getKey(),client
            )

    }


    override fun getInteractPoint(): Point {
        return getRandomPoint(getConvexHull())
    }

    override suspend fun clickOnMiniMap(): Boolean {
        return client.let {
            Calculations.worldToMiniMap(npc.getX(), npc.getY(), client)
        }.let {
            mouse.click(it)
        } ?: false
    }

    suspend fun talkTo(): Boolean {
        return interact("Talk-to")
    }
}