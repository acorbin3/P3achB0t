import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.Area
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.tabs.Prayer
import com.p3achb0t.client.util.Util
import com.p3achb0t.scripts.RuneDragsMain
import com.p3achb0t.scripts.RuneDragsMain.Companion.Divinepottimer
import com.p3achb0t.scripts.RuneDragsMain.Companion.Killtimer
import com.p3achb0t.scripts.RuneDragsMain.Companion.kills
import com.p3achb0t.scripts.VorkathMain
import com.p3achb0t.scripts.Task
import kotlinx.coroutines.delay
import kotlin.random.Random

class doCombat(val ctx: Context) : Task(ctx.client) {

    companion object {
        var prayer = 35
        var firsttrip = true
        var eatto = 0
        var eatfrom = 47

    }

    override suspend fun isValidToRun(): Boolean {
        val combatArea = Area(
                Tile(1575, 5086, ctx = ctx),
                Tile(1597, 5062, ctx = ctx), ctx = ctx
        )
        return !RuneDragsMain.shouldBank(ctx) && combatArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation()) && RuneDragsMain.canFightNext(ctx)
    }


    override suspend fun execute() {
        var killedtarget = false
        if(!ctx.players.getLocal().isIdle()){
            RuneDragsMain.IdleTimer.reset()
            RuneDragsMain.IdleTimer.start()
        }

        val center = Tile(1588, 5075, ctx = ctx)
        val loot: IntArray = intArrayOf(1432, 2363, 1127, 1079, 1303, 1347, 4087, 4180, 4585, 1149, 892, 21880, 562, 560, 212, 208, 3052, 220, 19580, 9381, 1616, 452, 19582,
                21930, 995, 21918, 22103, 11286, 1333, 536)
        val groundloot = ctx.groundItems.getItempred(loot)
        val combatArea = Area(
                Tile(1575, 5086, ctx = ctx),
                Tile(1597, 5062, ctx = ctx), ctx = ctx
        )


        val antifires = hashSetOf(11951, 11953, 11955, 11957)
        val divinecombats = hashSetOf(23685, 23688, 23691, 23694)
        val prayerpots: IntArray = intArrayOf(143, 141, 139, 2434)
        var extendedantifires = hashSetOf(22209, 22212, 22215, 22218)
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
                if (ctx.inventory.Contains(it)) {
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
                    Random.nextInt(585, 1178)
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
            if(ctx.npcs.getTargetted("Rune dragon")?.health == 0.0 && Utils.getElapsedSeconds(RuneDragsMain.Killtimer.time) > 4){
                kills++
                RuneDragsMain.Killtimer.reset()
                RuneDragsMain.Killtimer.start()
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
            groundloot.forEach {
                println("item found " + it.id)
                if (ctx.inventory.isFull()) {
                    ctx.inventory.eat(385)
                    delay(666)
                }
                if (!ctx.inventory.isFull()) {
                    try {
                                if(it.distanceTo() >= 4) {
                                    it.takedoAction()
                                    delay(Random.nextLong(466, 999))
                                }
                        if(it.distanceTo() < 5) {
                            it.takedoAction()
                            delay(Random.nextLong(167, 356))
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
    suspend fun shouldEat(hp: Int, hpeatto: Int){
        while(ctx.stats.currentLevel(Stats.Skill.HITPOINTS) <= hpeatto){
            ctx.inventory.eat(385)
            Random.nextInt(555, 999)
        }
    }
}

