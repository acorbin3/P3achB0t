package com.p3achb0t.client.managers.loginhandler

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.GameState
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.client.managers.accounts.Account
import kotlinx.coroutines.delay
import java.awt.Point
import java.awt.event.KeyEvent
import kotlin.random.Random

class LoginHandler(var client: Client, var account: Account = Account()) {

    fun isLoggedIn(): Boolean{
        return false
    }

    fun isAtHomeScreen(): Boolean{
        return GameState.of(client.getGameState()) == GameState.LOGIN_SCREEN
    }

    suspend fun login(){
        println("${client.getLogin_username()}:${client.getLogin_password()}")
        val mouseWrapper = com.p3achb0t.api.user_inputs.Mouse(client)
        val keyboardWrapper = com.p3achb0t.api.user_inputs.Keyboard(client)
        mouseWrapper.moveMouse(Point(400, 310), true, com.p3achb0t.api.user_inputs.Mouse.ClickType.Left)
        delay(500)
        mouseWrapper.moveMouse(Point(466, 294), true, com.p3achb0t.api.user_inputs.Mouse.ClickType.Left)
        println("${client.getLogin_username()}:${client.getLogin_password()}")
        if(client.getLogin_username() != account.username){
            //Delete user name and replace
            mouseWrapper.moveMouse(Point(Random.nextInt(360,480), Random.nextInt(240,249)), true, com.p3achb0t.api.user_inputs.Mouse.ClickType.Left)
            while(client.getLogin_username().isNotEmpty()){
                keyboardWrapper.pressDownKey(KeyEvent.VK_BACK_SPACE)
            }
            keyboardWrapper.release(KeyEvent.VK_DOWN)

            keyboardWrapper.sendKeys(account.username, false, true)

        }

        //Move to password
        mouseWrapper.moveMouse(Point(Random.nextInt(360,480), Random.nextInt(258,269)), true, com.p3achb0t.api.user_inputs.Mouse.ClickType.Left)
        if(client.getLogin_password().isNotEmpty()){
            //Clear password
            while(client.getLogin_password().isNotEmpty()){
                keyboardWrapper.pressDownKey(KeyEvent.VK_BACK_SPACE)
            }
            keyboardWrapper.release(KeyEvent.VK_DOWN)
        }
        keyboardWrapper.sendKeys(account.password, true, true)
        Utils.waitFor(10, object : Utils.Condition {
            override suspend fun accept(): Boolean {
                delay(100)
                return client.getGameState() == 30
            }
        })
        println("Game state == ${client.getGameState()}")
        delay(5500)

        val ctx = Context(client)
        //Press red button
        ctx.widgets.waitTillWidgetNotNull(378,87)

        println("Clicking button")
        WidgetItem(ctx.widgets.find(378,87),ctx = ctx).click()

    }

}