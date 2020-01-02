package com.p3achb0t.api.wrappers

import com.p3achb0t.client.configs.Constants
import org.runestar.cache.content.config.VarBitType
import org.runestar.cache.format.Cache
import org.runestar.cache.format.disk.DiskCache
import org.runestar.cache.format.net.NetCache
import org.runestar.cache.tools.MemCache
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.file.Path
import java.util.*

//Some cases the normal functions dont get all the right data like NPC names.
//Steps here would be to update the cache on start up and store the pointer there.
class Cache {
    companion object{
        val cachePath = ".cache"
        //There are some cases where the Context will initialize the Cache a few times, we only need to update the cache 1 time
        var cacheUpdated: Boolean = false
    }
    init {
        // Update Cache
        if(!cacheUpdated) {
            try {
                NetCache.connect(InetSocketAddress("oldschool7.runescape.com", NetCache.DEFAULT_PORT), Constants.REVISION).use { net ->
                    DiskCache.open(Path.of(cachePath)).use { disk ->
                        println("Updating Cache")
                        Cache.update(net, disk).join()
                        println("Complete: Cache updated")
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
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
}