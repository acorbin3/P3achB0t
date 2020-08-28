package com.p3achb0t.api.script

import com.p3achb0t.client.accounts.Account

/*
* This script type responsibility would be to some kind of background task that might not need needed for the main
* script to run actions. It also has the possibly to pause the main action script while it can take over. For example
* a Login Handler could pause the main script to logout. Possible other events: BankPin, BreakEvent, DismissRandoms,
* GenieSolver, LoginScreen, WelcomeScreen
*
* Note: Service Scripts will always be running in the background
* */
abstract class ServiceScript(var shouldPauseActionScript: Boolean = false, var runWhenActionScriptIsPausedOrStopped: Boolean = true) : SuperScript() {
    open suspend fun isValidToRun(account: Account): Boolean {
        return true
    }

    abstract suspend fun loop(account: Account)
}