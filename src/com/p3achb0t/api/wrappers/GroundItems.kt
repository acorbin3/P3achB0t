package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.api.ObjectPositionInfo
import com.p3achb0t.hook_interfaces.Item
import com.p3achb0t.hook_interfaces.Model

class GroundItems {
    companion object {
        fun getAllItems(): ArrayList<GroundItem> {
            val itemList = ArrayList<GroundItem>()
            val groundItems = Main.clientData.getGroundItemList()
            val groundItemModels = Main.clientData.getGroundItemModelCache()
            val tiles = Main.clientData.getRegion().getTiles()
            for ((iP, plane) in groundItems.withIndex()) {
                for ((iX, x) in plane.iterator().withIndex()) {
                    for ((iY, itemPile) in x.iterator().withIndex()) {
                        if (itemPile != null) {

                            var gi = itemPile.getTail()

                            if (gi != null) {
                                gi = gi.getPrevious()
                            }
                            if (gi != null) {
                                if (gi is Item) {
//                                    println(" ID: ${gi.getId()} ($iP,$iX,$iY) Found Item: ${gi.getItem_id()} stackSize: ${gi.getStackSize()}")
                                    groundItemModels.getHashTable().getBuckets().iterator().forEach {
                                        if (it != null) {
                                            var next = it.getNext()
                                            while (next.getId() > 0 && next is Model && next.getId().toInt() == gi.getItem_id()) {
                                                try {
                                                    val x = tiles[iP][iX][iY].getX() * 128 + 64
                                                    val y = tiles[iP][iX][iY].getY() * 128 + 64
                                                    val id = gi.getItem_id()
                                                    itemList.add(
                                                        GroundItem(
                                                            id,
                                                            ObjectPositionInfo(x, y, plane = iP)
                                                        )
                                                    )
                                                    next = next.getNext()
                                                } catch (e: Exception) {
                                                    println(e.stackTrace)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return itemList
        }

    }
}