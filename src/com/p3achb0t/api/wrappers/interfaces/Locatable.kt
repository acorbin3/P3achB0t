package com.p3achb0t.api.wrappers.interfaces

import com.p3achb0t.api.Calculations
import com.p3achb0t.api.Constants
import com.p3achb0t.api.Utils
import com.p3achb0t.api.user_inputs.Camera
import com.p3achb0t.api.wrappers.Players
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.ui.Context
import kotlinx.coroutines.delay
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import kotlin.random.Random

interface Locatable {
    var loc_ctx: Context?
    abstract fun draw(g: Graphics2D)
    abstract fun draw(g: Graphics2D, color: Color)


    // In general players, objects, ground items getting x and y are stored in Regional coordinates.
    // For consistancy internal getting locations will be done using global coordnates. To convert Regional to global,
    // you right shift, and then add the applet.baseX and applet.baseY

    //namePoint returns the position at which the name of an item, npc, or actor would have the name displayed
    abstract fun getNamePoint(): Point

    abstract fun getGlobalLocation(): Tile

    // This function will remove the base and shift over by 7
    fun getLocalLocation(): Tile {
        val tile = getGlobalLocation()
        return if(loc_ctx != null) {
            val x = (tile.x - loc_ctx!!.client.getBaseX())
            val y = (tile.y - loc_ctx!!.client.getBaseY())

            Tile(x, y, tile.z, loc_ctx)
        }else{
            Tile()
        }

    }

    fun getRegionalLocation(): Tile {
        val tile = getGlobalLocation()
        return if(loc_ctx != null) {
            val x = ((tile.x - loc_ctx!!.client.getBaseX()) shl Constants.REGION_SHIFT)
            val y = ((tile.y - loc_ctx!!.client.getBaseY()) shl Constants.REGION_SHIFT)

            Tile(x, y, tile.z, loc_ctx)
        }else{
            Tile()
        }
    }

    suspend fun waitTillNearObject(time: Int = 4, desired: Int = 4) {
        Utils.waitFor(time, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(100)
                return Players(loc_ctx!!).getLocal().isIdle() || distanceTo() < desired
            }
        })
    }

    suspend fun turnTo() {
        Camera(loc_ctx!!.client, ioKeyboard = loc_ctx!!.keyboard).turnTo(this)
        delay(Random.nextLong(100, 200)) // This is to limit any movement on next interactions
    }
    abstract fun isOnScreen(): Boolean

    fun distanceTo(): Int {
        return loc_ctx?.let { Calculations.distanceTo(getGlobalLocation(), it) } ?: -1
    }

    fun distanceTo(locatable: Locatable): Int {
        return Calculations.distanceBetween(getGlobalLocation(), locatable.getGlobalLocation())
    }

    fun distanceTo(tile: Tile): Int {
        return Calculations.distanceBetween(getGlobalLocation(), tile.getGlobalLocation())
    }
}