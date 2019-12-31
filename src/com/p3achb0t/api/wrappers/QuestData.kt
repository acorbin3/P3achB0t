package com.p3achb0t.api.wrappers

import com.p3achb0t.api.Context
import com.p3achb0t.api.wrappers.widgets.WidgetID

class QuestData(val ctx: Context) {
    enum class F2PQuests(name: String){
        Cooks_Assistant("Cook's Assistant"),
        Demon_Slayer("Demon Slayer"),
        The_Restless_Ghost("The Restless Ghost"),
        Romeo_And_Juliet("Romeo & Juliet"),
        Sheep_Shearer("Sheep Shearer"),
        Shield_of_Arrav("Shield of Arrav"),
        Ernest_the_Chicken("Ernest the Chicken"),
        Vampire_Slayer("Vampire Slayer"),
        Imp_Catcher("Imp Catcher"),
        Prince_Ali_Rescue("Prince Ali Rescue"),
        Dorics_Quest("Doric's Quest"),
        Black_Knights_Fortress("Black Knights' Fortress"),
        Witchs_Potion("Witch's Potion"),
        The_Knights_Sword("The Knight's Sword"),
        Goblin_Diplomacy("Goblin Diplomacy"),
        Pirates_Treasure("Pirate's Treasure"),
        Dragon_Slayer("Dragon Slayer"),
        Rune_Mysteries("Rune Mysteries"),
        Misthalin_Mystery("Misthalin Mystery"),
        The_Corsair_Curse("The Corsair Curse"),
        X_Marks_The_Spot("X Marks the Spot"),
    }
    enum class QuestStatus(val color: Int){
        Not_Started(16711680),  //Red
        In_Progress(0),
        Complete(0);

        companion object{
            fun valueOf(color: Int): QuestStatus = values().find { it.color == color } ?: Not_Started
        }

    }

    //TODO - get latest step of quest
    val questListParentID = WidgetID.QUESTLIST_GROUP_ID
    val questListF2PChildID = WidgetID.QuestList.FREE_CONTAINER
    val questPointsChildID = WidgetID.QuestList.BOX

    fun getQuestStatus(quest: QuestData.F2PQuests): QuestStatus{
        val questList = ctx.widgets.find(questListParentID,questListF2PChildID)?.getChildren()
        questList?.iterator()?.forEach {
            if(it.getText() == quest.name){
                return QuestStatus.valueOf(it.getColor())
            }
        }
        return QuestStatus.Not_Started
    }

    fun getQuestPoints(): Int{
        return ctx.widgets.find(questListParentID,questPointsChildID)?.getText()?.split(" ")?.last()?.toInt() ?: 0
    }

}