package com.p3achb0t.scripts.service.login

import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.script.ServiceScript
import com.p3achb0t.api.utils.Script
import com.p3achb0t.client.accounts.Account
import com.p3achb0t.client.accounts.LoginHandler
import kotlinx.coroutines.delay

@ScriptManifest(Script.SERVICE, "Login Handler", "P3aches", "0.1")
class LoginAndBreakHandlerHandler : ServiceScript(shouldPauseActionScript = true) {
    var loginHandler = LoginHandler()
    var status = ""
    override suspend fun isValidToRun(account: Account): Boolean {
        return account.startActionScriptAutomatically && loginHandler.isAtHomeScreen(ctx)
    }

    override suspend fun loop(account: Account) {
        if (loginHandler.account.username.isEmpty()) {
            loginHandler.account = account
        }
        if(account.startActionScriptAutomatically && loginHandler.isAtHomeScreen(ctx)) {
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

}