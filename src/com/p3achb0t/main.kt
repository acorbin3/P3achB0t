package com.p3achb0t

import com.p3achb0t.MainApplet.Data.customCanvas
import com.p3achb0t.MainApplet.Data.mouseEvent
import com.p3achb0t.MainApplet.Data.runeStar
import com.p3achb0t._runestar_interfaces.Component
import com.p3achb0t._runestar_interfaces.GameShell
import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.runestar.RuneStarAnalyzer
import com.p3achb0t.api.LoggingIntoAccount
import com.p3achb0t.api.Utils
import com.p3achb0t.api.painting.PaintDebug
import com.p3achb0t.api.painting.debugPaint
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.Client
import com.p3achb0t.client.MenuBar
import com.p3achb0t.downloader.Downloader
import com.p3achb0t.downloader.Parameters
import com.p3achb0t.loader.Loader
import com.p3achb0t.scripts.TutorialIsland
import com.p3achb0t.util.Util
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
import java.lang.Thread.sleep
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.Path
import java.util.jar.JarFile
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants


class MainApplet(game: Applet) {
    init {
        client = game
    }

    companion object Data {
        var client: Applet? = null
        var runeStar: RuneStarAnalyzer? = null
        var classLoader: URLClassLoader? = null
        var selectedWidget: Component? = null
        var customCanvas: CustomCanvas? = null
        var mouseEvent: MouseEvent? = null
        val mouse = Mouse()

    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Util.createAllDirs()
        if (!Files.exists(Path.of("./cache"))) {
            Files.createDirectory(Path.of("./cache"))
        }
        System.setProperty("user.home", "cache") // set cache next to your applet

        val loader = Loader()
        val gamePackWithPath = loader.run()
        val downloader = Downloader()
//        val gamePackWithPath = downloader.getGamepack()
        //val gamePackWithPath = downloader.getLocalGamepack()
        println("Using $gamePackWithPath")

        runeStar = RuneStarAnalyzer()
        runeStar?.loadHooks()

//    dream = DreamBotAnalyzer()
//
//    dream?.getDreamBotHooks()
//    dream?.parseHooks()

        val gamePackJar = JarFile(gamePackWithPath)
        runeStar?.parseJar(gamePackJar)
//        dream?.parseJar(gamePackJar)


        val analyser = Analyser()
        analyser.parseJar(gamePackJar, runeStar)

//        return

        // Getting parameters
        Parameters(83)
        println("Starting Client")
        val path = System.getProperty("user.dir")
        val filePathToInjected = "$path/injected_jar.jar"
        val file = File(filePathToInjected)
        val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
        println(file.toURI().toURL())
        MainApplet.classLoader = URLClassLoader(urlArray)
        val clientClazz = MainApplet.classLoader?.loadClass("applet")?.newInstance()
        val game: Applet = clientClazz as Applet
        MainApplet(game)
//        clientData = clientClazz as com.p3achb0t.hook_interfaces.Client

        Client.client = clientClazz as com.p3achb0t._runestar_interfaces.Client


        game.apply {
            preferredSize = Dimension(CustomCanvas.dimension.width, CustomCanvas.dimension.height)
            val loader = RSLoader(83)
            game.setStub(loader)
        }

        val jFrame = JFrame()
        jFrame.title = "Runescape"

        jFrame.apply {

            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            jMenuBar = MenuBar()
            size = Dimension(850, 650)
            minimumSize = game.minimumSize
            setLocationRelativeTo(null)
            isVisible = true

            //val panel = JPanel()
            //add(panel)
            contentPane = game
            //panel.add(game)
            pack()
        }
        //Attempt to resize but didnt work
//    jFrame.addComponentListener( object : ComponentAdapter(){
//        override fun componentResized(e: ComponentEvent?) {
//            println("REsizing to ${jFrame.size}")
//            CustomCanvas.dimension = jFrame.size
//            CustomCanvas.updateImageSize()
//            super.componentResized(e)
//        }
//    })


        game.apply {
            init()
            start()

        }
        sleep(100)
        /*
        // This block is used to create a custom Canvas and replace it with the RSCanvas using reflection
        game.apply {

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
                try {

                    val canvasClazz = MainApplet.classLoader?.loadClass(canvasType)
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
*/

//        EventQueue.invokeLater(::createAndShowGUI)
//        WidgetExplorerV3.createWidgetExplorer()
          //LoggingIntoAccount()
//        class MyApp : App(WidgetExplorer::class)
//        launch<MyApp>()



    }



}

