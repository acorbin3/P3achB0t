package com.p3achb0t.api

import com.p3achb0t.UserDetails
import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.Point

class LoggingIntoClient {
    companion object {
        var loggedIn = false
    }
}

fun LoggingIntoAccount(ctx: Context) {
    //Logging into the client
    Thread.sleep(2000)
    GlobalScope.launch {

        repeat(2) {
            try {

//                val disconnected = WidgetItem(ctx.widgets.find(WidgetID.login, 85), ctx = ctx)
                // When loaded login
                if (ctx.client.getGameState() == 10) {
                    delay(500)
                    ctx.mouse.moveMouse(Point(400, 310), true, Mouse.ClickType.Left)
                    delay(500)
                    ctx.mouse.moveMouse(Point(466, 294), true, Mouse.ClickType.Left)
                    delay(500)
                    ctx.keyboard.sendKeys(UserDetails.data.username, true, true)
                    ctx.keyboard.sendKeys(UserDetails.data.password, true, true)

                    Utils.waitFor(10, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return ctx.client.getGameState() == 30
                        }
                    })
                    delay(1500)
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