package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem

class WorldHop(val ctx: Context) {
    companion object {
        enum class World(val region: Int) {
         UK(1),
            USA(0),
            AUS(3),
            GER(7),

        }

    }
    val isLoggedIn: Boolean get() { return GameState.currentState(ctx) == GameState.LOGGED_IN}

    val freeWorldNoTotalRequirement = intArrayOf(301, 308, 316, 326, 335, 379, 380, 382, 383, 384, 393, 394, 397, 398, 399, 417, 418, 425, 426, 430, 431,
            433, 434, 435, 436, 437, 438, 439, 440, 451, 452, 453, 454, 455, 456, 457, 458, 459, 469, 470, 471, 472, 473, 474, 475, 476, 497, 498, 499, 500, 591, 502, 503, 504)

    fun getCurrent(): Int{
        var World = 0
        try {
            if(ctx.client.getWorldsCount() > 0) {
                World = ctx.client.getWorldId()
            }
        } catch (e: Exception) {
            println("World Exception")
        }
        return World
    }

    suspend fun logout() {
        val logoutX = WidgetItem(
                ctx.widgets.find(
                        WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID,
                        WidgetID.ResizableViewportBottomLine.LOGOUT_BUTTON_OVERLAY
                ),
                ctx = ctx
        )
        logoutX.click()
        val logoutButton = WidgetItem(ctx.widgets.find(WidgetID.LOGOUT_PANEL_ID, 12), ctx = ctx)
        logoutButton.click()
    }
}