package com.p3achb0t.client.injection

import com.p3achb0t.api.Context
import com.p3achb0t.api.MenuOpcode
import com.p3achb0t.api.MenuOpcode.CANCEL
import com.p3achb0t.api.StopWatch
import com.p3achb0t.api.script.ActionScript
import com.p3achb0t.api.script.GroupTask
import com.p3achb0t.api.script.PaintScript
import com.p3achb0t.api.script.ServiceScript
import com.p3achb0t.api.script.listeners.ChatListener
import com.p3achb0t.api.script.listeners.GameTick
import com.p3achb0t.api.utils.Logging
import com.p3achb0t.api.wrappers.GameState
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.client.accounts.Account
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.scripts.NullScript
import com.p3achb0t.client.scripts.loading.ScriptInformation
import com.p3achb0t.scripts.paint.debug.TotalDebugPaint
import com.p3achb0t.scripts.service.restart_action.RestartIdle
import kotlinx.coroutines.*
import java.applet.Applet
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.ConcurrentHashMap

enum class ScriptState {
    Running,
    Stopped,
    Paused,
    LoginScreenNotPaused
}

class InstanceManager(val client: Any) : Logging() {


    // Client candy
    lateinit var ctx: Context
    var isContextLoaded = false
    lateinit var instanceUUID: String

    // Action script vars
    var actionScript: ActionScript = NullScript()
    private var actionScriptLoop: Job? = null

    //var isActionScriptPaused = false
    var previousScriptState = ScriptState.Stopped
    var scriptState = ScriptState.Stopped


    // Service script
    // Many different kinds of services scripts could use the loaded account info such as the following:
    // BankPinHandler, LoginHandler, break Handler

    var account = Account()
    var actionScriptFilename = ""
    private var serviceLoop: Job? = null
    val serviceScripts = ConcurrentHashMap<String, ServiceScript>()

    // Paint Script
    val paintScripts = ConcurrentHashMap<String, PaintScript>()

    //Util
    var drawCanvas = true

    // Don't delete this. Its used within the injected functions
    var blockFocus = false

    var isDisableScenery = false

    var isDisableAll = false

    var lastTile = Tile()
    val lastMoved = StopWatch()

    init {

        // TODO fail after 1 sec need to be thread
        GlobalScope.launch {
            while ((client as Applet).componentCount == 0) {
                delay(20)
            }
            delay(1000)
            ctx = Context(client)
            actionScript::ctx.set(ctx)
            isContextLoaded = true
        }
    }

    fun startActionScript(scriptFileName: String) {
        startActionScript(scriptFileName, Account())
    }

    fun startActionScript(scriptFileName: String, account: Account = Account()) {
        this.actionScriptFilename = scriptFileName
        this.account = account

        if (actionScriptLoop != null)
            stopActionScript()


        //Look for script file name or the script name. Script name would be used for account loading from JSON
        val script: ScriptInformation = GlobalStructs.scripts.findLoadedScript(scriptFileName) ?: return

        println("Setting up Action Script: ${script.name}")
        val actionScriptLoaded = script.load() as ActionScript
        waitOnContext()
        actionScriptLoaded::ctx.set(setupContext(client))
        actionScriptLoaded::account.set(account)
        actionScript = actionScriptLoaded
        actionScript.start()


        //Check to see if we need to login or handle welcome button
        scriptState = if (serviceScripts.contains("Login Handler")
            && (ctx.client.getGameState().let { GameState.of(it) } == GameState.LOGIN_SCREEN
                    || (ctx.client.getGameState().let { GameState.of(it) } == GameState.LOGGED_IN
                    && ctx.worldHop.isWelcomeRedButtonAvailable())) && account.startActionScriptAutomatically
        ) {
            ScriptState.LoginScreenNotPaused
        } else {
            ScriptState.Running
        }


        actionScriptState(true)
        this.account.sessionStartTime = System.currentTimeMillis()
        GlobalStructs.botManager.botNavMenu.updateScriptManagerButtons()
        if (account.actionScript.isEmpty()) account.actionScript = script.name
        if (account.username.isEmpty()) account.username = UUID.randomUUID().toString()
        if (account.sessionToken.isEmpty()) account.sessionToken = UUID.randomUUID().toString()

        GlobalStructs.db.initalScriptLoad(account)
    }

