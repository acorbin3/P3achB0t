package com.p3achb0t.api.wrappers.walking

import com.p3achb0t.Main
import com.p3achb0t.api.Context
import com.p3achb0t.api.utils.Time.sleep
import com.p3achb0t.api.wrappers.Area
import com.p3achb0t.api.wrappers.Dialog
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.cache.RSCache
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.walking.TransportLoader.buildTransports
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.client.cache.util.Util
import java.io.File
import java.io.IOException
import java.io.UncheckedIOException
import java.util.*
import java.util.concurrent.*
import java.util.function.Predicate

class WalkingPathFinding(val ctx: Context) {

    private val MAX_WALK_DISTANCE = 19
    private val DEATHS_OFFICE = Area(Tile(3167, 5733, 0), Tile(3184, 5720, 0), ctx = ctx)

    companion object {
        private const val MAX_MIN_ENERGY = 50
        private const val MIN_ENERGY = 15
        private var minEnergy = Random().nextInt(MAX_MIN_ENERGY - MIN_ENERGY + 1) + MIN_ENERGY

        private var map: CollisionMap? = null
        val PATHFINDING_EXECUTOR = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
        ) { r: Runnable? ->
            val thread = Executors.defaultThreadFactory().newThread(r)
            thread.isDaemon = true
            thread
        }

