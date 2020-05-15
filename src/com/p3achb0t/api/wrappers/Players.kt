package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import kotlin.math.abs
import kotlin.math.max

class Players(val ctx: Context) {
    fun getLocal(): Player {
        return Player(ctx.client.getLocalPlayer(), ctx, 0)
    }

    fun getBaseX(): Int {
       return ctx.client.getLocalPlayer().getX() / 128 + ctx.client.getBaseX()
    }

    fun getBaseY(): Int {
        return ctx.client.getLocalPlayer().getY() / 128 + ctx.client.getBaseY()
    }
       // This function will return a list of NPCs with closes distance to you
    fun findPlayers(playerName: String, sortByDist: Boolean = false): ArrayList<Player> {
           val players = ArrayList<Player>()
           return try {
               ctx.client.getPlayers().forEachIndexed { index, player ->
                   if(player != null){

                   }
                   if (player != null && player.getUsername().toString().contains(playerName, true)) {
                       players.add(Player(player, ctx, index))
                   }
                   if (player != null && player.getUsername().getCleanName().replace("_", " ").toString().contains(playerName, true)) {
                       players.add(Player(player, ctx, index))
                   }
               }
               if (sortByDist) {
                   players.sortBy {
                       // Sort closest to player
                       val localPlayer = ctx.client.getLocalPlayer()
                       max(
                               abs(localPlayer.getX() - it.player.getX()),
                               abs(localPlayer.getY() - it.player.getY())
                       )
                   }
               }
               return players
           } catch (e: Exception) {
               return players
           }
    }

    // This function will return a list of NPCs with closes distance to you
    fun findPlayersClean(playerName: String, sortByDist: Boolean = false): ArrayList<Player> {
        val players = ArrayList<Player>()
        ctx.client.getPlayers().forEachIndexed { index, player ->
            if (player != null && player.getUsername().getCleanName().contentEquals(playerName)) {
                players.add(Player(player, ctx, index))
            }
        }
        if (sortByDist) {
            players.sortBy {
                // Sort closest to player
                val localPlayer = ctx.client.getLocalPlayer()
                max(
                        abs(localPlayer.getX() - it.player.getX()),
                        abs(localPlayer.getY() - it.player.getY())
                )
            }
        }
        return players
    }


}