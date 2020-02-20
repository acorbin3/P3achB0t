package com.p3achb0t.scripts.BrutalBlackDrags
import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.Area
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.scripts.Task
import kotlinx.coroutines.delay
import kotlin.random.Random

class doCombat(val ctx: Context) : Task(ctx.client) {

    val loot: IntArray = intArrayOf(9243, 536, 1747, 4087, 4180, 4585, 1249, 1631, 11377, 1247, 1079, 1163, 811, 1303, 2503, 2491, 868, 805, 1127, 1149, 1305, 1215, 830, 11232, 451, 11237, 19582, 13510, 13511, 11286, 560, 566,563, 892, 995, 11992, 565, 13441, 11993, 452, 1319)
    val bonesandhide: IntArray = intArrayOf(536, 1747, 4087, 4180, 4585, 1249, 1631, 11377, 1247, 1079, 1163, 811, 1303, 2503, 2491, 868, 805, 1127, 1149, 1305, 1215, 830, 11232, 451, 11237, 19582, 13510, 13511, 11286, 560, 566,563, 892, 11992, 565, 13441, 11993, 452, 1319)

    companion object {
        var prayer = 35
        var firsttrip = true
        var eatto = 0
        var eatfrom = 47
        var loottile = Tile()
        var haskilled = false

    }
    val combatArea = Area(
            Tile(1607, 10088, ctx = ctx),
            Tile(1624, 10105, ctx = ctx), ctx = ctx
    )

