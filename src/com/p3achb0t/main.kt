package com.p3achb0t

import UserDetails
import com.p3achb0t.Main.Data.clientData
import com.p3achb0t.Main.Data.customCanvas
import com.p3achb0t.Main.Data.dream
import com.p3achb0t.Main.Data.mouseEvent
import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.DreamBotAnalyzer
import com.p3achb0t.api.Calculations
import com.p3achb0t.downloader.Downloader
import com.p3achb0t.downloader.Parameters
import com.p3achb0t.hook_interfaces.Model
import com.p3achb0t.hook_interfaces.Npc
import com.p3achb0t.hook_interfaces.Player
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
import java.applet.Applet
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener
import java.io.File
import java.lang.Thread.sleep
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
        lateinit var clientData: com.p3achb0t.hook_interfaces.Client

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



    val client = game::class.java


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

        println(client.name)
        val currentWorldObs =
            dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.find { it.field == "getCurrentWorld" }?.name
        val modifer =
            dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.find { it.field == "getCurrentWorld" }
                ?.decoder?.toInt()
        println(modifer)

        dream?.classRefObs?.get("client")?.fields?.find { it.field == "getCurrentWorld" }?.name
        val worldField = Main.client!!::class.java.getDeclaredField(currentWorldObs)
        worldField.isAccessible = true

        val worldNum = worldField.getInt(game) * modifer!!
        println("currentWorld :$worldNum")

    }
//    sleep(500)
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
                        g.drawString("clientData.gameCycle :${clientData.getGameCycle()}", 50, 60)
                        g.drawString("Game State:: ${clientData.getGameState()}", 50, 70)
                        g.drawString("clientData.loginState :${clientData.getLoginState()}", 50, 80)
                        g.drawString("Account status :${clientData.getAccountStatus()}", 50, 90)
//                        g.drawString("cameraX :${clientData.getCameraX()}", 50, 100)
//                        g.drawString("cameraY :${clientData.getCameraY()}", 50, 110)
                        mouseEvent?.x?.let { mouseEvent?.y?.let { it1 -> g.drawRect(it, it1, 5, 5) } }
//                        print("[")
//                        for (x in clientData.get_widgetHeights()) {
//                            print("$x,")
//                        }
//                        println("]")
//                        println(clientData.get_username() + " " + clientData.get_isWorldSelectorOpen())
                        g.color = Color.GREEN
                        val players = clientData.getPlayers()
                        var count = 0
                        var point = Point(200,50)
                        players.iterator().forEach { _player ->
                            if (_player != null && _player.getLevel() > 0 && _player.getComposite().getStaticModelID() > 0) {
//                                print("${_player.getName()} ${_player.getLevel()} x:${_player.getLocalX()} y: ${_player.getLocalY()}, ")
//                                print("Queue size: ${_player.getQueueSize()}")
                                count += 1
                                val point = Calculations.worldToScreen(
                                    _player.getLocalX(),
                                    _player.getLocalY(),
                                    _player.getModelHeight()
                                )
                                if(point.x != -1 && point.y != -1) {
                                    g.color = Color.GREEN
                                    g.drawString(_player.getName().getName(), point.x, point.y)
                                    val polygon = getPlayerTriangles(_player)
                                    g.color = Color.YELLOW
                                    polygon.forEach {
                                        g.drawPolygon(it)
                                    }
                                }
                                point.y += 20
                            }
                        }

                        count = 0
                        val localNpcs = clientData.getLocalNPCs()
                        var npc: Npc? = null
                        localNpcs.iterator().forEach {
                            if(it != null){
                                npc = it

//                                print("Name: ${it.getComposite().getName()}, ID:${it.getComposite().getNpcComposite_id()} x:${it.getLocalX()} y:${it.getLocalY()},")
                                count += 1
                                val point =
                                    Calculations.worldToScreen(it.getLocalX(), it.getLocalY(), it.getModelHeight())
                                if (point.x != -1 && point.y != -1) {
                                    g.color = Color.GREEN
                                    g.drawString(it.getComposite().getName(), point.x, point.y)
                                    val polygon = getTriangles(npc)
                                    g.color = Color.BLUE
                                    polygon.forEach {
                                        g.drawPolygon(it)
                                    }
                                }
                            }
                        }