    fun stopActionScript() {
        scriptState = ScriptState.Stopped
        actionScriptState(false)
        actionScript.stop()
        GlobalStructs.communication.unsubscribeAllChannels(
            actionScript.ctx.ipc.channels.keys,
            actionScript.ctx.ipc.scriptUUID
        )

        val nullScript = NullScript()
        waitOnContext()
        nullScript::ctx.set(setupContext(client))
        actionScript = nullScript
    }

    suspend fun stopServiceLoop() {
        serviceLoop?.cancelAndJoin()
    }


    fun togglePauseActionScript(isFromUI: Boolean = false) {

        previousScriptState = scriptState
        if (scriptState == ScriptState.Running) {
            actionScript.pause()
            scriptState = if (isFromUI) {
                ScriptState.Paused
            } else {
                ScriptState.LoginScreenNotPaused
            }
            actionScriptState(false)
        } else {
            scriptState = if (previousScriptState == ScriptState.Paused) {
                if (!ctx.worldHop.isLoggedIn) {
                    ScriptState.LoginScreenNotPaused
                } else {
                    actionScript.resume()
                    actionScriptState(true)
                    ScriptState.Running
                }
            } else {
                actionScript.resume()
                actionScriptState(true)
                ScriptState.Running
            }
        }
        GlobalStructs.botManager.botNavMenu.updateScriptManagerButtons()
    }

    private fun actionScriptState(loopRunning: Boolean) {
        if (loopRunning) {
            //Close out all tasks
            if (!loopRunning) {
                actionScript.tasks.forEach {
                    it.jobs.forEach { job ->
                        GlobalScope.launch { job.cancelAndJoin() }

                    }
                }
            }
            if (actionScriptLoop == null) {
                actionScriptLoop = GlobalScope.launch {
                    while (true) {

                        if (account.banned) {
                            stopActionScript()
                        }
                        try {
                            actionScript.loop()
                        } catch (e: Exception) {
                            if (e.localizedMessage != null) {
                                logger.error(e.localizedMessage)
                            }
                            if (e.stackTrace != null) {
                                e.stackTrace.forEach {
                                    logger.error(it.toString())
                                }
                            }
                        }
                        delay(10)
                    }
                }
            }
        } else {
            actionScriptLoop?.cancel()
            actionScriptLoop = null
        }
    }

    fun togglePaintScript(scriptFileName: String) {
        if (paintScripts[scriptFileName] == null) {
            addPaintScript(scriptFileName)
        } else {
            removePaintScript(scriptFileName)
        }
    }

    fun addPaintScript(scriptFileName: String) {
        val script: ScriptInformation = GlobalStructs.scripts.findLoadedScript(scriptFileName) ?: return
        val paintScript = script.load() as PaintScript
        waitOnContext()
        paintScript::ctx.set(setupContext(client))
        paintScript.start()
        paintScripts[scriptFileName] = paintScript
    }

    fun removePaintScript(scriptFileName: String) {
        val paintScript = paintScripts[scriptFileName] ?: return
        paintScripts.remove(scriptFileName)
        paintScript.stop()
        GlobalStructs.communication.unsubscribeAllChannels(
            paintScript.ctx.ipc.channels.keys,
            paintScript.ctx.ipc.scriptUUID
        )
    }

    fun drawPaintScripts(g: Graphics) {
        g.font = Font("Consolas", Font.PLAIN, 12)

        for (script in paintScripts.values) {
            script.draw(g)
        }

        if (this::ctx.isInitialized && !ctx.worldHop.isLoggedIn && scriptState == ScriptState.Running) {
            val oldFont = g.font
            val olcColor = g.color
            g.font = g.font.deriveFont(30F)
            g.color = Color.RED
            g.drawString("Must login before script starts", 300, 300)
            g.font = oldFont
            g.color = olcColor
        }
    }

    fun drawsServiceScripts(g: Graphics) {
        g.font = Font("Consolas", Font.PLAIN, 12)
        for (script in serviceScripts.values) {
            script.draw(g)
        }
    }

