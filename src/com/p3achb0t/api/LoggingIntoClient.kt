package com.p3achb0t.api

import UserDetails
import com.p3achb0t.Main
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.user_inputs.Mouse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.Point

fun LoggingIntoAccount() {
    //Logging into the client
    Thread.sleep(100)
    GlobalScope.launch {
        var loggedIn = false
        val mouse = Mouse()
        repeat(1000) {
            try {
//                clientData = getClientData()

                // When loaded login
                if (!loggedIn && Main.clientData.getGameState() == 10) {
                    mouse.moveMouse(Point(430, 280), true, Mouse.ClickType.Left)

                    delay(200)
                    Keyboard.sendKeys(UserDetails.data.password)
                    delay(200)

                    mouse.moveMouse(Point(300, 310), true, Mouse.ClickType.Left)
                }
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