//                        println("")
//                        println("Model list size:" + modelList.size)

                    }

                    private fun getTriangles(npc: Npc?): ArrayList<Polygon> {
                        val polygonList = ArrayList<Polygon>()
                        val npcModels = clientData.getNpcModelCache()
                        npcModels.getHashTable().getBuckets().iterator().forEach { bucketItem ->
                            if (bucketItem != null) {
                                val next = bucketItem.getNext()
                                if (next.getId().toInt() == npc?.getComposite()?.getNpcComposite_id()) {
//                                    println("(" + bucketItem.getId().toString() + "," + next.getId().toString() + "),")

                                    val model = next as Model

                                    if (npc != null) {

                                        val locX = npc.getLocalX()
                                        val locY = npc.getLocalY()
                                        val xPoints = model.getVerticesX()
                                        val yPoints = model.getVerticesY()
                                        val zPoints = model.getVerticesZ()
                                        val indiciesX = model.getIndicesX()
                                        val indiciesY = model.getIndicesY()
                                        val indiciesZ = model.getIndicesZ()

                                        for (i in 0 until model.getIndicesLength()) {
                                            val one = Calculations.worldToScreen(
                                                locX + xPoints[indiciesX[i]],
                                                locY + zPoints[indiciesX[i]], 0 - yPoints[indiciesX[i]]
                                            )
                                            val two = Calculations.worldToScreen(
                                                locX + xPoints[indiciesY[i]],
                                                locY + zPoints[indiciesY[i]], 0 - yPoints[indiciesY[i]]
                                            )
                                            val three = Calculations.worldToScreen(
                                                locX + xPoints[indiciesZ[i]],
                                                locY + zPoints[indiciesZ[i]], 0 - yPoints[indiciesZ[i]]
                                            )
                                            if (one.x >= 0 && two.x >= 0 && three.x >= 0) {
                                                polygonList.add(
                                                    Polygon(
                                                        intArrayOf(one.x, two.x, three.x),
                                                        intArrayOf(one.y, two.y, three.y),
                                                        3
                                                    )
                                                )
                                            }

                                        }

                                    }

                                }
                            }
                        }
                        return polygonList
                    }

                    private fun getPlayerTriangles(player: Player?): ArrayList<Polygon> {
                        val polygonList = ArrayList<Polygon>()
                        val playerModels = clientData.getPlayerModelCache()
                        playerModels.getHashTable().getBuckets().iterator().forEach { bucketItem ->
                            if (bucketItem != null) {
                                val next = bucketItem.getNext()
                                if (next.getId() == player?.getComposite()?.getStaticModelID()) {
//                                    println("(" + bucketItem.getId().toString() + "," + next.getId().toString() + "),")

                                    val model = next as Model

                                    if (player != null) {

                                        val locX = player.getLocalX()
                                        val locY = player.getLocalY()
                                        val xPoints = model.getVerticesX().copyOf()
                                        val yPoints = model.getVerticesY().copyOf()
                                        val zPoints = model.getVerticesZ().copyOf()
                                        val indiciesX = model.getIndicesX().copyOf()
                                        val indiciesY = model.getIndicesY().copyOf()
                                        val indiciesZ = model.getIndicesZ().copyOf()

                                        //TODO - set rotation
                                        //TODO orentation
                                        val orientation = (player.getOrientation()).rem(2048)
                                        if (orientation != 0) {
                                            val sin = Calculations.SINE[orientation]
                                            val cos = Calculations.COSINE[orientation]
                                            for (i in 0 until xPoints.size) {
                                                val oldX = xPoints[i]
                                                val oldZ = zPoints[i]
                                                xPoints[i] = (oldX * cos + oldZ * sin).shr(16)
                                                zPoints[i] = (oldZ * cos - oldX * sin).shr(16)
                                            }
                                        }


                                        for (i in 0 until model.getIndicesLength()) {
                                            val one = Calculations.worldToScreen(
                                                locX + xPoints[indiciesX[i]],
                                                locY + zPoints[indiciesX[i]], 0 - yPoints[indiciesX[i]]
                                            )
                                            val two = Calculations.worldToScreen(
                                                locX + xPoints[indiciesY[i]],
                                                locY + zPoints[indiciesY[i]], 0 - yPoints[indiciesY[i]]
                                            )
                                            val three = Calculations.worldToScreen(
                                                locX + xPoints[indiciesZ[i]],
                                                locY + zPoints[indiciesZ[i]], 0 - yPoints[indiciesZ[i]]
                                            )
                                            if (one.x >= 0 && two.x >= 0 && three.x >= 0) {
                                                polygonList.add(
                                                    Polygon(
                                                        intArrayOf(one.x, two.x, three.x),
                                                        intArrayOf(one.y, two.y, three.y),
                                                        3
                                                    )
                                                )
                                            }

                                        }

                                    }

                                }
                            }
                        }
                        return polygonList
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
    sleep(100)
    GlobalScope.launch {
        var loggedIn = false
        val mouse = Mouse()
        repeat(1000) {
            try {
//                clientData = getClientData()

                // When loaded login
                if (!loggedIn && clientData.getGameState() == 10) {
                    mouse.moveMouse(Point(430, 280), true, Mouse.ClickType.Left)

                    delay(200)
                    sendKeys(UserDetails.data.password)
                    delay(200)

                    mouse.moveMouse(Point(300, 310), true, Mouse.ClickType.Left)
                }
//                if (Client.GameState.LoggedIn.intState == clientData.gameState.toInt()) {
//                    getLocalNPCData()
//                    getLocalPlayersData()
//                    getGroundItemData()
//                    getItemTableData()
//                    getRegion()
//                }
            } catch (e: Exception) {
                println("Exception" + e.toString())
                for (statck in e.stackTrace) {
                    println(statck.toString())
                }
            }
            delay(50)
        }
    }

//    launch<MyApp>(args)



}

