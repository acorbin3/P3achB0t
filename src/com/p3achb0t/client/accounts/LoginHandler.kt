package com.p3achb0t.client.accounts

import com.p3achb0t.api.Context
import com.p3achb0t.api.userinputs.Mouse
import com.p3achb0t.api.wrappers.GameState
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import java.awt.Point
import java.awt.event.KeyEvent
import kotlin.random.Random

class LoginHandler(var account: Account = Account()) {

    fun isLoggedIn(ctx: Context): Boolean{
        return ctx.client.getGameState().let { GameState.of(it) } == GameState.LOGGED_IN
    }

    fun isAtHomeScreen(ctx: Context): Boolean{
        return ctx.client.getGameState().let { GameState.of(it) } == GameState.LOGIN_SCREEN
    }

    suspend fun login(ctx: Context){
        println("Logging in")
        println("current user & pass: ${ctx.client.getLogin_username()}:${ctx.client.getLogin_password()}")
        ctx.mouse.moveMouse(Point(400, 310), true, Mouse.ClickType.Left)
        delay(500)
        ctx.mouse.moveMouse(Point(466, 294), true, Mouse.ClickType.Left)
        println("${ctx.client.getLogin_username()}:${ctx.client.getLogin_password()}")
        if(ctx.client.getLogin_username() != account.username){
            //Delete user name and replace
            ctx.mouse.moveMouse(Point(Random.nextInt(360,480), Random.nextInt(240,249)), true, Mouse.ClickType.Left)
            while(ctx.client.getLogin_username().isNotEmpty() == true){
                ctx.keyboard.pressDownKey(KeyEvent.VK_BACK_SPACE)
            }
            ctx.keyboard.release(KeyEvent.VK_DOWN)

            ctx.keyboard.sendKeys(account.username, false, true)

        }

        //Move to password
        ctx.mouse.moveMouse(Point(Random.nextInt(360,480), Random.nextInt(258,269)), true, Mouse.ClickType.Left)
        if(ctx.client.getLogin_password().isNotEmpty() == true){
            //Clear password
            while(ctx.client.getLogin_password().isNotEmpty() == true){
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
        if ((LoginResponse.getLoginResponse(ctx) == LoginResponse.BANNED) ||
                (LoginResponse.getLoginResponse(ctx) == LoginResponse.INVALID)) {
            if (LoginResponse.getLoginResponse(ctx) == LoginResponse.BANNED){
                Manager.db.setBanned(ScriptManager.loginHandler.account.username, ScriptManager.sessionID)
            }
        println("account banned or invalid credentials")
        val game = manager.tabManager.getInstance(manager.tabManager.getSelectedIndexx())
        val manager = game.client.getScriptManager()
        manager.stop()
    }
    println("Game state == ${ctx.client.getGameState()}")
    delay(2000)

//        val ctx = Context(client)
    //Press red button
    ctx.widgets.waitTillWidgetNotNull(378,87)

    println("Clicking button")
    WidgetItem(ctx.widgets.find(378,87),ctx = ctx).click()
        Utils.sleepUntil({!ctx.widgets.isWidgetAvaliable(378, 87)} , 5)


}

}