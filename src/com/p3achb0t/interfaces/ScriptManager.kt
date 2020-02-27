package com.p3achb0t.interfaces

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.DebugScript
import com.p3achb0t.api.StopWatch
import com.p3achb0t.api.listeners.ChatListener
import com.p3achb0t.client.managers.loginhandler.LoginHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import kotlin.random.Random

class ScriptManager(val client: Any) {

    private val mouse = (client as IScriptManager).getMouse()
    private val keyboard = (client as IScriptManager).getKeyboard()
    var script: AbstractScript = com.p3achb0t.scripts.NullScript()
    var blockFocus = false
    val debugScripts = mutableListOf<DebugScript>()

    var loginHandler = LoginHandler(client = client as Client)

    var x = 800
    var y = 600
    private var image: BufferedImage = BufferedImage(x, y, BufferedImage.TYPE_INT_RGB)
    var captureScreen = false
    var captureScreenFrame = 1000
    private var isRunning = false
    private var paused = false
    lateinit var thread: Job
    var gameLoopI = 0

    var breaking = false
    var breakReturnTime = 0L

    fun setUpScript(s: AbstractScript) {
        s.initialize(client)
        loginHandler.ctx = s.ctx
        this.script = s
    }

    suspend fun loop() {
        if (isRunning) {
            try {
                script.loop()
            } catch (e: Error) {
                println(e.localizedMessage)
                for (el in e.stackTrace) {
                    println(el.toString())
                }
            }
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


    fun start() {
//        mouse.inputBlocked(true)
        isRunning = true
        //This the script thread.
        thread = GlobalScope.launch {
            val runtime = StopWatch()
            val lastCheck = StopWatch()
            val fiveMin = 5 * 60 *1000
            script.start()
            while (isRunning) {
                // Check to see if we have a good loaded account
                // Every 5 min check to see if we need to logout
                if(loginHandler.account.userBreaks && lastCheck.elapsed > fiveMin){
                    //Are we in the runtime range
                    if(runtime.elapsedSec < Random.nextInt(loginHandler.account.minRuntime,loginHandler.account.maxRuntime)){
                        val breakTime = Random.nextInt(loginHandler.account.minBreakTime,loginHandler.account.maxBreakTime)
                        println("Hit our break handler. Logging out")
                        delay(5000) // Some times its good to add a little delay
                        loginHandler.ctx?.worldHop?.logout()
                        println("Delaying ${1000*breakTime.toLong()}ms")
                        breakReturnTime = System.currentTimeMillis() + 1000*breakTime.toLong()
                        breaking = true
                        delay(1000*breakTime.toLong())
                        runtime.reset()
                        breaking = false

                    }
                    lastCheck.reset()
                }
                if (!paused
                        && loginHandler.account.username.isNotEmpty()
                        && loginHandler.isAtHomeScreen()) {
                    loginHandler.login()
                }
                while (paused) {
                    delay(100)
                }
                loop()
            }
        }
    }

    fun pause() {
        isRunning = true
        paused = true

    }

    fun resume() {
        isRunning = true
        paused = false
    }

    fun stop() {
        isRunning = false
        script.stop()
        thread.cancel()
        GlobalScope.launch {
            thread.join()
        }
    }

    fun setGameImage(buffer: BufferedImage) {
        image = buffer
    }

    fun takeScreenShot(): BufferedImage {
        return image
    }

    fun paintScript(g: Graphics) {
        if(breaking) {
            g.color = Color.RED
            val timeLeftInSec = (breakReturnTime - System.currentTimeMillis()) / 1000
            g.drawString("Breaking. Will return in $timeLeftInSec", 280, 225)
        }
        script.draw(g)
    }

    // Rs Canvas debug
    fun paintDebug(g: Graphics) {
        for (i in debugScripts) {
            i.draw(g)
        }
    }

    fun addDebugPaint(debugScript: DebugScript) {
        debugScript.initialize(client)
        debugScripts.add(debugScript)
    }

    fun removeDebugPaint(debugScript: DebugScript) {
        debugScripts.remove(debugScript)
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
