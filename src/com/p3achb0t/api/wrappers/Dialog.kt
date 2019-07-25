package com.p3achb0t.api.wrappers

import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetID.Companion.DIALOG_PLAYER_GROUP_ID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import kotlin.random.Random

class Dialog {
    companion object {
        private const val PARENT = WidgetID.DIALOG_NPC_GROUP_ID
        private const val CONTINUE = WidgetID.DialogNPC.CONTINUE
        private const val PARENT_BACKUP = DIALOG_PLAYER_GROUP_ID
        private const val CONTINUE_BACKUP = WidgetID.DialogNPC.CONTINUE
        private const val PARENT_DIALOG_OPTIONS = WidgetID.DIALOG_OPTION_GROUP_ID
        private const val PARENT_BACKUP_2 = 229
        private const val CONTINUE_BACKUP_2 = 2


        fun isDialogUp(): Boolean {
            return getDialogContinue().widget != null
        }

        fun getDialogContinue(): WidgetItem {
            var dialog = WidgetItem(Widgets.find(PARENT, CONTINUE))
            if (dialog.widget == null || (dialog.widget != null && !dialog.containsText("continue"))) {
                dialog = WidgetItem(Widgets.find(PARENT_BACKUP, CONTINUE_BACKUP))
                if (dialog.widget == null || (dialog.widget != null && !dialog.containsText("continue"))) {
                    dialog = WidgetItem(Widgets.find(PARENT_BACKUP_2, CONTINUE_BACKUP_2))
                }
            }
            return dialog
        }

        suspend fun continueDialog() {
            println("")
            val dialog = getDialogContinue()
            dialog.interact("continue")

        }

        suspend fun selectionOption(action: String) {
            var dialog = WidgetItem(Widgets.find(PARENT_DIALOG_OPTIONS, 1))
            // Options are in children but not index zero
        }

        suspend fun selectRandomOption() {
            val dialog = WidgetItem(Widgets.find(PARENT_DIALOG_OPTIONS, 1))
            val childrenSize = dialog.widget?.getChildren()?.size ?: 0
            if (childrenSize == 0) return
            val randOptionIndex = Random.nextInt(1, childrenSize)
            WidgetItem(dialog.widget?.getChildren()?.get(randOptionIndex)).click()
        }
    }
}