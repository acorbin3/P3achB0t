package com.p3achb0t.api.wrappers.quests

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.widgets.WidgetID

class QuestData(val ctx: Context) {

    fun getState(quest: Quest): QuestState{
        return quest.getState(ctx)
    }

    val questListParentID = WidgetID.QUESTLIST_GROUP_ID
    val questPointsChildID = WidgetID.QuestList.BOX
    fun getQuestPoints(): Int{
        return ctx.widgets.find(questListParentID,questPointsChildID)?.getText()?.split(" ")?.last()?.toInt() ?: 0
    }

}