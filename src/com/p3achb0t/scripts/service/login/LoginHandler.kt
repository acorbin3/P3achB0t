package com.p3achb0t.scripts.service.login

import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.script.ServiceScript
import com.p3achb0t.api.utils.Script
import com.p3achb0t.client.accounts.Account
import com.p3achb0t.client.accounts.LoginHandler
import java.awt.Graphics

@ScriptManifest(Script.SERVICE,"Login Handler","P3aches", "0.1")
class LoginAndBreakHandlerHandler: ServiceScript(shouldStopActionScript=true) {
    var loginHandler = LoginHandler()
    var status = ""
    override suspend fun isValidToRun(account: Account): Boolean {
        return account.startActionScriptAutomatically && loginHandler.isAtHomeScreen(ctx)
    }

    override suspend fun loop(account: Account) {
        if(loginHandler.account.username.isEmpty()){
            loginHandler.account = account
        }
        if(account.startActionScriptAutomatically && loginHandler.isAtHomeScreen(ctx)) {
            status = "Logging in"
            loginHandler.login(ctx)
        }

    }

    override fun draw(g: Graphics) {
        super.draw(g)
    }
}