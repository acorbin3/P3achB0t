package com.p3achb0t

import UserDetails
import com.p3achb0t.Main.Data.clientData
import com.p3achb0t.Main.Data.customCanvas
import com.p3achb0t.Main.Data.dream
import com.p3achb0t.Main.Data.mouseEvent
import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.DreamBotAnalyzer
import com.p3achb0t.analyser.RuneLiteAnalyzer
import com.p3achb0t.downloader.Downloader
import com.p3achb0t.downloader.Parameters
import com.p3achb0t.interfaces.PaintListener
import com.p3achb0t.rsclasses.Client
import com.p3achb0t.rsclasses.Widget
import com.p3achb0t.user_inputs.Mouse
import com.p3achb0t.user_inputs.sendKeys
import com.p3achb0t.widgetexplorer.WidgetExplorer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tornadofx.App
import tornadofx.launch
import java.applet.Applet
import java.awt.*
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

class MyApp : App(WidgetExplorer::class)

class CustomApplet : Applet()

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
        var clientData: Client = Client()

    }
}

fun main(args: Array<String>){

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
//    dream?.generateSuperGraph()


    val analyser = Analyser()

    analyser.parseJar(gamePackJar)


    dream?.createAccessorFieldsForClass(Client::class.java)
//    dream.createAccessorFieldsForClass(Tile::class.java)
//    dream.createAccessorFieldsForClass(Renderable::class.java)
//    dream.createAccessorFieldsForClass(ObjectComposite::class.java)
//    dream.createAccessorFieldsForClass(ObjectComposite::class.java)
//    dream.createAccessorFieldsForClass(ObjectComposite::class.java)




    // Getting parameters
    Parameters(1)
    println("Starting Client")
    val file = File("./injected_jar.jar")
    val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
    Main.classLoader = URLClassLoader(urlArray)
    val clientClazz = Main.classLoader?.loadClass("client")?.newInstance()
    val game: Applet = clientClazz as Applet
    Main(game)
    val theClient: com.p3achb0t.hook_interfaces.Client = clientClazz as com.p3achb0t.hook_interfaces.Client



    val client = game::class.java


    game.apply {
        preferredSize = Dimension(765, 503)
        val loader = RSLoader()
        game.setStub(loader)
    }
    val jFrame = JFrame()
    jFrame.title = "Runescape"
//    val jMenuBar = JMenuBar()
//    val jmenu = JMenu()
//    val jItem = JMenuItem("size")
//    jmenu.add(jItem)
//    jMenuBar.add(jmenu)
    jFrame.apply {

        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
//        add(jMenuBar)
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

        println(client.name)
        val currentWorldObs =
            dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.find { it.field == "currentWorld" }?.name
        val modifer =
            dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.find { it.field == "currentWorld" }
                ?.decoder?.toInt()
        println(modifer)

        dream?.classRefObs?.get("client")?.fields?.find { it.field == "currentWorld" }?.name
        val worldField = Main.client!!::class.java.getDeclaredField(currentWorldObs)
        worldField.isAccessible = true

        val worldNum = worldField.getInt(game) * modifer!!
        println("currentWorld :$worldNum")

    }

    game.apply {

        val canvasType =
            dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.find { it.field == "canvas" }?.owner
        val canvasFieldName =
            dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.find { it.field == "canvas" }?.name

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

                customCanvas?.addPaintListener(object : PaintListener {
                    override fun onPaint(g: Graphics) {
                        g.color = Color.white
                        g.drawString("Mouse x:${mouseEvent?.x} y:${mouseEvent?.y}", 50, 50)
                        g.drawString("clientData.gameCycle :${theClient.get_gameCycle()}", 50, 60)
                        g.drawString("Game State:: ${theClient.get_gameState()}", 50, 70)
                        g.drawString("clientData.loginState :${theClient.get_loginState()}", 50, 80)
                        g.drawString("Account status :${theClient.get_accountStatus()}", 50, 90)
                        g.drawString("cameraX :${theClient.get_cameraX()}", 50, 100)
                        g.drawString("cameraY :${theClient.get_cameraY()}", 50, 110)
                        mouseEvent?.x?.let { mouseEvent?.y?.let { it1 -> g.drawRect(it, it1, 5, 5) } }
//                        print("[")
//                        for (x in theClient.get_widgetHeights()) {
//                            print("$x,")
//                        }
//                        println("]")
//                        println(theClient.get_username() + " " + theClient.get_isWorldSelectorOpen())
                        val players = theClient.get_players()
                        players.iterator().forEach { _player ->
                            if (_player != null && _player.get_level() > 0) {
                                print("${_player.get_name()} ${_player.get_level()}, ")
                            }
                        }
                        println()
//                        println(players.size)
                    }

                })
//                customCanvas?.addPaintListener(object : PaintListener {
//                    override fun onPaint(g: Graphics) {
//                        g.color = Color.white
//                        g.drawString("clientData.gameCycle :${clientData.gameCycle}", 50, 60)
//                        g.drawString("clientData.gameState :${clientData.gameState}", 50, 70)
//                        g.drawString("clientData.loginState :${clientData.loginState}", 50, 80)
//                        g.drawString("clientData.password :${clientData.password}", 50, 90)
//
//                    }
//
//                })
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
                canvasField.set(game, customCanvas) // Needed to have Applet instead of null
                game.add(customCanvas)
                loaded = true

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
        var loggedIn = false
        val mouse = Mouse()
        repeat(1000) {
            try {
//                clientData = getClientData()

                // When loaded login
                if (!loggedIn && clientData.gameState == "10") {
                    mouse.moveMouse(Point(430, 280), true, Mouse.ClickType.Left)

                    delay(200)
                    sendKeys(UserDetails.data.password)
                    delay(200)

                    mouse.moveMouse(Point(300, 310), true, Mouse.ClickType.Left)
                }
                if (Client.GameState.LoggedIn.intState == clientData.gameState.toInt()) {
//                    getLocalNPCData()
//                    getLocalPlayersData()
//                    getGroundItemData()
//                    getItemTableData()
//                    getRegion()
                }
            } catch (e: Exception) {
                println("Exception" + e.toString())
                for (statck in e.stackTrace) {
                    println(statck.toString())
                }
            }
            delay(200)
        }
    }

    launch<MyApp>(args)



}