    // Service script
    fun addServiceScript(scriptFileName: String) {
        val script: ScriptInformation = GlobalStructs.scripts.findLoadedScript(scriptFileName) ?: return
        val serviceScript = script.load() as ServiceScript
        waitOnContext()
        serviceScript::ctx.set(setupContext(client))
        serviceScript.start()
        serviceScript.account = account
        serviceScripts[scriptFileName] = serviceScript


        if (serviceLoop == null) {
            serviceLoop = GlobalScope.launch {
                while (true) {
                    loopServiceScripts()
                    delay(200)
                }
            }
        }
    }


    fun removeServiceScript(scriptFileName: String) {
        val serviceScript = serviceScripts[scriptFileName] ?: return
        serviceScripts.remove(scriptFileName)
        serviceScript.stop()
        GlobalStructs.communication.unsubscribeAllChannels(
            serviceScript.ctx.ipc.channels.keys,
            serviceScript.ctx.ipc.scriptUUID
        )

        if (serviceScripts.size == 0 && serviceLoop != null) {
            serviceLoop?.cancel()
            serviceLoop = null
        }
    }

    fun toggleServiceScript(scriptFileName: String) {
        if (serviceScripts[scriptFileName] == null) {
            addServiceScript(scriptFileName)
        } else {
            removeServiceScript(scriptFileName)
        }
    }

    suspend fun loopServiceScripts() {
        for (script in serviceScripts.values) {
            if (script.isValidToRun(account)) {

                if (script is RestartIdle) {
                    if (script.shouldRestart) {
                        script.shouldRestart = false
                        script.idleStopWatch.reset()
                        this.stopActionScript()
                        this.startActionScript(this.actionScriptFilename, this.account)

                    }

                }
                if (script.shouldPauseActionScript && !actionScript.currentJobSuspendable && ctx.worldHop.isLoggedIn) {
                    println("We are trying to suspend but current job(${actionScript.currentJob}) is not suspendable. Wait till next execution")
                    continue
                }
                /*
                Only pause the script if it needs to be paused based on the Service script conditions and when
                ActionScript is running
                */
                if (script.shouldPauseActionScript && scriptState == ScriptState.Running) {
                    togglePauseActionScript()
                }


                var runServiceScript = true
                if ((scriptState == ScriptState.Paused || scriptState == ScriptState.Stopped)
                    && !script.runWhenActionScriptIsPausedOrStopped
                ) {
                    runServiceScript = false

                }
                if (runServiceScript && !account.banned) {
                    script.loop(account)
                    if (account.banned) {
                        Logging.error("Account got banned, we are now stopping all scripts")
                        stopActionScript()
                    }
                    if (script.shouldPauseActionScript
                        && (scriptState == ScriptState.Paused
                                || scriptState == ScriptState.LoginScreenNotPaused)
                    ) {
                        togglePauseActionScript()
                    }
                }
            }
        }
    }

    suspend fun stoppingScriptAndLoggingOut() {
        scriptState = ScriptState.Stopped
        actionScriptState(false)
        actionScript.stop()

        ctx.worldHop.logout()
        stopActionScript()
    }

    private fun waitOnContext() {
        while (!isContextLoaded) {
            sleep(20)
        }
    }

    private fun setupContext(client: Any): Context {
        val ctx = ctx
        ctx.ipc::broker.set(GlobalStructs.communication)
        ctx.ipc::uuid.set(instanceUUID)
        return ctx
    }


    fun notifyMessage(flags: Int, name: String, message: String, prefix: String?) {
        val updatedPrefix = prefix ?: ""
        if (this.actionScript is ChatListener) {
            (this.actionScript as ChatListener).notifyMessage(flags, name, message, updatedPrefix)
        }
        this.actionScript.tasks.forEach {
            if (it is ChatListener) {
                (it as ChatListener).notifyMessage(flags, name, message, updatedPrefix)
            }
            if (it is GroupTask) {
                it.children.forEach { child ->
                    if (child is ChatListener) {
                        (child as ChatListener).notifyMessage(flags, name, message, updatedPrefix)
                    }
                }
            }
        }
    }

    fun gameLoop() {
        if (this.actionScript is GameTick) {
            (this.actionScript as GameTick).onTick()
        }
        this.actionScript.tasks.forEach {
            if (it is GameTick) {
                (it as GameTick).onTick()
            }
            if (it is GroupTask) {
                it.children.forEach { child ->
                    if (child is GameTick) {
                        (child as GameTick).onTick()
                    }
                }
            }
        }
    }

