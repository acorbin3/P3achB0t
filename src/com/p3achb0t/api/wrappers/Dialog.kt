package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.utils.Logging
import com.p3achb0t.api.utils.Time.sleep
import com.p3achb0t.api.wrappers.utils.Timer
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetID.Companion.DIALOG_PLAYER_GROUP_ID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import kotlin.random.Random

class Dialog(val ctx: Context) : Logging() {
    companion object {
        private const val PARENT = WidgetID.DIALOG_NPC_GROUP_ID
        private const val CONTINUE = WidgetID.DialogNPC.CONTINUE
        private const val PARENT_BACKUP = DIALOG_PLAYER_GROUP_ID
        private const val CONTINUE_BACKUP = WidgetID.DialogNPC.CONTINUE
        const val PARENT_DIALOG_OPTIONS = WidgetID.DIALOG_OPTION_GROUP_ID
        private const val PARENT_BACKUP_2 = 229
        private const val CONTINUE_BACKUP_2 = 2
        private const val PARENT_BACKUP_3 = 193
        private const val CONTINUE_BACKUP_3 = 0
        private const val PARENT_BACKUP_4 = 11
        private const val CONTINUE_BACKUP_4 = 4
        private const val PARENT_BACKUP_5 = 633
        private const val CONTINUE_BACKUP_5 = 0
    }

    enum class ChatState {
        CLOSED, PLAYER_CHAT, NPC_CHAT, ITEM_CHAT, OPTIONS_CHAT, SPECIAL, MODEL, SPRITE
    }


    fun chatState(): ChatState {

        if(ctx.widgets.find(11, 0) != null){
            return ChatState.ITEM_CHAT
        }else if(ctx.widgets.find(217, 0) != null){
            return ChatState.PLAYER_CHAT
        }else if(ctx.widgets.find(231, 0) != null){
            return ChatState.NPC_CHAT
        }else if(ctx.widgets.find(219, 0) != null){
            return ChatState.OPTIONS_CHAT
        }else if(ctx.widgets.find(193, 0) != null){
            return ChatState.SPECIAL
        }else if(ctx.widgets.find(229, 0) != null){
            return ChatState.MODEL
        }else if(ctx.widgets.find(633, 0) != null){
            return ChatState.SPRITE
        }else{
            return ChatState.CLOSED
        }


//        val mainWidget = ctx.widgets.find(162, 562)
//        return when (mainWidget?.getChildren()?.first()?.getParentId()) {
//            11 -> ChatState.ITEM_CHAT
//            217 -> ChatState.PLAYER_CHAT
//            231 -> ChatState.NPC_CHAT
//            219 -> ChatState.OPTIONS_CHAT
//            193 -> ChatState.SPECIAL
//            229 -> ChatState.MODEL
//            633 -> ChatState.SPRITE
//            else -> ChatState.CLOSED
//
//        }
    }

    suspend fun chat(vararg options: String?) {
        Utils.sleepUntil({ chatState() != ChatState.CLOSED })
        for (option in options) {
            continueChats()
            selectionOption(option ?: "")
        }
        continueChats()
    }

    suspend fun continueChats() {
        while (chatState() != ChatState.CLOSED && chatState() != ChatState.OPTIONS_CHAT) {
            sleep(600)
            if (chatState() != ChatState.CLOSED && chatState() != ChatState.OPTIONS_CHAT) {
                continueChat()
            }
            sleep(400)
        }
    }

    suspend fun continueChat() {
        when (chatState()) {
            ChatState.CLOSED -> println("There is not chat")
            ChatState.OPTIONS_CHAT -> println("can't continue, this is an options chat");
            ChatState.PLAYER_CHAT -> WidgetItem(ctx.widgets.find(217, 3), ctx = ctx).click()
            ChatState.NPC_CHAT -> WidgetItem(ctx.widgets.find(231, 3), ctx = ctx).click()
            ChatState.ITEM_CHAT -> WidgetItem(ctx.widgets.find(11, 4), ctx = ctx).click()
            ChatState.SPECIAL -> WidgetItem(ctx.widgets.find(193, 0)?.getChildren()?.get(1), ctx = ctx).click()
            ChatState.MODEL -> WidgetItem(ctx.widgets.find(229, 2), ctx = ctx).click()
            ChatState.SPRITE -> WidgetItem(ctx.widgets.find(633, 0)?.getChildren()?.get(1), ctx = ctx).click()
        }
    }

    fun isDialogUp(): Boolean {
//        val cycleDiff = abs(getDialogContinue().widget?.getCycle()?:0 - ctx.client.getCycle())
//        logger.info("Cycle diff: $cycleDiff. ID: ${getDialogContinue().widget?.getId()}. " +
//                "wCycle ${getDialogContinue().widget?.getCycle()} ClienctCycle ${ctx.client.getCycle()} ")

        return getDialogContinue().widget != null

    }

    fun isContinueAvailable(): Boolean {
        if (isDialogUp()) {
            return getDialogContinue().containsText("continue") || getDialogContinue().containsText("Please wait")
        } else return false
    }

