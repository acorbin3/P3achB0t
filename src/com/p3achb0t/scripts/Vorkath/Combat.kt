package com.p3achb0t.scripts.Vorkath

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.scripts.RuneDragsMain
import com.p3achb0t.scripts.Task
import com.p3achb0t.scripts.VorkathMain
import kotlinx.coroutines.delay
import kotlin.random.Random

class Combat(val ctx: Context) : Task(ctx.client) {
    override suspend fun isValidToRun(): Boolean {
        return true
    }
    companion object {
        var prayer = 35
        var firsttrip = true
        var eatto = 0
        var eatfrom = 59
        var explosion = false
        var explosiontile = Tile()
        var explosionproj = explosion
    }
    val divinecombats = hashSetOf(23685, 23688, 23691, 23694)
    val prayerpots: IntArray = intArrayOf(143, 141, 139, 2434)
    val extendedantifires = intArrayOf(22218, 22215, 22212, 22209)
    val combatTile1 = Tile(ctx.npcs.getNearestNPC("Vorkath").getGlobalLocation().x, ctx.npcs.getNearestNPC("Vorkath").getGlobalLocation().y -5 ,ctx = ctx )
    val combatTile2 = Tile(ctx.npcs.getNearestNPC("Vorkath").getGlobalLocation().x, ctx.npcs.getNearestNPC("Vorkath").getGlobalLocation().y -6 ,ctx = ctx )
    val combatTile3 = Tile(ctx.npcs.getNearestNPC("Vorkath").getGlobalLocation().x, ctx.npcs.getNearestNPC("Vorkath").getGlobalLocation().y -7 ,ctx = ctx )
    val combatTile4 = Tile(ctx.npcs.getNearestNPC("Vorkath").getGlobalLocation().x, ctx.npcs.getNearestNPC("Vorkath").getGlobalLocation().y -8 ,ctx = ctx )
    val tile1 = Tile(combatTile1.x + 1, combatTile1.y, ctx = ctx)
    val tile2 = Tile(combatTile1.x + 1, combatTile1.y - 1, ctx = ctx)
    val tile3 = Tile(combatTile1.x + 1, combatTile1.y - 2, ctx = ctx)
    val tile4 = Tile(combatTile1.x + 1, combatTile1.y - 3, ctx = ctx)
    val tile5 = Tile(combatTile1.x - 1, combatTile1.y , ctx = ctx)
    val tile6 = Tile(combatTile1.x - 1, combatTile1.y - 1, ctx = ctx)
    val tile7 = Tile(combatTile1.x - 1, combatTile1.y - 2, ctx = ctx)
    val tile8 = Tile(combatTile1.x - 1, combatTile1.y - 3, ctx = ctx)
    var s1 = false
    var s2 = false
    var s3 = false
    var s4 = false
    var s5 = false
    var s6 = false

