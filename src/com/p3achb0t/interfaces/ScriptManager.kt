package com.p3achb0t.interfaces

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.api.DebugScript
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.awt.Graphics
import java.awt.image.BufferedImage

class ScriptManager(val client: Any) {

    private val mouse = (client as IScriptManager).getMouse()
    private val keyboard = (client as IScriptManager).getKeyboard()
    private var script: AbstractScript = com.p3achb0t.scripts.NullScript()
    private val debugScripts = mutableListOf<DebugScript>()

    var x = 800
    var y = 600
    private var image: BufferedImage = BufferedImage(x,y,BufferedImage.TYPE_INT_RGB)
    var captureScreen = false
    var captureScreenFrame = 1000
    private var isRunning = false

    fun setScript(s: AbstractScript) {
        s.initialize(client)
        this.script = s
    }

    fun getScript(): AbstractScript {
        return script
    }

    fun loop() {
        if (isRunning) {
            script.loop()
        }
    }


    fun start() {
        //mouse.inputBlocked(true)
        isRunning = true
        script.start()

    }

    fun pause() {
        isRunning = false
    }

    fun resume() {
        isRunning = true
    }

    fun stop() {
        isRunning = false
        script.stop()

    }

    fun setGameImage(buffer: BufferedImage) {
        image = buffer
    }

    fun takeScreenShot() : BufferedImage {
        return image
    }

    fun paintScript(g: Graphics) {
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