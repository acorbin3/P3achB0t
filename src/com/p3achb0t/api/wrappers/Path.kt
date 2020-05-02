package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.utils.Logging

/*
    This class is intended to define a list of tiles and interact to the destination. It will be smart enough to start
    in the middle of the path and walk towards the right direction.
 */
class Path(val tiles: ArrayList<Tile>, var ctx: Context?=null):Logging() {
    init {
        if(ctx != null){
            tiles.forEach {
                it.updateCTX(ctx!!)
            }
        }
    }

    fun updateCTX(ctx: Context){
        this.ctx = ctx
        tiles.forEach {
            it.updateCTX(ctx)
        }
    }
    suspend fun walk(reverse: Boolean = false){
        Walking.walkPath(tiles, reverse)
    }


    fun distanceToEndTile(): Int {
        return tiles.last().distanceTo()
    }

    fun distanceToFirstTile(): Int {
        return tiles.first().distanceTo()
    }

    fun isOnPath(): Boolean {

        tiles.forEach {
            if (it.distanceTo() < 10) {
                return true
            }
        }
        return false
    }
}