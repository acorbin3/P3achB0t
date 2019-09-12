package com.p3achb0t.api.wrappers

//import com.p3achb0t.MainApplet
import com.p3achb0t._runestar_interfaces.IterableNodeHashTable
import com.p3achb0t._runestar_interfaces.Npc
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.getConvexHull
import java.awt.Point
import java.awt.Polygon

class NPC(var npc: Npc, client: com.p3achb0t._runestar_interfaces.Client) : Actor(npc, client) {
    override fun isMouseOverObj(): Boolean {
        //val mousePoint = Point(MainApplet.mouseEvent?.x ?: -1,MainApplet.mouseEvent?.y ?: -1)
        return true//getConvexHull().contains(mousePoint)
    }

    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return client?.let { Calculations.worldToScreen(region.x, region.y, npc.getHeight(), it) } ?: Point(-1,-1)
    }
    fun getConvexHull(): Polygon {
        return client?.getNPCType_cachedModels()?.let {
            getConvexHull(
                    this.npc,
                    it,
                    this.npc.getType().getKey(),client


            )
        } ?: Polygon()
    }

    override fun getInteractPoint(): Point {
        return getRandomPoint(getConvexHull())
    }

    override suspend fun clickOnMiniMap(): Boolean {
        return true /*client?.let {
            Calculations.worldToMiniMap(npc.getX(), npc.getY(), it)
        }?.let {
            MainApplet.mouse.click(it)
        } ?: false*/
    }

    suspend fun talkTo(): Boolean {
        return interact("Talk-to")
    }

    fun isInCombat() : Boolean {
        if (npc.getHitmarkCount() == 1.toByte() && npc.getTargetIndex() == -1)
            return false
        return (npc.getTargetIndex() != -1 || npc.getHitmarkCount() == 1.toByte())
    }
}