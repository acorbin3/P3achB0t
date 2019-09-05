package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Calculations
import com.p3achb0t.api.getConvexHull
import com.p3achb0t.api.user_inputs.Mouse
import java.awt.Point
import kotlin.math.abs
import kotlin.math.max


class Player(var player: com.p3achb0t._runestar_interfaces.Player, client: com.p3achb0t._runestar_interfaces.Client, mouse: Mouse) : Actor(player, client, mouse) {
    // This function will return a list of NPCs with closes distance to you
    fun findPlayers(sortByDist: Boolean = false): ArrayList<Player> {
        val players = ArrayList<Player>()
        client.getPlayers().forEach {
            if (it != null) {
                players.add(Player(it, client,mouse))
            }
        }

        if (sortByDist) {
            players.sortBy {
                // Sort closest to player
                val localPlayer = client.getLocalPlayer()
                max(
                        abs(localPlayer.getX() - it.player.getX()),
                        abs(localPlayer.getY() - it.player.getY())
                )
            }
        }
        return players
    }

    override fun getNamePoint(): Point {
        val region = getRegionalLocation()
        return Calculations.worldToScreen(region.x, region.y, player.getHeight(), client)
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
            val ch = getConvexHull(
                    this.player,
                    client.getLocType_cachedModels(),
                    this.player.getAppearance().getNpcTransformId().toLong(),
                    client)
            //Checking to see if this is on screen
            Interact(client, mouse).interact(ch, action)
        } catch (e: Exception) {
        }
        return false
    }
}