package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import kotlin.math.abs
import kotlin.math.max

class NPCs(val ctx: Context) {
    fun findNpc(npcName: String, sortByDist: Boolean = true): ArrayList<NPC> {
        val foundNPCs = ArrayList<NPC>()
        try {
            val npcs = findNpcs(sortByDist = sortByDist)
            npcs.forEach {
                if (it.npc.getType().getName().contains(npcName)) {
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


    fun nearestAttackableNpc(npcId: Int): ArrayList<NPC> {
        val foundNPCs = ArrayList<NPC>()
        try {
            val npcs = findNpcs(sortByDist = true)
            npcs.forEach {
                if (it.npc.getType().getId().toInt() == npcId && it.npc.getTargetIndex() == -1) {
                    foundNPCs.add(it)
                }
            }
        } catch (e: Exception) {
        }
        return foundNPCs
    }


    fun nearestAttackableNpc(npcNames: ArrayList<String>): ArrayList<NPC> {
        val foundNPCs = ArrayList<NPC>()
        try {
            val npcs = findNpcs(sortByDist = true)
            npcs.forEach {
                if (npcNames.indexOf(it.npc.getType().getName())!= -1 && (it.npc.getTargetIndex() - 32768 == (ctx.players.getLocal().player.getIndex()) || it.npc.getTargetIndex() == -1 && it.npc.getSequence() != 92)) {
                    println("Found suitable npc")
                    foundNPCs.add(it)
                }
            }
        } catch (e: Exception) {
        }
        return foundNPCs
    }

    fun nearestAttackableNpc(npcName: String): ArrayList<NPC> {
        val foundNPCs = ArrayList<NPC>()
        try {
            val npcs = findNpcs(sortByDist = true)
            npcs.forEach {
                if (it.npc.getType().getName().contains(npcName) && (it.npc.getTargetIndex() - 32768 == (ctx.players.getLocal().player.getIndex()) || it.npc.getTargetIndex() == -1 && it.npc.getSequence() != 92)) {
                    println("Found suitable npc")
                    foundNPCs.add(it)
                }
            }
        } catch (e: Exception) {
        }
        return foundNPCs
    }


    fun isTargetted(): Boolean {
        var isTargetted = false
        try {
            val npcs = findNpcs(sortByDist = true)
            npcs.forEach {
                if (it.npc.getTargetIndex() - 32768 == (ctx.players.getLocal().player.getIndex())) {
                    isTargetted = true
                }
            }
        } catch (e: Exception) {
        }
        return isTargetted
    }

    // This function will return a list of NPCs with closes distance to you
    fun findNpcs(npcName: String, sortByDist: Boolean = false): ArrayList<NPC> {
        val npcs = ArrayList<NPC>()
        ctx.client.getNpcs().forEachIndexed { index, npc ->
            if (npc != null && npc.getType().getName().contentEquals(npcName)) {
                npcs.add(NPC(npc, ctx, index))
            }
        }
        if (sortByDist) {
            npcs.sortBy {
                it.distanceTo()
            }
        }
        return npcs
    }

    // This function will return a list of NPCs with closes distance to you
    fun findNpcs(npcName: String, area: Area, sortByDist: Boolean = false): ArrayList<NPC> {
        val npcs = ArrayList<NPC>()
        ctx.client.getNpcs().forEachIndexed { index, npc ->
            if (npc != null && npc.getType().getName().contentEquals(npcName) && area.contains(NPC(npc, ctx, index))) {
                npcs.add(NPC(npc, ctx, index))
            }
        }
        if (sortByDist) {
            npcs.sortBy {
                // Sort closest to player
                val localPlayer = ctx.client.getLocalPlayer()
                max(
                        abs(localPlayer.getX() - it.npc.getX()),
                        abs(localPlayer.getY() - it.npc.getY())
                )
            }
        }
        return npcs
    }

    val randomEventNPCsNamesToIgnore = arrayListOf("Dr Jekyll", "Evil Bob", "Bee keeper", "Capt' Arnav", "Niles",
    "Miles", "Giles", "Sergeant Damien", "Drunken Dwarf", "Freaky Forester", "Genie", "Leo", "Postie Pete", "Molly", "Mysterious Old Man",
    "Pillory Guard", "Tilt", "Flippa", " Prison Pete", "Quiz Master", "Rick Turpentine", "Sandwich Lady", "Strange plant", "Dunce", "Mr.Mordaut")
    val randomEventNPCsNamesToIgnoreLowercase = randomEventNPCsNamesToIgnore.map { it.toLowerCase() }


    fun getTargetted(): NPC? {
        val foundNPCs = ArrayList<NPC>()
        val npcs = findNpcs(sortByDist = true)
        npcs.forEach {
            if (it.npc.getTargetIndex() - 32768 == (ctx.players.getLocal().player.getIndex()) && it.npc.getSequence() != 92
                    && !randomEventNPCsNamesToIgnoreLowercase.contains(it.npc.getType().getName().toLowerCase())) {
                foundNPCs.add(it)
            }
        }
        when (foundNPCs.isEmpty()) {
            true -> return null
            false -> return foundNPCs[0]

        }
    }

    fun getTargetted(npcname: String): NPC? {
        val foundNPCs = ArrayList<NPC>()
        val npcs = findNpcs(npcname, sortByDist = true)
        npcs.forEach {
            if (it.npc.getType().getName().contains(npcname)
                    && !randomEventNPCsNamesToIgnoreLowercase.contains(it.npc.getType().getName().toLowerCase())
                    && it.npc.getTargetIndex() - 32768 == (ctx.players.getLocal().player.getIndex()) && it.npc.getSequence() != 92) {
                foundNPCs.add(it)
            }
        }
        when (foundNPCs.isEmpty()) {
            true -> return null
            false -> return foundNPCs[0]

        }
    }

    // This function will return a list of NPCs with closes distance to you
    fun findNpcs(sortByDist: Boolean = false): ArrayList<NPC> {
        val npcs = ArrayList<NPC>()
        try {
            ctx.client.getNpcs().forEachIndexed { index, npc ->
                if (npc != null) {
                    npcs.add(NPC(npc, ctx, index))
                }
            }
            if (sortByDist) {
                npcs.sortBy {
                    // Sort closest to player
                    it.distanceTo()
                }
            }
        } catch (e: Exception) {
        }
        return npcs
    }

    fun getNearestNPC(npcname: String): NPC? {
        val npcs = findNpcs(npcname, true)
        return if(npcs.isNotEmpty()) {
            npcs[0]
        }
        else{
         null
        }
    }

    fun getNearestNPC(npcname: String, area: Area): NPC {
        return findNpcs(npcname,area, true)[0]
    }

    fun getNearestNPC() : NPC {
        return findNpcs()[0]
    }

    fun getNearestNPC(npcId: Int) : NPC? {
        val npcs = findNpc(npcId)
        return if(npcs.isEmpty()){
            null
        }else{
            npcs.first()
        }
    }
}