package com.p3achb0t.api.wrappers

import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets

class Logout {
    companion object {
        suspend fun logout() {
            val logoutX = WidgetItem(
                Widgets.find(
                    WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID,
                    WidgetID.ResizableViewportBottomLine.LOGOUT_BUTTON_OVERLAY
                )
            )
            logoutX.click()
            val logoutButton = WidgetItem(Widgets.find(WidgetID.LOGOUT_PANEL_ID, 12))
            logoutButton.click()
        }
    }
}