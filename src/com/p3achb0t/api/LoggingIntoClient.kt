package com.p3achb0t.api

import com.p3achb0t.UserDetails
import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.Point

class LoggingIntoClient {
    companion object {
        var loggedIn = false
    }
}

fun LoggingIntoAccount(ctx: Client, mouse: Mouse, keyboard: Keyboard) {
    //Logging into the client
    Thread.sleep(3000)
    GlobalScope.launch {

        repeat(1000) {
            try {
//                clientData = getClientData()

                // When loaded login
                if (!LoggingIntoClient.loggedIn && ctx.getGameState() == 10) {
                    mouse.moveMouse(Point(430, 280), true, Mouse.ClickType.Left)

                    delay(200)
                    keyboard.sendKeys(UserDetails.data.password)
                    delay(200)

                    mouse.moveMouse(Point(300, 310), true, Mouse.ClickType.Left)

                    while (ctx.getGameState() != 30) {
                        delay(100)
                    }
                    delay(1500)
                    println("Clicking login")
                    val login = WidgetItem(Widgets.find(ctx, WidgetID.LOGIN_CLICK_TO_PLAY_GROUP_ID, 85), client =ctx)
                    println("login: ${login.area.x},${login.area.y},${login.area.height},${login.area.width}")

                    login.click()
                    LoggingIntoClient.loggedIn = true
                }


                //IF not on tutorial island: click the play. Widget 378,85
//                if (Client.GameState.LoggedIn.intState == clientData.gameState.toInt()) {
//                    getLocalNPCData()
//                    getLocalPlayersData()
//                    getGroundItemData()
//                    getItemTableData()
//                    getRegion()
//                }
            } catch (e: Exception) {
                println("Exception" + e.toString())
                for (statck in e.stackTrace) {
                    println(statck.toString())
                }
            }
            delay(50)
        }
    }
}