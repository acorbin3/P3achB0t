package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.StopWatch
import java.lang.Math.pow
import java.text.DecimalFormat
import java.util.*
import kotlin.math.floor


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

    val startXP = EnumMap<Skill,Int>(Skill::class.java)
    val curXP = EnumMap<Skill,Int>(Skill::class.java)
    var runtime = StopWatch()

    init {
        Skill.values().iterator().forEach {
            startXP[it] = 0
            curXP[it] = 0
        }

        try {
            if (ctx.worldHop.isLoggedIn) {
                updateStats()
            }
        }catch (e: Exception){

        }
    }

    fun level(skill: Skill): Int =  ctx.client.getLevels()[skill.statID]
    fun currentLevel(skill: Skill): Int = ctx.client.getCurrentLevels()[skill.statID]
    fun currentXP(skill: Skill): Int = ctx.client.getExperience()[skill.statID]

    fun experienceForLevel(level: Int): Int {
        var total = 0.0
        for (i in 1 until level) {
            total += floor(i + 300 * pow(2.0, i / 7.0))
        }
        return floor(total / 4).toInt()
    }

    fun xpGained(skill: Skill) : Int{
        return curXP[skill]!! - startXP[skill]!!
    }
    fun xpPerHour(skill: Skill): Double {
        val xpGained = xpGained(skill)
        return if( skill in curXP && xpGained > 0) {
            xpGained.toDouble() / runtime.elapsed * 3_600_000

        }else{
            0.0
        }
    }
    fun xpPerMin(skill: Skill): Double{
        val xpGained = xpGained(skill)
        return if( skill in curXP && xpGained > 0) {
            xpPerHour(skill)/60

        }else{
            0.0
        }
    }
    fun xpPerSec(skill: Skill): Double{
        val xpGained = xpGained(skill)
        return if( skill in curXP && xpGained > 0) {
            xpPerMin(skill)/60

        }else{
            0.0
        }
    }

    fun xpPerMills(skill: Skill): Double{
        val xpGained = xpGained(skill)
        return if( skill in curXP && xpGained > 0) {
            xpGained.toDouble() / runtime.elapsed

        }else{
            0.0
        }
    }
    fun xpPerHourFormatted(skill: Skill): String{
        val df = DecimalFormat("###,###,###")
        return df.format(xpPerHour(skill))
    }

    fun timeTillNextLevel(skill: Skill): String{
        val xpPerMills = xpPerMills(skill)

        val xpTillNextLevel = experienceForLevel(currentLevel(skill) + 1) - currentXP(skill)
        val millsecsRemaining = xpTillNextLevel / xpPerMills


        return getRuntimeString(millsecsRemaining.toLong())
    }

    fun getRuntimeString(elapsed: Long): String {
        val days = elapsed.toInt() / 86400000
        var remainder = elapsed % 86400000
        val hours = remainder.toInt() / 3600000
        remainder = elapsed % 3600000
        val minutes = remainder.toInt() / 60000
        remainder = remainder % 60000
        val seconds = remainder.toInt() / 1000
        val dd = if (days < 10) "0$days" else Integer.toString(days)
        val hh = if (hours < 10) "0$hours" else Integer.toString(hours)
        val mm = if (minutes < 10) "0$minutes" else Integer.toString(minutes)
        val ss = if (seconds < 10) "0$seconds" else Integer.toString(seconds)
        return "$dd:$hh:$mm:$ss"
    }

    fun updateStats(){
//        println("Updating stats")
        //Weird case where we might be logged in but the stats havent been loaded yet.
        // Lets just skip the update for now
        if(currentXP(Skill.ATTACK) == 0)
            return
        Skill.values().iterator().forEach {
            if(startXP[it] == 0 && ctx.worldHop.isLoggedIn) {
                startXP[it] = currentXP(it)
            }
            curXP[it] = currentXP(it)
        }
    }

}