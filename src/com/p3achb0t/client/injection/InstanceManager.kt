package com.p3achb0t.client.injection

import com.p3achb0t.api.ActionScript
import com.p3achb0t.api.Context
import com.p3achb0t.api.PaintScript
import com.p3achb0t.api.ServiceScript
import com.p3achb0t.api.listeners.ChatListener
import com.p3achb0t.client.accounts.LoginHandler
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.scripts.NullScript
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.applet.Applet
import java.awt.Graphics
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InstanceManager(val client: Any) {

    // Client candy
    var ctx: Context? = null
    var isContextLoaded = false
    lateinit var instanceUUID: String

    // Action script vars
    var actionScript: ActionScript = NullScript()
    private var actionScriptLoop: Job? = null
    var isActionScriptPaused = false

    // Service script
    private var serviceLoop: Job? = null
    val serviceScripts = ConcurrentHashMap<String, ServiceScript>()

    // Paint Script
    val paintScripts = ConcurrentHashMap<String, PaintScript>()

    //Util
    var fps = 50
    var canvasWidth = GlobalStructs.width
    var canvasHeight = GlobalStructs.height

    // remove section
    var loginHandler = LoginHandler()

    // Don't delete this. Its used within the injected functions
    var blockFocus = false

    init {
        // TODO fail after 1 sec need to be thread
        GlobalScope.launch {
            while ((client as Applet).componentCount == 0 ) { delay(20) }
            ctx = setupContext(client)
            actionScript::ctx.set(ctx!!)
            isContextLoaded = true
        }
    }

    fun startActionScript(scriptFileName: String) {

        if (actionScriptLoop != null)
            stopActionScript()


        val script = GlobalStructs.scripts.scriptsInformation[scriptFileName] ?: return
        val actionScriptLoaded = script.load() as ActionScript
        waitOnContext()
        actionScriptLoaded::ctx.set(setupContext(client))
        actionScript = actionScriptLoaded
        actionScript.start()

        isActionScriptPaused = false
        actionScriptState(true)
    }

    fun stopActionScript() {

        isActionScriptPaused = true
        actionScriptState(false)
        actionScript.stop()
        GlobalStructs.communication.unsubscribeAllChannels(actionScript.ctx.ipc.channels.keys, actionScript.ctx.ipc.scriptUUID)

        val nullScript = NullScript()
        waitOnContext()
        nullScript::ctx.set(setupContext(client))
        actionScript = nullScript
    }


    fun pauseActionScript() {

        if (!isActionScriptPaused) {
            actionScript.pause()
            actionScriptState(false)
        } else {
            actionScript.resume()
            actionScriptState(true)
        }
        isActionScriptPaused = !isActionScriptPaused
    }

    private fun actionScriptState(loopRunning: Boolean) {
        if (loopRunning) {
            if (actionScriptLoop == null) {
                actionScriptLoop = GlobalScope.launch {
                    while (true) {
                        actionScript.loop()
                        delay(10)
                        //delay(1000/fps.toLong())
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
        val script = GlobalStructs.scripts.scriptsInformation[scriptFileName] ?: return
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
        GlobalStructs.communication.unsubscribeAllChannels(paintScript.ctx.ipc.channels.keys, paintScript.ctx.ipc.scriptUUID)
    }

    fun drawPaintScripts(g: Graphics) {
        for (script in paintScripts.values) {
            script.draw(g)
        }
    }

    // Service script
    fun addServiceScript(scriptFileName: String) {
        val script = GlobalStructs.scripts.scriptsInformation[scriptFileName] ?: return
        val serviceScript = script.load() as ServiceScript
        waitOnContext()
        serviceScript::ctx.set(setupContext(client))
        serviceScript.start()
        serviceScripts[scriptFileName] = serviceScript

        if (serviceLoop == null) {
            serviceLoop = GlobalScope.launch {
                while (true) {
                    loopServiceScripts()
                    delay(200)
                    //delay(1000/fps.toLong())
                }
            }
        }
    }


    fun removeServiceScript(scriptFileName: String) {
        val serviceScript = serviceScripts[scriptFileName] ?: return
        serviceScripts.remove(scriptFileName)
        serviceScript.stop()
        GlobalStructs.communication.unsubscribeAllChannels(serviceScript.ctx.ipc.channels.keys, serviceScript.ctx.ipc.scriptUUID)

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
            script.loop()
        }
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
        if (this.actionScript is ChatListener) {
            val updatedPrefix = prefix ?: ""
            (this.actionScript as ChatListener).notifyMessage(flags, name, message, updatedPrefix)
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