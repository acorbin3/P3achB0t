package com.p3achb0t.api.wrappers

import com.p3achb0t._runestar_interfaces.Inventory


class Items {
    data class Item(val id: Int, val stackSize: Int)
    companion object {
        fun getItemComposite(id: Int) {
            val itemTable = Client.client.getItemContainers()
            itemTable.getBuckets().iterator().forEach {
                if (it != null) {
                    var item = it.getNext()
                    while (item != null && item != it) {
                        if (item is Inventory) {
                            println("Found Inventory ${item.getIds()}.")
                            item.getIds().iterator().forEach {
                                print("$it,")
                            }
                            println()
                            item.getQuantities().iterator().forEach {
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
            val itemTable = Client.client.getItemContainers()
            itemTable.getBuckets().iterator().forEach {
                if (it != null) {
                    var item = it.getNext()
                    while (item != null && item != it) {
                        if (item is Inventory && item.getKey().toInt() == nodeId) {
                            return Item(
                                item.getIds()[index],
                                item.getQuantities()[index]
                            )
                        }
                        item = item.getNext()
                    }
                }
            }
            return Item(-1, -1)
        }


        fun dumpItems() {
            val itemTable = Client.client.getItemContainers()
            itemTable.getBuckets().iterator().forEach {
                if (it != null) {
                    var item = it.getNext()
                    while (item != null && item != it) {
                        if (item is Inventory) {
                            println("Found Inventory ${item.getKey()}.")
                            item.getIds().iterator().forEach {
                                print("$it,")
                            }
                            println()
                            item.getQuantities().iterator().forEach {
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