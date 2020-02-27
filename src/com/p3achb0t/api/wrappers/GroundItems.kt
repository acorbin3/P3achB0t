package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Node
import com.p3achb0t._runestar_interfaces.Obj
import com.p3achb0t._runestar_interfaces.Tile
import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.ObjectPositionInfo

class GroundItems(val ctx: Context) {

    fun getAllItems(): ArrayList<GroundItem> {

        val itemList = ArrayList<GroundItem>()
        val groundItems = ctx.client.getObjStacks()
        val tiles = ctx.client.getScene().getTiles()

        //groundItems = [4][104][104]
        // height, r_x, r_y
        // tile specific, not obj specific.
        // stores if tile has or not, only 1 needs to be present.


        groundItems.forEachIndexed { planeIndex, groundObjs ->
            groundObjs.forEachIndexed { xIndex, groundObjPlanes ->
                groundObjPlanes.forEachIndexed groundobjectplane@{ yIndex, groundObjByPlane ->
                    if (groundObjByPlane == null) return@groundobjectplane


                    val sentinel = groundObjByPlane.getSentinel()
                    var cur : Node? = null
                    if (sentinel != null)
                        cur  = sentinel.getPrevious()

                    while(cur != null && cur != sentinel){
                        if (cur is Obj) {
                            try {
                                addGroundItem(cur, tiles, planeIndex, xIndex, yIndex, itemList)

                            } catch (e: Exception) {
                                e.stackTrace.iterator().forEach {
                                    println(it)
                                }
                            }
                        }
                        cur = cur.getPrevious()
                    }
                }
            }

        }
        return itemList
    }

    private fun addGroundItem(thirdItem: Obj, tiles: Array<Array<Array<Tile>>>, planeIndex: Int, xIndex: Int, yIndex: Int, itemList: ArrayList<GroundItem>) {
        val stacksize = thirdItem.getQuantity()
        val x = tiles[planeIndex][xIndex][yIndex].getX() * 128 + 64
        val y = tiles[planeIndex][xIndex][yIndex].getY() * 128 + 64
        val id = thirdItem.getId()
        itemList.add(
                GroundItem(
                        ctx,
                        id,
                        ObjectPositionInfo(x, y, plane = planeIndex),
                        stacksize
                )
        )
    }


    fun getItempred(itemid: IntArray): ArrayList<GroundItem> {
        val itemList = ArrayList<GroundItem>()
        val groundItems = ctx.client.getObjStacks()
        val tiles = ctx.client.getScene().getTiles()

        groundItems.forEachIndexed { groundItemIndex, groundObjs ->
            groundObjs.forEachIndexed { planeIndex, groundObjPlanes ->
                groundObjPlanes.forEachIndexed groundobjectplane@{ index, groundObjByPlane ->

                    if (groundObjByPlane == null) return@groundobjectplane

                    var obj = groundObjByPlane.getSentinel()

                    if (obj != null)
                        obj = obj.getPrevious()
                    if (obj is Obj) {
                        try {
                            val stacksize = obj.getQuantity()
                            val x = tiles[groundItemIndex][planeIndex][index].getX() * 128 + 64
                            val y = tiles[groundItemIndex][planeIndex][index].getY() * 128 + 64
                            var id = obj.getId()
                            itemid.forEach {
                                if (it == id) {
                                    itemList.add(
                                            GroundItem(
                                                    ctx,
                                                    id,
                                                    ObjectPositionInfo(x, y, plane = groundItemIndex),
                                                    stacksize
                                            )
                                    )
                                }
                            }

                        } catch (e: Exception) {
                            println(e.stackTrace)
                        }
                    }
                }
            }
        }
        return itemList
    }

    fun getItempredinarea(itemid: IntArray, tile: com.p3achb0t.api.wrappers.Tile): ArrayList<GroundItem> {
        val itemList = ArrayList<GroundItem>()
        val groundItems = ctx.client.getObjStacks()
        val tiles = ctx.client.getScene().getTiles()
        val lootArea = Area(
                Tile(tile.x - 3, tile.y + 3, ctx = ctx),
                Tile(tile.x + 3, tile.y - 3, ctx = ctx), ctx = ctx
        )

        groundItems.forEachIndexed { groundItemIndex, groundObjs ->
            groundObjs.forEachIndexed { planeIndex, groundObjPlanes ->
                groundObjPlanes.forEachIndexed groundobjectplane@{ index, groundObjByPlane ->

                    if (groundObjByPlane == null) return@groundobjectplane

                    var obj = groundObjByPlane.getSentinel()

                    if (obj != null)
                        obj = obj.getPrevious()
                    if (obj is Obj) {
                        try {
                            val stacksize = obj.getQuantity()
                            val x = tiles[groundItemIndex][planeIndex][index].getX() * 128 + 64
                            val y = tiles[groundItemIndex][planeIndex][index].getY() * 128 + 64
                            val LootTile =  Tile(x, y, ctx = ctx)
                            var id = obj.getId()
                            itemid.forEach {
                                if (it == id && lootArea.containsOrIntersects(LootTile)) {
                                    itemList.add(
                                            GroundItem(
                                                    ctx,
                                                    id,
                                                    ObjectPositionInfo(x, y, plane = groundItemIndex),
                                                    stacksize
                                            )
                                    )
                                }
                            }

                        } catch (e: Exception) {
                            println(e.stackTrace)
                        }
                    }
                }
            }
        }
        return itemList
    }

    fun getItempredinarea(itemid: IntArray, area: Area): ArrayList<GroundItem> {
        val itemList = ArrayList<GroundItem>()
        val groundItems = ctx.client.getObjStacks()
        val tiles = ctx.client.getScene().getTiles()


        groundItems.forEachIndexed { groundItemIndex, groundObjs ->
            groundObjs.forEachIndexed { planeIndex, groundObjPlanes ->
                groundObjPlanes.forEachIndexed groundobjectplane@{ index, groundObjByPlane ->

                    if (groundObjByPlane == null) return@groundobjectplane

                    var obj = groundObjByPlane.getSentinel()

                    if (obj != null)
                        obj = obj.getPrevious()
                    if (obj is Obj) {
                        try {
                            val stacksize = obj.getQuantity()
                            val x = tiles[groundItemIndex][planeIndex][index].getX() * 128 + 64
                            val y = tiles[groundItemIndex][planeIndex][index].getY() * 128 + 64
                            var X = (x - 64) / 128
                            var Y = (y - 64) / 128
                            val basex = ctx.client.getBaseX()
                            val basey = ctx.client.getBaseY()
                            val LootTile =  Tile(X + basex, Y + basey, ctx = area.ctx)
                            var id = obj.getId()
                            itemid.forEach {
                                if (it == id && area.containsOrIntersects(LootTile)) {
                                    itemList.add(
                                            GroundItem(
                                                    ctx,
                                                    id,
                                                    ObjectPositionInfo(x, y, plane = groundItemIndex),
                                                    stacksize
                                            )
                                    )
                                }
                            }

                        } catch (e: Exception) {
                            println(e.stackTrace)
                        }
                    }
                }
            }
        }
        return itemList
    }

    fun find(itemID: Int, sortByDistance: Boolean = true): List<GroundItem> {
        val items = getAllItems()
        var filteredItems = items.filter { it.id == itemID }
        if (sortByDistance)
            filteredItems.sortedBy { it.distanceTo() }
        return filteredItems
    }

    fun findNearest(itemID: Int): GroundItem {
        return find(itemID)[0]
    }
}
