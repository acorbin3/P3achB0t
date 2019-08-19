package com.p3achb0t.api.wrappers

import kotlin.math.abs
import kotlin.math.max

class NPCs {
    companion object {
        fun findNpc(npcName: String, sortByDist: Boolean = true): ArrayList<NPC> {
            val foundNPCs = ArrayList<NPC>()
            try {
                val npcs = findNpcs(sortByDist = sortByDist)
                npcs.forEach {
                    if (it.npc.getType().getName() == npcName) {
                        foundNPCs.add(it)
                    }
                }
            } catch (e: Exception) {
            }
            return foundNPCs
        }

        fun findNpc(npcId: Int): ArrayList<NPC> {
            val foundNPCs = ArrayList<NPC>()
            try {
                val npcs = findNpcs(sortByDist = true)
                npcs.forEach {
                    if (it.npc.getType().getId().toInt() == npcId) {
                        foundNPCs.add(it)
                    }
                }
            } catch (e: Exception) {
            }
            return foundNPCs
        }

        // This function will return a list of NPCs with closes distance to you
        fun findNpcs(sortByDist: Boolean = false): ArrayList<NPC> {
            val npcs = ArrayList<NPC>()
            Client.client.getNpcs().forEach {
                if (it != null) {
                    npcs.add(NPC(it))
                }
            }
            if (sortByDist) {
                npcs.sortBy {
                    // Sort closest to player
                    val localPlayer = Client.client.getLocalPlayer()
                    max(
                        abs(localPlayer.getX() - it.npc.getX()),
                        abs(localPlayer.getY() - it.npc.getY())
                    )
                }
            }
            return npcs
        }
    }
}