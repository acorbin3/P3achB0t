package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.hook_interfaces.ItemNode

class Items {
    data class Item(val id: Int, val stackSize: Int)
    companion object {
        fun getItemComposite(id: Int) {
            val itemTable = Main.clientData.getItemTable()
            itemTable.getBuckets().iterator().forEach {
                if (it != null) {
                    var item = it.getNext()
                    while (item != null && item != it) {
                        if (item is ItemNode) {
                            println("Found ItemNode ${item.getId()}.")
                            item.getIds().iterator().forEach {
                                print("$it,")
                            }
                            println()
                            item.getStackSizes().iterator().forEach {
                                print("$it,")
                            }
                            println()
                        }
                        item = item.getNext()
                    }
                }
            }
        }

        fun getItemInfo(nodeId: Int, index: Int): Item {
            val itemTable = Main.clientData.getItemTable()
            itemTable.getBuckets().iterator().forEach {
                if (it != null) {
                    var item = it.getNext()
                    while (item != null && item != it) {
                        if (item is ItemNode && item.getId().toInt() == nodeId) {
                            return Item(
                                item.getIds()[index],
                                item.getStackSizes()[index]
                            )
                        }
                        item = item.getNext()
                    }
                }
            }
            return Item(-1, -1)
        }


        fun dumpItems() {
            val itemTable = Main.clientData.getItemTable()
            itemTable.getBuckets().iterator().forEach {
                if (it != null) {
                    var item = it.getNext()
                    while (item != null && item != it) {
                        if (item is ItemNode) {
                            println("Found ItemNode ${item.getId()}.")
                            item.getIds().iterator().forEach {
                                print("$it,")
                            }
                            println()
                            item.getStackSizes().iterator().forEach {
                                print("$it,")
                            }
                            println()
                        }
                        item = item.getNext()
                    }
                }
            }
        }
    }
}