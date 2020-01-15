package com.p3achb0t.scripts.RuneDragons

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.tabs.Equipment
import com.p3achb0t.api.wrappers.tabs.Prayer
import com.p3achb0t.scripts.RuneDragsMain
import com.p3achb0t.scripts.Task
import kotlinx.coroutines.delay

class Bank(val ctx: Context) : Task(ctx.client) {
    override suspend fun isValidToRun(): Boolean {
        return RuneDragsMain.shouldBank(ctx)
    }


    override suspend fun execute() {
        var cwBank = Tile(2442, 3083, 0 , ctx=ctx)
        val duelingids = hashSetOf(2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566)
        val pendantids = hashSetOf(11194,11193,11192,11191,11190)
        val antifires = hashSetOf(11951, 11953, 11955, 11957)
        run teleportcw@{
        if(cwBank.distanceTo() > 25) {
            duelingids.forEach {
                    if (ctx.inventory.Contains(it) && ctx.equipment.getItemAtSlot(Equipment.Companion.Slot.Ring)?.id != it) {
                        ctx.inventory.getItem(it)?.click()
                        delay(600)
                    }
                    if (ctx.equipment.getItemAtSlot(Equipment.Companion.Slot.Ring)?.id == it) {
                        ctx.equipment.interactWithSlot(Equipment.Companion.Slot.Ring, "Castle wars")
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
            if(cwBank.distanceTo() < 25) {
                if(ctx.prayer.isProtectMageActive()){
                    ctx.prayer.disable(Prayer.Companion.PrayerKind.PROTECT_FROM_MAGIC);
                }
                if(ctx.prayer.isPietyActive()){
                    ctx.prayer.disable(Prayer.Companion.PrayerKind.PIETY);
                }
                val bank = ctx.gameObjects.find(4483)
                if(!ctx.bank.isOpen()) {
                    if (bank.size > 0) {
                        if (!bank[0].isOnScreen()) {
                            bank[0].turnTononeco()
                            Utils.waitFor(5, object : Utils.Condition {
                                override suspend fun accept(): Boolean {
                                    delay(100)
                                    return bank[0].isOnScreen()
                                }
                            })
                        }
                        if (bank[0].isOnScreen()) {
                            bank[0].click()
                            Utils.waitFor(5, object : Utils.Condition {
                                override suspend fun accept(): Boolean {
                                    delay(100)
                                    return ctx.bank.isOpen()
                                }
                            })
                        }
                    }
                }
                if(ctx.bank.isOpen()) {
                    if(!ctx.inventory.isEmpty()) {
                        ctx.bank.depositAll()
                    }
                    delay(500)
                    ctx.bank.withdraw(2434, "Prayer potion(4)", 4)
                    delay(600)
                    run withdrawdueling@{
                        duelingids.forEach {
                            if(!ctx.inventory.Contains(it) && ctx.bank.getItemCount(it) > 0){
                                ctx.bank.withdraw1(it, "Ring of dueling")
                                delay(600)
                            }
                            if(ctx.inventory.Contains(it)){
                                return@withdrawdueling
                            }
                        }
                    }
                    run withdrawpendant@{
                        pendantids.forEach {
                            if(!ctx.inventory.Contains(it) && ctx.bank.getItemCount(it) > 0){
                                ctx.bank.withdraw1(it, "digsite")
                                delay(600)
                            }
                            if(ctx.inventory.Contains(it)){
                                return@withdrawpendant
                            }
                        }
                    }
                    run withdrawantifire@{
                        antifires.forEach {
                            if(!ctx.inventory.Contains(it) && ctx.bank.getItemCount(it) > 0){
                                ctx.bank.withdraw1(it, "antifire")
                                delay(600)
                            }
                            if(ctx.inventory.Contains(it)){
                                return@withdrawantifire
                            }
                        }
                    }
                    ctx.bank.withdraw1(23685, "Divine super combat")
                    delay(600)
                    ctx.bank.withdrawAll(385, "Shark")
                    delay(600)
                }
            }
        }
    }
}