package com.p3achb0t.api.wrappers

import com.p3achb0t.Main
import com.p3achb0t.api.getConvexHull
import com.p3achb0t.hook_interfaces.Npc
import kotlin.math.abs
import kotlin.math.max

class NPC(var npc: Npc) : Actor(npc) {

    companion object {

        fun findNpc(npcName: String): ArrayList<NPC> {
            val foundNPCs = ArrayList<NPC>()
            try {
                val npcs = findNPCs(sortByDist = true)
                npcs.forEach {
                    if (it.npc.getComposite().getName() == npcName) {
                        foundNPCs.add(it)
                    }
                }
            } catch (e: Exception) {
            }
            return foundNPCs
        }

        // This function will return a list of NPCs with closes distance to you
        fun findNPCs(sortByDist: Boolean = false): ArrayList<NPC> {
            val npcs = ArrayList<NPC>()
            Main.clientData.getLocalNPCs().forEach {
                if (it != null) {
                    npcs.add(NPC(it))
                }
            }
            if (sortByDist) {
                npcs.sortBy {
                    // Sort closest to player
                    val localPlayer = Main.clientData.getLocalPlayer()
                    max(
                        abs(localPlayer.getLocalX() - it.npc.getLocalX()),
                        abs(localPlayer.getLocalY() - it.npc.getLocalY())
                    )
                }
            }
            return npcs
        }
    }

    suspend fun interact(action: String) {
        //TODO check is player is on scree
        //  TODO - Move camera for player to be on screen
        try {

            val ch = getConvexHull(
                this.npc,
                Main.clientData.getNpcModelCache(),
                this.npc.getComposite().getNpcComposite_id().toLong()
            )

            Interact.interact(ch, action)
        } catch (e: Exception) {
        }
    }
}