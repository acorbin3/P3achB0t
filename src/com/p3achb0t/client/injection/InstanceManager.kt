package com.p3achb0t.client.injection

import com.p3achb0t.Main
import com.p3achb0t.api.*
import com.p3achb0t.api.listeners.ChatListener
import com.p3achb0t.api.userinputs.DoActionParams
import com.p3achb0t.api.utils.Time
import com.p3achb0t.api.wrappers.ClientMode
import com.p3achb0t.api.wrappers.Stats
import com.p3achb0t.client.accounts.Account
import com.p3achb0t.client.accounts.LoginHandler
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.scripts.NullScript
import com.p3achb0t.scripts_debug.paint_debug.PaintDebug
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.applet.Applet
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random
import kotlin.reflect.full.findAnnotation

class InstanceManager(val client: Any) {

    companion object {
        var mule = false
    }
    var ctx: Context? = null

    var scriptName: String = ""
    var isContextLoaded: Boolean = false
    lateinit var instanceUUID: String

    // Scripts vars
    var script: AbstractScript = NullScript()
    var isScriptRunning: Boolean = false
    private var paused = false

    val debugScripts = ConcurrentHashMap<String, DebugScript>()
    val backgroundScripts = ConcurrentHashMap<String, BackgroundScript>() // TODO Higher precedence
    var loginHandler = LoginHandler()
    var sessionID = UUID.randomUUID().toString()

    lateinit var statsThread: Job
    lateinit var dbUpdaterThread: Job
    val runtime = StopWatch()
    val lastCheck = StopWatch()
    val fiveMin = Time.getMinInMils(5)
    var breaking = false
    var breakReturnTime = 0L

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

    init {
        // TODO fail after 1 sec need to be thread
        GlobalScope.launch {
            while ((client as Applet).componentCount == 0 ) {
                delay(20)
            }
            // setup context
            ctx = setupContext(client)
            script::ctx.set(ctx!!)

            isContextLoaded = true
        }

    }

