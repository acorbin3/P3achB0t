//package com.p3achb0t.api.wrappers.walking
//
//import com.google.gson.GsonBuilder
//import com.p3achb0t.api.Context
//import java.net.URI
//import java.net.http.HttpClient
//import java.util.*
//
//class CollisionMapGenerator(val ctx: Context) {
//    companion object {
//        private val HTTP_CLIENT = HttpClient.newHttpClient()
//        private val GSON = GsonBuilder().create()
//        private val XTEA_URI = URI.create("https://archive.runestats.com/osrs/xtea/2021-05-05-rev195.json")
//        private val DOOR_NAMES = java.util.Set.of("Door", "Gate", "Large door")
//        private val REGULAR_DOORS: Set<Int> = HashSet()
//    }
//
//
//    private fun isDoorWithAction(id: Int, action: String): Boolean {
//
//
//        val isADoor = ctx.cache.getItemName()
//        val containsOpen = ctx.cache.getActions(id)?.contains("Open")
//        if (Arrays.stream<Any>(CacheSystem.cache.archive(2).group(6).fileIds).noneMatch { i: Any -> i == id }) {
//            return false
//        }
//        val `object`: Unit = CacheSystem.getObject(id)
//        return `object`.name != null && CollisionMapGenerator.DOOR_NAMES.contains(`object`.name) && Arrays.asList<Any>(
//            `object`.actions
//        ).contains(action)
//    }
//
//    private fun buildCollisionMap(regions: List<Int>): CollisionMap? {
//        val map = CollisionMap()
//        for (region in regions) {
//            map.createRegion(region)
//        }
//        for (region in regions) {
//            CollisionMapGenerator.buildCollisionMap(map, region)
//        }
//        return map
//    }
//
//    private fun buildCollisionMap(map: CollisionMap, region: Int) {
//        val regionX = region shr 8
//        val regionY = region and 0xff
//        val objects: Unit = GameObjectInstance.decodeRegion(
//            regionX,
//            regionY,
//            CacheSystem.cache.archive(5).group("l" + regionX + "_" + regionY).file(0)
//        )
//        val tiles: Unit = TileData.decodeRegion(
//            regionX,
//            regionY,
//            CacheSystem.cache.archive(5).group("m" + regionX + "_" + regionY).file(0)
//        )
//        for (dx in 0..63) {
//            for (dy in 0..63) {
//                for (dz in 0..3) {
//                    if (tiles.get(dz).get(dx).get(dy).flags() and 1 === 0) {
//                        continue
//                    }
//                    val x = regionX * 64 + dx
//                    val y = regionY * 64 + dy
//                    var z = dz
//                    if (tiles.get(1).get(dx).get(dy).flags() and 2 !== 0) {
//                        z--
//                        if (z < 0) {
//                            continue
//                        }
//                    }
//                    markBlocking(map, x, y, z, 0)
//                    markBlocking(map, x, y, z, 1)
//                    markBlocking(map, x, y, z, 2)
//                    markBlocking(map, x, y, z, 3)
//                }
//            }
//        }
//        for (instance in objects) {
//            val baseObject: Unit = CacheSystem.getObject(instance.`object`)
//            val transforms = if (baseObject.transforms != null) baseObject.transforms else intArrayOf(instance.`object`)
//            for (transformId in transforms) {
//                if (transformId == -1) {
//                    continue
//                }
//                markObject(map, tiles, instance, CacheSystem.getObject(transformId))
//            }
//        }
//    }
//
//
//    private fun markObject(
//        map: CollisionMap,
//        tiles: Array<Array<Array<TileData>>>,
//        instance: GameObjectInstance,
//        objectDef: ObjectDefinition
//    ) {
//        if (REGULAR_DOORS.contains(objectDef.id)) {
//            return
//        }
//        val x: Unit = instance.position.x()
//        val y: Unit = instance.position.y()
//        var z: Unit = instance.position.z()
//        val type: Unit = ObjectType.values().get(instance.type)
//        val category: Unit = type.category
//        if (type == ObjectType.WALL_CONNECTOR || type == ObjectType.WALL_PILLAR) {
//            return
//        }
//        val flags0: Unit = tiles[0][x % 64][y % 64].flags()
//        val flags1: Unit = tiles[1][x % 64][y % 64].flags()
//        val flagsP: Unit = tiles.get(z)[x % 64][y % 64].flags()
//        if (flags0 and 0x10 != 0 && flagsP and 2 == 0) {
//            return
//        }
//        if (objectDef.interactType === 0 || objectDef.hollow) {
//            return
//        }
//        if (flags1 and 2 != 0) {
//            if (z == 0) {
//                return
//            }
//            z--
//        }
//
//        when (category) {
//            REGULAR -> {
//                sizeX: Int
//                sizeY: Int
//                if (instance.orientation % 2 == 0) {
//                    sizeX = objectDef.sizeX;
//                    sizeY = objectDef.sizeY;
//                } else {
//                    sizeY = objectDef.sizeX;
//                    sizeX = objectDef.sizeY;
//                }
//
//                for (var dx = 0; dx < sizeX; dx++) {
//                    for (var dy = 0; dy < sizeY; dy++) {
//                        markBlocking(map, x + dx, y + dy, z, 0);
//                        markBlocking(map, x + dx, y + dy, z, 1);
//                        markBlocking(map, x + dx, y + dy, z, 2);
//                        markBlocking(map, x + dx, y + dy, z, 3);
//                    }
//                }
//            }
//
//            FLOOR_DECORATION -> {
//                if (objectDef.interactType == 1 && (objectDef.wall != 0 || objectDef.blocksGround)) {
//                    markBlocking(map, x, y, z, 0);
//                    markBlocking(map, x, y, z, 1);
//                    markBlocking(map, x, y, z, 2);
//                    markBlocking(map, x, y, z, 3);
//                }
//            }
//
//            WALL -> {
//                markBlocking(map, x, y, z, instance.orientation);
//
//                if (type == ObjectType.WALL_CORNER) {
//                    markBlocking(map, x, y, z, (instance.orientation + 1) % 4);
//                }
//            }
//        }
//    }
//        private fun markBlocking(map: CollisionMap, x: Int, y: Int, z: Int, orientation: Int) {
//        when (orientation) {
//             0 -> set(map, x - 1, y, z, 1); // west
//             1 -> set(map, x, y, z, 0); // north
//             2 -> set(map, x, y, z, 1); // east
//             3 -> set(map, x, y - 1, z, 0); // south
//        }
//    }
//
//    private operator fun set(map: CollisionMap, x: Int, y: Int, z: Int, w: Int) {
//        if (x == 3105 && y == 3162 && z == 2) {
//            println()
//        }
//        map[x, y, z, w] = false
//    }
//
//    private class RegionKey private constructor(val mapsquare: Int, val key: IntArray)
//}