    override suspend fun execute() {
        explosionproj = explosion
    val vokathwaiting = ctx.npcs.getNearestNPC(8059)
        val slimes = ctx.gameObjects.gameObjects
        val vorkathcombat = ctx.npcs.getNearestNPC(8061)
        val undead = ctx.npcs.getNearestNPC(8063)
        // poke to wake up
        if (!ctx.prayer.isQuickPrayerActive()){
            ctx.prayer.ActivateQuickPrayer()
            Utils.waitFor(1, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return ctx.prayer.isQuickPrayerActive()
                }
            })
        }
        if (Utils.getElapsedSeconds(RuneDragsMain.Antifiretimer.time) > 355 || firsttrip && ctx.players.getLocal().player.getSequence() != 829) {
            extendedantifires.forEach {
                while (ctx.inventory.Contains(it)) {
                    println("using antifire")
                    ctx.inventory.drink(it)
                    RuneDragsMain.Antifiretimer.reset()
                    RuneDragsMain.Antifiretimer.start()
                    firsttrip = false
                }
            }
        }
        if (ctx.stats.level(Stats.Skill.STRENGTH) == ctx.stats.currentLevel(Stats.Skill.STRENGTH) && ctx.players.getLocal().player.getSequence() != 829) {
            divinecombats.forEach {
                if (ctx.inventory.Contains(it)) {
                    println("using combats")
                    ctx.inventory.drink(it)

                }
            }
        }
        if(ctx.players.getLocal().getHealth() <= eatfrom && ctx.players.getLocal().player.getSequence() != 829) {
            eatto = ctx.stats.level(Stats.Skill.HITPOINTS) - Random.nextInt(9, 17)
            if (ctx.inventory.contains(385) && ctx.players.getLocal().getHealth() < eatto) {
                ctx.inventory.eat(385)
            }
            if (ctx.players.getLocal().getHealth() >= eatto) {
                eatto = ctx.players.getLocal().getHealth() - Random.nextInt(9, 17)
                eatfrom = Random.nextInt(55, 63)
            }
        }
        run prayerpots@{
            if (ctx.players.getLocal().getPrayer() < prayer && ctx.players.getLocal().player.getSequence() != 829) {
                prayerpots.forEach {
                    if (ctx.inventory.Contains(it)) {
                        ctx.inventory.drink(it)
                        delay(Random.nextLong(111,322))
                    }
                    if (ctx.players.getLocal().getPrayer() >= prayer) {
                        prayer = Random.nextInt(14, 37)
                        return@prayerpots
                    }
                }
            }
        }

        if(undead != null && undead.health != 0.0 && ctx.players.getLocal().player.getSequence() != 724){
            println("undead found")
        }
        if(vorkathcombat?.npc?.getSequence() != 7957){
            if(!ctx.run.isRunActivated()){
                ctx.run.activateRun()
            }
            s1 = false
            s2 = false
            s3 = false
            s4 = false
            s5 = false
            s6 = false
        }
        if(undead == null || undead.health == 0.0){
            println("undead = null or hp 0")
              if(vorkathcombat?.npc?.getSequence() == 7960 && exolisionProjectile()){
                  println("fireball detected")
                  if(Utils.getElapsedSeconds(VorkathMain.Explosion.time) > 2){
                      println("fireball detected + eplosion time > 2")
                      explosion = true
                      VorkathMain.Explosion.reset()
                      VorkathMain.Explosion.start()
                      explosiontile = ctx.players.getLocal().getGlobalLocation()
                      println("Explosion Tile:" + explosiontile)
                  }
              }
            if(explosion){
                var safetile =  Tile(explosiontile.x - 2, explosiontile.y, ctx = ctx)
                if( explosiontile.x < combatTile1.x) {
                    println("x < explosion tile")
                    safetile = Tile(explosiontile.x + 2, explosiontile.y, ctx = ctx)
                }
                if(safetile.distanceTo() > 0){
                    safetile.walktoTile()
                }
                if(!exolisionProjectile() && Utils.getElapsedSeconds(VorkathMain.Explosion.time) > 2){
                    println("Explosion = false")
                    explosion = false
                }
                if(safetile.distanceTo() == 0 && ctx.players.getLocal().player.getTargetIndex() == -1 ){
                    vorkathcombat?.doActionAttack()
                }
            }
            if(!explosion && vorkathcombat?.npc?.getSequence() != 7960 && !undeadproj()){
                if (vorkathcombat != null && ctx.players.getLocal().player.getTargetIndex() == -1) {
                    vorkathcombat.doActionAttack()
                }
            }
        }
        if(undeadproj() && !exolisionProjectile()){
            run prayerpots@{
                if (ctx.players.getLocal().getPrayer() < 47 && ctx.players.getLocal().player.getSequence() != 829) {
                    prayerpots.forEach {
                        if (ctx.inventory.Contains(it)) {
                            ctx.inventory.drink(it)
                            delay(Random.nextLong(111,322))
                        }
                        if (ctx.players.getLocal().getPrayer() >= prayer) {
                            prayer = Random.nextInt(14, 37)
                            return@prayerpots
                        }
                    }
                }
            }
            if(ctx.players.getLocal().getHealth() <= 75 && ctx.players.getLocal().player.getSequence() != 829) {
                if (ctx.inventory.contains(385) && ctx.players.getLocal().getHealth() < eatto) {
                    ctx.inventory.eat(385)
                }
            }
        }

        if(vorkathcombat?.npc?.getSequence() == 7957){
            if(ctx.run.isRunActivated()){
                ctx.run.activateRun()
            }
            ctx.gameObjects.gameObjects.forEach {
            if(it.getGlobalLocation() == tile1){
                s1 = true
            }
                if(it.getGlobalLocation() == tile2){
                    s2 = true
                }
                if(it.getGlobalLocation() == tile3){
                    s3 = true
                }
                if(it.getGlobalLocation() == tile4){
                    s4 = true
                }
                if(it.getGlobalLocation() == tile5){
                    s5 = true
                }
                if(it.getGlobalLocation() == tile6){
                    s6 = true
                }
            }
            var line1 = false
            var line2 = false
            if(s1 || s2) {
                line1 = true
            }
            if(line1){
                println("Line1")
                if(ctx.players.getLocal().player.getTargetIndex() != -1){
                    tile8.walktoTile()
                    delay(1150)
                }
                if(tile8.distanceTo() == 0){
                   vorkathcombat.doActionAttack()
                    delay(550)
                }
            }
            if(s4 || s5) {
                line2 = true
            }
            if(line2){
                println("Line2")
                if(ctx.players.getLocal().player.getTargetIndex() != -1){
                    tile4.walktoTile()
                    delay(1150)
                }
                if(tile4.distanceTo() == 0){
                    vorkathcombat.doActionAttack()
                    delay(550)
                }
            }
            if(!line1 && !line2){
                println("combat line")
                if(ctx.players.getLocal().player.getTargetIndex() != -1){
                    combatTile4.walktoTile()
                    delay(1150)
                }
                if(combatTile4.distanceTo() == 0){
                    vorkathcombat.doActionAttack()
                    delay(550)
                }
            }
    }


    }



    suspend fun exolisionProjectile(): Boolean {
        var hasproj = false
        ctx.projectiles.projectiles.forEach{
            println("projectile id = " + it.id)
            if(it.id == 1481){
                hasproj = true
            }
        }
        return hasproj
    }

    suspend fun undeadproj(): Boolean {
        var hasproj = false
        ctx.projectiles.projectiles.forEach{
            println(it.id)
            if(it.id == 1484){
                hasproj = true
            }
        }
        return hasproj
    }

}