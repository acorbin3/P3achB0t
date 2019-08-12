package com.p3achb0t.api.painting

import com.p3achb0t.MainApplet
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.user_inputs.Camera
import com.p3achb0t.api.wrappers.Bank
import com.p3achb0t.api.wrappers.Client
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
        debugText.add(DebugText("Mouse x:${MainApplet.mouseEvent?.x} y:${MainApplet.mouseEvent?.y}"))
        debugText.add(DebugText("clientData.gameCycle :${Client.client.getCycle()}"))
        debugText.add(DebugText("Game State:: ${Client.client.getGameState()}"))
        debugText.add(DebugText("clientData.loginState :${Client.client.getLoginState()}"))
        debugText.add(DebugText("Account status :${Client.client.get__cq_aw()}"))
        debugText.add(DebugText("Camera: x:${Camera.x} y:${Camera.y} z:${Camera.z} pitch:${Camera.pitch} yaw: ${Camera.yaw} angle: ${Camera.angle}"))
        debugText.add(DebugText("OpenTab: ${Tabs.getOpenTab()?.name}"))
        debugText.add(DebugText("Bank Status: ${Bank.isOpen()}"))



        try {
            debugText.add(DebugText("Spell: ${Client.client.getSelectedSpellName()}"))
            debugText.add(DebugText("Animation: ${Client.client.getLocalPlayer().getSequence()}"))
            debugText.add(DebugText("Mode: ${ClientMode.getMode().name}"))
            debugText.add(
                DebugText(
                    "LocalPlayer Position: (${Client.client.getLocalPlayer().getX() / 128},${Client.client.getLocalPlayer().getY() / 128})" +
                            " RAW: (${Client.client.getLocalPlayer().getX()},${Client.client.getLocalPlayer().getY()}"
                )
            )
            debugText.add(DebugText("Base(x,y): (${Client.client.getBaseX()},${Client.client.getBaseY()})"))

            val miniMapPlayer = Calculations.worldToMiniMap(
                Client.client.getLocalPlayer().getX(),
                Client.client.getLocalPlayer().getY()
            )
            debugText.add(
                DebugText(
                    "localPlayer minimap: (x,y) (${miniMapPlayer.x},${miniMapPlayer.y})" +
                            "Including base(${Client.client.getLocalPlayer().getX() / 128 + Client.client.getBaseX()}," +
                            "${Client.client.getLocalPlayer().getY() / 128 + Client.client.getBaseY()})  " +
                            "mapAngle: ${Client.client.getCamAngleY()}"
                )
            )
            debugText.add(DebugText("Tutorial Island % Complete: ${(TutorialIsland.getPercentComplete() * 100)}"))
            debugText.add(DebugText("Zoom: ${Client.client.getViewportZoom()}"))
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
    MainApplet.mouseEvent?.x?.let { MainApplet.mouseEvent?.y?.let { it1 -> g.drawRect(it, it1, 5, 5) } }
}