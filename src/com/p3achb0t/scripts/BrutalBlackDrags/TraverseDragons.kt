package com.p3achb0t.scripts.BrutalBlackDrags
import com.p3achb0t._runestar_interfaces.DynamicObject
import com.p3achb0t.api.Context
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.wrappers.*
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.scripts.Task
import doCombat.Companion.firsttrip
import kotlinx.coroutines.delay
import net.runelite.api.MenuOpcode
import kotlin.random.Random

class TraverseDragons(val ctx: Context) : Task(ctx.client) {

    val clawwars = Area(
            Tile(3345, 3181, ctx = ctx),
            Tile(3401, 3146, ctx = ctx), ctx = ctx
    )
    val portalArea = Area(
            Tile(3307, 4762, ctx = ctx),
            Tile(3342, 4740, ctx = ctx), ctx = ctx
    )
    val ladderArea = Area(
            Tile(1545, 3794, ctx = ctx),
            Tile(1561, 3782, ctx = ctx), ctx = ctx
    )
    val catacoumbs = Area(
            Tile(1593, 10118, ctx = ctx),
            Tile(1658, 10062, ctx = ctx), ctx = ctx
    )
    val furnace = Area(
            Tile(1489, 3824, ctx = ctx),
            Tile(1519, 3799, ctx = ctx), ctx = ctx
    )

    override suspend fun isValidToRun(): Boolean {
        return !BrutalBlackDragsMain.shouldBank(ctx) && !catacoumbs.containsOrIntersects(ctx.players.getLocal().getGlobalLocation()) && (ctx.stats.currentLevel(Stats.Skill.HITPOINTS) > ctx.stats.level(Stats.Skill.HITPOINTS) - 25)
                && (ctx.stats.currentLevel(Stats.Skill.PRAYER) > ctx.stats.level(Stats.Skill.PRAYER) - 25)
    }


    override suspend fun execute() {
        if (ctx.bank.isOpen()) {
            ctx.bank.close()
            delay(150)
        }
        if (!ctx.bank.isOpen() && ctx.inventory.Contains(13393) && clawwars.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())) {
            if (!ctx.dialog.isDialogOptionsOpen()) {
                ctx.inventory.rub2(13393)
                delay(1000)
            }
            if (ctx.dialog.isSpiritDialogOpen()) {
                ctx.dialog.selectTreeOptionDoAction("Xeric's Inferno")
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return furnace.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())
                    }
                })
            }
        }

        val path = arrayListOf(Tile(1508, 3815, ctx = ctx),Tile(1513, 3811, ctx = ctx), Tile(1520, 3810, ctx = ctx), Tile(1528, 3810, ctx = ctx), Tile(1532, 3805, ctx = ctx), Tile(1539, 3801, ctx = ctx),
                Tile(1545, 3799, ctx = ctx), Tile(1548, 3793, ctx = ctx), Tile(1550, 3791, ctx = ctx), Tile(1555, 3790, ctx = ctx))
        if(!clawwars.containsOrIntersects(ctx.players.getLocal().getGlobalLocation()) && !ladderArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())) {
            Walking.walkPath(path)
        }
        if (ladderArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())) {
            println("In area")
            if (!ctx.prayer.isQuickPrayerActive()){
                ctx.prayer.ActivateQuickPrayer()
                Utils.waitFor(5, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return ctx.prayer.isQuickPrayerActive()
                    }
                })
            }
            doActionHole()
                Utils.waitFor(5, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return catacoumbs.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())
                    }
                })
        }
    }
    suspend fun doActionHole() {
        val scenebasex = ctx.client.getBaseX()
        val scenebasey = ctx.client.getBaseY()
        val holeposx = 1563
        val holeposy = 3791
        val doActionParams =   DoActionParams(holeposx - scenebasex, holeposy - scenebasey, 3, 28921, "", "", 0, 0)
        ctx?.mouse?.overrideDoActionParams = true
        ctx?.mouse?.doAction(doActionParams)
    }
}