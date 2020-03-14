package com.p3achb0t.client.test

import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.Javalin
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream





fun main(args: Array<String>) {
    val tabBotWindow = BotWindow()

    val app = Javalin.create { config ->
        //config.defaultContentType = "application/json"
        //config.addStaticFiles("/public")
        config.enableCorsForAllOrigins()
    }.start(7000)
    app.routes {

        get("/bot/add") {

            tabBotWindow.manager.tabManager.addInstance()
        }

        get("/bot/add/:times") { ctx ->
            val t = ctx.pathParam("times").toInt()
            for (i in 1..t) {
                println("bot")
                tabBotWindow.manager.tabManager.addInstance()
            }
            ctx.status(200)
        }

        get("/bot/:id/kill") { ctx ->
            val kill = ctx.pathParam("id").toInt()
            //tabBotWindow.manager.tabManager.destroy(kill)
            ctx.status(200)
        }

        get("/bots") {ctx ->
            //tabBotWindow.manager.tabManager.getBots()
            ctx.status(200)
        }

        get("/bot/:id/screenshot") { ctx ->
            val id = ctx.pathParam("id").toInt()
            val image = tabBotWindow.manager.tabManager.getInstance(id).client.getScriptManager().takeScreenShot()
            val baos = ByteArrayOutputStream()
            ImageIO.write(image, "png", baos)
            val inputStream = ByteArrayInputStream(baos.toByteArray())
            ctx.header("Content-Type", "image/png")
            //ctx.header("Content-Length", "size")
            ctx.result(inputStream)
            ctx.status(200)
        }

        get("/bot/:id/screenshot/framerate/:fps") { ctx ->
            val id = ctx.pathParam("id").toInt()
            val fps = ctx.pathParam("fps").toInt()
            tabBotWindow.manager.tabManager.getInstance(id).client.getScriptManager().captureScreenFrame = fps
            //tabBotWindow.manager.bots[id].getScriptManager().captureScreenFrame = fps
            ctx.status(200)
        }

        get("/bot/:id/screenshot/scheduled/:state") { ctx ->
            val id = ctx.pathParam("id").toInt()
            val stateParam = ctx.pathParam("state").toInt()
            val state: Boolean
            state = when (stateParam) {
                0 -> false
                1 -> true
                else -> {
                    ctx.status(404)
                    return@get
                }
            }

            tabBotWindow.manager.tabManager.getInstance(id).client.getScriptManager().captureScreen = state
            ctx.status(200)
        }

        get("/bot/:id/start") { ctx ->
            val id = ctx.pathParam("id").toInt()

            tabBotWindow.manager.tabManager.getInstance(id).client.startScript()
            ctx.status(200)
        }

        get("/bot/:id/stop") { ctx ->
            val id = ctx.pathParam("id").toInt()

            tabBotWindow.manager.tabManager.getInstance(id).client.stopScript()
            ctx.status(200)
        }

        get("/bot/:id/add-debug/:name") { ctx ->
            val id = ctx.pathParam("id").toInt()
            val name = ctx.pathParam("name")
            tabBotWindow.manager.tabManager.getInstance(id).client.addDebugScript(name)
            ctx.status(200)
        }

        get("/bot/:id/remove-debug/:name") { ctx ->
            val id = ctx.pathParam("id").toInt()
            val name = ctx.pathParam("name")
            tabBotWindow.manager.tabManager.getInstance(id).client.removeDebugScript(name)
            ctx.status(200)
        }

        get("/bot/:id/script/:name") { ctx ->
            val id = ctx.pathParam("id").toInt()
            val name = ctx.pathParam("name")
            //tabBotWindow.manager.scriptManager.setScript(id,name)
            ctx.status(200)
        }

        get("/bot/refresh") { ctx ->

            //tabBotWindow.manager.scriptManager.refreshScripts()
            ctx.status(200)
        }
    }
}
