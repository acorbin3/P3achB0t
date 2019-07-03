package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.api.getConvexHull
import kotlin.math.abs
import kotlin.math.max


class Player(var player: com.p3achb0t.hook_interfaces.Player) : Actor(player) {
    companion object {

        // This function will return a list of NPCs with closes distance to you
        fun findPlayers(sortByDist: Boolean = false): ArrayList<Player> {
            val players = ArrayList<Player>()
            Main.clientData.getPlayers().forEach {
                if (it != null) {
                    players.add(Player(it))
                }
            }

            if (sortByDist) {
                players.sortBy {
                    // Sort closest to player
                    val localPlayer = Main.clientData.getLocalPlayer()
                    max(
                        abs(localPlayer.getLocalX() - it.player.getLocalX()),
                        abs(localPlayer.getLocalY() - it.player.getLocalY())
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
            println("${this.player.getName()}: Getting Hull!")
            val ch = getConvexHull(
                this.player,
                Main.clientData.getPlayerModelCache(),
                this.player.getComposite().getAnimatedModelID()
            )
            //Checking to see if this is on screen
            Interact.interact(ch, action)
        } catch (e: Exception) {
        }
    }
}