package com.p3achb0t.interfaces

import com.p3achb0t.Main
import com.p3achb0t.api.*
import com.p3achb0t.api.listeners.ChatListener
import com.p3achb0t.api.user_inputs.DoActionParams
import com.p3achb0t.api.utils.Time
import com.p3achb0t.api.wrappers.ClientMode
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.client.managers.Manager
import com.p3achb0t.client.managers.accounts.Account
import com.p3achb0t.client.managers.loginhandler.LoginHandler
import com.p3achb0t.scripts.tutorial_island.TutorialIsland
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.*
import kotlin.random.Random
import kotlin.reflect.full.findAnnotation

class ScriptManager(val client: Any) {

    private val mouse = (client as IScriptManager).getMouse()
    private val keyboard = (client as IScriptManager).getKeyboard()
    var script: AbstractScript = com.p3achb0t.scripts.NullScript()
    var blockFocus = false // Dont delete this. Its used within the injected functions
    val debugScripts = mutableListOf<DebugScript>()

    var loginHandler = LoginHandler()
    var sessionID = UUID.randomUUID().toString()

    var x = 800
    var y = 600
    private var image: BufferedImage = BufferedImage(x, y, BufferedImage.TYPE_INT_RGB)
    var captureScreen = false
    var captureScreenFrame = 1000
    private var isRunning = false
    private var paused = false
    lateinit var thread: Job
    lateinit var statsThread: Job
    lateinit var dbUpdaterThread: Job
    val runtime = StopWatch()
    val lastCheck = StopWatch()
    val fiveMin = Time.getMinInMils(5)
    var gameLoopI = 0
    lateinit var ctx: Context


    var breaking = false
    var breakReturnTime = 0L

    fun setUpScript(s: AbstractScript) {
        s.initialize(this.ctx)
        this.script = s
    }

    fun setLoginHandlerAccount(account: Account){
        loginHandler.account = account
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


    private suspend fun trackStats(){
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
        }
    }

    val prevXP = EnumMap<Stats.Skill,Int>(Stats.Skill::class.java)
    val prevTotalTrackedItemCount = HashMap<Int, Int>() // Key is an item ID, value is the item picked up count
    suspend fun dbUpdater(){
        Stats.Skill.values().iterator().forEach {
            prevXP[it] = 0
        }
        while(isRunning){
            if(ctx.worldHop.isLoggedIn) {

                //Do stats tracking
                //Initialize previous db
                if (ctx.stats.curXP.get(Stats.Skill.ATTACK) == 0) {
                    Stats.Skill.values().iterator().forEach {
                        prevXP[it] = ctx.stats.curXP[it]
                    }

                } else {
                    Stats.Skill.values().iterator().forEach { skill ->
                        val diff = ctx.stats.curXP[skill]?.minus(prevXP[skill]!!)
                        val wasPrevZero = prevXP[skill] == 0
                        prevXP[skill] = ctx.stats.curXP[skill]
                        //Skip the initial load
                        if(!wasPrevZero) {
                            if (diff != null && diff > 0) {
                                println("Updating ${skill.name} wiht diff: $diff")
                                Manager.db.updateStat(loginHandler.account.username, sessionID, skill, diff)
                            }
                        }
                    }
                }

                //Do inventory Tracking
                ctx.inventory.totalTrackedItemCount.forEach { itemID, count ->
                    var diff: Int = 0
                    if(itemID in prevTotalTrackedItemCount){
                        diff = count.minus(prevTotalTrackedItemCount[itemID]!!)
                        prevTotalTrackedItemCount[itemID] = count
                    }else{
                        diff = count
                        prevTotalTrackedItemCount[itemID] = count
                    }
                    if(diff > 0) {
                        Manager.db.updateItemCount(
                                loginHandler.account.username,
                                sessionID,
                                ctx.cache.getItemName(itemID),
                                diff
                        )
                    }
                }
            }

            //Only delay long if we have initialized
            if(ctx.worldHop.isLoggedIn && prevXP[Stats.Skill.ATTACK]!! > 0) {
                delay(Time.getMinInMils(15))
            }else{
                delay(1000)
            }
        }
    }

