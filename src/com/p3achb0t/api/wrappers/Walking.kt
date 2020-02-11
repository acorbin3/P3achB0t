package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.Utils
import kotlinx.coroutines.delay
import kotlin.random.Random

//Interesting website that can create paths: https://explv.github.io/
class Walking {
    companion object {
        // It is recommended to pass in the ctx so the list of Tiles dont need to have the ctx in each tile. That way
        // the walkPath can update the ctx on each tile.

        suspend fun walkPath(path: ArrayList<Tile>, reverse: Boolean = false, ctx: Context? = null) {
            //Since all parameters are declared as val we need to create a mutable list so we can update the CTX if needed
            var updatedPath = path.toMutableList()
            updatedPath.forEach {
                if (it.ctx == null && ctx != null) {
                    it.ctx = ctx
                    it.loc_ctx = ctx
                }
            }
            if (reverse) updatedPath.reverse()

            //find the most optimal tile to start at if we are in the middle of the path.
            var starterTile = Tile()
            var targetDistance = Float.POSITIVE_INFINITY
            updatedPath.forEach{
                val distanceToTile = it.distanceTo()
                if(distanceToTile < targetDistance){
                    starterTile = it
                    targetDistance = distanceToTile.toFloat()
                }
            }

            var foundFirstTile = false
            updatedPath.forEach{
                //Keep looping until we find the starter tile
                if(!foundFirstTile){
                    if(it == starterTile){
                        println("Found optimal start tile here $it")
                        foundFirstTile = true

                    }else{
                        //Skip until we find the starter tile
                        return@forEach
                    }
                }

                //Check to see if we are already at the end of the path, return if so
                if (updatedPath.last().distanceTo() < 5) {
                    println("Already at location: ${updatedPath.last().distanceTo()}")
                    return
                }
                var distance = 0

                val t = it.getGlobalLocation()
                println("Clicking on map: for tile: (${t.x},${t.y})")
                t.walktoTile(t)
//                if(!it.isOnScreen()) Camera(client).turnAngleTo(it)
//                it.click()
                Utils.waitFor(5, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        val curDist = it.distanceTo()
                        if (distance != curDist) {
                            distance = curDist
                        }
                        return curDist < 4
                    }
                })
                delay(Random.nextLong(143, 788))
            }
//            while(path[path.lastIndex].distanceTo() > 4){
//                // get next tile
//                //MAke sure tile is on screen. If not turn to file
//                //Click on Tile
//                //Wait till close to tile
//
//            }
        }
    }
}