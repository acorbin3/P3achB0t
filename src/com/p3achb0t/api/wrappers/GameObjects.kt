package com.p3achb0t.api.wrappers

import com.p3achb0t.Main

class GameObjects {
    companion object {
        val gameObjects: ArrayList<GameObject>
            get() {
                val gameObjects = ArrayList<GameObject>()
                val region = Main.clientData.getRegion()

                region.getTiles().iterator().forEach { plane ->
                    plane.iterator().forEach { row ->
                        row.iterator().forEach { tile ->
                            if (tile != null) {
                                if (tile.getObjects().isNotEmpty()) {
                                    tile.getObjects().iterator().forEach {
                                        if (it != null && it.getId() > 0) {
                                            gameObjects.add(GameObject(it))

                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                return gameObjects
            }

        fun find(id: Int, tile: Tile = Tile(), sortDistance: Boolean = false): ArrayList<GameObject> {
            val gameObjects = ArrayList<GameObject>()
            val region = Main.clientData.getRegion()

            //Default tile we will iterate over the region
            if (tile.x == -1 && tile.y == -1) {

                region.getTiles().iterator().forEach { plane ->
                    plane.iterator().forEach { row ->
                        row.iterator().forEach { colTile ->
                            if (colTile != null) {
                                if (colTile.getObjects().isNotEmpty()) {
                                    colTile.getObjects().iterator().forEach {
                                        if (it != null) {
                                            val gmObj = GameObject(it)
                                            if (gmObj.id == id)
                                                gameObjects.add(gmObj)

                                        }
                                    }
                                }
                                if (colTile.getBoundary() != null) {
                                    val boundaryObject = colTile.getBoundary()
                                    val gmObj = GameObject(boundaryObject = boundaryObject)
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
            if (sortDistance) {
                val local = Players.getLocal()
                gameObjects.sortBy { it.distanceTo(local) }
            }
            return gameObjects
        }

        fun find(name: String, tile: Tile = Tile(), sortDistance: Boolean = false): ArrayList<GameObject> {
            val gameObjects = ArrayList<GameObject>()
            val region = Main.clientData.getRegion()

            //Default tile we will iterate over the region
            if (tile.x == -1 && tile.y == -1) {

                region.getTiles().iterator().forEach { plane ->
                    plane.iterator().forEach { row ->
                        row.iterator().forEach { colTile ->
                            if (colTile != null) {
                                if (colTile.getObjects().isNotEmpty()) {
                                    colTile.getObjects().iterator().forEach {
                                        if (it != null) {
                                            val gmObj = GameObject(it)
                                            if (gmObj.name.toLowerCase() == name.toLowerCase())
                                                gameObjects.add(gmObj)

                                        }
                                    }
                                }
                                if (colTile.getBoundary() != null) {
                                    val boundaryObject = colTile.getBoundary()
                                    val gmObj = GameObject(boundaryObject = boundaryObject)
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
            if (sortDistance) {
                val local = Players.getLocal()
                gameObjects.sortBy { it.distanceTo(local) }
            }
            return gameObjects
        }
    }
}