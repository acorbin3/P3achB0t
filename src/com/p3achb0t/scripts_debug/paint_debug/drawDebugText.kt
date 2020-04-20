package com.p3achb0t.scripts_debug.paint_debug

import com.p3achb0t.api.Context
import com.p3achb0t.api.LoginResponse
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.widgets.WidgetID
import java.awt.Color
import java.awt.Graphics


data class DebugText(val text: String = "", val color: Color = Color.white)

fun drawDebugText(g: Graphics, ctx: Context) {

    val debugText = arrayListOf<DebugText>()
    if (true) {

        g.color = Color.white
        debugText.add(DebugText("hasFocus? x: ${ctx.client.getHasFocus()}"))
        debugText.add(DebugText("Mouse x: ${ctx.mouse.getX()} y:${ctx.mouse.getY()}"))
        debugText.add(DebugText("clientData.gameCycle :${ctx.client.getCycle()}"))
        debugText.add(DebugText("Game State:: ${ctx.client.getGameState()}"))
        debugText.add(DebugText("clientData.loginState :${ctx.client.getLoginState()}"))
        debugText.add(DebugText("fps :${PaintDebug.fps}"))

        val heapSize = Runtime.getRuntime().totalMemory()

        // Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.

        // Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
        val heapMaxSize = Runtime.getRuntime().maxMemory()

        // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.

        // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
        val heapFreeSize = Runtime.getRuntime().freeMemory()

        debugText.add(DebugText("totalmemory :${formatSize(heapSize)}"))
        debugText.add(DebugText("maxmemory :${formatSize(heapMaxSize)}"))
        debugText.add(DebugText("freemmeory :${formatSize(heapFreeSize)}"))
        debugText.add(DebugText("freemmeory :${heapFreeSize}"))
        debugText.add(DebugText("Loginresponse :${LoginResponse.getLoginResponse(ctx)}"))

//        debugText.add(DebugText("antifire timer :${Utils.getElapsedSeconds(VorkathMain.Antifiretimer.time)}"))
//        debugText.add(DebugText("divine pot timer :${Utils.getElapsedSeconds(VorkathMain.Divinepottimer.time)}"))
//        debugText.add(DebugText("Account status :${ctx.client.get__cq_aw()}"))

        try {
            if(ctx.client.getGameState() == 30) {
                debugText.add(DebugText("Camera: x:${ctx.camera.x} y:${ctx.camera.y} z:${ctx.camera.z} pitch:${ctx.camera.pitch} yaw: ${ctx.camera.yaw} angle: ${ctx.camera.angle}"))
                debugText.add(DebugText("World: ${ctx.client.getWorldId()}  Host: ${ctx.client.getWorldHost()}"))

//                debugText.add(DebugText("OpenTab: ${ctx.tabs.getOpenTab()?.name}"))
//                debugText.add(DebugText("Bank Status: ${ctx.bank.isOpen()}"))
//                debugText.add(DebugText("Run Enabled: ${ctx.vars.getVarp(173)}"))
//                debugText.add(DebugText("Prayer Enabled(4101): ${ctx.vars.getVarbit(4101)}"))
//                debugText.add(DebugText("quick Prayer Enabled(4103): ${ctx.vars.getVarbit(4103)}"))
//                debugText.add(DebugText("Spell: ${ctx.client.getSelectedSpellName()}"))
//                debugText.add(DebugText("Animation: ${ctx.client.getLocalPlayer().getSequence()}"))
//                debugText.add(DebugText("Mode: ${ctx.clientMode.getMode().name}"))
//                debugText.add(DebugText("Bank search Text: ${text}"))
//                debugText.add(DebugText("idle timer: ${Utils.getElapsedSeconds(VorkathMain.IdleTimer.time)}"))
                debugText.add(
                        DebugText(
                                "LocalPlayer Position: (${ctx.client.getLocalPlayer().getX() / 128},${ctx.client.getLocalPlayer().getY() / 128})" +
                                        " RAW: (${ctx.client.getLocalPlayer().getX()},${ctx.client.getLocalPlayer().getY()}"
                        )
                )
                debugText.add(DebugText("Base(x,y): (${ctx.client.getBaseX()},${ctx.client.getBaseY()}) Plane: ${ctx.client.getPlane()}"))

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
//                debugText.add(DebugText("Tutorial Island % Complete: ${(TutorialIsland.getPercentComplete(ctx) * 100)}"))
//                debugText.add(DebugText("Zoom: ${ctx.client.getViewportZoom()}"))
//                debugText.add(DebugText(ctx.menu.getHoverAction()))
//                debugText.add(DebugText("Cooks assistant var: " + Quest.COOKS_ASSISTANT.getVar(ctx).toString()))



//                var statsStr = ""
//                for(stat in Stats.Skill.values()){
//
//                    statsStr+= stat.name + ":" + ctx.stats.currentLevel(stat) + " "
//                }
//                debugText.add(DebugText(statsStr))
//            debugText.add(DebugText())
            }


        } catch (e: Exception) {
        }
        val x = 50
        var y = 50
        debugText.forEach {
            g.color = it.color
            g.drawString(it.text, x, y)
            y += 20
        }
    }

}

fun formatSize(v: Long): String? {
    if (v < 1024) return "$v B"
    val z = (63 - java.lang.Long.numberOfLeadingZeros(v)) / 10
    return String.format("%.1f %sB", v.toDouble() / (1L shl z * 10), " KMGTPE"[z])
}