/*
      GlobalScope.launch {
            // launch a new coroutine in background and continue

            while (true) {
                //Wait till we are logged in
//                if (Client.applet!!.getGameState() == 30) {
//                    if (true) {
//                        try {
//                            val npcs = NPCs.findNpcs(sortByDist = true)
//                            if (npcs.size > 0) {
//                                npcs.forEach {
//                                    if (!it.isOnScreen()) {
//
//                                        println("Turning to: ${it.npc.getType().getName()}")
//                                        it.turnTo()
//                                    }
//                                    it.interact("Cancel")
//                                }
//                            }
//                            val players = Player.findPlayers(true)
//                            players.forEach {
//                                if (!it.isOnScreen()) {
//                                    println("Turning to: ${it.player.getUsername().getCleanName()}")
//                                    it.turnTo()
//                                }
//                                it.interact("Cancel")
//                            }
//                        } catch (e: Exception) {
//                        }
//
//                        val items = Inventory.getAll()
//                        if (items.size > 0) {
//
//                            items.forEach {
//                                if (!Inventory.isOpen()) {
//                                    Inventory.open()
//                                }
//                                it.interact("Cancel")
//                            }
//                        }
//                    }
//                }
//
//
//                    // Cycle between tabs
//            Tabs.Tab_Types.values().iterator().forEach {
//                Tabs.Tab_Types.valueOf(it.id)?.let { it1 -> Tabs.openTab(it1) }
//            }
//
////            // Go between equiptment unequipt & back to inventory to equipt
////            Equipment.Companion.Slot.values().iterator().forEach {
////                if(Equipment.isEquipmentSlotEquipted(it)){
////                    if(!Equipment.isOpen()) Equipment.open()
////                    Equipment.unEquiptItem(it)
////                }
////            }
////            // Loop over inventory to see if there is an item that has action Wield, and if so equipt it
////            Inventory.getAll().forEach {
////                if(!Inventory.isOpen()) Inventory.open()
////                it.hover(true,Mouse.ClickType.Right)
////                if(Menu.isActionInMenu("Wield")) {
////                    println("Wielding: ${it.id}")
////                    it.interact("Wield")
////                    // Wait till item leaves inventory
////                    Utils.waitFor(2, object : Utils.Condition {
////                        override suspend fun accept(): Boolean {
////                            delay(100)
////                            return Inventory.getCount(it.id) == 0
////                        }
////                    })
////                }
////            }
//
//
//                    //If bank not open, find a banker and open it
////            if(!Bank.isOpen()){
////                Bank.open()
////                //TODO -withdraw
////                Bank.withdraw(995,3)
////                Bank.deposit(995,3)
////                Bank.withdraw(995,7)
////                Bank.depositAll()
////                Bank.close()
////            }else{
////                println("Bank Size: ${Bank.getSize()}")
////                val items = Bank.getAll()
////                items.forEach {
////                    println("${it.widgetID}:${it.stackSize}")
////                }
////                Bank.close()
////            }
//
//
//                    // Dont drop any items if there are still some on the ground
//
////            val groundItems2 = GroundItems.getAllItems()
////            var count = 0
////            groundItems2.forEach {
////                if(it.isOnScreen()){
//////                    println("Requesting to take ${it.widgetID}")
////                    count += 1
////                }
////            }
////
////            //Drop items
////            val items = Inventory.getAll()
////            if (items.size > 0 && count == 0) {
////
////                items.forEach {
////                    if (!Inventory.isOpen()) {
////                        Inventory.open()
////                    }
////                    var inventoryCount = Inventory.getCount()
////                    it.interact("Drop")
////                    Utils.waitFor(2, object : Utils.Condition {
////                        override suspend fun accept(): Boolean {
////                            delay(100)
////                            println("Waiting for inventory to change $inventoryCount == ${Inventory.getCount()}")
////                            return inventoryCount != Inventory.getCount()
////                        }
////                    })
////                }
////            }
////
////            //Pick up items
////            val groundItems = GroundItems.getAllItems()
////            groundItems.forEach {
////                if(it.isOnScreen()){
//////                    println("Requesting to take ${it.widgetID}")
////                    it.take()
////                }
////            }
//
////            Prayer.activate(Prayer.Companion.PrayerKind.THICK_SKIN)
////            delay(500)
////            Prayer.disable(Prayer.Companion.PrayerKind.THICK_SKIN)
////                Magic.cast(Magic.Companion.Spells.Wind_Strike)
////                Inventory.open()
//                    // Cast wind Strike on a chicken
////                Magic.cast(Magic.Companion.Spells.Wind_Strike)
////                val chickens = NPCs.findNpc("Chicken")
////                if(chickens.isNotEmpty()){
////                    chickens[0].interact("Cast")
////                }
//
////                val path = arrayListOf(Tile(3098,3107),Tile(3103,3103),Tile(3102,3095))
////                Walking.walkPath(path)
////                Walking.walkPath(path,true)
//                    if (!Calculations.screenInit && LoggingIntoClient.loggedIn) {
//                        Calculations.initScreenWidgetDimentions()
//                    }
//
                //TutorialIsland.run()


//                }
                //Delay between 0-50 ms
                delay((Math.random() * 50).toLong())
            }
        }
 */