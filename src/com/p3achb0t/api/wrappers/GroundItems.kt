package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.EvictingDualNodeHashTable
import com.p3achb0t._runestar_interfaces.Model
import com.p3achb0t._runestar_interfaces.Obj
import com.p3achb0t._runestar_interfaces.Tile
import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.ObjectPositionInfo

class GroundItems(val ctx: Context) {

    fun getAllItems(): ArrayList<GroundItem> {

        val itemList = ArrayList<GroundItem>()
        val groundItems = ctx.client.getObjStacks()
        val groundItemModels = ctx.client.getObjType_cachedModels()
        val tiles = ctx.client.getScene().getTiles()

        //groundItems = [4][104][104]
        // height, r_x, r_y
        // tile specific, not obj specific.
        // stores if tile has or not, only 1 needs to be present.

        val stackedGroundItems = mutableListOf<Obj>()

        tiles.iterator().forEach {yTile->
            yTile.iterator().forEach {xTile ->

                xTile.iterator().forEach {
//                    it.
                }
            }

        }
        groundItems.forEachIndexed { planeIndex, groundObjs ->
            groundObjs.forEachIndexed { xIndex, groundObjPlanes ->
                groundObjPlanes.forEachIndexed groundobjectplane@{ yIndex, groundObjByPlane ->
                    if (groundObjByPlane == null) return@groundobjectplane


                    var obj = groundObjByPlane.getSentinel()

                    if (obj != null)
                        obj = obj.getPrevious()
                    if (obj is Obj) {
                        try {
                            val stacksize = obj.getQuantity()
//                            tiles[planeIndex][xIndex][yIndex].getObjStack().getFirst()
                            val x = tiles[planeIndex][xIndex][yIndex].getX() * 128 + 64
                            val y = tiles[planeIndex][xIndex][yIndex].getY() * 128 + 64
                            val id = obj.getId()
                            itemList.add(
                                    GroundItem(
                                            ctx,
                                            id,
                                            ObjectPositionInfo(x, y, plane = planeIndex),
                                            stacksize
                                    )
                            )

                        } catch (e: Exception) {
                            println(e.stackTrace)
                        }
                    }
                    val observableTile = tiles[planeIndex][xIndex][yIndex]
                    if (observableTile != null) {
                        val objectStackPile = observableTile.getObjStack()
                        if (objectStackPile != null) {
                            val firstItem = objectStackPile.getFirst()
//                        println("Tile: ${observableTile.getX()},${observableTile.getY()}")
                            if (firstItem != null && firstItem is Obj) {
//                            println("\tFirst: ID: ${firstItem.getKey()} ($groundItemIndex,$planeIndex,$index) Found Item: ${firstItem.getId()} stackSize: ${firstItem.getQuantity()}")
                                addIfGroundModelExistInHashTable(groundItemModels, firstItem, tiles, planeIndex, xIndex, yIndex, itemList)
                            }
                            val secondItem = objectStackPile.getSecond()
                            if (secondItem != null && secondItem is Obj) {
//                            println("\tSecond: ID: ${secondItem.getKey()} ($groundItemIndex,$planeIndex,$index) Found Item: ${secondItem.getId()} stackSize: ${secondItem.getQuantity()}")
                                addIfGroundModelExistInHashTable(groundItemModels, secondItem, tiles, planeIndex, xIndex, yIndex, itemList)
                            }
                            val thirdItem = objectStackPile.getThird()
                            if (thirdItem != null && thirdItem is Obj) {
//                            println("\tThird: ID: ${thirdItem.getKey()} ($groundItemIndex,$planeIndex,$index) Found Item: ${thirdItem.getId()} stackSize: ${thirdItem.getQuantity()}")
                                addIfGroundModelExistInHashTable(groundItemModels, thirdItem, tiles, planeIndex, xIndex, yIndex, itemList)
                            }
                        }
                    }
                }

            }

        }
        return itemList
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
                            val id = obj.getId()
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


    fun addIfGroundModelExistInHashTable(groundItemModels: EvictingDualNodeHashTable, gi: Obj, tiles: Array<Array<Array<Tile>>>, groundItemIndex: Int, planeIndex: Int, index: Int, itemList: ArrayList<GroundItem>) {
//        println("Looking for item: ${gi.getId()}")
        val listOfIds = ArrayList<Int>()
        groundItemModels.getHashTable().getBuckets().iterator().forEach {
            if (it != null) {

                var next = it.getNext()
                while (next != null && next != it ) {
                    try {
                        listOfIds.add(next.getKey().toInt())
                        if(next is Model && next.getKey().toInt() == gi.getId()) {
                            val x = tiles[groundItemIndex][planeIndex][index].getX() * 128 + 64
                            val y = tiles[groundItemIndex][planeIndex][index].getY() * 128 + 64
                            val id = gi.getId()

                            itemList.add(
                                    GroundItem(
                                            ctx,
                                            id,
                                            ObjectPositionInfo(x, y, plane = groundItemIndex)
                                    )
                            )
                        }
                        next = next.getNext()
                    } catch (e: Exception) {
                        println(e.stackTrace)
                    }
                }
            }
        }
//        listOfIds.forEach { print(" " + it) }
//        if(listOfIds.isNotEmpty())
//            println()
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
