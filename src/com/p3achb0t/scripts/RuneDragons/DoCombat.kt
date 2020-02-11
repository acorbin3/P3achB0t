import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.Area
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.scripts.RuneDragsMain
import com.p3achb0t.scripts.RuneDragsMain.Companion.kills
import com.p3achb0t.scripts.Task
import kotlinx.coroutines.delay
import kotlin.random.Random

class doCombat(val ctx: Context) : Task(ctx.client) {

    companion object {
        var prayer = 35
        var firsttrip = true
        var eatto = 0
        var eatfrom = 47
        var loottile = Tile()
    }

    val loot: IntArray = intArrayOf(1432, 2363, 1127, 1079, 1303, 1347, 4087, 4180, 4585, 1149, 892, 21880, 562, 560,561, 212, 208, 3052, 220, 19580, 9381, 1616, 452, 19582,
            21930, 995, 21918, 22103, 11286, 1333, 536)

    val combatArea = Area(
            Tile(1575, 5086, ctx = ctx),
            Tile(1597, 5062, ctx = ctx), ctx = ctx
    )

    override suspend fun isValidToRun(): Boolean {
        val combatArea = Area(
                Tile(1575, 5086, ctx = ctx),
                Tile(1597, 5062, ctx = ctx), ctx = ctx
        )
        return !RuneDragsMain.shouldBank(ctx) && combatArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation()) && RuneDragsMain.canFightNext(ctx)
    }


    override suspend fun execute() {
        var groundloot = ctx.groundItems.getItempred(loot)
        if(!ctx.players.getLocal().isIdle()){
            RuneDragsMain.IdleTimer.reset()
            RuneDragsMain.IdleTimer.start()
        }




        val antifires = hashSetOf(11951, 11953, 11955, 11957)
        val divinecombats = hashSetOf(23685, 23688, 23691, 23694)
        val prayerpots: IntArray = intArrayOf(143, 141, 139, 2434)
        val extendedantifires = intArrayOf(22218, 22215, 22212, 22209)
        if (!ctx.prayer.isQuickPrayerActive()){
                    ctx.prayer.ActivateQuickPrayer()
            Utils.waitFor(5, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return ctx.prayer.isQuickPrayerActive()
                }
            })
                }
        if (Utils.getElapsedSeconds(RuneDragsMain.Antifiretimer.time) > 355 || firsttrip) {
            extendedantifires.forEach {
                while (ctx.inventory.Contains(it)) {
                    println("using antifire")
                    ctx.inventory.drink(it)
                    RuneDragsMain.Antifiretimer.reset()
                    RuneDragsMain.Antifiretimer.start()
                    firsttrip = false
                }
            }
            delay(1300)
        }
        if (ctx.stats.level(Stats.Skill.STRENGTH) == ctx.stats.currentLevel(Stats.Skill.STRENGTH)) {
            divinecombats.forEach {
                if (ctx.inventory.Contains(it)) {
                    println("using combats")
                    ctx.inventory.drink(it)

                }
            }
            delay(1250)
        }
        if(ctx.players.getLocal().getHealth() <= eatfrom) {
            eatto = ctx.stats.level(Stats.Skill.HITPOINTS) - Random.nextInt(12, 21)
            run eat@{
               if (ctx.inventory.contains(385) && ctx.players.getLocal().getHealth() < eatto) {
                    ctx.inventory.eat(385)
                    Random.nextInt(777, 1178)
                   return@eat
                }
                if (ctx.players.getLocal().getHealth() >= eatto) {
                    eatto = ctx.players.getLocal().getHealth() - Random.nextInt(12, 21)
                    eatfrom = Random.nextInt(37, 51)
                }
            }
        }
        run prayerpots@{
            if (ctx.players.getLocal().getPrayer() < prayer) {
                prayerpots.forEach {
                    if (ctx.inventory.Contains(it)) {
                        ctx.inventory.drink(it)
                        delay(1500)
                    }
                    if (ctx.players.getLocal().getPrayer() >= prayer) {
                        prayer = Random.nextInt(14, 37)
                        return@prayerpots
                    }
                }
            }
        }
        if (ctx.players.getLocal().player.getTargetIndex() != -1) {
            var npc = ctx.npcs.getTargetted("Rune dragon")
            if (npc != null) {
                if (ctx.npcs.getTargetted("Rune dragon")?.health == 0.0 && Utils.getElapsedSeconds(RuneDragsMain.Killtimer.time) > 4) {
                    kills++
                    loottile = npc.getGlobalLocation()
                    RuneDragsMain.Killtimer.reset()
                    RuneDragsMain.Killtimer.start()
                }
            }
        }

        if (groundloot.isEmpty()) {
            if (ctx.players.getLocal().player.getTargetIndex() == -1 && !ctx.npcs.isTargetted()) {
                println("Getting new target")

                val dragon = ctx.npcs.nearestAttackableNpc("Rune dragon")
                if (dragon.size > 0) {
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
                val dragon = ctx.npcs.getTargetted("Rune dragon")
                println(dragon?.distanceTo())
                if (dragon != null) {
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

        if (!groundloot.isEmpty()) {
            println(groundloot[0].id)
            println(groundloot[0].position)
            loottile = groundloot[0].getGlobalLocation()
            groundloot.forEach {
                println("item found " + it.id)
                if (ctx.inventory.isFull()) {
                    ctx.inventory.eat(385)
                    delay(Random.nextLong(333, 888))
                }
                if (!ctx.inventory.isFull()) {
                    try {
                        if(tileContainsLoot(loottile)) {
                                if (it.distanceTo() >= 6 && it.getGlobalLocation() == loottile) {
                                    it.takedoAction()
                                    delay(Random.nextLong(2111, 2999))
                                }
                                if (it.distanceTo() < 7 && it.getGlobalLocation() == loottile) {
                                    it.takedoAction()
                                    delay(Random.nextLong(277, 577))
                                }
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

