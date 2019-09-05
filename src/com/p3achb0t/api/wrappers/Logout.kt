package com.p3achb0t.api.wrappers

import com.p3achb0t.api.user_inputs.Mouse
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets

class Logout(val client: com.p3achb0t._runestar_interfaces.Client, val mouse: Mouse) {
    suspend fun logout() {
        val logoutX = WidgetItem(
                Widgets.find(
                        client,
                        WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID,
                        WidgetID.ResizableViewportBottomLine.LOGOUT_BUTTON_OVERLAY
                ),
                client =client,
                mouse = mouse
        )
        logoutX.click()
        val logoutButton = WidgetItem(Widgets.find(client, WidgetID.LOGOUT_PANEL_ID, 12), client =client, mouse = mouse)
        logoutButton.click()
    }
}