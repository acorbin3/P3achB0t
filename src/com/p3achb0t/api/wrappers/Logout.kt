package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem

class Logout(val ctx: Context) {
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