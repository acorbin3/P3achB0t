package com.p3achb0t.scripts.RuneDragons

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.tabs.Equipment
import com.p3achb0t.scripts.RuneDragsMain
import com.p3achb0t.scripts.Task
import kotlinx.coroutines.delay
import kotlin.random.Random

class Bank(val ctx: Context) : Task(ctx.client) {
    override suspend fun isValidToRun(): Boolean {
        return RuneDragsMain.shouldBank(ctx)
    }


    override suspend fun execute() {


        var cwBank = Tile(2442, 3083, 0, ctx = ctx)
        var duelingids = hashSetOf(2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566).shuffled()
        var pendantids = hashSetOf(11194, 11193, 11192, 11191, 11190).shuffled()
        var antifires = hashSetOf(11951, 11953, 11955, 11957).shuffled()
        var extendedantifires = hashSetOf(22209, 22212, 22215).shuffled()
        var supercombats = hashSetOf(23688, 23691, 23685).shuffled()
        var itemstokeep = arrayListOf<Int>(2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566, 11194, 11193, 11192, 11191, 11190, 11951, 11953, 11955, 11957, 22209, 22212, 22215, 23688, 23691, 23685, 385)


        run teleportcw@{
            if (cwBank.distanceTo() > 25) {
                duelingids.forEach {
                    if (ctx.inventory.Contains(it) && ctx.equipment.getItemAtSlot(Equipment.Companion.Slot.Ring)?.id != it) {
                        ctx.inventory.wear(it)
                        delay(600)
                    }
                    if (ctx.equipment.getItemAtSlot(Equipment.Companion.Slot.Ring)?.id == it) {
                        ctx.equipment.duelingcastlewars()
                        Utils.waitFor(5, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return cwBank.distanceTo() < 25
                            }
                        })
                    }
                    if (cwBank.distanceTo() < 25) {
                        return@teleportcw
                    }
                }
            }
            if (cwBank.distanceTo() < 25) {
                ctx.inventory.wear(11773)
                if (!ctx.inventory.contains(11773)) {
                    if (ctx.prayer.isQuickPrayerActive()) {
                        ctx.prayer.ActivateQuickPrayer()
                        Utils.waitFor(5, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return !ctx.prayer.isQuickPrayerActive()
                            }
                        })
                    }
                    val bank = ctx.gameObjects.find(4483)
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
                        ctx.bank.withdrawXdoAction(2434, 4)
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
                        run withdrawpendant@{
                            pendantids.forEach {
                                if (!ctx.inventory.Contains(it) && ctx.bank.getItemCount(it) > 0) {
                                    ctx.bank.withdraw1doAction(it)
                                    delay(Random.nextLong(189, 777))
                                }
                                if (ctx.inventory.Contains(it)) {
                                    return@withdrawpendant
                                }
                            }
                        }
//                    run withdrawantifire@{
//                        antifires.forEach {
//                            if(!ctx.inventory.Contains(it) && ctx.bank.getItemCount(it) > 0){
//                                ctx.bank.withdraw1(it, "antifire")
//                                delay(600)
//                            }
//                            if(ctx.inventory.Contains(it)){
//                                return@withdrawantifire
//                            }
//                        }
//                    }
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
                            supercombats.forEach {
                                if (!ctx.inventory.Contains(it) && ctx.bank.getItemCount(it) > 0) {
                                    ctx.bank.withdraw1doAction(it)
                                    delay(Random.nextLong(189, 777))
                                }
                                if (ctx.inventory.Contains(it)) {
                                    return@withdrawsupercombats
                                }
                            }
                        }
                        ctx.bank.withdrawAlldoAction(385)
                        delay(Random.nextLong(466, 1111))
                    }
                }
            }

        }
    }
}