    fun setLoginHandlerAccount(account: Account){
        loginHandler.account = account
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

/*
    fun setAbstractScript(name:String){
        if(name in GlobalStructs.scripts.scripts) {
            waitOnContext()
            GlobalStructs.scripts.scripts[name]!!.abstractScript?.ctx = setupContext(client)
            script = GlobalStructs.scripts.scripts[name]!!.abstractScript!!
        }else{
            println("ERROR: Count not find script $name")
        }
    }


    fun addAbstractScript(name:String, abstractScript: AbstractScript){
        GlobalStructs.scripts.scripts[name]?.abstractScript = abstractScript
        waitOnContext()
        abstractScript::ctx.set(setupContext(client))
        script = abstractScript
    }
*/


    fun addAbstractScript(scriptFileName: String) {
        val abstractScript = GlobalStructs.scripts.scripts[scriptFileName]!!.load() as AbstractScript
        waitOnContext()
        abstractScript::ctx.set(setupContext(client))
        script = abstractScript
    }

    fun addAbstractScript(abstractScript: AbstractScript) {
        scriptName = abstractScript::class.java.name.split(".").last()
        println("Setting up script: $scriptName")
        waitOnContext()
        abstractScript::ctx.set(setupContext(client))
        script = abstractScript
    }

    fun removeAbstractScript(scriptFileName: String) {

    }

    private suspend fun trackStats(){
        Stats.Skill.values().iterator().forEach {
            prevXP
        }
        //Track stats
        while(isScriptRunning) {
            if (ctx?.worldHop?.isLoggedIn == true) {
                ctx?.stats?.updateStats()
                ctx?.inventory?.updateTrackedItems()
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
        while(isScriptRunning){
            if(ctx?.worldHop?.isLoggedIn == true) {

                //Do stats tracking
                //Initialize previous db
                if (ctx?.stats?.curXP?.get(Stats.Skill.ATTACK) == 0) {
                    Stats.Skill.values().iterator().forEach {
                        prevXP[it] = ctx?.stats?.curXP?.get(it)
                    }

                } else {
                    Stats.Skill.values().iterator().forEach { skill ->
                        val diff = ctx?.stats?.curXP?.get(skill)?.minus(prevXP[skill]!!)
                        val wasPrevZero = prevXP[skill] == 0
                        prevXP[skill] = ctx?.stats?.curXP?.get(skill)
                        //Skip the initial load
                        if(!wasPrevZero) {
                            if (diff != null && diff > 0) {
                                println("Updating ${skill.name} wiht diff: $diff")
                                GlobalStructs.db.updateStat(loginHandler.account.username, sessionID, skill, diff)
                            }
                        }
                    }
                }

                //Do inventory Tracking
                ctx?.inventory?.totalTrackedItemCount?.forEach { itemID, count ->
                    var diff: Int = 0
                    if(itemID in prevTotalTrackedItemCount){
                        diff = count.minus(prevTotalTrackedItemCount[itemID]!!)
                        prevTotalTrackedItemCount[itemID] = count
                    }else{
                        diff = count
                        prevTotalTrackedItemCount[itemID] = count
                    }
                    if(diff > 0) {
                        GlobalStructs.db.updateItemCount(
                                loginHandler.account.username,
                                sessionID,
                                ctx?.cache?.getItemName(itemID)?: "none",
                                diff
                        )
                    }
                }
            }

            //Only delay long if we have initialized
            if(ctx?.worldHop?.isLoggedIn == true && prevXP[Stats.Skill.ATTACK]!! > 0) {
                delay(Time.getMinInMils(30))
            }else{
                delay(1000)
            }
        }
    }

    fun startScript() {
        if(script.validate){
            Main.validationKey
            PaintDebug.key
            val annotations = script::class.findAnnotation<ScriptManifest>()
            println("name: ${annotations?.name} author: ${annotations?.author} ")
            if(GlobalStructs.db.validateScript(annotations?.name ?:"",  PaintDebug.key)){
                println("Validation success for script:${annotations?.name} key: ${ PaintDebug.key}")
            }else{
                println("Failed to provide a validation key. Be sure to pass in they key from the" +
                        " commandline. Example 'java -jar <jarname>.jar -key <entered_key>'")
                return
            }


        }

        if(loginHandler.account.username.isNotEmpty()){
            fps = loginHandler.account.fps
        }

        if(loginHandler.account.username.isEmpty()){
            loginHandler.account.username = UUID.randomUUID().toString().substring(0,10)
        }
        if(loginHandler.account.script.isEmpty()){
            loginHandler.account.script = scriptName
        }



        sessionID = UUID.randomUUID().toString()
        GlobalStructs.db.initalScriptLoad(loginHandler.account.username, sessionID, loginHandler.account.script)

        isScriptRunning = true

        statsThread = GlobalScope.launch {
            trackStats()
        }
        dbUpdaterThread = GlobalScope.launch {
            dbUpdater()
        }

        abstractScriptLoop = GlobalScope.launch {
            while (true) {
                ctx?.stats?.runtime?.reset()
                runtime.reset()
                lastCheck.reset()

                script.start()

                while (isScriptRunning) {
                    // Check to see if we have a good loaded account
                    // Every 5 min check to see if we need to logout
                    val timeTillBreak = (fiveMin - lastCheck.elapsed) / 1000
                    if (loginHandler.account.userBreaks && timeTillBreak < 0) {
                        //Are we in the runtime range
                        if (runtime.elapsedSec > Random.nextInt(loginHandler.account.minRuntimeSec, loginHandler.account.maxRuntimeSec)) {
                            val breakTime = Random.nextInt(loginHandler.account.minBreakTimeSec, loginHandler.account.maxBreakTimeSec)
                            println("Hit our break handler. Logging out")
                            delay(5000) // Some times its good to add a little delay
                            ctx?.worldHop?.logout()
                            println("Delaying ${1000 * breakTime.toLong()}ms")
                            breakReturnTime = System.currentTimeMillis() + 1000 * breakTime.toLong()
                            breaking = true
                            delay(1000 * breakTime.toLong())
                            runtime.reset()
                            breaking = false

                        }
                        lastCheck.reset()
                    }
                    if (!paused && !mule
                            && loginHandler.account.username.isNotEmpty()
                            && ctx != null
                            && loginHandler.isAtHomeScreen(ctx!!)) {
                        println("Account: " + loginHandler.account)
                        loginHandler.login(ctx!!)
                    }

                    if (ctx != null
                            && loginHandler.isLoggedIn(ctx!!)) {
                        if (ctx!!.clientMode.getMode() == ClientMode.Companion.ModeType.FixedMode) {
                            //Open options
                            //argument0:-1, argument1:10747944, argument2:57, argument3:1, action:Options, targetName:, mouseX:712, mouseY:585, argument8:-1223904486
                            ctx!!.mouse.doAction(DoActionParams(-1, 35913765, 57, 1, "Options", "", 0, 0))
                            delay(1000)
                            //set re-size mode
                            //argument0:-1, argument1:17104930, argument2:57, argument3:1, action:Resizable mode, targetName:, mouseX:688, mouseY:362, argument8:-1223904486
                            ctx!!.mouse.doAction(DoActionParams(-1, 17104930, 57, 1, "World Switcher", "", 0, 0))
                            delay(1000)
                        }
                    }
                    while (paused) {
                        delay(100)
                    }
                    script.loop()
                    delay(1000 / fps.toLong())
                }
            }
        }
        // TODO needs change
        if (backgroundLoop != null && backgroundLoop?.isActive!!)
            runBackgroundScripts()
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
                val timeLeft = ctx?.stats?.getRuntimeString((estTimeToBreak*1000).toLong())
                g.drawString("Estimated Time till break: ~$timeLeft", 100, 100)
//                g.drawString("${runtime.elapsedSec}  ${loginHandler.account.maxRuntimeSec}", 100,110)
            }
        }

        script.draw(g)
    }

    fun isPaused() : Boolean{
        return paused
    }

    fun pauseScript(){
        paused = !paused
    }

    fun stopScript() {
        isScriptRunning = false
        paused = false
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
        debugScript::ctx.set(setupContext(client))
        debugScripts[scriptFileName] = debugScript
    }

    fun addDebugScript(scriptName: String, debugScript: DebugScript) {
        waitOnContext()
        debugScript::ctx.set(setupContext(client))
        debugScripts[scriptName] = debugScript
        GlobalScope.launch { debugScripts[scriptName]?.start() }

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
        backgroundScript::ctx.set(setupContext(client))
        backgroundScripts[scriptFileName] = backgroundScript
    }

    fun addBackgroundScript(backgroundScript: BackgroundScript) {
        waitOnContext()
        backgroundScript::ctx.set(setupContext(client))
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

    private fun setupContext(client: Any) : Context {
        val ctx = Context(client)
        ctx.ipc::broker.set(GlobalStructs.communication)
        ctx.ipc::uuid.set(instanceUUID)
        return ctx
    }


    fun notifyMessage(flags: Int, name: String, message: String, prefix: String?) {
        if (this.script is ChatListener) {
            val updatedPrefix = prefix ?: ""
            (this.script as ChatListener).notifyMessage(flags, name, message, updatedPrefix)
        }
    }

    fun doActionCallback(argument0: Int, argument1: Int, argument2: Int, argument3: Int, action: String, targetName: String, mouseX: Int, mouseY: Int, argument8: Int) {
        val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(Date())
        println("$timeStamp - Info - argument0:$argument0, argument1:$argument1, argument2:$argument2, argument3:$argument3, action:$action, targetName:$targetName, mouseX:$mouseX, mouseY:$mouseY, argument8:$argument8")
    }

    fun getModelCallback(argument1: Int) {
//        val arg1 = argument1 * -1917052667
//        println("getModel Callback arg1: $argument1 $arg1")
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