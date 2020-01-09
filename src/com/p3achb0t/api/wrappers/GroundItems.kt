package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.*
import com.p3achb0t._runestar_interfaces.Tile
import com.p3achb0t.api.ObjectPositionInfo
import com.p3achb0t.api.Context

class GroundItems(val ctx: Context)  {
    fun getAllItems(): ArrayList<GroundItem> {
        val itemList = ArrayList<GroundItem>()
        val groundItems = ctx.client.getObjStacks()
        val groundItemModels = ctx.client.getObjType_cachedModels()
        val tiles = ctx.client.getScene().getTiles()
        for ((iP, plane) in groundItems.withIndex()) {
            for ((iX, x) in plane.iterator().withIndex()) {
                for ((iY, itemPile) in x.iterator().withIndex()) {
                    if (itemPile != null) {

                        var gi = itemPile.getSentinel()


                        if (gi != null) {
                            gi = gi.getPrevious()
                        }
                        if (gi != null) {
                            if (gi is ObjectNode) {
                                println("Found ObjectNode")
                            }
                            if (gi is ObjStack) {
                                println("Found ObjStack")
                            }
                            if (gi is Obj) {
//                                    println("Found Obj")
//                                    println(" ID: ${gi.getKey()} ($iP,$iX,$iY) Found Item: ${gi.getId()} stackSize: ${gi.getQuantity()}")
                                addIfGroundModelExistInHashTable(groundItemModels, gi, tiles, iP, iX, iY, itemList)
                            }
                        }
                    }

                    //Now look at the tileObjectStack
                    val observableTile = tiles[iP][iX][iY]
                    if(observableTile != null) {
                        val objectStackPile = observableTile.getObjStack()
                        if (objectStackPile != null) {
                            val firstItem = objectStackPile.getFirst()
//                        println("Tile: ${observableTile.getX()},${observableTile.getY()}")
                            if (firstItem != null && firstItem is Obj) {
//                            println("\tFirst: ID: ${firstItem.getKey()} ($iP,$iX,$iY) Found Item: ${firstItem.getId()} stackSize: ${firstItem.getQuantity()}")
                            addIfGroundModelExistInHashTable(groundItemModels, firstItem, tiles, iP, iX, iY, itemList)
                            }
                            val secondItem = objectStackPile.getSecond()
                            if (secondItem != null && secondItem is Obj) {
//                            println("\tSecond: ID: ${secondItem.getKey()} ($iP,$iX,$iY) Found Item: ${secondItem.getId()} stackSize: ${secondItem.getQuantity()}")
                            addIfGroundModelExistInHashTable(groundItemModels, secondItem, tiles, iP, iX, iY, itemList)
                            }
                            val thirdItem = objectStackPile.getThird()
                            if (thirdItem != null && thirdItem is Obj) {
//                            println("\tThird: ID: ${thirdItem.getKey()} ($iP,$iX,$iY) Found Item: ${thirdItem.getId()} stackSize: ${thirdItem.getQuantity()}")
                            addIfGroundModelExistInHashTable(groundItemModels, thirdItem, tiles, iP, iX, iY, itemList)
                            }
                        }
                    }

                }
            }
        }
        return itemList
    }

    private fun addIfGroundModelExistInHashTable(groundItemModels: EvictingDualNodeHashTable, gi: Obj, tiles: Array<Array<Array<Tile>>>, iP: Int, iX: Int, iY: Int, itemList: ArrayList<GroundItem>) {
//        println("Looking for item: ${gi.getId()}")
        val listOfIds = ArrayList<Int>()
        groundItemModels.getHashTable().getBuckets().iterator().forEach {
            if (it != null) {
                var next = it.getNext()
                while (next != null && next != it && next is Model) {
                    try {
                        listOfIds.add(next.getKey().toInt())
                        if(next.getKey().toInt() == gi.getId()) {
                            val x = tiles[iP][iX][iY].getX() * 128 + 64
                            val y = tiles[iP][iX][iY].getY() * 128 + 64
                            val id = gi.getId()

                            itemList.add(
                                    GroundItem(
                                            ctx,
                                            id,
                                            ObjectPositionInfo(x, y, plane = iP)
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

    fun find(itemID: Int, sortByDistance: Boolean = true): List<GroundItem>{
        val items = getAllItems()
        var filteredItems = items.filter { it.id == itemID }
        if(sortByDistance)
            filteredItems.sortedBy { it.distanceTo() }
        return filteredItems
    }

    fun findNearest(itemID: Int): GroundItem{
        return find(itemID)[0]
    }
}