    override suspend fun isValidToRun(): Boolean {
        val catacoumbs = Area(

                Tile(1593, 10118, ctx = ctx),
                Tile(1658, 10062, ctx = ctx), ctx = ctx
        )
        return !BrutalBlackDragsMain.shouldBank(ctx) && catacoumbs.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())
    }


    override suspend fun execute() {
        if(!combatArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())){
            combatArea.getRandomTile().walktoTile()
            delay(1300)

        }
        if(!ctx.players.getLocal().isIdle()){
            BrutalBlackDragsMain.IdleTimer.reset()
            BrutalBlackDragsMain.IdleTimer.start()
        }


        val groundloot = ctx.groundItems.getItempredinarea(loot, combatArea)
        val bonesongorund = ctx.groundItems.getItempredinarea(bonesandhide, combatArea)


        val antifires = intArrayOf(11957, 11955, 11953, 11951)
        val divinecombats = hashSetOf(23733, 23736, 23739, 23742)
        val prayerpots: IntArray = intArrayOf(143, 141, 139, 2434)
        if (!ctx.prayer.isQuickPrayerActive()){
                    ctx.prayer.ActivateQuickPrayer()
            Utils.waitFor(5, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return ctx.prayer.isQuickPrayerActive()
                }
            })
                }
        if (Utils.getElapsedSeconds(BrutalBlackDragsMain.Antifiretimer.time) > 715 || firsttrip) {
            antifires.forEach {
                while (ctx.inventory.Contains(it)) {
                    println("using antifire")
                    ctx.inventory.drink(it)
                    BrutalBlackDragsMain.Antifiretimer.reset()
                    BrutalBlackDragsMain.Antifiretimer.start()
                    firsttrip = false
                }
            }
            delay(1300)
        }
        if (ctx.stats.level(Stats.Skill.RANGED) == ctx.stats.currentLevel(Stats.Skill.RANGED)) {
            divinecombats.forEach {
                if (ctx.inventory.Contains(it)) {
                    println("using ranged")
                    ctx.inventory.drink(it)

                }
            }
            delay(1250)
        }
        if(ctx.players.getLocal().getHealth() <= eatfrom) {
            eatto = ctx.stats.level(Stats.Skill.HITPOINTS) - Random.nextInt(12, 17)
            run eat@{
               if (ctx.inventory.contains(3144) && ctx.players.getLocal().getHealth() < eatto) {
                    ctx.inventory.eat(3144)
                    Random.nextInt(585, 1178)
                   return@eat
                }
                if (ctx.players.getLocal().getHealth() >= eatto) {
                    eatto = ctx.players.getLocal().getHealth() - Random.nextInt(12, 17)
                    eatfrom = Random.nextInt(37, 51)
                }
            }
        }
        run prayerpots@{
            if (ctx.players.getLocal().getPrayer() < prayer) {
                prayerpots.forEach {
                    if (ctx.inventory.Contains(it)) {
                        ctx.inventory.drink(it)
                        delay(1000)
                    }
                    if (ctx.players.getLocal().getPrayer() >= prayer) {
                        prayer = Random.nextInt(14, 37)
                        return@prayerpots
                    }
                }
            }
        }
        if (ctx.players.getLocal().player.getTargetIndex() != -1){
            var npc = ctx.npcs.getTargetted("Brutal black dragon")
            if(npc != null) {
                if (npc.distanceTo() < 4 && groundloot.isEmpty()) {
                    println("Walking to tile "+ combatArea.getRandomTile().walktoTile())
                    combatArea.getRandomTile().walktoTile()
                    delay(Random.nextLong(1343, 1888))
                }
            }
            if(npc?.health == 0.0 && Utils.getElapsedSeconds(BrutalBlackDragsMain.Killtimer.time) > 4){
                haskilled = true
                loottile = npc.getGlobalLocation()
                BrutalBlackDragsMain.kills++
                BrutalBlackDragsMain.Killtimer.reset()
                BrutalBlackDragsMain.Killtimer.start()
            }
        }
        if(!bonesongorund.isEmpty()){
            haskilled = true
            loottile = bonesongorund[0].getGlobalLocation()
        }
        if (!haskilled) {
            if (ctx.players.getLocal().player.getTargetIndex() == -1 && !ctx.npcs.isTargetted()) {
                println("Getting new target")

                val dragon = ctx.npcs.nearestAttackableNpc("Brutal black dragon")
                if (dragon.size > 0 && combatArea.containsOrIntersects(dragon[0].getGlobalLocation())) {
                    println(dragon[0].distanceTo())
                    if(dragon[0].distanceTo() < 4){
                        println("Walking to tile "+ dragon[0].getRegionalLocation())
                        combatArea.getRandomTile().walktoTile()
                        delay(Random.nextLong(1343, 1888))
                }
                        dragon[0].doActionAttack()
                    Utils.waitFor(2, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return ctx.players.getLocal().player.getTargetIndex() != -1
                        }
                    })

                }
            }

            if (ctx.players.getLocal().player.getTargetIndex() == -1 && ctx.npcs.isTargetted()) {
                println("being attacked - locating target")
                val dragon = ctx.npcs.getTargetted("Brutal black dragon")
                if (dragon != null && combatArea.containsOrIntersects(dragon.getGlobalLocation())) {
                    println(dragon.distanceTo())
                    if(dragon.distanceTo() < 4){
                        println("Walking to tile "+ dragon.getRegionalLocation())
                        combatArea.getRandomTile().walktoTile()
                        delay(Random.nextLong(1343, 1888))
                    }
                        dragon.doActionAttack()
                    Utils.waitFor(2, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return ctx.players.getLocal().player.getTargetIndex() != -1
                        }
                    })

                }
            }
        }
        if (haskilled && groundloot.isEmpty() && Utils.getElapsedSeconds(BrutalBlackDragsMain.Killtimer.time) > 6) {
            println("ground loot empty + timer over 4")
            haskilled = false
        }
        if (!groundloot.isEmpty() && haskilled) {
            val lootArea = Area(
                    Tile(loottile.x - 2 , loottile.y + 2, ctx = ctx),
                    Tile(loottile.x + 2, loottile.y - 2, ctx = ctx), ctx = ctx
            )
            println(groundloot[0].id)
            println(groundloot[0].position)
            groundloot.forEach {
                println("item found " + it.id)
                if (ctx.inventory.isFull()) {
                    ctx.inventory.eat(3144)
                    delay(666)
                }
                if (!ctx.inventory.isFull()) {
                    try {
                                if (it.distanceTo() >= 6 && lootArea.containsOrIntersects(it.getGlobalLocation())) {
                                    it.takedoAction()
                                    delay(Random.nextLong(2111, 2999))
                                }
                                if (it.distanceTo() < 7 && lootArea.containsOrIntersects(it.getGlobalLocation())) {
                                    it.takedoAction()
                                    delay(Random.nextLong(277, 577))
                                }

                    } catch (e: Exception) {
                        println("Error: NPC " + e.message)
                        e.stackTrace.iterator().forEach {
                            println(it)
                        }
                    }
                }
            }
        }
    }

    suspend fun tileContainsLoot(tile: Tile): Boolean {
        var hasLoot = false
        val groundloot = ctx.groundItems.getItempredinarea(loot, combatArea)
        groundloot.forEach {
            if(it.getGlobalLocation() == tile){
                hasLoot = true
            }
        }
        return hasLoot
    }
    suspend fun shouldEat(hp: Int, hpeatto: Int){
        while(ctx.stats.currentLevel(Stats.Skill.HITPOINTS) <= hpeatto){
            ctx.inventory.eat(385)
            Random.nextInt(555, 999)
        }
    }
}

