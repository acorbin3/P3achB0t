package com.p3achb0t.api.painting

import com.p3achb0t.api.Calculations
import com.p3achb0t.api.user_inputs.Camera
import com.p3achb0t.api.wrappers.Bank
import com.p3achb0t.api.wrappers.ClientMode
import com.p3achb0t.api.wrappers.Menu
import com.p3achb0t.api.wrappers.tabs.Tabs
import com.p3achb0t.scripts.TutorialIsland
import com.p3achb0t.ui.Context
import java.awt.Color
import java.awt.Graphics

data class DebugText(val text: String = "", val color: Color = Color.white)

fun drawDebugText(g: Graphics, ctx: Context) {

    val debugText = arrayListOf<DebugText>()
    if (true) {
        g.color = Color.white
        debugText.add(DebugText("Mouse x:${ctx.mouse.ioMouse.getX()} y:${ctx.mouse.ioMouse.getY()}"))
        debugText.add(DebugText("clientData.gameCycle :${ctx.client.getCycle()}"))
        debugText.add(DebugText("Game State:: ${ctx.client.getGameState()}"))
        debugText.add(DebugText("clientData.loginState :${ctx.client.getLoginState()}"))
//        debugText.add(DebugText("Account status :${ctx.client.get__cq_aw()}"))
        debugText.add(DebugText("Camera: x:${Camera(ctx.client).x} y:${Camera(ctx.client).y} z:${Camera(ctx.client).z} pitch:${Camera(ctx.client).pitch} yaw: ${Camera(ctx.client).yaw} angle: ${Camera(ctx.client).angle}"))
        debugText.add(DebugText("OpenTab: ${Tabs(ctx).getOpenTab()?.name}"))
        debugText.add(DebugText("Bank Status: ${Bank(ctx).isOpen()}"))



        try {
            debugText.add(DebugText("Spell: ${ctx.client.getSelectedSpellName()}"))
            debugText.add(DebugText("Animation: ${ctx.client.getLocalPlayer().getSequence()}"))
            debugText.add(DebugText("Mode: ${ClientMode(ctx).getMode().name}"))
            debugText.add(
                DebugText(
                    "LocalPlayer Position: (${ctx.client.getLocalPlayer().getX() / 128},${ctx.client.getLocalPlayer().getY() / 128})" +
                            " RAW: (${ctx.client.getLocalPlayer().getX()},${ctx.client.getLocalPlayer().getY()}"
                )
            )
            debugText.add(DebugText("Base(x,y): (${ctx.client.getBaseX()},${ctx.client.getBaseY()})"))

            val miniMapPlayer = Calculations.worldToMiniMap(
                    ctx.client.getLocalPlayer().getX(),
                    ctx.client.getLocalPlayer().getY(),
                    ctx

            )
            debugText.add(
                DebugText(
                    "localPlayer minimap: (x,y) (${miniMapPlayer.x},${miniMapPlayer.y})" +
                            "Including base(${ctx.client.getLocalPlayer().getX() / 128 + ctx.client.getBaseX()}," +
                            "${ctx.client.getLocalPlayer().getY() / 128 + ctx.client.getBaseY()})  " +
                            "mapAngle: ${ctx.client.getCamAngleY()}"
                )
            )
            debugText.add(DebugText("Tutorial Island % Complete: ${(TutorialIsland.getPercentComplete(ctx) * 100)}"))
            debugText.add(DebugText("Zoom: ${ctx.client.getViewportZoom()}"))
            debugText.add(DebugText(Menu(ctx.client).getHoverAction()))
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