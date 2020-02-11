package com.p3achb0t.scripts.BrutalBlackDrags

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.Area
import com.p3achb0t.api.wrappers.Player
import com.p3achb0t.api.wrappers.Players
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.tabs.Equipment
import com.p3achb0t.scripts.Task
import kotlinx.coroutines.delay
import kotlin.random.Random

class Bank(val ctx: Context) : Task(ctx.client) {
    override suspend fun isValidToRun(): Boolean {
        return BrutalBlackDragsMain.shouldBank(ctx)
    }


    override suspend fun execute() {
        val clawwars = Area(
                Tile(3345, 3181, ctx = ctx),
                Tile(3401, 3146, ctx = ctx), ctx = ctx
        )
        var duelingids = hashSetOf(2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566).shuffled()
        var extendedantifires = hashSetOf(11951, 11955, 11953).shuffled()
        var superrange = hashSetOf(23733, 23736, 23739).shuffled()
        var itemstokeep = arrayListOf<Int>(2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566, 11194, 11193, 11192, 11191, 11190, 11951, 11953, 11955, 11957, 22209, 22212, 22215, 23688, 23691, 23685, 385)


        run teleportcw@{
            if (!clawwars.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())) {
                duelingids.forEach {
                    if (ctx.inventory.Contains(it) && ctx.equipment.getItemAtSlot(Equipment.Companion.Slot.Ring)?.id != it) {
                        ctx.inventory.wear(it)
                        delay(600)
                    }
                    if (ctx.equipment.getItemAtSlot(Equipment.Companion.Slot.Ring)?.id == it) {
                        ctx.equipment.duelingclawnwars()
                        Utils.waitFor(5, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return clawwars.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())
                            }
                        })
                    }
                    if (clawwars.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())) {
                        return@teleportcw
                    }
                }
            }
            if (clawwars.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())) {
                    if (ctx.prayer.isQuickPrayerActive()) {
                        ctx.prayer.ActivateQuickPrayer()
                        Utils.waitFor(5, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return !ctx.prayer.isQuickPrayerActive()
                            }
                        })
                    }
                    val bank = ctx.gameObjects.find(26707)
                    if (!ctx.bank.isOpen()) {
                        bank[0].doAction()
                        Utils.waitFor(5, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return ctx.bank.isOpen()
                            }
                        })
                    }
                    if (ctx.bank.isOpen()) {
                        if (!ctx.inventory.isEmpty()) {
                            ctx.bank.depositInvdoAction()
                        }
                        delay(Random.nextLong(189, 777))
                        ctx.bank.withdrawXdoAction(2434, 3)
                        delay(Random.nextLong(189, 777))
                        run withdrawdueling@{
                            duelingids.forEach {
                                if (!ctx.inventory.Contains(it) && ctx.bank.getItemCount(it) > 0) {
                                    ctx.bank.withdraw1doAction(it)
                                    delay(Random.nextLong(189, 777))
                                }
                                if (ctx.inventory.Contains(it)) {
                                    return@withdrawdueling
                                }
                            }
                        }
                                if (!ctx.inventory.Contains(13393) && ctx.bank.getItemCount(13393) > 0) {
                                    ctx.bank.withdraw1doAction(13393)
                                    delay(Random.nextLong(189, 777))
                                }
                        run extendedantifires@{
                            extendedantifires.forEach {
                                if (!ctx.inventory.Contains(it) && ctx.bank.getItemCount(it) > 0) {
                                    ctx.bank.withdraw1doAction(it)
                                    delay(Random.nextLong(189, 777))
                                }
                                if (ctx.inventory.Contains(it)) {
                                    return@extendedantifires
                                }
                            }
                        }
                        run withdrawsupercombats@{
                            superrange.forEach {
                                if (!ctx.inventory.Contains(it) && ctx.bank.getItemCount(it) > 0) {
                                    ctx.bank.withdraw1doAction(it)
                                    delay(Random.nextLong(189, 777))
                                }
                                if (ctx.inventory.Contains(it)) {
                                    return@withdrawsupercombats
                                }
                            }
                        }
                        ctx.bank.withdrawXdoAction(3144, Random.nextInt(4, 6))
                        delay(Random.nextLong(466, 1111))
                    }
            }

        }
    }
}