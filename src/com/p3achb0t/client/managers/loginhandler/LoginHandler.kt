package com.p3achb0t.client.managers.loginhandler

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.GameState
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.client.managers.accounts.Account
import kotlinx.coroutines.delay
import java.awt.Point
import java.awt.event.KeyEvent
import kotlin.random.Random

class LoginHandler(var account: Account = Account(), var ctx: Context? = null) {

    fun isLoggedIn(): Boolean{
        return ctx?.client?.getGameState()?.let { GameState.of(it) } == GameState.LOGGED_IN
    }

    fun isAtHomeScreen(): Boolean{
        return ctx?.client?.getGameState()?.let { GameState.of(it) } == GameState.LOGIN_SCREEN
    }

    suspend fun login(){
        println("${ctx?.client?.getLogin_username()}:${ctx?.client?.getLogin_password()}")
        ctx?.mouse?.moveMouse(Point(400, 310), true, com.p3achb0t.api.user_inputs.Mouse.ClickType.Left)
        delay(500)
        ctx?.mouse?.moveMouse(Point(466, 294), true, com.p3achb0t.api.user_inputs.Mouse.ClickType.Left)
        println("${ctx?.client?.getLogin_username()}:${ctx?.client?.getLogin_password()}")
        if(ctx?.client?.getLogin_username() != account.username){
            //Delete user name and replace
            ctx?.mouse?.moveMouse(Point(Random.nextInt(360,480), Random.nextInt(240,249)), true, com.p3achb0t.api.user_inputs.Mouse.ClickType.Left)
            while(ctx?.client?.getLogin_username()?.isNotEmpty() == true){
                ctx?.keyboard?.pressDownKey(KeyEvent.VK_BACK_SPACE)
            }
            ctx?.keyboard?.release(KeyEvent.VK_DOWN)

            ctx?.keyboard?.sendKeys(account.username, false, true)

        }

        //Move to password
        ctx?.mouse?.moveMouse(Point(Random.nextInt(360,480), Random.nextInt(258,269)), true, com.p3achb0t.api.user_inputs.Mouse.ClickType.Left)
        if(ctx?.client?.getLogin_password()?.isNotEmpty()== true){
            //Clear password
            while(ctx?.client?.getLogin_password()?.isNotEmpty() == true){
                ctx?.keyboard?.pressDownKey(KeyEvent.VK_BACK_SPACE)
            }
            ctx?.keyboard?.release(KeyEvent.VK_DOWN)
        }
        ctx?.keyboard?.sendKeys(account.password, true, true)
        Utils.waitFor(10, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(100)
                println("Current game state " + GameState.currentState(ctx!!).name)
                return GameState.currentState(ctx!!) == GameState.LOGGED_IN
            }
        })
        println("Game state == ${ctx?.client?.getGameState()}")

//        val ctx = Context(client)
        //Press red button
        ctx?.widgets?.waitTillWidgetNotNull(378,87)

        println("Clicking button")
        WidgetItem(ctx?.widgets?.find(378,87),ctx = ctx).click()

    }

}
