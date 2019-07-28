package com.p3achb0t.api.painting

import com.p3achb0t.Main
import com.p3achb0t.api.Calculations
import com.p3achb0t.api.user_inputs.Camera
import com.p3achb0t.api.wrappers.Bank
import com.p3achb0t.api.wrappers.ClientMode
import com.p3achb0t.api.wrappers.tabs.Tabs
import com.p3achb0t.scripts.TutorialIsland
import java.awt.Color
import java.awt.Graphics

fun drawDebugText(g: Graphics) {
    if (true) {
        g.color = Color.white
        g.drawString("Mouse x:${Main.mouseEvent?.x} y:${Main.mouseEvent?.y}", 50, 50)
        g.drawString("clientData.gameCycle :${Main.clientData.getGameCycle()}", 50, 60)
        g.drawString("Game State:: ${Main.clientData.getGameState()}", 50, 70)
        g.drawString("clientData.loginState :${Main.clientData.getLoginState()}", 50, 80)
        g.drawString("Account status :${Main.clientData.getAccountStatus()}", 50, 90)

        g.drawString(
            "Camera: x:${Camera.x} y:${Camera.y} z:${Camera.z} pitch:${Camera.pitch} yaw: ${Camera.yaw} angle: ${Camera.angle}",
            50,
            110
        )
        g.drawString("OpenTab: ${Tabs.getOpenTab()?.name}", 50, 120)
        g.drawString("Bank Status: ${Bank.isOpen()}", 50, 130)

        try {
            g.drawString("Spell: ${Main.clientData.getSelectedSpellName()}", 50, 140)

            g.drawString("Animation: ${Main.clientData.getLocalPlayer().getAnimation()}", 50, 100)
            g.drawString("Mode: ${ClientMode.getMode().name}", 50, 150)
            g.drawString(
                "LocalPlayer Position: (${Main.clientData.getLocalPlayer().getLocalX() / 128},${Main.clientData.getLocalPlayer().getLocalY() / 128})" +
                        " RAW: (${Main.clientData.getLocalPlayer().getLocalX()},${Main.clientData.getLocalPlayer().getLocalY()}",
                50,
                160
            )
            g.drawString(
                "Base(x,y): (${Main.clientData.getBaseX()},${Main.clientData.getBaseY()})",
                50,
                180
            )
            val miniMapPlayer = Calculations.worldToMiniMap(
                Main.clientData.getLocalPlayer().getLocalX(),
                Main.clientData.getLocalPlayer().getLocalY()
            )
            g.drawString(
                "localPlayer minimap: (x,y) (${miniMapPlayer.x},${miniMapPlayer.y})" +
                        "Including base(${Main.clientData.getLocalPlayer().getLocalX() / 128 + Main.clientData.getBaseX()}," +
                        "${Main.clientData.getLocalPlayer().getLocalY() / 128 + Main.clientData.getBaseY()})  " +
                        "mapAngle: ${Main.clientData.getMapAngle()}", 50, 190
            )
            g.drawString("Tutorial Island % Complete: ${TutorialIsland.getPercentComplete()}", 50, 200)
            g.drawString("Zoom: ${Main.clientData.getZoomExact()}", 50, 170)
        } catch (e: Exception) {
        }
    }
    //                        g.drawString("cameraX :${clientData.getCameraX()}", 50, 100)
    //                        g.drawString("cameraY :${clientData.getCameraY()}", 50, 110)
    Main.mouseEvent?.x?.let { Main.mouseEvent?.y?.let { it1 -> g.drawRect(it, it1, 5, 5) } }
}