package com.p3achb0t

import com.p3achb0t.Main.Data.clientData
import com.p3achb0t.Main.Data.customCanvas
import com.p3achb0t.Main.Data.dream
import com.p3achb0t.Main.Data.mouseEvent
import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.DreamBotAnalyzer
import com.p3achb0t.analyser.RuneLiteAnalyzer
import com.p3achb0t.api.LoggingIntoAccount
import com.p3achb0t.api.debugPaint
import com.p3achb0t.downloader.Downloader
import com.p3achb0t.downloader.Parameters
import com.p3achb0t.hook_interfaces.Widget
import com.p3achb0t.rsclasses.Client
import java.applet.Applet
import java.awt.Canvas
import java.awt.Dimension
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile
import javax.swing.JFrame
import javax.swing.WindowConstants


class Main(game: Applet) {
    init {
        client = game
    }

    companion object Data {
        var client: Applet? = null
        var dream: DreamBotAnalyzer? = null
        var classLoader: ClassLoader? = null
        var selectedWidget: Widget? = null
        var customCanvas: CustomCanvas? = null
        var mouseEvent: MouseEvent? = null
        lateinit var clientData: com.p3achb0t.hook_interfaces.Client

    }
}

fun main() {

    val downloader = Downloader()
//    val gamePackWithPath = downloader.getGamepack()
    val gamePackWithPath = downloader.getLocalGamepack()
    println("Using $gamePackWithPath")

    RuneLiteAnalyzer().getHooks()

    dream = DreamBotAnalyzer()

    dream?.getDreamBotHooks()
    dream?.parseHooks()
    val gamePackJar = JarFile(gamePackWithPath)
    dream?.parseJar(gamePackJar)


    val analyser = Analyser()
    analyser.parseJar(gamePackJar, dream)


    // Getting parameters
    Parameters(1)
    println("Starting Client")
    val file = File("./injected_jar.jar")
    val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
    Main.classLoader = URLClassLoader(urlArray)
    val clientClazz = Main.classLoader?.loadClass("client")?.newInstance()
    val game: Applet = clientClazz as Applet
    Main(game)
    clientData = clientClazz as com.p3achb0t.hook_interfaces.Client


    game.apply {
        preferredSize = Dimension(765, 503)
        val loader = RSLoader()
        game.setStub(loader)
    }

    val jFrame = JFrame()
    jFrame.title = "Runescape"
    jFrame.apply {

        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        add(game)

        pack()
        preferredSize = size
        minimumSize = game.minimumSize
        setLocationRelativeTo(null)
        isVisible = true
    }

    game.apply {
        init()
        start()

    }
//    sleep(500)

    // This block is used to create a custom Canvas and replace it with the RSCanvas using reflection
    game.apply {

        val canvasType =
            dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.find { it.field == "getCanvas" }?.owner
        val canvasFieldName =
            dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.find { it.field == "getCanvas" }?.name

        println("canvas " + canvasType + " Field parentId : $canvasFieldName")
        var loaded = false
        //Wait till canvas is not null so we can replace it
        while (!loaded) {
            try {

                val canvasClazz = Main.classLoader?.loadClass(canvasType)
                val canvasField = canvasClazz?.getDeclaredField(canvasFieldName)
                canvasField?.isAccessible = true
                val oldCanvas = canvasField?.get(game) as Canvas // Needed to have Applet instead of null
                game.remove(oldCanvas)
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
                        mouseEvent = e

                    }

                    override fun mouseDragged(e: MouseEvent?) {
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
                canvasField.set(game, customCanvas)
                game.add(customCanvas)
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

    LoggingIntoAccount()
//    class MyApp : App(WidgetExplorer::class)
//    launch<MyApp>()

}

