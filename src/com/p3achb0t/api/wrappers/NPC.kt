package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Npc
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.getConvexHull
import com.p3achb0t.api.user_inputs.Mouse
import java.awt.Point
import java.awt.Polygon

class NPC(var npc: Npc, client: com.p3achb0t._runestar_interfaces.Client, mouse: Mouse) : Actor(npc, client, mouse) {
    override fun isMouseOverObj(): Boolean {
        val mousePoint = Point(mouse?.mouseEvent?.x ?: -1,mouse?.mouseEvent?.y ?: -1)
        return getConvexHull().contains(mousePoint)
    }

    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return client.let { it?.let { it1 -> Calculations.worldToScreen(region.x, region.y, npc.getHeight(), it1) } } ?: Point(-1,-1)
    }
    fun getConvexHull(): Polygon {
        return client?.let {
            getConvexHull(
                    this.npc,
                    it.getNPCType_cachedModels(),
                    this.npc.getType().getKey(),it
            )
        } ?: Polygon()

    }


    override fun getInteractPoint(): Point {
        return getRandomPoint(getConvexHull())
    }

    override suspend fun clickOnMiniMap(): Boolean {
        return client.let {
            it?.let { it1 -> Calculations.worldToMiniMap(npc.getX(), npc.getY(), it1) }
        }.let {
            it?.let { it1 -> mouse?.click(it1) }
        } ?: false
    }

    suspend fun talkTo(): Boolean {
        return interact("Talk-to")
    }
}