package com.p3achb0t.api.wrappers

import com.p3achb0t.api.getConvexHull
import kotlin.math.abs
import kotlin.math.max


class Player(var player: com.p3achb0t._runestar_interfaces.Player) : Actor(player) {
    companion object {


        // This function will return a list of NPCs with closes distance to you
        fun findPlayers(sortByDist: Boolean = false): ArrayList<Player> {
            val players = ArrayList<Player>()
            Client.client.getPlayers().forEach {
                if (it != null) {
                    players.add(Player(it))
                }
            }

            if (sortByDist) {
                players.sortBy {
                    // Sort closest to player
                    val localPlayer = Client.client.getLocalPlayer()
                    max(
                        abs(localPlayer.getX() - it.player.getX()),
                        abs(localPlayer.getY() - it.player.getY())
                    )
                }
            }
            return players
        }
    }

    suspend fun interact(action: String) {
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
                Client.client.getLocType_cachedModels(),
                this.player.getAppearance().getNpcTransformId().toLong()
            )
            //Checking to see if this is on screen
            Interact.interact(ch, action)
        } catch (e: Exception) {
        }
    }
}