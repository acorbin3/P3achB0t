package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Npc
import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.getConvexHull
import net.runelite.api.MenuOpcode
import java.awt.Point
import java.awt.Polygon

class NPC(var npc: Npc, ctx: Context, val menuIndex: Int) : Actor(npc, ctx) {

    val name: String
        get() {
            return npc.getType().getName()
        }

    override fun isMouseOverObj(): Boolean {
        val mousePoint = ctx?.let { Point(it.mouse.getX(), it.mouse.getY()) }
        return getConvexHull().contains(mousePoint)
    }

    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return ctx?.client.let { it?.let { it1 -> Calculations.worldToScreen(region.x, region.y, npc.getHeight(), ctx!!) } } ?: Point(-1,-1)
    }
    fun getConvexHull(): Polygon {
        return ctx?.let {
            getConvexHull(
                    this.npc,
                    it
            )
        } ?: Polygon()

    }

    suspend fun doActionAttack(){
        val doActionParams = DoActionParams(0, 0, MenuOpcode.NPC_SECOND_OPTION.id, menuIndex, "", "", 0 ,0)
        ctx?.mouse?.overrideDoActionParams = true
        ctx?.mouse?.doAction(doActionParams)
    }

    suspend fun doActionCast(){
        val doActionParams = DoActionParams(0, 0, MenuOpcode.SPELL_CAST_ON_NPC.id, menuIndex, "", "", 0 ,0)
        ctx?.mouse?.overrideDoActionParams = true
        ctx?.mouse?.doAction(doActionParams)
    }
    suspend fun doAction(){

        val doActionParams = DoActionParams(this.getLocalLocation().x, this.getLocalLocation().y, MenuOpcode.NPC_FIRST_OPTION.id, menuIndex, "", "", 0 ,0)
        ctx?.mouse?.overrideDoActionParams = true
        ctx?.mouse?.doAction(doActionParams)
    }

    override fun getInteractPoint(): Point {
        return getRandomPoint(getConvexHull())
    }

    suspend fun walkTo(){
        this.getGlobalLocation().walktoTile()
    }
    override suspend fun clickOnMiniMap(): Boolean {
        return ctx?.client.let {
            it?.let { it1 -> Calculations.worldToMiniMap(npc.getX(), npc.getY(), ctx!!) }
        }.let {
            it?.let { it1 -> ctx?.mouse?.click(it1) }
        } ?: false
    }

    suspend fun talkTo(): Boolean {
        return interact("Talk-to")
    }
}
