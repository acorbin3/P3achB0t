package com.p3achb0t.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

object PriceCheck {
    private val gson = Gson()
    private val itemNameMapping: MutableMap<String, Int> = HashMap()
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
        prices.size
        if (prices.size == 0) {
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
        println(prices.size)
        if (prices.size == 0) {
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
        return prices[id] ?: backupItemPrice
    }

    fun reload() {
        if (!isReloadEnabled && prices.size > 0) {
            return
        }
        try {
            val node = Unirest.get("https://rsbuddy.com/exchange/summary.json").asString()
            if (node.status != 200) {
                println(node.body)
                println("PriceCheck: " + "Failed to load prices. Result: " + node.body)
                return
            }
            val o = gson.fromJson(node.body, JsonObject::class.java)
            for (s in o.keySet()) {
                val price = gson.fromJson(o[s].asJsonObject, ItemPrice::class.java)
                val id = s.toInt()
                val name = price.name!!.toLowerCase()
                itemNameMapping.remove(name)
                itemNameMapping[name] = id
                if(id  == 13190){
                    println(price.buy_average)
                }
                if( id in prices && price.buy_average > 0) {
                    prices.remove(id)
                    prices[id] = price
                }
                if(id !in prices){
                    prices[id] = price
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

    class ItemPrice {
        val id: Int = 0
        val name: String = ""
        val isMembers = false
        var buy_average = 0
        var sell_average = 0
        var overall_average = 0
        var overall_quantity = 0

    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Doing reload")
        PriceCheck.reload()
        println(PriceCheck.getPrice(2434).sell_average)
    }
}