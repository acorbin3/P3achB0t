package com.p3achb0t.api.wrappers.interfaces

import com.p3achb0t.api.Calculations
import com.p3achb0t.api.Constants
import com.p3achb0t.api.Utils
import com.p3achb0t.api.user_inputs.Camera
import com.p3achb0t.api.wrappers.Client
import com.p3achb0t.api.wrappers.Players
import com.p3achb0t.api.wrappers.Tile
import kotlinx.coroutines.delay
import java.awt.Color
import java.awt.Graphics2D
import kotlin.random.Random

interface Locatable {
    fun draw(g: Graphics2D)
    fun draw(g: Graphics2D, color: Color)


    // In general players, objects, ground items getting x and y are stored in Regional coordinates.
    // For consistancy internal getting locations will be done using global coordnates. To convert Regional to global,
    // you right shift, and then add the client.baseX and client.baseY

    fun getGlobalLocation(): Tile

    // This function will remove the base and shift over by 7
    fun getLocalLocation(): Tile {
        val tile = getGlobalLocation()
        val x = (tile.x - Client.client.getBaseX())
        val y = (tile.y - Client.client.getBaseY())

        return Tile(x, y, tile.z)
    }

    fun getRegionalLocation(): Tile {
        val tile = getGlobalLocation()
        val x = ((tile.x - Client.client.getBaseX()) shl Constants.REGION_SHIFT)
        val y = ((tile.y - Client.client.getBaseY()) shl Constants.REGION_SHIFT)

        return Tile(x, y, tile.z)
    }

    suspend fun waitTillNearObject(time: Int = 4, desired: Int = 4) {
        Utils.waitFor(time, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(100)
                return Players.getLocal().isIdle() || distanceTo() < desired
            }
        })
    }

    suspend fun turnTo() {
        Camera.turnTo(this)
        delay(Random.nextLong(100, 200)) // This is to limit any movement on next interactions
    }
    fun isOnScreen(): Boolean

    fun distanceTo(): Int {
        return Calculations.distanceTo(getGlobalLocation())
    }

    fun distanceTo(locatable: Locatable): Int {
        return Calculations.distanceBetween(getGlobalLocation(), locatable.getGlobalLocation())
    }

    fun distanceTo(tile: Tile): Int {
        return Calculations.distanceBetween(getGlobalLocation(), tile.getGlobalLocation())
    }
}