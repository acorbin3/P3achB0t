package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context


class GameObjects(val ctx: Context) {
    val gameObjects: ArrayList<GameObject>
        get() {
            val gameObjects = ArrayList<GameObject>()
            val region = ctx.client.getScene()
            region.getTiles().forEachIndexed { planeIndex, plane ->
                plane.iterator().forEach { row ->
                    row.iterator().forEach { tile ->
                        if (tile != null) {
                            if (tile.getScenery().isNotEmpty()) {
                                tile.getScenery().iterator().forEach {
                                    if (it != null && it.getTag() > 0) {
                                        gameObjects.add(GameObject(it, plane = planeIndex, ctx = ctx))

                                    }
                                }
                            }
                            if (tile.getFloorDecoration() != null) {
                                val floorDecoration = tile.getFloorDecoration()
                                if (floorDecoration.getTag() > 0) {
                                    val gmObj =
                                        GameObject(floorDecoration = floorDecoration, plane = planeIndex, ctx = ctx)
                                    gameObjects.add(gmObj)
                                }
                            }
                            if (tile.getWall() != null) {
                                val boundaryObject = tile.getWall()
                                if (boundaryObject.getTag() > 0) {
                                    val gmObj = GameObject(wallObject = boundaryObject, plane = planeIndex, ctx = ctx)
                                    gameObjects.add(gmObj)
                                }
                            }

                            if(tile.getWallDecoration() != null){
                                val wallDecoration = tile.getWallDecoration()
                                if (wallDecoration.getTag() > 0) {
                                    val gmObj = GameObject(wallDecoration = wallDecoration, plane = planeIndex, ctx = ctx)
                                    gameObjects.add(gmObj)
                                }
                            }
                        }
                    }
                }

            }
            return gameObjects
        }


    fun find(id: Int, tile: Tile = Tile(), sortByDistance: Boolean = true): ArrayList<GameObject> {
        val returnedGameObjects = ArrayList<GameObject>()
        val updatedList = if(tile.isSameTile(Tile())){
            this.gameObjects.filter { it.id == id }.toMutableList()
        }else{
            this.gameObjects.filter { it.id == id && it.getGlobalLocation().isSameTile(tile) }.toMutableList()
        }
        if (sortByDistance) {
            val local = ctx.players.getLocal()
            updatedList.sortBy { it.distanceTo(local) }
        }
        updatedList.toCollection(returnedGameObjects)
        return returnedGameObjects
    }

    fun findAny(ids: IntArray, sortByDistance: Boolean = true): ArrayList<GameObject> {

        val returnedGameObjects = ArrayList<GameObject>()
        val updatedList = this.gameObjects.filter { it.id in ids }.toMutableList()
        if (sortByDistance) {
            val local = ctx.players.getLocal()
            updatedList.sortBy { it.distanceTo(local) }
        }
        updatedList.toCollection(returnedGameObjects)
        return returnedGameObjects
    }

    fun findinArea(
        name: String,
        area: Area,
        tile: Tile = Tile(),
        sortByDistance: Boolean = false
    ): ArrayList<GameObject> {
        val returnedGameObjects = ArrayList<GameObject>()
        val updatedList = this.gameObjects.filter {
            it.name.equals(
                name,
                ignoreCase = true
            ) && area.containsOrIntersects(it.getGlobalLocation())
        }.toMutableList()
        if (sortByDistance) {
            val local = ctx.players.getLocal()
            updatedList.sortBy { it.distanceTo(local) }
        }
        updatedList.toCollection(returnedGameObjects)

        return returnedGameObjects
    }

    fun findinArea(id: Int, area: Area, tile: Tile = Tile(), sortByDistance: Boolean = true): ArrayList<GameObject> {
        return findinArea(intArrayOf(id), area, tile, sortByDistance)
    }

    fun findinArea(
        ids: IntArray,
        area: Area,
        tile: Tile = Tile(),
        sortByDistance: Boolean = true
    ): ArrayList<GameObject> {

        val returnedGameObjects = ArrayList<GameObject>()
        val updatedList = this.gameObjects.filter {
            it.id in ids && area.containsOrIntersects(it.getGlobalLocation())
        }.toMutableList()
        if (sortByDistance) {
            val local = ctx.players.getLocal()
            updatedList.sortBy { it.distanceTo(local) }
        }
        updatedList.toCollection(returnedGameObjects)

        return returnedGameObjects

    }

    fun findNearest(id: Int): GameObject? {
        val items = find(id, sortByDistance = true)
        return if (items.isNotEmpty()) {
            items.first()
        } else {
            null
        }
    }


    fun find(name: String, tile: Tile = Tile(), sortByDistance: Boolean = true): ArrayList<GameObject> {

        val returnedGameObjects = ArrayList<GameObject>()
        val updatedList = if (tile.isSameTile(Tile())) {
            this.gameObjects.filter {
                it.name.equals(
                    name,
                    ignoreCase = true
                )
            }.toMutableList()
        } else {
            this.gameObjects.filter {
                it.name.equals(
                    name,
                    ignoreCase = true
                ) && tile.isSameTile(it.getGlobalLocation())
            }.toMutableList()
        }
        if (sortByDistance) {
            val local = ctx.players.getLocal()
            updatedList.sortBy { it.distanceTo(local) }
        }
        updatedList.toCollection(returnedGameObjects)

        return returnedGameObjects

    }
}