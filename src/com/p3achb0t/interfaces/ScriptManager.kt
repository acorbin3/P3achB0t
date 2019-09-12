package com.p3achb0t.interfaces

class ScriptManager : ScriptHook {

    var script: Script = NullScript()
    var thread = Thread { println("${Thread.currentThread()} has run.") }

    override fun getScriptHook(): Script {
        return script
    }

    override fun setScriptHook(s: Script) {
        script = s
    }

    fun start() {
        thread = createThread()
        thread.start()
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
 */