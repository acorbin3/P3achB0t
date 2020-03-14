package com.p3achb0t.api.wrappers

import com.p3achb0t.client.configs.Constants
import org.runestar.cache.content.config.ConfigType
import org.runestar.cache.content.config.NPCType
import org.runestar.cache.content.config.ObjType
import org.runestar.cache.content.config.VarBitType
import org.runestar.cache.format.Cache
import org.runestar.cache.format.disk.DiskCache
import org.runestar.cache.format.net.NetCache
import org.runestar.cache.tools.MemCache
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.file.Path
import java.util.*
import kotlin.collections.HashMap

//Some cases the normal functions dont get all the right data like NPC names.
//Steps here would be to update the cache on start up and store the pointer there.
class Cache {
    companion object{
        val cachePath = ".cache"
        //There are some cases where the Context will initialize the Cache a few times, we only need to update the cache 1 time
        var cacheUpdated: Boolean = false
        lateinit var npcCacheInfo: Map<Int,NPCCacheType>
        lateinit var itemCacheInfo: Map<Int,ItemCacheType>
    }

    suspend fun updateCache(){
        // Update Cache
        if(!cacheUpdated) {
            try {

                NetCache.connect(InetSocketAddress("oldschool83.runescape.com", NetCache.DEFAULT_PORT), Constants.REVISION).use { net ->
                    DiskCache.open(Path.of(cachePath)).use { disk ->
                        println("Updating Cache")
                        Cache.update(net, disk).join()
                        println("Complete: Cache updated")
                    }
                }
                cacheUpdated = true
            } catch (e: IOException) {
                e.printStackTrace()
            }
            println("loading NPC info from cache")
            npcCacheInfo = getNPCInfo()
            println("Complete loading NPC info from cache")
            println("Loading item info from cache")
            itemCacheInfo = getItemInfo()
            println("Complete item info from cache")
        }
    }

    fun getItemName(id: Int): String{
        return if(cacheUpdated && id in itemCacheInfo)
            itemCacheInfo[id]?.name ?: id.toString()
        else
            ""
    }
    fun getItemID(name: String): IntArray{
        //Find all items with the same name. Return
        val returnedItems = itemCacheInfo.filter { name == name }
        return if(returnedItems.size > 0){
            returnedItems.keys.toIntArray()
        }else{
            intArrayOf(-1)
        }

    }


    fun getVarbitInfo(): ArrayList<VarbitData>{
        val varBitDataList = ArrayList<VarbitData>()

        try {
            DiskCache.open(Path.of(cachePath)).use { disk ->
                println("Getting VarBit info")
                val cache = MemCache.of(disk)
                for (file in cache.archive(VarBitType.ARCHIVE).group(VarBitType.GROUP).files()) {
                    val varbit = VarBitType()
                    varbit.decode(file.data())
                    varBitDataList.add(VarbitData(file.id(), varbit.startBit, varbit.endBit, varbit.baseVar))
                }
                println("Complete: getting varbit info")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return varBitDataList
    }

    data class NPCCacheType(val id: Int, val name: String, val modelIDs: IntArray)
    private fun getNPCInfo(): Map<Int,NPCCacheType>{
        val npcCacheInfo = HashMap<Int,NPCCacheType>()

        try {
            DiskCache.open(Path.of(cachePath)).use { disk ->
                val cache = MemCache.of(disk)
                for (file in cache.archive(VarBitType.ARCHIVE).group(NPCType.GROUP).files()) {
                    val npcType = NPCType()
                    npcType.decode(file.data())
                    file.id()
                    npcCacheInfo[file.id()] = NPCCacheType(file.id(),npcType.name, npcType.models?: IntArray(0))
//                    print("ID:${file.id()} Name: ${npcType.name} op: ")
//                    npcType.op.forEach { print(" $it") }
//                    print(" params:")
//                    npcType.params?.forEach { print(" ${it.key}:${it.value}") }
//                    print(" Models:")
//                    npcType.models?.forEach {
//                        print(" $it")
//                    }
//                    println("")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return npcCacheInfo
    }

    data class ItemCacheType(val id: Int, val name: String, val modelID: Int)
    private fun getItemInfo():  Map<Int,ItemCacheType>{
        val LitemCacheInfo = HashMap<Int,ItemCacheType>()
        try {

            DiskCache.open(Path.of(cachePath)).use { disk ->
                val cache = MemCache.of(disk)
                for (file in cache.archive(ConfigType.ARCHIVE).group(ObjType.GROUP).files()) {
                    val obj = ObjType()
                    obj.decode(file.data())
//                    println("Item id:  ${file.id()} model#:${obj.model} name: ${obj.name}")
                    LitemCacheInfo[file.id()] = ItemCacheType(file.id(),obj.name, obj.model)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return LitemCacheInfo
    }
}