package com.p3achb0t.api.painting

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.user_inputs.Camera
import com.p3achb0t.api.wrappers.Bank
import com.p3achb0t.api.wrappers.ClientMode
import com.p3achb0t.api.wrappers.Menu
import com.p3achb0t.api.wrappers.tabs.Tabs
import com.p3achb0t.scripts.TutorialIsland
import java.awt.Color
import java.awt.Graphics

data class DebugText(val text: String = "", val color: Color = Color.WHITE)
fun drawDebugText(g: Graphics) {

    val debugText = arrayListOf<DebugText>()
    if (true) {
        g.color = Color.white
        debugText.add(DebugText("Mouse x:${Main.mouseEvent?.x} y:${Main.mouseEvent?.y}"))
        debugText.add(DebugText("clientData.gameCycle :${Main.clientData.getGameCycle()}"))
        debugText.add(DebugText("Game State:: ${Main.clientData.getGameState()}"))
        debugText.add(DebugText("clientData.loginState :${Main.clientData.getLoginState()}"))
        debugText.add(DebugText("Account status :${Main.clientData.getAccountStatus()}"))
        debugText.add(DebugText("Camera: x:${Camera.x} y:${Camera.y} z:${Camera.z} pitch:${Camera.pitch} yaw: ${Camera.yaw} angle: ${Camera.angle}"))
        debugText.add(DebugText("OpenTab: ${Tabs.getOpenTab()?.name}"))
        debugText.add(DebugText("Bank Status: ${Bank.isOpen()}"))



        try {
            debugText.add(DebugText("Spell: ${Main.clientData.getSelectedSpellName()}"))
            debugText.add(DebugText("Animation: ${Main.clientData.getLocalPlayer().getAnimation()}"))
            debugText.add(DebugText("Mode: ${ClientMode.getMode().name}"))
            debugText.add(
                DebugText(
                    "LocalPlayer Position: (${Main.clientData.getLocalPlayer().getLocalX() / 128},${Main.clientData.getLocalPlayer().getLocalY() / 128})" +
                            " RAW: (${Main.clientData.getLocalPlayer().getLocalX()},${Main.clientData.getLocalPlayer().getLocalY()}"
                )
            )
            debugText.add(DebugText("Base(x,y): (${Main.clientData.getBaseX()},${Main.clientData.getBaseY()})"))

            val miniMapPlayer = Calculations.worldToMiniMap(
                Main.clientData.getLocalPlayer().getLocalX(),
                Main.clientData.getLocalPlayer().getLocalY()
            )
            debugText.add(
                DebugText(
                    "localPlayer minimap: (x,y) (${miniMapPlayer.x},${miniMapPlayer.y})" +
                            "Including base(${Main.clientData.getLocalPlayer().getLocalX() / 128 + Main.clientData.getBaseX()}," +
                            "${Main.clientData.getLocalPlayer().getLocalY() / 128 + Main.clientData.getBaseY()})  " +
                            "mapAngle: ${Main.clientData.getMapAngle()}"
                )
            )
            debugText.add(DebugText("Tutorial Island % Complete: ${(TutorialIsland.getPercentComplete() * 100)}"))
            debugText.add(DebugText("Zoom: ${Main.clientData.getZoomExact()}"))
            debugText.add(DebugText(Menu.getHoverAction()))
//            debugText.add(DebugText())


        } catch (e: Exception) {
        }
        val x = 50
        var y = 50
        debugText.forEach {
            g.color = it.color
            g.drawString(it.text, x, y)
            y += 10
        }
    }
    Main.mouseEvent?.x?.let { Main.mouseEvent?.y?.let { it1 -> g.drawRect(it, it1, 5, 5) } }
}