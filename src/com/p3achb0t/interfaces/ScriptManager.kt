package com.p3achb0t.interfaces

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.client.ui.components.GameTab
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class ScriptManager(val client: Any) : ScriptHook {
    val dd = client as IScriptManager
    val o = client
    var shouldRun = false
    var script: Script = NullScript()
    val debug: Script = PrintScript(client as Client, dd)
    var gb : AbstractScript = com.p3achb0t.scripts.NullScript()

    var x = 800
    var y = 600



    var thread = Thread { println("${Thread.currentThread()} has run.") }

    override fun getScriptHook(): Script {
        return script
    }

    override fun setScriptHook(s: Script) {

        script = s
    }

    fun setScriptHookAbs(s: AbstractScript) {
        s.initialize(client)
        gb = s

    }

    suspend fun start() {
        gb.start()
        shouldRun = true
        //thread = createThread()
        //thread.start()
    }

    fun suspend() {
        thread.suspend()
    }

    fun resume() {
        thread.resume()
    }

    fun stop() {
        thread.stop()

    }

    private fun createThread() : Thread {
        return Thread {
            println("${Thread.currentThread()} has run.")
            while (true) {
                script.loop()
                Thread.sleep(50)
            }
        }
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