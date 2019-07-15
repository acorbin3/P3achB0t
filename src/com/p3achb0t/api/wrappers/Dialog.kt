package com.p3achb0t.api.wrappers

import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetID.Companion.DIALOG_PLAYER_GROUP_ID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets

class Dialog {
    companion object {
        private const val PARENT = WidgetID.DIALOG_NPC_GROUP_ID
        private const val CONTINUE = WidgetID.DialogNPC.CONTINUE
        private const val PARENT_BACKUP = DIALOG_PLAYER_GROUP_ID
        private const val CONTINUE_BACKUP = WidgetID.DialogNPC.CONTINUE
        private const val PARENT_DIALOG_OPTIONS = WidgetID.DIALOG_OPTION_GROUP_ID

        fun getDialogContinue(): WidgetItem {
            var dialog = Widgets.find(PARENT, CONTINUE)
            if (dialog == null) {
                dialog = Widgets.find(PARENT_BACKUP, CONTINUE_BACKUP)
            }
            return WidgetItem(dialog)
        }

        suspend fun continueDialog() {
            val dialog = getDialogContinue()
            dialog.interact("Continue")

        }
    }
}