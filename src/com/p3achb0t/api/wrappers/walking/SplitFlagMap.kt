package com.p3achb0t.api.wrappers.walking

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import com.google.common.cache.Weigher
import com.google.common.util.concurrent.UncheckedExecutionException
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.UncheckedIOException
import java.util.concurrent.ExecutionException
import java.util.zip.GZIPInputStream

abstract class SplitFlagMap(
    private val regionSize: Int,
    compressedRegions: Map<Position, ByteArray>,
    private val flagCount: Int
) {
    private val regionMaps: LoadingCache<Position, FlagMap>
    operator fun get(x: Int, y: Int, z: Int, flag: Int): Boolean {
        return try {
            regionMaps[Position(x / regionSize, y / regionSize)][x, y, z, flag]
        } catch (e: ExecutionException) {
            throw UncheckedExecutionException(e)
        }
    }

    class Position(val x: Int, val y: Int) {
        override fun equals(o: Any?): Boolean {
            return o is Position && o.x == x && o.y == y
        }

        override fun hashCode(): Int {
            return x * 31 + y
        }

        override fun toString(): String {
            return "($x, $y)"
        }
    }

    companion object {
        private const val MAXIMUM_SIZE = 20 * 1024 * 1024
    }

    init {
        regionMaps = CacheBuilder
            .newBuilder()
            .weigher(Weigher { k: Position?, v: FlagMap -> v.flags.size() / 8 } as Weigher<Position?, FlagMap>)
            .maximumWeight(MAXIMUM_SIZE.toLong())
            .build(CacheLoader.from { position: Position? ->
                val compressedRegion = compressedRegions[position]
                    ?: return@from FlagMap(
                        position!!.x * regionSize,
                        position.y * regionSize,
                        (position.x + 1) * regionSize - 1,
                        (position.y + 1) * regionSize - 1,
                        flagCount
                    )
                try {
                    GZIPInputStream(ByteArrayInputStream(compressedRegion)).use { `in` ->
                        return@from FlagMap(
                            `in`.readAllBytes(),
                            flagCount
                        )
                    }
                } catch (e: IOException) {
                    throw UncheckedIOException(e)
                }
            })
    }
}