        init {
            if (true) {
                try {

                    val collisonsFileName = "collision-map"

                    //Depending on if we are running within IntelliJ or from Jar the hooks file might be in a different location
                    val collisionsMapFile = if (File("./resources/$collisonsFileName").exists()) {
                        File("./resources/$collisonsFileName").inputStream()
                    } else {
                        Main.javaClass.getResourceAsStream("/$collisonsFileName")
                    }

                    try {
                        map = CollisionMap(Util.ungzip(collisionsMapFile.readAllBytes()))
                        println("All read!")
                    } catch (e: IOException) {
                        throw UncheckedIOException(e)
                    }
                } catch (e: java.io.IOException) {
                    throw UncheckedIOException(e)
                }

            }


        }

    }

    suspend fun walkTo(target: Area) {
        if (target.isPlayerInArea(false)) {
            return
        }

        // DEATHS_OFFICE.contains(ctx.localPlayer().templateTile())
        if (DEATHS_OFFICE.isPlayerInArea(true)) {
            if (ctx.dialog.chatState() !== Dialog.ChatState.NPC_CHAT) {
                ctx.npcs.findNpc("Death", true).stream().findFirst().get().interact("Talk-to")
                Utils.sleepUntil({ ctx.dialog.chatState() == Dialog.ChatState.NPC_CHAT })
            }
            ctx.dialog.chat(
                "How do I pay a gravestone fee?",
                "How long do I have to return to my gravestone?",
                "How do I know what will happen to my items when I die?",
                "I think I'm done here."
            )
//            ctx.dialog.selectionOption("How do I pay a gravestone fee?")
//            ctx.dialog.continueDialog()
//            ctx.dialog.selectionOption("How long do I have to return to my gravestone?")
//            ctx.dialog.continueDialog()
//            ctx.dialog.selectionOption("How do I know what will happen to my items when I die?")
//            ctx.dialog.continueDialog()
//            ctx.dialog.selectionOption("I think I'm done here.")
//            ctx.dialog.continueDialog()

            ctx.gameObjects.find("Portal").first().interact("Use")

            Utils.sleepUntil({ !DEATHS_OFFICE.isPlayerInArea() })
        }
        System.out.println(
            "[Walking] Pathfinding " + ctx.players.getLocal().getGlobalLocation().toString() + " -> " + target
        )
        val transports: MutableMap<Tile, MutableList<Transport>> = HashMap()
        val transportTiles: MutableMap<Tile, MutableList<Tile>> = HashMap()
        for (transport in buildTransports(ctx)) {
            if(transport != null) {
                transports.computeIfAbsent(
                    transport.source
                ) { k: Tile? -> ArrayList() }.add(transport)
                transportTiles.computeIfAbsent(
                    transport.source
                ) { k: Tile? -> ArrayList() }.add(transport.target)
            }
        }

        val teleports: MutableMap<Tile, Teleport> = LinkedHashMap()
        for (teleport in TeleportLoader(ctx).buildTeleports()!!) {
            teleports.putIfAbsent(teleport.target, teleport)
        }

        val starts = ArrayList(teleports.keys)
        starts.add(ctx.players.getLocal().getGlobalLocation())

//        val path = map?.let {
//            Pathfinder(it, transportTiles, starts) { locatables: Tile? ->
//                target.contains(locatables!!)
//            }.find()
//        } ?: throw IllegalStateException(
//            "couldn't pathfind " + ctx.players.getLocal().getGlobalLocation().toString() + " -> " + target
//        )
        val path: List<Tile?>? = pathfind(starts, target, transportTiles)
        println("[Walking] Done pathfinding. size: ${path?.size}")
        val startTile = path?.firstOrNull()
        if(startTile != null) {
            val teleport = teleports[startTile]
            if (teleport != null) {
                println("[Walking] Teleporting to path start")
                teleport.handler()
                Utils.sleepUntil({ teleport.target.distanceTo() < teleport.radius })
            }
        }
        if(path != null) {
            walkAlong(path as List<Tile>, transports)
        }
    }

    private suspend fun pathfind(
        start: ArrayList<Tile>,
        target: Area,
        tranports: Map<Tile, List<Tile>>
    ): List<Tile?>? {
//        val result: Future<*> = PATHFINDING_EXECUTOR.submit(Runnable {
//                Pathfinder(
//                    map!!,
//                    tranports,
//                    start,
//                    target::contains
//                ).find()
//        })
//        while (!result.isDone) {
//            sleep(600)
//        }
//        return result.get() as List<Tile?>?
        return Pathfinder(
            map!!,
            tranports,
            start,
            target.getRandomTile()
        ).find()
    }

    private suspend fun walkAlong(path: List<Tile>, transports: Map<Tile, List<Transport>>) {
        var failed = 0
        val target = path.last()
        target.updateCTX(ctx)
        path.forEachIndexed { index, tile ->
            tile.updateCTX(ctx)
            print("($index)$tile -> ")
        }
        var fails = 0
        println("TEleports")
        transports.forEach { t, u ->
            t.updateCTX(ctx)
        }
        transports.filter { it.key.distanceTo() < 100 }.forEach { (t, u) ->
            println("$t(d:${t.distanceTo()}) -> ${u.first().source}->${u.first().target}")
        }
        while (target.distanceTo() > 3) {
            val remainingPath: List<Tile> = remainingPath(path)
            val start: Tile = path[0]
            val current: Tile = ctx.players.getLocal().getGlobalLocation()
            val end: Tile = path[path.size - 1]
            val progress = path.size - remainingPath.size
            println("[Walking] " + start + " -> " + current + " -> " + end + ": " + progress + " / " + path.size)

            if (handleBreak(remainingPath, transports)) {
                continue
            }

            if (!stepAlong(remainingPath)) {
                check(fails++ != 5) { "stuck in path at " + ctx.players.getLocal().getGlobalLocation() }
            } else {
                fails = 0
            }
        }
        println("[Walking] Path end reached")
    }

    private suspend fun openDiagonalDoor(tile: Tile): Boolean {
        val gameObjects = ctx.gameObjects.gameObjects.first { it.distanceTo(tile) == 0 }
        gameObjects.interact("Open")
        Utils.sleepUntil({ isStill() })
        return true
    }

    private suspend fun openDoor(tile: Tile): Boolean {
        val gameObjects = ctx.gameObjects.gameObjects.first { it.distanceTo(tile) == 0 }
        gameObjects.interact("Open")
        Utils.sleepUntil({ isStill() })
        return true
    }

    private fun hasDoor(tile: Tile): Boolean {
        val walls = ctx.gameObjects.gameObjects.filter { it.distanceTo(tile) == 0 }
        return walls.isNotEmpty()
                && walls.first().wallObject != null
                //Check to see from the cache if we have Open for this item
                && ctx.cache.getActions(walls.first().id)?.contains("Open") ?: false
    }

    private fun hasDiagonalDoor(tile: Tile): Boolean {

        val gameObjects = ctx.gameObjects.gameObjects.filter { it.distanceTo(tile) == 0 }
        return gameObjects.isNotEmpty()
                && gameObjects.first().wallObject != null
                //Check to see from the cache if we have Open for this item
                && ctx.cache.getActions(gameObjects.first().id)?.contains("Open") ?: false
    }

    private fun isWallBlocking(a: Tile, b: Tile): Boolean {
        val wallA = ctx.gameObjects.gameObjects.first { it.distanceTo(a) == 0 }
        return when (wallA.wallObject?.getOrientationA()) {
            0 -> a.west().distanceTo(b)==0 || a.northWest().distanceTo(b)==0 || a.southWest().distanceTo(b)==0;
            1 -> a.north().distanceTo(b)==0 || a.northWest().distanceTo(b)==0 || a.northEast().distanceTo(b)==0;
            2 -> a.east().distanceTo(b)==0 || a.northEast().distanceTo(b)==0 || a.southEast().distanceTo(b)==0;
            3 -> a.south().distanceTo(b)==0 || a.southWest().distanceTo(b)==0 || a.southEast().distanceTo(b)==0;
            else -> true
        }
    }


    private suspend fun handleTransport(transport: Transport): Boolean {
        println("[Walking] Handling transport " + transport.source + " -> " + transport.target)
        transport.handler(ctx)
        Utils.sleepUntil({ ctx.players.getLocal().distanceTo(transport.target) < 10 }, 10)
        return ctx.players.getLocal().distanceTo(transport.target) < 10
    }

    private suspend fun handleBreak(path: List<Tile>, transports: Map<Tile, List<Transport?>>): Boolean {
        for (i in 0 until MAX_WALK_DISTANCE) {
            if (i + 1 >= path.size) {
                break
            }
            val a = path[i]
            val b = path[i + 1]
//            println("Checking teleports from $a & $b")
            val tileA: Tile = a
            val tileB: Tile = b
            if (tileA == null) {
                return false
            }
            val transportTargets = transports.filter { it.key.isSameTile(a) }

//            val transport2 = transportTargets?.firstOrNull { it?.target?.distanceTo(b) == 0 && it.target.z == b.z }
            var transport : Transport? = null
            transportTargets.forEach { t, u ->
                if(u.first()?.target?.isSameTile(b) == true){
                    transport = u.first()
                }
            }
//            val transport = transportTargets?.firstOrNull { it?.target?.isSameTile(b) ?: false }
            if (transport != null && transport!!.source.distanceTo() <= transport!!.sourceRadius) {
//                println("Found transport ${transport!!.source} -> ${transport!!.target}")

                return handleTransport(transport!!)
            }
            if (hasDiagonalDoor(tileA)) return openDiagonalDoor(a)
            if (tileB == null) {
                return false // scene edge
            }
            if (hasDoor(tileA) && isWallBlocking(a, b)) return openDoor(a)
            if (hasDoor(tileB) && isWallBlocking(b, a)) return openDoor(b)
        }
        return false
    }


    private fun remainingPath(path: List<Tile>): List<Tile> {
        val currentPlaneTiles = path.filter { it.z == ctx.players.getLocal().getGlobalLocation().z }
//        println("Tiles on plane")
        currentPlaneTiles.forEach {
            print("$it ")
        }
        println()
        val closestTile = path.minByOrNull { it.distanceTo() }
        println("Closest tile: $closestTile")
        val remainingPath = path.subList(path.indexOf(closestTile), path.size)
        check(!remainingPath.isEmpty()) {
            "too far from path " + ctx.players.getLocal().getGlobalLocation().toString() + " -> " + closestTile
        }
        return remainingPath
    }

    private suspend fun stepAlong(path: List<Tile>): Boolean {
        val reachable: MutableList<Tile> = ArrayList()
        for (tile in path) {
            tile.updateCTX(ctx)
            if (tile.distanceTo() >= MAX_WALK_DISTANCE) {
                break
            }
            reachable.add(tile)
        }
        check(!reachable.isEmpty()) {
            "no tiles in the path are reachable " + ctx.players.getLocal().getGlobalLocation()
                .toString() + " " + path[0].toString() + " -> " + path[path.size - 1]
        }
        val step = reachable[Math.min(Random().nextInt(MAX_WALK_DISTANCE), reachable.size - 1)]
        step.updateCTX(ctx)
        val currentDistance: Int = step.distanceTo()
        val nextStepDistance = 2 + Random().nextInt(1 + currentDistance / 3)

        step.clickOnMiniMap()
        //TODO do action 
        var lastTile: Tile = ctx.players.getLocal().getGlobalLocation()
        while (step.distanceTo() > nextStepDistance) {
            sleep(1000, 1500)
            if (ctx.run.runEnergy > minEnergy) {
                setRun(true)
            }
            if (lastTile.distanceTo() == 0) {
                System.err.println(
                    "[Walking] Path step stuck: " + ctx.players.getLocal().getGlobalLocation()
                        .toString() + " -> " + step
                )
                return false // stuck, cancel this step
            }
            lastTile = ctx.players.getLocal().getGlobalLocation()
        }
        return true
    }

    suspend fun setRun(run: Boolean) {
        if (ctx.run.isRunActivated() != run) {
            WidgetItem(ctx.widgets.find(160, 22), ctx = ctx).click()
            minEnergy = Random().nextInt(MAX_MIN_ENERGY - MIN_ENERGY + 1) + MIN_ENERGY
            println("[Walking] Enabling run, next minimum run energy: $minEnergy")
            sleep(400, 700)
        }
    }

    fun reachable(target: Area): Boolean {
        if (target.isPlayerInArea()) {
            return true
        }
        val path = map?.let {
            Pathfinder(
                it, emptyMap(), java.util.List.of(ctx.players.getLocal().getGlobalLocation()),
                target = target.getCentralTile()).find()
        } ?: return false
        for (tile in path) {
            val walls = ctx.gameObjects.gameObjects.filter { it.distanceTo(tile) == 0 }
            return walls.isNotEmpty()
                    && walls.first().wallObject != null
                    //Check to see from the cache if we have Open for this item
                    && ctx.cache.getActions(walls.first().id)?.contains("Open") ?: false
        }
        return true
    }

    suspend fun isStill(): Boolean {
        val tile: Tile = ctx.players.getLocal().getGlobalLocation()
        sleep(600) // wait one game tick
        return tile.distanceTo() == 0
    }

}