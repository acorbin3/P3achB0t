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
import java.time.ZoneId
import kotlin.math.abs
import kotlin.random.Random

@ScriptManifest(Script.SERVICE, "Login Handler", "P3aches", "0.1")
class LoginAndBreakHandlerHandler : ServiceScript(shouldPauseActionScript = true, runWhenActionScriptIsPausedOrStopped = false) {
    var loginHandler = LoginHandler()
    var status = ""
    var startTime = 0

    var runTimeStopWatch = StopWatch()
    var breakDurationMils = 0L
    val breakTimeStopWatch = StopWatch()
    val windowTimeStopwatch = StopWatch()
    var timeTillValidWindowTimeInMin = 0
    var timeTillInvalidWindowTimeMin = 0
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
        return account.useBreaks && runTimeStopWatch.elapsedSec >= sessionTimeInSec
    }

    fun isInValidWindowTime(): Boolean{
        val hour = java.time.LocalTime.now(ZoneId.of("GMT+1")).hour
        val min = java.time.LocalTime.now(ZoneId.of("GMT+1")).minute
        val timeInSeconds = hour*60 + min

        var validRange = false
        //Range where start and end time are on the same day
        if(account.windowStartTime < account.windowEndTime) {
            validRange = timeInSeconds in account.windowStartTime..account.windowEndTime
        }else{
            // Range where start is on day 1 and end is on day 2
            validRange = timeInSeconds in account.windowStartTime..24*60 || timeInSeconds in 0..account.windowEndTime
        }

        return !account.useWindowTime || account.useWindowTime && validRange
    }

    override suspend fun loop(account: Account) {


        // Initialize values
        if (sessionTimeInSec == 0 && (account.maximumSessionTime * 60) > 0) {
            sessionTimeInSec = Random.nextInt(account.minimumSessionTime, account.maximumSessionTime) * 60 // Converting to time in seconds
        }

        if (isBreakTime()) {
            breakDurationMils = Random.nextLong((account.minimumBreakTime * 60 * 1000).toLong(), (account.maximumBreakTime * 60 * 1000).toLong())
            breakTimeStopWatch.reset()
            ctx.worldHop.logout()
            sleep(breakDurationMils)
            runTimeStopWatch.reset()
            //Get new session duration
            sessionTimeInSec = Random.nextInt(account.minimumSessionTime, account.maximumSessionTime) * 60 // Converting to time in seconds
        }
        val hour = java.time.LocalTime.now(ZoneId.of("GMT+1")).hour
        val min = java.time.LocalTime.now(ZoneId.of("GMT+1")).minute
        val currentTimeInMins = hour*60 + min
        if(account.useWindowTime){
            timeTillInvalidWindowTimeMin = if(currentTimeInMins <= account.windowEndTime){
                (account.windowEndTime - currentTimeInMins)
            }else{
                //time till 24h based on current time, then add the next day till start time
                (account.windowEndTime + 24*60 - currentTimeInMins)
            }
        }

        if(!isInValidWindowTime()){

            windowTimeStopwatch.reset()
            //Sleep time in milis
            if(ctx.worldHop.isLoggedIn) {
                ctx.worldHop.logout()
            }

        }

        if (loginHandler.account.username != account.username) {
            loginHandler.account = account
        }


        if (account.startActionScriptAutomatically

                && (loginHandler.isAtHomeScreen(ctx) || loginHandler.isRedButtonAvailable(ctx))
                && isInValidWindowTime()) {
            status = "Logging in"
            val failedLogin = !loginHandler.login(ctx)
            if (failedLogin) {
                logger.error("Failed to login. Login handler will now set account banned. ${account.username}")
                account.banned = true
            }
        }
    }

    override fun draw(g: Graphics) {
        super.draw(g)
        val debugText = arrayListOf<DebugText>()
        if(this.account.useBreaks
                && sessionTimeInSec > 0) {
            if(sessionTimeInSec > runTimeStopWatch.elapsedSec || ctx.worldHop.isLoggedIn) {
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

        val hour = java.time.LocalTime.now(ZoneId.of("GMT+1")).hour
        val min = java.time.LocalTime.now(ZoneId.of("GMT+1")).minute
        val sec = java.time.LocalTime.now(ZoneId.of("GMT+1")).second
        val timeInMins = hour*60 + min

        if(!isInValidWindowTime()){
            timeTillValidWindowTimeInMin = if(timeInMins < account.windowStartTime){
                (account.windowStartTime - timeInMins)
            }else{
                //time till 24h based on current time, then add the next day till start time
                (account.windowStartTime + 24*60 - timeInMins)
            }
            val timeLeft = abs(timeTillValidWindowTimeInMin * 60 * 1000L) + (60 - sec) * 1000L
            val stringTimeLeft = Time.getRuntimeString(timeLeft)
            debugText.add(DebugText("InvalidWindow time... Time left: $stringTimeLeft. Current time:$timeInMins Should be between ${account.windowStartTime}..${account.windowEndTime}", color = Color.yellow))
        }else if(this.account.useWindowTime){
            timeTillInvalidWindowTimeMin = if(timeInMins <= account.windowEndTime){
                (account.windowEndTime - timeInMins)
            }else{
                //time till 24h based on current time, then add the next day till start time
                (account.windowEndTime + 24*60 - timeInMins)
            }

            val timeLeft = abs(timeTillInvalidWindowTimeMin * 60 * 1000L) + (60 - sec) * 1000L
            val stringTimeLeft = Time.getRuntimeString(timeLeft)
            debugText.add(DebugText("Valid window time. CurrentTimeInMin: $timeInMins Time till invalid:$stringTimeLeft " +
                    "Start:${account.windowStartTime} end:${account.windowEndTime} ", color = Color.green))
        }


        val x = 300
        var y = 350
        val savedColor = g.color
        debugText.forEach {
            g.color = it.color

            g.drawString(it.text, x, y)
            y += 10
        }
        g.color = savedColor
    }

}