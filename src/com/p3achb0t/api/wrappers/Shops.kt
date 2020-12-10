package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.utils.Time
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlin.math.abs
import kotlin.random.Random

class Shops(val ctx: Context) {
    companion object {
        val PARENT = 300
        val CHILD = 16

    }

    fun  isShopOpen(): Boolean{
        val shop = ctx.widgets.find(PARENT, CHILD)
        return shop != null && abs(shop.getCycle() - ctx.client.getCycle()) <400
    }
    suspend fun buyItem(id: Int){
        val shop = ctx.widgets.find(PARENT, CHILD)
        val childArray = shop?.getChildren()
        childArray?.forEachIndexed { index, component ->
            if(component.getItemId() == id){
                val buyItem = WidgetItem(shop, index = index, ctx=ctx)
                buyItem.click()
                Utils.sleepUntil({ ctx.inventory.contains(id) })
            }
        }
    }

    suspend fun sellItem(id: Int ){
        ctx.inventory.getAll().forEach {
            if(it.id == id) {
                val countBefore = ctx.inventory.getCount(it.id, true)
                it.click()
                Time.sleep(Random.nextLong(1100, 1900))
                Utils.sleepUntil({ countBefore != ctx.inventory.getCount(it.id, true) })
            }
        }
    }
}