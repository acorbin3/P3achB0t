package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.hook_interfaces.Item
import com.p3achb0t.hook_interfaces.ItemComposite
import com.p3achb0t.hook_interfaces.ItemNode

class Items {
    fun getItemComposite(id: Int) {
        val itemTable = Main.clientData.getItemTable()
        itemTable.getBuckets().iterator().forEach {
            if (it != null) {
                var item = it.getNext()
                while (item != null && item.getId() > 0) {
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
                    if (item is Item) {
                        println("Found Item ${item.getItem_id()}")
                    }
                    if (item is ItemComposite) {
                        println("Found ItemComposite ${item.getName()} ${item.getItemComposite_id()}")
                    }
                    item = item.getNext()
                }
            }
        }
    }
}