    fun isInCutscene(): Boolean {
        return ctx.vars.getVarbit(542) == 1
                && ctx.vars.getVarbit(4606) == 1
    }

    fun isInDialogue(): Boolean {
        return (ctx.vars.getVarp(1021) and 1024 != 0
                || ctx.vars.getVarp(1021) and 192 != 0
//                || ctx.client.get__client_mq() != null
//                || ctx.client.getWidgets().isPleaseWait()
                || getDialogContinue().widget != null)
    }

    fun getDialogContinue(): WidgetItem {
        var dialog = WidgetItem(ctx.widgets.find(PARENT, CONTINUE), ctx = ctx)
        if (dialog.widget == null || (dialog.widget != null && !dialog.containsText("continue"))) {
            dialog = WidgetItem(ctx.widgets.find(PARENT_BACKUP, CONTINUE_BACKUP), ctx = ctx)
            if (dialog.widget == null || (dialog.widget != null && !dialog.containsText("continue"))) {
                dialog = WidgetItem(ctx.widgets.find(PARENT_BACKUP_2, CONTINUE_BACKUP_2), ctx = ctx)
                if (dialog.widget == null || (dialog.widget != null && !dialog.containsText("continue"))) {
                    dialog = WidgetItem(ctx.widgets.find(PARENT_BACKUP_3, CONTINUE_BACKUP_3), ctx = ctx)
                    if (dialog.widget == null || (dialog.widget != null && !dialog.containsText("continue"))) {
                        dialog = WidgetItem(ctx.widgets.find(PARENT_BACKUP_4, CONTINUE_BACKUP_4), ctx = ctx)
                        if (dialog.widget == null || (dialog.widget != null && !dialog.containsText("continue"))) {
                            dialog = WidgetItem(ctx.widgets.find(PARENT_BACKUP_5, CONTINUE_BACKUP_5), ctx = ctx)
                        }
                    }
                }
            }
        }

        return dialog
    }

    suspend fun continueDialog(sleep: Boolean = true) {
        val time = 20 //20 seconds
        val t = Timer(Random.nextLong((time * 1000).toLong(), ((time + 2) * 1000).toLong()))
        while (isContinueAvailable() && t.isRunning()) {
            doConversation(sleep)
        }
    }


    private suspend fun doConversation(sleep: Boolean) {
        val dialog = getDialogContinue()
        if (dialog.containsText("continue", false)) {
            logger.info("Sending space bar")
//            dialog.click()
            ctx.keyboard.sendKeys(" ")
            delay(Random.nextLong(200, 350))

        } else if (dialog.containsText("continue")) {
            //NEed to find children
            dialog.widget?.getChildren()?.iterator()?.forEach {
                if (WidgetItem(it, ctx = ctx).containsText("continue")) {
//                    WidgetItem(it, ctx = ctx).click()
                    ctx.keyboard.sendKeys(" ")
                    delay(Random.nextLong(200, 350))
                }
            }
        } else if (isContinueAvailable()) {
            ctx.keyboard.sendKeys(" ")
            delay(Random.nextLong(200, 350))
        } else {
            logger.info("Sending spacebar even though it didnt find anything")
            ctx.keyboard.sendKeys(" ")
        }

        //TODO - add a smart sleep based on the number of words in the continue dialog
        if (sleep)//&& getDialogContinue().containsText("continue"))
            delay(Random.nextLong(650, 750))
    }

    suspend fun selectionOption(action: String) {
        val dialog = WidgetItem(ctx.widgets.find(PARENT_DIALOG_OPTIONS, 1), ctx = ctx)
        // Options are in children but not index zero
        dialog.widget?.getChildren()?.iterator()?.forEach {
            if (it.getText().contains(action)) {
                WidgetItem(it, ctx = ctx).click()
                delay(Random.nextLong(1500, 2500))
            }
        }
    }

    suspend fun selecTeleportOption(action: String) {
        val dialog = WidgetItem(ctx.widgets.find(219, 1), ctx = ctx)
        // Options are in children but not index zero
        dialog.widget?.getChildren()?.iterator()?.forEach {
            if (it.getText().contains(action)) {
                WidgetItem(it, ctx = ctx).click()
                delay(Random.nextLong(1500, 2500))
            }
        }
    }

    fun isDialogOptionsOpen(): Boolean {
        return ctx.widgets.isWidgetAvaliable(219, 1)
                && ctx.widgets.find(219, 1)?.getWidth() ?: 0 > 0
    }

    fun isSpiritDialogOpen(): Boolean {
        return ctx.widgets.isWidgetAvaliable(187, 3)
    }


    suspend fun selectRandomOption() {
        val dialog = WidgetItem(ctx.widgets.find(PARENT_DIALOG_OPTIONS, 1), ctx = ctx)
        val childrenSize = dialog.widget?.getChildren()?.size ?: 0
        if (childrenSize == 0) return
        val randOptionIndex = Random.nextInt(1, childrenSize)
        WidgetItem(dialog.widget?.getChildren()?.get(randOptionIndex), ctx = ctx).click()
    }
}