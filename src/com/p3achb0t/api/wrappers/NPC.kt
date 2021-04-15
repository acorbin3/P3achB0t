package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.interfaces.NPCType
import com.p3achb0t.api.interfaces.Npc
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.getConvexHull
import java.awt.Point
import java.awt.Polygon
import kotlin.math.floor



class NPC(var npc: Npc, ctx: Context, val menuIndex: Int) : Actor(npc, ctx) {
    val name: String
        get() {
            val npcDef = getType(npc.getType().getId())?.let { NpcDefinition(it) }
            return npcDef?.name ?: "null"
        }

    override fun isMouseOverObj(): Boolean {
        val mousePoint = ctx?.let { Point(it.mouse.getX(), it.mouse.getY()) }
        return getConvexHull().contains(mousePoint)
    }

    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return ctx?.client.let { it?.let { it1 -> Calculations.worldToScreen(region.x, region.y, npc.getHeight(), ctx!!) } }
                ?: Point(-1, -1)
    }

    fun getConvexHull(): Polygon {
        return ctx?.let {
            getConvexHull(
                    this.npc,
                    it
            )
        } ?: Polygon()

    }

    fun getBaseX(): Int {
        return this.npc.getX() / 128 + ctx!!.client.getBaseX()
    }

    fun getBaseY(): Int {
        return this.npc.getY() / 128 + ctx!!.client.getBaseY()
    }


    override fun getInteractPoint(): Point {
        return getRandomPoint(getConvexHull())
    }

    suspend fun walkTo() {
        this.getGlobalLocation().clickOnMiniMap()
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

    fun isTargeted(): Boolean {
        return npc.getTargetIndex() - 32768 == (ctx?.players?.getLocal()?.player?.getIndex())
    }

    fun getArea(): Area {
        val generatedTiles = arrayListOf<Tile>()
        val maxOffset = floor(this.npc.getSize() / 2.0).toInt()
        val x = getGlobalLocation().x
        val y = getGlobalLocation().y


        //NorthEast
        generatedTiles.add(Tile(x + maxOffset+1, y - maxOffset, ctx = ctx))

        //NorthWest
        generatedTiles.add(Tile(x - maxOffset, y - maxOffset, ctx = ctx))

        //SouthWest
        generatedTiles.add(Tile(x - maxOffset, y + maxOffset+1, ctx = ctx))
        //SouthEast
        generatedTiles.add(Tile(x + maxOffset+1, y + maxOffset+1, ctx = ctx))

        return Area(generatedTiles, ctx)
    }

    // This method will get the tiles around the getArea function.
    fun getBorderTiles(): ArrayList<Tile> {
        val generatedTiles = arrayListOf<Tile>()
        val maxOffset = floor((this.npc.getSize() + 1) / 2.0).toInt()
        val x = getGlobalLocation().x
        val y = getGlobalLocation().y
        //Sides
        for (offset in -maxOffset..maxOffset) {
            //sides
            generatedTiles.add(Tile(x - maxOffset, y + offset, z=ctx?.client?.getPlane()?:0, ctx = ctx))
            generatedTiles.add(Tile(x + maxOffset, y + offset, z=ctx?.client?.getPlane()?:0, ctx = ctx))
            //Left and right side
            generatedTiles.add(Tile(x + offset, y - maxOffset, z=ctx?.client?.getPlane()?:0, ctx = ctx))
            generatedTiles.add(Tile(x + offset, y + maxOffset, z=ctx?.client?.getPlane()?:0, ctx = ctx))
        }
        return generatedTiles
    }

    val type = getType(npc.getType().getId())

    fun getType(id: Int): NPCType? {
        this.ctx!!.client.getNPCType_cached().getHashTable().getBuckets().iterator().forEach { bucketItem ->
            if (bucketItem != null) {
                var type = bucketItem.getNext()
                while (type != null && type is NPCType) {
                    if (type.getKey().toInt() == id) {
                        var definition = type as NPCType?
                        if (type.getTransforms() != null) {
                            definition = transform(type)
                        }
                        return definition
                    }
                    type = type.getNext()
                }
            }
        }
        return null
    }
    fun transform(): NPCType? {
        return transform(type!!)
    }

    fun transform(type: NPCType): NPCType? {
        var var1 = -1
        val transforms = type.getTransforms()

        if (type.getTransformVarbit() != -1) {
            var1 = this.ctx?.client?.getVarbit(type.getTransformVarbit())!!
        } else if (type.getTransformVarp() != -1) {
            var1 = this.ctx?.client?.getVarps_main()!![type.getTransformVarp()]
        }

        val var2 = if (var1 >= 0 && var1 < transforms.lastIndex) {
            transforms[var1]
        } else {
            transforms[transforms.lastIndex]
        }

        return if (var2 == -1) null else getType(var2)
    }
}
