package com.p3achb0t.scripts

import com.p3achb0t.UserDetails
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.LoggingIntoAccount
import com.p3achb0t.api.ScriptManifest
import com.p3achb0t.api.wrappers.Projectile
import com.p3achb0t.api.wrappers.Tile
import kotlinx.coroutines.delay
import java.awt.Color
import java.awt.Graphics

@ScriptManifest("testVork","testVork","zak")
class TestVorkScript: AbstractScript()  {
    override suspend fun loop() {
        val proj = ctx.projectiles.projectiles.forEach{
            println("proj it = " + it.id)
            println("proj pos = " + it.getPosition)
            println("proj predicted tile = " + it.predictedTile)
            println("my tile = " + Tile(ctx.players.getLocal().x, ctx.players.getLocal().y))
            val scenebase = Tile(ctx.client.getBaseX(), ctx.client.getBaseY())
            println(scenebase)
        }
        delay(250)
    }

    override suspend fun start() {
        try {
            if (ctx.client.getGameState() == 30){
                UserDetails.data.username = ctx.client.getLogin_username()
                UserDetails.data.password = ctx.client.getLogin_password()
            }
        } catch (e: Exception) {
        }
        println("Running Start")
        LoggingIntoAccount(ctx)
        //Lets wait till the client is logged in
        while (ctx.client.getGameState() != 30) {
            delay(100)
        }
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(g: Graphics) {
        g.color = Color.black
        g.drawString("Current Runtime: ", 10, 450)
        super.draw(g)
    }
}