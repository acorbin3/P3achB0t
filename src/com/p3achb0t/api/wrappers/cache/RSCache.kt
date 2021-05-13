package com.p3achb0t.api.wrappers.cache

import com.google.gson.GsonBuilder
import com.p3achb0t.api.wrappers.VarbitData
import com.p3achb0t.client.cache.*
import com.p3achb0t.client.cache.client.CacheClient
import com.p3achb0t.client.cache.definitions.loaders.VarbitLoader
import com.p3achb0t.client.cache.fs.*
import com.p3achb0t.client.cache.protocol.api.login.HandshakeResponseType
import com.p3achb0t.client.cache.region.Region
import com.p3achb0t.client.configs.Constants.Companion.REVISION
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Path


class RSCache {

    companion object{

        val cachePath = "cache/jagexcache/oldschool/LIVE"
        //There are some cases where the Context will initialize the Cache a few times, we only need to update the cache 1 time
        var cacheUpdated: Boolean = false

        var store: Store? = null

        var regions: HashMap<Int, Region> = HashMap()
        public lateinit var objectManager: ObjectManager
        lateinit var npcManager: NpcManager
        lateinit var itemManager: ItemManager
        lateinit var widgetManager: InterfaceManager

        fun load(){
            val cacheFolder = File(cachePath)
            if(!cacheFolder.exists()){
                cacheFolder.mkdirs()
            }
            store = Store(File(cachePath))
            store?.load()
            val cacheClient: CacheClient = CacheClient(store,REVISION)
            cacheClient.connect()
            val handshake = cacheClient.handshake()
            val result = handshake.get()
            println("Cache result: $result")

            if(result.equals(HandshakeResponseType.RESPONSE_OK)){
                cacheClient.download()
                cacheClient.close()
                store?.save()
            }

            store?.let {
//                loadRegions(it)
                objectManager = ObjectManager(store)
                objectManager.load()

                npcManager = NpcManager(store)
                npcManager.load()

                itemManager = ItemManager(store)
                itemManager.load()

                widgetManager = InterfaceManager(store)
                widgetManager.load()

            }
        }


    }

    fun getItemName(id: Int): String{
        return  itemManager.getItem(id).name
    }

    fun getActions(id: Int): Array<String>? {
        return objectManager.getObject(id).actions
    }

    fun getNPCActions(id: Int): Array<String>? {
        return npcManager.get(id).actions
    }

    fun getWidgetActions(parentId: Int, childID:Int) : Array<String>?{
        return widgetManager.getInterface(parentId,childID).actions
    }

    fun getVarbitInfo(): ArrayList<VarbitData> {
        val varBitDataList = ArrayList<VarbitData>()

        val storage: Storage = store!!.storage
        val index: Index = store!!.getIndex(IndexType.CONFIGS)
        val archive: Archive = index.getArchive(ConfigType.VARBIT.id)

        val archiveData: ByteArray = storage.loadArchive(archive)
        val files: ArchiveFiles = archive.getFiles(archiveData)
        for (file in files.files) {
            val loader = VarbitLoader()
            val varbit = loader.load(file.fileId, file.contents)
            varBitDataList.add(VarbitData(varbit.id,baseVar = varbit.index, startBit = varbit.leastSignificantBit,endBit = varbit.mostSignificantBit))
        }

        return varBitDataList
    }
}