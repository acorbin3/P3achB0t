import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.Area
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.scripts.RuneDragsMain
import com.p3achb0t.scripts.Task
import doCombat.Companion.firsttrip
import kotlinx.coroutines.delay
import kotlin.random.Random

class TraverseDragons(val ctx: Context) : Task(ctx.client) {
    override suspend fun isValidToRun(): Boolean {
        val combatArea = Area(
                Tile(1575, 5086, ctx = ctx),
                Tile(1597, 5062, ctx = ctx)
        )
        return !RuneDragsMain.shouldBank(ctx) && !combatArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())
    }


    override suspend fun execute() {
        val prayerpots: IntArray = intArrayOf(143, 141, 139, 2434)

        val BarrierArea = Area(
                Tile(1570, 5082, ctx = ctx),
                Tile(1578, 5068, ctx = ctx)
        )

        val starArea = Area(
                Tile(3532, 10407, ctx = ctx),
                Tile(3563, 10384, ctx = ctx),ctx=ctx)

        val starArea2 = Area(
                Tile(3544, 10470, ctx = ctx),
                Tile(3559, 10447, ctx = ctx),ctx=ctx)


        val combatArea = Area(
                Tile(1575, 5086, ctx = ctx),
                Tile(1597, 5062, ctx = ctx),ctx=ctx)

        val barrierArea = Area(
                Tile(1569, 5077, ctx = ctx),
                Tile(1573, 5071, ctx = ctx),ctx=ctx)

        var cwBank = Tile(2442, 3083, 0, ctx = ctx)
        val pendantids: IntArray = intArrayOf(11194, 11193, 11192, 11191, 11190)
        if (ctx.bank.isOpen()) {
            ctx.bank.close()
            delay(150)
        }

        run teleportcw@{
            if (cwBank.distanceTo() < 30) {
                pendantids.forEach {
                    if (ctx.inventory.Contains(it)) {
                        println("inventory contains " + it)
                        if (!ctx.dialog.isDialogOptionsOpen()) {
                            ctx.inventory.rub(it)
                            delay(1000)
                        }
                        if (ctx.dialog.isDialogOptionsOpen()) {
                            ctx.dialog.selectionOptiondoAction("Lithkren")
                            Utils.waitFor(5, object : Utils.Condition {
                                override suspend fun accept(): Boolean {
                                    delay(100)
                                    return cwBank.distanceTo() > 30
                                }
                            })
                        }
                        if (cwBank.distanceTo() > 30) {
                            return@teleportcw
                        }
                    }
                }
            }
            if (cwBank.distanceTo() > 30) {
                val Stairs = ctx.gameObjects.find("Staircase", sortByDistance = true)
                val Door = ctx.gameObjects.find("Broken Grandiose Doors", sortByDistance = true)
                var Barrier = ctx.gameObjects.find("Barrier", sortByDistance = true)
                if (starArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())) {
                    println("in area 1")
                    if (Stairs.size > 0) {
                        Stairs[0].doAction(offsetX = -1, offsetY = 23, offsetID = 0)
                        Utils.waitFor(5, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return !starArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation()) && !starArea2.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())
                            }
                        })
                    }
                }
                if (starArea2.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())) {
                    println("in area 2")
                    if (Stairs.size > 0) {
                        Stairs[0].doAction(offsetX = -1, offsetY = 23, offsetID = 1)
                        Utils.waitFor(5, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return !starArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation()) && !starArea2.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())
                            }
                        })
                    }
                }
                if (Door.size > 0 && Barrier.size < 1) {
                    if (Door[0].distanceTo() <= 15) {
                            Door[0].doAction(offsetX = -3, offsetY = -1, offsetID = 0)
                            Utils.waitFor(5, object : Utils.Condition {
                                override suspend fun accept(): Boolean {
                                    delay(100)
                                    return Barrier.size > 0
                                }
                            })
                        }
                    }
                if (!combatArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation()) && !barrierArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())) {
                    Barrier = ctx.gameObjects.findinArea("Barrier",  BarrierArea, sortByDistance = true)
                }
                if(Barrier.size > 0) {
                    delay(1000)
                    val extendedantifires = intArrayOf(22218, 22215, 22212, 22209)

                    if (Utils.getElapsedSeconds(RuneDragsMain.Antifiretimer.time) > 355 || firsttrip) {
                        extendedantifires.forEach {
                            while (ctx.inventory.Contains(it)) {
                                println("using antifire")
                                ctx.inventory.drink(it)
                                RuneDragsMain.Antifiretimer.reset()
                                RuneDragsMain.Antifiretimer.start()
                                firsttrip = false
                                delay(389)
                            }
                        }
                    }
                    val random = Random.nextInt(0, 5)
                    Barrier[random].doAction()
                    Utils.waitFor(7, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return combatArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())
                        }
                    })
                }
            }
        }
    }
}