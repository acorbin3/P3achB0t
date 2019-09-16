package com.p3achb0t.api.painting


import com.p3achb0t.api.Calculations
import com.p3achb0t.api.user_inputs.Camera
import com.p3achb0t.api.wrappers.Bank
import com.p3achb0t.api.wrappers.ClientMode
import com.p3achb0t.api.wrappers.Menu
import com.p3achb0t.api.wrappers.tabs.Tabs
//import com.p3achb0t.scripts.TutorialIsland
import java.awt.Color
import java.awt.Graphics

data class DebugText(val text: String = "", val color: Color = Color.RED)

fun drawDebugText(g: Graphics, client: com.p3achb0t._runestar_interfaces.Client) {

    val debugText = arrayListOf<DebugText>()
    if (true) {
        g.color = Color.white
        //debugText.add(DebugText("Mouse x:${MainApplet.mouseEvent?.x} y:${MainApplet.mouseEvent?.y}"))
        debugText.add(DebugText("clientData.gameCycle :${client.getCycle()}"))
        debugText.add(DebugText("Game State:: ${client.getGameState()}"))
        debugText.add(DebugText("clientData.loginState :${client.getLoginState()}"))
//        debugText.add(DebugText("Account status :${client.get__cq_aw()}"))
        debugText.add(DebugText("Camera: x:${Camera(client).x} y:${Camera(client).y} z:${Camera(client).z} pitch:${Camera(client).pitch} yaw: ${Camera(client).yaw} angle: ${Camera(client).angle}"))
        debugText.add(DebugText("OpenTab: ${Tabs(client).getOpenTab()?.name}"))
        debugText.add(DebugText("Bank Status: ${Bank(client).isOpen()}"))



        try {
            debugText.add(DebugText("Spell: ${client.getSelectedSpellName()}"))
            debugText.add(DebugText("Animation: ${client.getLocalPlayer().getSequence()}"))
            debugText.add(DebugText("Mode: ${ClientMode(client).getMode().name}"))
            debugText.add(
                DebugText(
                    "LocalPlayer Position: (${client.getLocalPlayer().getX() / 128},${client.getLocalPlayer().getY() / 128})" +
                            " RAW: (${client.getLocalPlayer().getX()},${client.getLocalPlayer().getY()}"
                )
            )
            debugText.add(DebugText("Base(x,y): (${client.getBaseX()},${client.getBaseY()})"))

            val miniMapPlayer = Calculations.worldToMiniMap(
                    client.getLocalPlayer().getX(),
                    client.getLocalPlayer().getY(),
                    client

            )
            debugText.add(
                DebugText(
                    "localPlayer minimap: (x,y) (${miniMapPlayer.x},${miniMapPlayer.y})" +
                            "Including base(${client.getLocalPlayer().getX() / 128 + client.getBaseX()}," +
                            "${client.getLocalPlayer().getY() / 128 + client.getBaseY()})  " +
                            "mapAngle: ${client.getCamAngleY()}"
                )
            )
            //debugText.add(DebugText("Tutorial Island % Complete: ${(TutorialIsland.getPercentComplete(client) * 100)}"))
            debugText.add(DebugText("Zoom: ${client.getViewportZoom()}"))
            debugText.add(DebugText(Menu(client).getHoverAction()))
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
}