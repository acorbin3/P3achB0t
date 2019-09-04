package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Calculations
import com.p3achb0t.api.getConvexHull
import java.awt.Point
import kotlin.math.abs
import kotlin.math.max


class Player(var player: com.p3achb0t._runestar_interfaces.Player, client: com.p3achb0t._runestar_interfaces.Client) : Actor(player,client ) {
    // This function will return a list of NPCs with closes distance to you
    fun findPlayers(sortByDist: Boolean = false): ArrayList<Player> {
        val players = ArrayList<Player>()
        client?.getPlayers()?.forEach {
            if (it != null) {
                players.add(Player(it,client ))
            }
        }

        if (sortByDist) {
            players.sortBy {
                // Sort closest to player
                val localPlayer = client?.getLocalPlayer()
                localPlayer?.getY()?.minus(it.player.getY())?.let { it1 -> abs(it1) }?.let { it2 ->
                    max(
                            localPlayer?.getX()?.minus(it.player.getX())?.let { it1 -> abs(it1) },
                            it2
                    )
                }
            }
        }
        return players
    }
    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return client?.let { Calculations.worldToScreen(region.x, region.y, player.getHeight(), it) } ?: Point()
    }

    override suspend fun interact(action: String): Boolean {
        //TODO check is player is on screen
        //  TODO - Move camera for player to be on screen
        //TODO - move mouse to player
        //TODO - check if you need to right click for menu or if left click is fine
        //  TODO - if right click interact
        //
        try {
            println("${this.player.getUsername()}: Getting Hull!")
            val ch = client?.getLocType_cachedModels()?.let {
                getConvexHull(
                        this.player,
                        it,
                        this.player.getAppearance().getNpcTransformId().toLong(),
                        client

                        )
            }
            //Checking to see if this is on screen
            client?.let { ch?.let { it1 -> Interact(it).interact(it1, action) } }
            return true

        } catch (e: Exception) {
        }
        return false
    }
}