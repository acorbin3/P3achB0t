package com.p3achb0t.scripts.action.woodcutting

import com.p3achb0t.api.Context
import com.p3achb0t.api.script.ActionScript
import com.p3achb0t.api.script.LeafTask
import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.wrappers.Area
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.api.wrappers.Tile
import kotlinx.coroutines.delay
import org.apache.commons.lang.time.StopWatch
import java.awt.Color
import java.awt.Graphics
import kotlin.random.Random

@ScriptManifest("Skills","Woodcutting","P3aches", "0.1")
class WoodCuttingScript() : ActionScript() {
    // class - Finding a tree closest to cut
    // Identify tree ids or go by the text
    // class - dropping logs when full inventory
    // class - banking logs
    // walking to bank and back to WC area
    init {

    }

    val stopwatch = StopWatch()
    override fun start() {
        this.tasks.add(ChopTree(ctx))
        this.tasks.add(DropLogs(ctx))
        stopwatch.start()
        super.start()
    }

    var status = ""
    override fun draw(g: Graphics) {
        g.color = Color.GREEN
        g.drawString(status,0,0)
        g.drawString("Current Runtime: $stopwatch", 10, 350)
        g.drawString("Status:$currentJob   itemSelected: ${ctx.client.getIsItemSelected()}", 10, 360)
        ctx.gameObjects.find(10820).forEach {
            val point = it.getMiniMapPoint()
            g.color = Color.BLUE
            g.drawRect(point.x, point.y,2,2)
        }
        super.draw(g)
    }

}

class ChopTree(ctx: Context) : LeafTask(ctx) {
    val areaGoodWCLocation:Area = Area(
            Tile(3171, 3253, 0),
            Tile(3170, 3262, 0),
            Tile(3183, 3268, 0),
            Tile(3190, 3261, 0),
            Tile(3195, 3255, 0),
            Tile(3200, 3257, 0),
            Tile(3208, 3252, 0),
            Tile(3208, 3237, 0),
            Tile(3202, 3237, 0),
            Tile(3199, 3232, 0),
            Tile(3189, 3232, 0),
            ctx=ctx
    )
    val TREE_IDs = intArrayOf(1278,1276)
    val OAK_TREE_ID = 10820

    override suspend fun isValidToRun(): Boolean {
        //Inventory not full && in the wc area
        return !ctx.inventory.isfull
    }

    override suspend fun execute() {
        // 1.enter condition
        // 2.action
        // 3.exit condition

        //Find a tree
        //chop tree
        if (ctx.players.getLocal().isIdle()) {
            var treeID = if (ctx.stats.level(Stats.Skill.WOODCUTTING)< 15) TREE_IDs.random() else OAK_TREE_ID
            val trees = ctx.gameObjects.findinArea(treeID,area=areaGoodWCLocation)
            if (trees.isNotEmpty()) {

                var randIndex = if (ctx.stats.level(Stats.Skill.WOODCUTTING)< 15) Random.nextInt(0, 3) else 0
                if(!trees[randIndex].isOnScreen()){
                    if(trees[randIndex].distanceTo() > 10){
                        trees[randIndex].clickOnMiniMap()
                        ctx.players.getLocal().waitTillIdle()
                    }
                    if(!trees[randIndex].isOnScreen()) {
                        trees[randIndex].turnTo()
                        delay(Random.nextLong(600, 1000))
                    }
                }
                trees[randIndex].interact("Chop down")
                ctx.players.getLocal().waitTillIdle(30)
            }
        }else{
            ctx.players.getLocal().waitTillIdle(30)
        }
    }

}

class DropLogs(ctx: Context) : LeafTask(ctx){
    override suspend fun isValidToRun(): Boolean {
        return ctx.inventory.isfull
    }

    override suspend fun execute() {
        val items = ctx.inventory.getAll()

        if(!ctx.inventory.isOpen()){
            ctx.inventory.open()
        }
        items.forEach {
            if(it.id in intArrayOf(1511,1521)){


                it.interact("Drop")
                //make sure a log was not accedently used
                if(ctx.client.getIsItemSelected() == 1){
                    it.click()
                    it.interact("Drop")
                }

            }
        }
    }

}