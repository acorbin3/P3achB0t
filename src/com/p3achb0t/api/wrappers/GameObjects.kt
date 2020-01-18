package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import net.runelite.api.MenuOpcode
import java.awt.Point


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

    /**
     * added by sirscript
     * doaction (local location.x + y and id of the gameobject it seems like MenuOpcode.GAME_OBJECT_SECOND_OPTION is the first option usually for the opcode
     */

    suspend fun gameObjectdoAction(obj: GameObject, opcode: MenuOpcode = MenuOpcode.GAME_OBJECT_FIRST_OPTION) {
        ctx.mouse.instantclick(Point(0,599))
        ctx.client.doAction(obj.getLocalLocation().x, obj.getLocalLocation().y, opcode.id, obj.id, "", "", 0 ,0)
    }

    suspend fun gameObjectdoAction2(obj: GameObject, opcode: MenuOpcode = MenuOpcode.GAME_OBJECT_SECOND_OPTION) {
        ctx.mouse.instantclick(Point(0,599))
        ctx.client.doAction(obj.getLocalLocation().x, obj.getLocalLocation().y, opcode.id, obj.id, "", "", 0 ,0)
    }

    suspend fun gameObjectdoAction3(obj: GameObject, opcode: MenuOpcode = MenuOpcode.GAME_OBJECT_THIRD_OPTION) {
        ctx.mouse.instantclick(Point(0,599))
        ctx.client.doAction(obj.getLocalLocation().x, obj.getLocalLocation().y, opcode.id, obj.id, "", "", 0 ,0)
    }

    suspend fun gameObjectdoAction4(obj: GameObject, opcode: MenuOpcode = MenuOpcode.GAME_OBJECT_FOURTH_OPTION) {
        ctx.mouse.instantclick(Point(0,599))
        ctx.client.doAction(obj.getLocalLocation().x, obj.getLocalLocation().y, opcode.id, obj.id, "", "", 0 ,0)
    }

    suspend fun gameObjectdoAction5(obj: GameObject, opcode: MenuOpcode = MenuOpcode.GAME_OBJECT_FIFTH_OPTION) {
        ctx.mouse.instantclick(Point(0,599))
        ctx.client.doAction(obj.getLocalLocation().x, obj.getLocalLocation().y, opcode.id, obj.id, "", "", 0 ,0)
    }

    fun find(id: Int, tile: Tile = Tile(), sortByDistance: Boolean = true): ArrayList<GameObject> {
        val gameObjects = ArrayList<GameObject>()
        val region = ctx.client.getScene()

        region.getTiles().iterator().forEach { plane ->
            plane.iterator().forEach { row ->
                row.iterator().forEach { colTile ->
                    if (colTile != null) {
                        if (colTile.getScenery().isNotEmpty()) {
                            colTile.getScenery().iterator().forEach {
                                if (it != null) {
                                    val gmObj = GameObject(it, ctx = ctx)
                                    if (gmObj.id == id) {
                                        //Check to see if we need to filter based on the tile
                                        if(tile.x == -1 && tile.y == -1) {
                                            gameObjects.add(gmObj)
                                        }
                                        else if(tile.x == gmObj.getGlobalLocation().x && tile.y == gmObj.getGlobalLocation().y){
                                            gameObjects.add(gmObj)
                                        }
                                    }

                                }
                            }
                        }
                        if (colTile.getWall() != null) {
                            val boundaryObject = colTile.getWall()
                            val gmObj = GameObject(wallObject = boundaryObject, ctx = ctx)
                            if (gmObj.id == id) {
                                //Check to see if we need to filter based on the tile
                                if(tile.x == -1 && tile.y == -1) {
                                    gameObjects.add(gmObj)
                                }
                                else if(tile.x == gmObj.getGlobalLocation().x && tile.y == gmObj.getGlobalLocation().y){
                                    gameObjects.add(gmObj)
                                }
                            }
                        }
                    }
                }
            }
        }
        if (sortByDistance) {
            val local = ctx.players.getLocal()
            gameObjects.sortBy { it.distanceTo(local) }
        }
        return gameObjects
    }
    fun findNearest(id: Int): GameObject?{
        val items = find(id,sortByDistance = true)
        return if(items.isNotEmpty()){
            items.first()
        }else{
            null
        }
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