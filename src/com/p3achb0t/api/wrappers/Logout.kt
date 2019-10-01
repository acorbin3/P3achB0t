package com.p3achb0t.api.wrappers

import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import com.p3achb0t.api.Context

class Logout(val ctx: Context) {
    suspend fun logout() {
        val logoutX = WidgetItem(
                Widgets.find(
                        ctx,
                        WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID,
                        WidgetID.ResizableViewportBottomLine.LOGOUT_BUTTON_OVERLAY
                ),
                ctx = ctx
        )
        logoutX.click()
        val logoutButton = WidgetItem(Widgets.find(ctx, WidgetID.LOGOUT_PANEL_ID, 12), ctx = ctx)
        logoutButton.click()
    }
}