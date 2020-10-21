package com.p3achb0t.client.accounts

import com.p3achb0t.api.Context
import com.p3achb0t.api.LoginResponse
import com.p3achb0t.api.StopWatch
import com.p3achb0t.api.userinputs.Mouse
import com.p3achb0t.api.wrappers.GameState
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.googleauth.GoogleAuthenticator
import kotlinx.coroutines.delay
import java.awt.Point
import java.awt.event.KeyEvent
import kotlin.random.Random

class LoginHandler(var account: Account = Account()) {

    fun isLoggedIn(ctx: Context): Boolean {
        return ctx.client.getGameState().let { GameState.of(it) } == GameState.LOGGED_IN
    }

    fun isAtHomeScreen(ctx: Context): Boolean {
        return ctx.client.getGameState().let { GameState.of(it) } == GameState.LOGIN_SCREEN
    }

    fun isRedButtonAvailable(ctx: Context): Boolean{
        return ctx.widgets.find(378,78)?.getIsHidden() == false
    }

    /**
     * @return - This will return if we failed to login
     */
    suspend fun login(ctx: Context): Boolean {
        if(!ctx.widgets.isWidgetAvaliable(378, 87) && !ctx.worldHop.isLoggedIn) {
            println("Logging in")
            println("current user & pass: ${ctx.client.getLogin_username()}:${ctx.client.getLogin_password()}")
            ctx.keyboard.sendKeys(" ", sendReturn = true)
            //OPENRS searhc loginBoxCenter
            ctx.mouse.moveMouse(Point(ctx.client.get__cd_v(), 310), true, Mouse.ClickType.Left)
            delay(500)
            ctx.mouse.moveMouse(Point(ctx.client.get__cd_v() + 55, 294), true, Mouse.ClickType.Left)
            println("${ctx.client.getLogin_username()}:${ctx.client.getLogin_password()}")
            val timeout = StopWatch()
            if (ctx.client.getLogin_username() != account.username) {
                //Delete user name and replace
              ctx.mouse.moveMouse(Point(Random.nextInt(ctx.client.get__cd_v() - 50, ctx.client.get__cd_v() + 70), Random.nextInt(240, 249)), true, Mouse.ClickType.Left)
                while (ctx.client.getLogin_username().isNotEmpty()
                        && timeout.elapsedSec < 5) {
                    ctx.keyboard.pressDownKey(KeyEvent.VK_BACK_SPACE)
                }
                ctx.keyboard.release(KeyEvent.VK_DOWN)

                ctx.keyboard.sendKeys(account.username, false, true)

            }

            timeout.reset()
            //Move to password
            ctx.mouse.moveMouse(Point(Random.nextInt(ctx.client.get__cd_v() - 50, ctx.client.get__cd_v() + 70), Random.nextInt(258, 269)), true, Mouse.ClickType.Left)
            if (ctx.client.getLogin_password().isNotEmpty() == true) {
                //Clear password
                while (ctx.client.getLogin_password().isNotEmpty()
                        && timeout.elapsedSec < 5) {
                    ctx.keyboard.pressDownKey(KeyEvent.VK_BACK_SPACE)
                }
                ctx.keyboard.release(KeyEvent.VK_DOWN)
            }
            ctx.keyboard.sendKeys(account.password, true, true)
            Utils.waitFor(12, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    println("Current game state " + GameState.currentState(ctx).name)
                    return GameState.currentState(ctx) == GameState.LOGGED_IN || (LoginResponse.getLoginResponse(ctx) == LoginResponse.BANNED) ||
                            (LoginResponse.getLoginResponse(ctx) == LoginResponse.INVALID)
                }
            })

            if(GameState.currentState(ctx) == GameState.LOGIN_SCREEN_AUTHENTICATOR && account.googleAuthKey.isNotEmpty()){
                val googleAuthenticator = GoogleAuthenticator(account.googleAuthKey)
                val key = googleAuthenticator.generate()
                ctx.keyboard.sendKeys(key, true, true)
            }


            if ((LoginResponse.getLoginResponse(ctx) == LoginResponse.BANNED) ||
                    (LoginResponse.getLoginResponse(ctx) == LoginResponse.INVALID)) {
                if (LoginResponse.getLoginResponse(ctx) == LoginResponse.BANNED) {
                    AccountManager.accounts.forEach {
                        if (it.username == account.username) {
                            it.banned = true
                        }
                        AccountManager.updateJSONFile()
                    }
                    GlobalStructs.db.setBanned(account.username, account.sessionToken)
                }
                println("account banned or invalid credentials")
                return false
            }
            println("Game state == ${ctx.client.getGameState()}")
            delay(2000)
        }

//        val ctx = Context(client)
        //Press red button
        ctx.widgets.waitTillWidgetNotNull(378, 87)

        println("Clicking button")
        try {
            WidgetItem(ctx.widgets.find(378, 87), ctx = ctx).click()
        }catch (e: Exception){

        }
        Utils.sleepUntil({ !ctx.widgets.isWidgetAvaliable(378, 87) }, 5)
        return true
    }
}