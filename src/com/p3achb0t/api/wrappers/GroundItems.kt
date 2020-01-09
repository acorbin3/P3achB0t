package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Obj
import com.p3achb0t.api.Context
import com.p3achb0t.api.ObjectPositionInfo
import tornadofx.getProperty

class GroundItems(val ctx: Context) {

    fun getTileItemsInRegion() {


    }

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
                            tiles[groundItemIndex][planeIndex][index].getObjStack().getFirst()
                            val x = tiles[groundItemIndex][planeIndex][index].getX() * 128 + 64
                            val y = tiles[groundItemIndex][planeIndex][index].getY() * 128 + 64
                            val id = obj.getId()
                            itemList.add(
                                    GroundItem(
                                            ctx,
                                            id,
                                            ObjectPositionInfo(x, y, plane = groundItemIndex),
                                            stacksize
                                    )
                            )

                        } catch (e: Exception) {
                            println(e.stackTrace)
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
