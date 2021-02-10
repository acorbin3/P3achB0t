package com.p3achb0t.api.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.p3achb0t.client.util.Util

data class ItemConfig(var itemID: Int = 0, var purchase: Boolean = false)
class EquipmentConfig {
    var head: ItemConfig = ItemConfig()
    var cape: ItemConfig = ItemConfig()
    var neck: ItemConfig = ItemConfig()
    var weapon: ItemConfig = ItemConfig()
    var body: ItemConfig = ItemConfig()
    var shield: ItemConfig = ItemConfig()
    var legs: ItemConfig = ItemConfig()
    var gloves: ItemConfig = ItemConfig()
    var boots: ItemConfig = ItemConfig()
    var ring: ItemConfig = ItemConfig()
    var quiver: ItemConfig = ItemConfig()

    override fun toString(): String {
        return "Item\tID\tPurchase\n" +
                "head\t${head.itemID}\t${head.purchase}\n" +
                "cape\t${cape.itemID}\t${cape.purchase}\n" +
                "neck\t${neck.itemID}\t${neck.purchase}\n" +
                "weapon\t${weapon.itemID}\t${weapon.purchase}\n" +
                "shield\t${shield.itemID}\t${shield.purchase}\n" +
                "legs\t${legs.itemID}\t${legs.purchase}\n" +
                "gloves\t${gloves.itemID}\t${gloves.purchase}\n" +
                "boots\t${boots.itemID}\t${boots.purchase}\n" +
                "ring\t${ring.itemID}\t${ring.purchase}\n" +
                "quiver\t${quiver.itemID}\t${quiver.purchase}\n"
    }
    fun toJson():String{
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        return gsonPretty.toJson(this)
    }
}

class EquipmentConfigParser{
    var equipmentConfig = EquipmentConfig()
    fun loadConfg(path: String) : EquipmentConfig{
        val content = Util.readConfig(path)
        if(content.isEmpty()){
            println("ERROR: couldnt find file: $path")
            return equipmentConfig
        }
        val gson = Gson()
        equipmentConfig = gson.fromJson(content, object : TypeToken<EquipmentConfig>() {}.type)

        return equipmentConfig
    }
}