    fun start() {
        if(script.validate){
            Main.validationKey
            val annotations = TutorialIsland::class.findAnnotation<ScriptManifest>()
            println("name: ${annotations?.name} author: ${annotations?.author} ")
            if(Manager.db.validateScript(annotations?.name ?:"", Main.validationKey)){
                println("Validation success for script:${annotations?.name} key: ${Main.validationKey}")
            }else{
                println("Failed to provide a validation key. Be sure to pass in they key from the" +
                        " commandline. Example 'java -jar <jarname>.jar -key <entered_key>'")
                return
            }


        }
        if(loginHandler.account.username.isEmpty()){
            loginHandler.account.username = UUID.randomUUID().toString().substring(0,10)
        }

        sessionID = UUID.randomUUID().toString()
//        mouse.inputBlocked(true)
        Manager.db.initalScriptLoad(loginHandler.account.username, sessionID, loginHandler.account.script)
        isRunning = true
        //This the script thread.

        statsThread = GlobalScope.launch {
            trackStats()
        }
        dbUpdaterThread = GlobalScope.launch {
            dbUpdater()
        }
        thread = GlobalScope.launch {
            ctx.stats.runtime.reset()
            runtime.reset()
            lastCheck.reset()

            script.start()
            while (isRunning) {
                // Check to see if we have a good loaded account
                // Every 5 min check to see if we need to logout
                val timeTillBreak = (fiveMin - lastCheck.elapsed) / 1000
                if(loginHandler.account.userBreaks && timeTillBreak< 0){
                    //Are we in the runtime range
                    if(runtime.elapsedSec > Random.nextInt(loginHandler.account.minRuntimeSec,loginHandler.account.maxRuntimeSec)){
                        val breakTime = Random.nextInt(loginHandler.account.minBreakTimeSec,loginHandler.account.maxBreakTimeSec)
                        println("Hit our break handler. Logging out")
                        delay(5000) // Some times its good to add a little delay
                        ctx.worldHop.logout()
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
                        && loginHandler.isAtHomeScreen(ctx)) {
                    println("Account: " + loginHandler.account)
                    loginHandler.login(ctx)
                }

                if(loginHandler.isLoggedIn(ctx)){
                    if(ctx.clientMode.getMode() == ClientMode.Companion.ModeType.FixedMode){
                        //Open options
                        //argument0:-1, argument1:10747944, argument2:57, argument3:1, action:Options, targetName:, mouseX:712, mouseY:585, argument8:-1223904486
                        ctx.mouse.doAction(DoActionParams(-1, 35913765, 57, 1, "Options", "", 0, 0))
                        delay(1000)
                        //set re-size mode
                        //argument0:-1, argument1:17104930, argument2:57, argument3:1, action:Resizable mode, targetName:, mouseX:688, mouseY:362, argument8:-1223904486
                        ctx.mouse.doAction(DoActionParams(-1, 17104930, 57, 1, "World Switcher", "", 0, 0))
                        delay(1000)
                    }
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
        statsThread.cancel()
        GlobalScope.launch {
            thread.join()
            statsThread.join()
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
        }else{
            if(loginHandler.account.userBreaks) {
                val timeTillBreak = loginHandler.account.maxRuntimeSec - runtime.elapsedSec
                val nextCheck = ((fiveMin - lastCheck.elapsed) / 1000).toInt()
                val estTimeToBreak = if(timeTillBreak>nextCheck) timeTillBreak else nextCheck
                g.drawString("Estimated Time till break: ~$estTimeToBreak", 100, 100)
//                g.drawString("${runtime.elapsedSec}  ${loginHandler.account.maxRuntimeSec}", 100,110)
            }
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
        debugScript.initialize(ctx)
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