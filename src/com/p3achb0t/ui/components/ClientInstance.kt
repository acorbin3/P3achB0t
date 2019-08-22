package com.p3achb0t.ui.components

import com.p3achb0t.CustomCanvas
import com.p3achb0t.RSLoader
import com.p3achb0t._runestar_interfaces.GameShell
import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.runestar.RuneStarAnalyzer
import com.p3achb0t.api.LoggingIntoAccount
import com.p3achb0t.api.painting.PaintDebug
import com.p3achb0t.api.painting.debugPaint
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.Client
import com.p3achb0t.downloader.Parameters
import com.p3achb0t.loader.Loader

import java.applet.Applet
import java.awt.Canvas
import java.awt.Dimension
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener
import java.io.File
import java.lang.Thread.sleep
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile

enum class ClientState {
    RUNNING, PAUSED, STOPPED
}

class ClientInstance {
        //var user: User?
        var script = 0 // placeholder
        var state: ClientState = ClientState.STOPPED
        var mouseEvent: MouseEvent? = null
        val mouse = Mouse()
        var client: Applet? = null
        var customCanvas: CustomCanvas? = null
        var runeStar: RuneStarAnalyzer? = null
        var classLoader: URLClassLoader? = null


    init {
        val loader = Loader() // Should maybe be static and check when the client is launched
        val gamePackWithPath = loader.run()

        runeStar = RuneStarAnalyzer()
        runeStar?.loadHooks()

        val gamePackJar = JarFile(gamePackWithPath)
        runeStar?.parseJar(gamePackJar)

        val analyser = Analyser()
        analyser.parseJar(gamePackJar, runeStar)


        Parameters(83)
        println("Starting Client")
        val path = System.getProperty("user.dir")
        val filePathToInjected = "$path/injected_jar.jar"
        val file = File(filePathToInjected)
        val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
        println(file.toURI().toURL())
        classLoader = URLClassLoader(urlArray)
        val clientClazz = classLoader?.loadClass("client")?.newInstance()
        val game: Applet = clientClazz as Applet
        client = game
        Client.client = clientClazz as com.p3achb0t._runestar_interfaces.Client
        game.apply {
            preferredSize = Dimension(CustomCanvas.dimension.width, CustomCanvas.dimension.height)
            val loader2 = RSLoader()
            game.setStub(loader2)
        }

    }

    fun getApplet() : Applet {
        return client!!
    }

    fun run() {
        client?.apply {
            init()
            start()

        }
        sleep(100)

        // This block is used to create a custom Canvas and replace it with the RSCanvas using reflection
        client.apply {

            val canvasType =
                    runeStar?.analyzers?.get(GameShell::class.java.simpleName)?.fields?.find { it.field == "getCanvas" }
                            ?.owner
            val canvasFieldName =
                    runeStar?.analyzers?.get(GameShell::class.java.simpleName)?.fields?.find { it.field == "getCanvas" }
                            ?.name

            println("canvas " + canvasType + " Field parentId : $canvasFieldName")
            var loaded = false
            //Wait till canvas is not null so we can replace it
            while (!loaded) {
                print("IN WHILE!!!!!!!!")
                try {

                    val canvasClazz = classLoader?.loadClass(canvasType)
                    val canvasField = canvasClazz?.getDeclaredField(canvasFieldName)
                    canvasField?.isAccessible = true
                    val oldCanvas = canvasField?.get(client) as Canvas // Needed to have Applet instead of null
                    client?.remove(oldCanvas)
                    customCanvas = CustomCanvas(oldCanvas.hashCode())

                    // Adding mouse, keyboard, and paint listeners
                    for (ml in oldCanvas.mouseListeners) {
                        customCanvas?.addMouseListener(ml)
                    }
                    for (ml in oldCanvas.mouseMotionListeners) {

                        customCanvas?.addMouseMotionListener(ml)
                    }
                    val mouseListener = object : MouseMotionListener {
                        override fun mouseMoved(e: MouseEvent?) {
                            if (e != null && e.x >= 0 && e.y >= 0)
                                mouseEvent = e

                        }

                        override fun mouseDragged(e: MouseEvent?) {
                            if (e != null && e.x >= 0 && e.y >= 0)
                                mouseEvent = e
                        }

                    }
                    customCanvas?.addPaintListener(debugPaint())

                    customCanvas?.addMouseMotionListener(mouseListener)
                    for (kl in oldCanvas.keyListeners) {
                        customCanvas?.addKeyListener(kl)
                    }
                    val keyboadListner = object : KeyListener {
                        override fun keyTyped(e: KeyEvent?) {
//                        println("KeyTyped: ${e?.keyChar} Code:${e?.keyCode}")
                        }

                        override fun keyPressed(e: KeyEvent?) {
//                        println("KeyPressed: ${e?.keyChar} Code:${e?.keyCode}")
                            if (e?.isControlDown == true)  {
                                if (e.keyCode == KeyEvent.VK_1) {
                                    PaintDebug.isDebugTextOn = !PaintDebug.isDebugTextOn
                                    println("PaintDebug.isDebugTextOn:${PaintDebug.isDebugTextOn}")
                                }
                                if (e.keyCode == KeyEvent.VK_2) {
                                    PaintDebug.isPlayerPaintOn = !PaintDebug.isPlayerPaintOn
                                    println("PaintDebug.isPlayerPaintOn:${PaintDebug.isPlayerPaintOn}")
                                }
                                if (e.keyCode == KeyEvent.VK_3) {
                                    PaintDebug.isNPCPaintOn = !PaintDebug.isNPCPaintOn
                                    println("PaintDebug.isNPCPaintOn:${PaintDebug.isNPCPaintOn}")
                                }
                                if (e.keyCode == KeyEvent.VK_4) {
                                    PaintDebug.isGroundItemsOn = !PaintDebug.isGroundItemsOn
                                    println("PaintDebug.isGroundItemsOn:${PaintDebug.isGroundItemsOn}")
                                }
                            }
                        }

                        override fun keyReleased(e: KeyEvent?) {
//                        println("KeyReleased: ${e?.keyChar} Code:${e?.keyCode}")

                        }

                    }
                    customCanvas?.addKeyListener(keyboadListner)
                    for (fl in oldCanvas.focusListeners) {
                        customCanvas?.addFocusListener(fl)
                    }

                    //Replace the canvas with the custom canvas
                    canvasField.set(client, customCanvas)
                    client!!.add(customCanvas)
                    loaded = true
                    println("Reflected custom canvas")

                    break

                } catch (e: Exception) {
                    println("Error hooking canvas $e ")
                    for (item in e.stackTrace) {
                        println(item.toString())
                    }
                }
            }
        }

        //LoggingIntoAccount()

    }
}
