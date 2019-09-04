package com.p3achb0t.api.wrappers

import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets

class Logout(val client: com.p3achb0t._runestar_interfaces.Client) {
    suspend fun logout() {
        val logoutX = WidgetItem(
                Widgets.find(
                        client,
                        WidgetID.RESIZABLE_VIEWPORT_BOTTOM_LINE_GROUP_ID,
                        WidgetID.ResizableViewportBottomLine.LOGOUT_BUTTON_OVERLAY
                ),
                client =client
        )
        logoutX.click()
        val logoutButton = WidgetItem(Widgets.find(client, WidgetID.LOGOUT_PANEL_ID, 12), client =client)
        logoutButton.click()
    }
}