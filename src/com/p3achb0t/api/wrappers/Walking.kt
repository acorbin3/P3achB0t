package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Utils
import kotlinx.coroutines.delay

class Walking {
    companion object {
        suspend fun walkPath(path: ArrayList<Tile>, reverse: Boolean = false) {
            if (reverse) path.reverse()
            //Check to see if we are alreay at the end of the path, return if so
            if (path[path.size - 1].distanceTo() < 5)
                return
            var distance = 0
            path.forEach {
                val t = it.getGlobalLocation()
                println("Clicking on map: for tile: (${t.x},${t.y})")
                it.clickOnMiniMap()
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