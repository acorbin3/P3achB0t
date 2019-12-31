package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context


class GameObjects(val ctx: Context) {
    val gameObjects: ArrayList<GameObject>
        get() {
            val gameObjects = ArrayList<GameObject>()
            val region = ctx.client.getScene()

            region.getTiles().iterator().forEach { plane ->
                plane.iterator().forEach { row ->
                    row.iterator().forEach { tile ->
                        if (tile != null) {
                            if (tile.getScenery().isNotEmpty()) {
                                tile.getScenery().iterator().forEach {
                                    if (it != null && it.getTag() > 0) {
                                        gameObjects.add(GameObject(it, ctx = ctx))

                                    }
                                }
                            }
                        }
                    }
                }

            }
            return gameObjects
        }

    fun find(id: Int, tile: Tile = Tile(), sortByDistance: Boolean = false): ArrayList<GameObject> {
        val gameObjects = ArrayList<GameObject>()
        val region = ctx.client.getScene()

        //Default tile we will iterate over the region
        if (tile.x == -1 && tile.y == -1) {

            region.getTiles().iterator().forEach { plane ->
                plane.iterator().forEach { row ->
                    row.iterator().forEach { colTile ->
                        if (colTile != null) {
                            if (colTile.getScenery().isNotEmpty()) {
                                colTile.getScenery().iterator().forEach {
                                    if (it != null) {
                                        val gmObj = GameObject(it, ctx = ctx)
                                        if (gmObj.id == id)
                                            gameObjects.add(gmObj)

                                    }
                                }
                            }
                            if (colTile.getWall() != null) {
                                val boundaryObject = colTile.getWall()
                                val gmObj = GameObject(wallObject = boundaryObject, ctx = ctx)
                                if (gmObj.id == id)
                                    gameObjects.add(gmObj)
                            }
                        }
                    }
                }
            }
        } else {
            //TODO - Specific tile we will zoom into that specific spot. This should be faster

        }
        if (sortByDistance) {
            val local = ctx.players.getLocal()
            gameObjects.sortBy { it.distanceTo(local) }
        }
        return gameObjects
    }

    fun find(name: String, tile: Tile = Tile(), sortByDistance: Boolean = false): ArrayList<GameObject> {
        val gameObjects = ArrayList<GameObject>()
        val region = ctx.client.getScene()

        //Default tile we will iterate over the region
        if (tile.x == -1 && tile.y == -1) {

            region.getTiles().iterator().forEach { plane ->
                plane.iterator().forEach { row ->
                    row.iterator().forEach { colTile ->
                        if (colTile != null) {
                            if (colTile.getScenery().isNotEmpty()) {
                                colTile.getScenery().iterator().forEach {
                                    if (it != null) {
                                        val gmObj = GameObject(it, ctx = ctx)
                                        if (gmObj.name.toLowerCase() == name.toLowerCase())
                                            gameObjects.add(gmObj)

                                    }
                                }
                            }
                            if (colTile.getWall() != null) {
                                val boundaryObject = colTile.getWall()
                                val gmObj = GameObject(wallObject = boundaryObject, ctx = ctx)
                                if (gmObj.name.toLowerCase() == name.toLowerCase())
                                    gameObjects.add(gmObj)
                            }
                        }
                    }
                }
            }
        } else {
            //TODO - Specific tile we will zoom into that specific spot. This should be faster

        }
        if (sortByDistance) {
            val local = ctx.players.getLocal()
            gameObjects.sortBy { it.distanceTo(local) }
        }
        return gameObjects
    }
}