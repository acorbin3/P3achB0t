package com.p3achb0t.scripts.service.login

import com.p3achb0t.api.StopWatch
import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.script.ServiceScript
import com.p3achb0t.api.utils.Script
import com.p3achb0t.api.utils.Time
import com.p3achb0t.api.utils.Time.sleep
import com.p3achb0t.client.accounts.Account
import com.p3achb0t.client.accounts.LoginHandler
import com.p3achb0t.scripts.paint.debug.DebugText
import kotlinx.coroutines.delay
import java.awt.Color
import java.awt.Graphics
import kotlin.math.abs
import kotlin.random.Random

@ScriptManifest(Script.SERVICE, "Login Handler", "P3aches", "0.1")
class LoginAndBreakHandlerHandler : ServiceScript(shouldPauseActionScript = true) {
    var loginHandler = LoginHandler()
    var status = ""
    var startTime = 0

    var runTimeStopWatch = StopWatch()
    var breakDurationMils = 0L
    val breakTimeStopWatch = StopWatch()
    var sessionTimeInSec = 0

    override suspend fun isValidToRun(account: Account): Boolean {
        return account.startActionScriptAutomatically
                && (loginHandler.isAtHomeScreen(ctx)
                    // Strange case where at the Click red button to start playing
                    || ctx.widgets.isWidgetAvaliable(378, 87))
                || isBreakTime()
                || !isInValidWindowTime()
    }


    fun isBreakTime(): Boolean{
        return runTimeStopWatch.elapsedSec >= sessionTimeInSec
    }

    fun isInValidWindowTime(): Boolean{
        return false
    }

    override suspend fun loop(account: Account) {


        // Initialize values
        if (sessionTimeInSec == 0 && (account.maximumSessionTime * 60) > 0) {
            sessionTimeInSec = Random.nextInt(account.minimumSessionTime, account.maximumSessionTime) * 60 // Converting to time in seconds
        }

        if (account.useBreaks
                && isBreakTime()) {
            breakDurationMils = Random.nextLong((account.minimumBreakTime * 60 * 1000).toLong(), (account.maximumBreakTime * 60 * 1000).toLong())
            breakTimeStopWatch.reset()
            ctx.worldHop.logout()
            sleep(breakDurationMils)
            runTimeStopWatch.reset()
            //Get new session duration
            sessionTimeInSec = Random.nextInt(account.minimumSessionTime, account.maximumSessionTime) * 60 // Converting to time in seconds
        }
        if(!isInValidWindowTime()){
            //TODO sleep until in valid window time
        }

        if (loginHandler.account.username.isEmpty()) {
            loginHandler.account = account
        }


        if (account.startActionScriptAutomatically && loginHandler.isAtHomeScreen(ctx)) {
            status = "Logging in"
            val failedLogin = !loginHandler.login(ctx)
            if (failedLogin) {
                logger.error("Failed to login. Login handler will now execute an infinite loop to prevent future logins")
                while (failedLogin) {

                    delay(1000 * 60)
                }
            }
        }
    }

    override fun draw(g: Graphics) {
        super.draw(g)
        val debugText = arrayListOf<DebugText>()
        if(sessionTimeInSec > 0) {
            if(sessionTimeInSec > runTimeStopWatch.elapsedSec) {
                val timeLeft = abs(sessionTimeInSec - runTimeStopWatch.elapsedSec)
                val sessionTimeLeft = Time.getRuntimeString((timeLeft).toLong() * 1000)
                debugText.add(DebugText("Remaining session time before break $sessionTimeLeft"))
            }
        }
        if(this.account.useBreaks && isBreakTime()){
            val timeLeft = abs(breakTimeStopWatch.elapsed - breakDurationMils)
            val stringTimeLeft = Time.getRuntimeString(timeLeft)
            debugText.add(DebugText("Breaking... Time left: $stringTimeLeft", color = Color.red))
        }

        val x = 300
        var y = 35
        val savedColor = g.color
        debugText.forEach {
            g.color = it.color

            g.drawString(it.text, x, y)
            y += 10
        }
        g.color = savedColor
    }

}