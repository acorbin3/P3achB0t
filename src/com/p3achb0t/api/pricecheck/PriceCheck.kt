package com.p3achb0t.api.pricecheck

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import com.p3achb0t.scripts_private.Zulrah.Constants
import com.p3achb0t.scripts_private.ZulrahGearer.Gear
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

object PriceCheck {
    private val gson = Gson()
    private val itemNameMapping: MutableMap<String, Int> = HashMap()
    private val itemIDToNameMapping: MutableMap<Int,String> = HashMap()
    private val prices: MutableMap<Int, ItemPrice> = HashMap()
    private const val reloadMinutes = 30
    private var isReloadEnabled = true

    init {
        val timer: Job = startCoroutineTimer(0, 1000 * 60 * 30) {
            reload()
        }
    }

    fun startCoroutineTimer(delayMillis: Long = 0, repeatMillis: Long = 0, action: () -> Unit) = GlobalScope.launch {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }

    fun getPrice(name: String, backupPrice: Int=0): ItemPrice? {
        if (prices.isEmpty()) {
            reload()
        }
        val backupItemPrice = ItemPrice()
        backupItemPrice.buy_average = backupPrice
        backupItemPrice.overall_average = backupPrice
        backupItemPrice.sell_average = backupPrice
        val id = itemNameMapping.getOrDefault(name.toLowerCase(), -1)
        return if (id == -1) backupItemPrice else getPrice(id, backupPrice)
    }

    fun getPrice(id: Int, backupPrice: Int= 0): ItemPrice {
        if (prices.isEmpty()) {
            println("Doing another reload")
            reload()
        }
        val backupItemPrice = ItemPrice()
        backupItemPrice.buy_average = backupPrice
        backupItemPrice.overall_average = backupPrice
        backupItemPrice.sell_average = backupPrice

        // REturn backup price if the buy average is zero and backup price is provided
        if(id in prices && prices[id]?.overall_average == 0 && backupPrice > 0){
            return backupItemPrice
        }
        println("id $id - ${prices[id]?.overall_average}")
        return prices[id] ?: backupItemPrice
    }

    fun reload() {
        if (!isReloadEnabled && prices.size > 0) {
            return
        }
        try {
            getItemInfo()
            val node = Unirest.get("https://prices.runescape.wiki/api/v1/osrs/latest").asString()
            if (node.status != 200) {
                println(node.body)
                println("PriceCheck: " + "Failed to load prices. Result: " + node.body)
                return
            }
            val o = gson.fromJson(node.body, JsonObject::class.java)
            val data = gson.fromJson(o["data"],JsonObject::class.java)
            for (s in data.keySet()) {
                val price = gson.fromJson(data[s].asJsonObject, ItemSimplePrice::class.java)
                val id = s.toInt()
                val itemPrice = ItemPrice()
                itemPrice.id = id
                itemPrice.overall_average = price.high
                itemPrice.buy_average = price.high
                itemPrice.sell_average = price.high
                itemPrice.sell_average = price.high
                itemPrice.name = itemIDToNameMapping[id] ?: "?NF?"



                if( id in prices && price.high > 0) {
                    prices.remove(id)
                    prices[id] = itemPrice
                }
                if(id !in prices){
                    prices[id] = itemPrice
                }
            }
        } catch (e: UnirestException) {
            e.printStackTrace()
            println(e)
        }
    }

    fun setShouldReload(value: Boolean) {
        isReloadEnabled = value
    }

    fun getItemInfo(){
        val node = Unirest.get("https://prices.runescape.wiki/api/v1/osrs/mapping").asString()
        if (node.status != 200) {
            println(node.body)
            println("PriceCheck: " + "Failed to get mapping info. Result: " + node.body)
            return
        }
        val o = gson.fromJson(node.body, JsonArray::class.java)
        for (s in o) {
            val info = gson.fromJson(s.asJsonObject, ItemInfo::class.java)
//            println(info)
            val name = info.name.toLowerCase()
//            itemNameMapping.remove(name)
            itemNameMapping[name] = info.id
            itemIDToNameMapping[info.id] = name
        }
    }

}

object MainPrice {

    @JvmStatic
    fun main(args: Array<String>) {
        println("Doing reload")
        PriceCheck.reload()
        println(PriceCheck.getPrice(2434).sell_average)
    }
}