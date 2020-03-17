package com.p3achb0t.client.injection

import com.p3achb0t.api.*
import com.p3achb0t.api.listeners.ChatListener
import com.p3achb0t.api.utils.Time
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.scripts.NullScript
import kotlinx.coroutines.*
import java.applet.Applet
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.lang.Thread.sleep
import java.util.concurrent.ConcurrentHashMap

class InstanceManager(val client: Any) {

    lateinit var ctx: Context
    var isContextLoaded: Boolean = false

    // Scripts vars
    var script: AbstractScript = NullScript()
    var isScriptRunning: Boolean = false

    val debugScripts = ConcurrentHashMap<String, DebugScript>()
    val backgroundScripts =ConcurrentHashMap<String, BackgroundScript>() // TODO Higher precedence

    //control fps
    var fps = 50

    // script execution


    // For future remote client TODO need renaming
    var canvasWidth = GlobalStructs.width
    var canvasHeight = GlobalStructs.height
    private var image: BufferedImage = BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB)
    var captureScreen = true
    var captureScreenFrame = 1000


    // Dont delete this. Its used within the injected functions
    var blockFocus = false

    private var abstractScriptLoop: Job? = null
    private var backgroundLoop: Job? = null



    private var isRunning = false
    private var paused = false
    lateinit var thread: Job
    lateinit var statsThread: Job
    lateinit var dbUpdaterThread: Job
    val runtime = StopWatch()
    val lastCheck = StopWatch()
    val fiveMin = Time.getMinInMils(5)
    var gameLoopI = 0


    init {
        // TODO fail after 1 sec need to be thread
        GlobalScope.launch {
            while ((client as Applet).componentCount == 0 ) {
                delay(20)
            }
            ctx = Context(client)
            script::ctx.set(ctx)
            isContextLoaded = true
        }

    }

    fun runBackgroundScripts() {
        backgroundLoop = GlobalScope.launch {
            while (true) {
                loopBackgroundScript()
                delay(200)
                //delay(1000/fps.toLong())
            }
        }
    }

    fun addAbstractScript(scriptFileName: String) {
        val abstractScript = GlobalStructs.scripts.scripts[scriptFileName]!!.load() as AbstractScript
        waitOnContext()
        abstractScript::ctx.set(ctx)
        script = abstractScript
    }

    fun addAbstractScript(abstractScript: AbstractScript) {
        waitOnContext()
        abstractScript::ctx.set(ctx)
        ctx.ipc::communication.set(GlobalStructs.communication)
        script = abstractScript
    }

    fun removeAbstractScript(scriptFileName: String) {

    }

    fun startScript() {
        isScriptRunning = true
        abstractScriptLoop = GlobalScope.launch {
            while (true) {
                script.loop()
                delay(1000/fps.toLong())
            }
        }
        /*
        if (!backgroundLoop?.isActive!!)
            runBackgroundScripts()*/
    }

    fun stopScript() {
        isScriptRunning = false

            abstractScriptLoop?.cancel()

            backgroundLoop?.cancel()
    }

    // Rs Canvas debug scripts TODO race conditions for removing
    fun paintDebugScripts(g: Graphics) {
        for (script in debugScripts.values) {
            script.draw(g)
        }
    }

    fun toggleDebugScript(scriptFileName: String) {
        if (debugScripts[scriptFileName] == null) {
            addDebugScript(scriptFileName)
        } else {
            removeDebugScript(scriptFileName)
        }
    }

    fun addDebugScript(scriptFileName: String) {
        val debugScript = GlobalStructs.scripts.scripts[scriptFileName]!!.load() as DebugScript
        waitOnContext()
        debugScript::ctx.set(ctx)
        debugScripts[scriptFileName] = debugScript
    }

    fun removeDebugScript(scriptFileName: String) {
        debugScripts.remove(scriptFileName)
    }

    // Background script
    suspend fun loopBackgroundScript() {
        for (script in backgroundScripts.values) {
            script.loop()
        }
    }

    fun addBackgroundScript(scriptFileName: String) {
        val backgroundScript = GlobalStructs.scripts.scripts[scriptFileName]!!.load() as BackgroundScript
        waitOnContext()
        backgroundScript::ctx.set(ctx)
        backgroundScripts[scriptFileName] = backgroundScript
    }

    fun addBackgroundScript(backgroundScript: BackgroundScript) {
        waitOnContext()
        backgroundScript::ctx.set(ctx)
        ctx.ipc::communication.set(GlobalStructs.communication)
        backgroundScripts["scriptFileName"] = backgroundScript
    }

    fun removeBackgroundScript(scriptFileName: String) {
        backgroundScripts.remove(scriptFileName)
    }

    private fun waitOnContext() {
        while (!isContextLoaded) {
            sleep(20)
        }
    }


    fun notifyMessage(flags: Int, name: String, message: String, prefix: String?) {
        if (this.script is ChatListener) {
            val updatedPrefix = prefix ?: ""
            (this.script as ChatListener).notifyMessage(flags, name, message, updatedPrefix)
        }
    }

    fun doActionCallback(argument0: Int, argument1: Int, argument2: Int, argument3: Int, action: String, targetName: String, mouseX: Int, mouseY: Int, argument8: Int) {
        println("argument0:$argument0, argument1:$argument1, argument2:$argument2, argument3:$argument3, action:$action, targetName:$targetName, mouseX:$mouseX, mouseY:$mouseY, argument8:$argument8")
    }

    fun getModelCallback(argument1: Int) {
//        val arg1 = argument1 * -1917052667
//        println("getModel Callback arg1: $argument1 $arg1")
    }


    // TODO move to background task
    private suspend fun trackStats(){
        /*
        Stats.Skill.values().iterator().forEach {
            prevXP
        }
        //Track stats
        while(isRunning) {
            if (ctx.worldHop.isLoggedIn) {
                ctx.stats.updateStats()
                ctx.inventory.updateTrackedItems()
            }
            delay(300)
        }*/
    }


    fun setGameImage(buffer: BufferedImage) {
        image = buffer
    }

    fun takeScreenShot(): BufferedImage {
        return image
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

 */