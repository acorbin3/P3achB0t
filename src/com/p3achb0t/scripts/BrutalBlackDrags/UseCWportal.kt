package com.p3achb0t.scripts.BrutalBlackDrags

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.*
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.tabs.Equipment
import com.p3achb0t.scripts.Task
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlin.random.Random

class UseCWportal(val ctx: Context) : Task(ctx.client) {
    val clawwars = Area(
            Tile(3345, 3181, ctx = ctx),
            Tile(3401, 3146, ctx = ctx), ctx = ctx
    )
    val portalArea = Area(
            Tile(3307, 4762, ctx = ctx),
            Tile(3342, 4740, ctx = ctx), ctx = ctx
    )
    val furnace = Area(
            Tile(1489, 3824, ctx = ctx),
            Tile(1519, 3799, ctx = ctx), ctx = ctx
    )
    override suspend fun isValidToRun(): Boolean {
        return !BrutalBlackDragsMain.shouldBank(ctx) && ((ctx.stats.currentLevel(Stats.Skill.HITPOINTS) <= ctx.stats.level(Stats.Skill.HITPOINTS) - 25)
                || (ctx.stats.currentLevel(Stats.Skill.PRAYER) <= ctx.stats.level(Stats.Skill.PRAYER) - 25)) && (clawwars.containsOrIntersects(ctx.players.getLocal().getGlobalLocation()) ||  portalArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation()))
    }


    override suspend fun execute() {
        var Barrier = ctx.gameObjects.find("Free-for-all portal", sortByDistance = true)
        if(Barrier != null &&  !portalArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())){
            Barrier[0].doAction(-1, -2)
            delay(Random.nextLong(2466, 4999))
        }
        if(portalArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())){
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
    }
}