    //.doActionCallback(int, int, int, int, java.lang.String, java.lang.String, int, int, int)'
    fun doActionCallback(
        argument0: Int,
        argument1: Int,
        argument2: Int,
        argument3: Int,
        action: String,
        targetName: String,
        mouseX: Int,
        mouseY: Int,
        argument8: Int
    ) {
        logger.info("argument0:$argument0, argument1:$argument1, argument2:$argument2, argument3:$argument3, action:$action, targetName:$targetName, mouseX:$mouseX, mouseY:$mouseY, argument8:$argument8")
        if (GlobalStructs.isMenuOptions) {
            when (argument2) {
                //GameObject
                in 1..6 -> {

                    val localX = argument0 // This can be -1 if not a child
                    val localY = argument1
                    val gameObjectID = argument3
                    val gameObject = ctx.gameObjects.find(gameObjectID).filter { it.menuX == localX && it.menuY ==localY }

                    val menuAction = MenuOpcode.valueOf(argument2)
                    if(gameObject.isNotEmpty()) {
                        logger.info("val gameObject = ctx.gameObjects.find($gameObjectID, Tile(${gameObject[0].getGlobalLocation().x},${gameObject[0].getGlobalLocation().y},ctx=ctx))\n" +
                                "gameObject.doAction()")
                    }

                }
                1001->{

                }
                // NPCs
                in 7..13 -> {

                }

                //Players
                in 14..15 -> {

                }
                //GroundItem
                in 16..22 -> {

                }
                //Widgets
                in 24..30 -> {
                    widgetDebug(argument0, argument1, argument2)
                }
                in 39..43->{
                    widgetDebug(argument0, argument1, argument2)
                }
                57->{
                    widgetDebug(argument0, argument1, argument2)
                }
                //This is inventory
                in 31..38 -> {
                    val itemSlotInInvetory = argument0
                    val rawWidgetID = argument1
                    val containerID = argument1.shr(16)
                    val childID = argument1.and(0xFF)
                    val menuAction = MenuOpcode.valueOf(argument2)
                    val itemId = argument3

                }
                CANCEL.id -> {

                }
            }
        }
    }

    private fun widgetDebug(argument0: Int, argument1: Int, argument2: Int) {
        val childID = argument0 // This can be -1 if not a child
        val widgetID = argument1
        val containerID = argument1.shr(16)
        val containerChildID = argument1.and(0xFF)
        val menuAction = MenuOpcode.valueOf(argument2)

        if (argument2 == MenuOpcode.WIDGET_DEFAULT.id) {

            if (childID == -1) {
                logger.info("WidgetItem(ctx.widgets.find(${containerID}, ${containerChildID}), ctx = ctx).doAction()")
            } else {
                logger.info("WidgetItem(ctx.widgets.find(${containerID}, ${containerChildID})?.getChildren()?.get($childID), ctx = ctx).doAction()")
            }
        }
    }

    fun getModelCallback(argument1: Int) {
//        val arg1 = argument1 * -1917052667
//        println("getModel Callback arg1: $argument1 $arg1")
    }

}

/* for the game loop lock at 100/75 tick a second
 const int TICKS_PER_SECOND = 50;
    const int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    const int MAX_FRAMESKIP = 10;

    // GetTickCount() returns the current number of milliseconds
    // that have elapsed since the system was started
    DWORD next_game_tick = GetTickCount();
    int loops;

    bool game_is_running = true;
    while( game_is_running ) {

        loops = 0;
        while( GetTickCount() > next_game_tick && loops < MAX_FRAMESKIP) {
            update_game();

            next_game_tick += SKIP_TICKS;
            loops++;
        }

        display_game();
    }

    val x = script.javaClass.getAnnotation(ScriptManifest::class.java)
        if(x!=null){
            game.client!!.category = x.category
            game.client!!.name = x.name
            game.client!!.author = x.author
        }


    // For future remote client do not delete
    var canvasWidth = GlobalStructs.width
    var canvasHeight = GlobalStructs.height
    private var image: BufferedImage = BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB)
    var captureScreen = true
    var captureScreenFrame = 1000

    fun setGameImage(buffer: BufferedImage) {
        image = buffer
    }

    fun takeScreenShot(): BufferedImage {
        return image
    }
 */