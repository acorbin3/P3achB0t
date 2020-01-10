package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context

class Stats(val ctx: Context) {
    enum class Skill(val statID: Int){
        ATTACK(0),
        DEFENCE(1),
        STRENGTH(2),
        HITPOINTS(3),
        RANGED(4),
        PRAYER(5),
        MAGIC(6),
        COOKING(7),
        WOODCUTTING(8),
        FLETCHING(9),
        FISHING(10),
        FIREMAKING(11),
        CRAFTING(12),
        SMITHING(13),
        MINING(14),
        HERBLORE(15),
        AGILITY(16),
        THIEVING(17),
        SLAYER(18),
        FARMING(19),
        RUNECRAFT(20),
        HUNTER(21),
        CONSTRUCTION(22),
    }
    fun level(skill: Skill): Int{
        return ctx.client.getLevels()[skill.statID]
    }
}