package com.p3achb0t.api.wrappers.interfaces

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.Actor
import com.p3achb0t.api.wrappers.NPC

interface ActorTargeting {
    var actTar_ctx: Context?
    val target: Actor? get() = targetNpc //?: targetPlayer

    val targetNpc: NPC? get() = npcTargetIndex.let { if (it == -1) null else actTar_ctx?.npcs?.findNpcs()?.get(it) }

    //TODO add in players in the future
//    val targetPlayer: Player? get() = playerTargetIndex.let { if (it == -1) null else actTar_ctx.players[it] }

    val hasTarget: Boolean get() = npcTargetIndex != -1 //|| playerTargetIndex != -1

    val npcTargetIndex: Int

//    val playerTargetIndex: Int
}