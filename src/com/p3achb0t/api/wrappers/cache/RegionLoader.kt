package com.p3achb0t.api.wrappers.cache

import com.p3achb0t.client.cache.IndexType
import com.p3achb0t.client.cache.definitions.loaders.LocationsLoader
import com.p3achb0t.client.cache.definitions.loaders.MapLoader
import com.p3achb0t.client.cache.fs.Archive
import com.p3achb0t.client.cache.fs.Storage
import com.p3achb0t.client.cache.fs.Store
import com.p3achb0t.client.cache.region.Region
import com.p3achb0t.client.cache.util.XteaKeyManager

class RegionLoader {
    companion object{
        private var keyManager: XteaKeyManager? = null
        private val MAX_REGION = 32768
        init {
            keyManager = XteaKeyManager()
            keyManager?.loadKeys()
        }

        fun loadRegions(store: Store){
            for (i in 0 until MAX_REGION) {
                val region: Region? = loadRegionFromArchive(i,store)
                if (region != null) {
                    RSCache.regions[i] = region
                }
            }
        }

        private fun loadRegionFromArchive(i: Int, store: Store): Region? {
            val x = i shr 8
            val y = i and 0xFF
            val index = store.getIndex(IndexType.MAPS)
            val storage: Storage? = store.getStorage()
            val land: Archive? = index?.findArchiveByName("l" + x + "_" + y)
            val map: Archive? = index?.findArchiveByName("m" + x + "_" + y)

            assert(map == null == (land == null))
            if (map == null || land == null) {
                return null
            }
            var data: ByteArray = map.decompress(storage?.loadArchive(map))
            val mapDef = MapLoader().load(x, y, data)
            val region = Region(i)
            region.loadTerrain(mapDef)
            val keys: IntArray? = keyManager?.getKeys(i)
            if (keys != null) {
                try {
                    data = land.decompress(storage?.loadArchive(land), keys)
                    val locDef = LocationsLoader().load(x, y, data)
                    region.loadLocations(locDef)
                } catch (ex: Exception) {
                }
            }
            return region
        }
    }
}