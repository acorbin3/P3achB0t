package com.p3achb0t

import com.p3achb0t.Main.Data.clientData
import com.p3achb0t.Main.Data.customCanvas
import com.p3achb0t.Main.Data.dream
import com.p3achb0t.Main.Data.mouseEvent
import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.DreamBotAnalyzer
import com.p3achb0t.api.LoggingIntoAccount
import com.p3achb0t.api.debugPaint
import com.p3achb0t.api.user_inputs.Camera
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.Inventory
import com.p3achb0t.api.wrappers.NPC
import com.p3achb0t.api.wrappers.Player
import com.p3achb0t.client.MenuBar
import com.p3achb0t.downloader.Downloader
import com.p3achb0t.downloader.Parameters
import com.p3achb0t.hook_interfaces.Widget
import com.p3achb0t.rsclasses.Client
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
import javax.swing.JPanel
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
        val mouse = Mouse()

    }
}

fun main() {

    val downloader = Downloader()
//    val gamePackWithPath = downloader.getGamepack()
    val gamePackWithPath = downloader.getLocalGamepack()
    println("Using $gamePackWithPath")

//    RuneLiteAnalyzer().getHooks()

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
        jMenuBar = MenuBar()
        size = Dimension(800, 600)
        minimumSize = game.minimumSize
        setLocationRelativeTo(null)
        isVisible = true

        val panel = JPanel()
        add(panel)
        panel.add(game)
        pack()
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
    GlobalScope.launch {
        // launch a new coroutine in background and continue

        while (true) {
            if (false) {
                try {
                    val npcs = NPC.findNPCs(sortByDist = true)
                    if (npcs.size > 0) {
                        npcs.forEach {
                            if (!it.isOnScreen()) {
                                println("Turning to: ${it.npc.getComposite().getName()}")
                                Camera.turnTo(it)
                            }
                            it.interact("Cancel")
                        }
                    }
                    val players = Player.findPlayers(true)
                    players.forEach {
                        if (!it.isOnScreen()) {
                            println("Turning to: ${it.player.getName()}")
                            Camera.turnTo(it)
                        }
                        it.interact("Cancel")
                    }
                } catch (e: Exception) {
                }

                val items = Inventory.getAll()
                if (items.size > 0) {

                    items.forEach {
                        if (!Inventory.isOpen()) {
                            Inventory.open()
                        }
                        it.interact("Cancel")
                    }
                }
            }

            // Dont drop any items if there are still some on the ground

//            val groundItems2 = GroundItems.getAllItems()
//            var count = 0
//            groundItems2.forEach {
//                if(it.isOnScreen()){
////                    println("Requesting to take ${it.id}")
//                    count += 1
//                }
//            }
//
//            //Drop items
//            val items = Inventory.getAll()
//            if (items.size > 0 && count == 0) {
//
//                items.forEach {
//                    if (!Inventory.isOpen()) {
//                        Inventory.open()
//                    }
//                    var inventoryCount = Inventory.getCount()
//                    it.interact("Drop")
//                    Utils.waitFor(2, object : Utils.Condition {
//                        override suspend fun accept(): Boolean {
//                            delay(100)
//                            println("Waiting for inventory to change $inventoryCount == ${Inventory.getCount()}")
//                            return inventoryCount != Inventory.getCount()
//                        }
//                    })
//                }
//            }
//
//            //Pick up items
//            val groundItems = GroundItems.getAllItems()
//            groundItems.forEach {
//                if(it.isOnScreen()){
////                    println("Requesting to take ${it.id}")
//                    it.take()
//                }
//            }

            //Delay between 0-50 ms
            delay((Math.random() * 50).toLong())
        }
    }


    LoggingIntoAccount()
//    class MyApp : App(WidgetExplorer::class)
//    launch<MyApp>()

}

