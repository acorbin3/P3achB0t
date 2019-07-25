package com.p3achb0t.scripts

import com.p3achb0t.api.Utils
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.wrappers.Dialog
import com.p3achb0t.api.wrappers.NPC
import com.p3achb0t.api.wrappers.tabs.Tabs
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import com.p3achb0t.hook_interfaces.Widget
import kotlinx.coroutines.delay
import kotlin.random.Random

class TutorialIsland {
    companion object {

        val names = arrayListOf(
            "starlenee", "FsckingSith", "loredanalonero", "MooseMuffin", "BossOfAwesome",
            "fishlet1233", "FrozenSprinkles", "aalinizi"
        )

        var isInititilized = false
        val jobs = ArrayList<Job>()
        fun init() {
            jobs.add(PickName())
            jobs.add(SelectCharOutfit())
            jobs.add(ChatWithGielinorGuide())
            jobs.add(OpenOptions())
            jobs.add(FinalChatWithGielinor())
            isInititilized = true
        }

        suspend fun run() {
            if (!isInititilized) init()
            jobs.forEach {
                if (it.isValidToRun()) it.execute()
            }
        }

        class PickName : Job() {
            override suspend fun isValidToRun(): Boolean {
                return Widgets.isWidgetAvaliable(558, 0)
            }

            override suspend fun execute() {
                println("Picking name")
                //Name widget to click into and type a name 558,7
                val nameEntry = WidgetItem(Widgets.find(558, 7))
                nameEntry.click()
                delay(Random.nextLong(200, 550))

                Keyboard.sendKeys(names.random(), sendReturn = true)
                delay(Random.nextLong(200, 550))

                // If not a valid name then random name in the follow selections 558,(14,15,16)
                // Once picked It should say available in 558,12
                val validName = WidgetItem(Widgets.find(558, 12))
                if (validName.widget?.getText()?.toLowerCase()?.contains("great!")!!) {
                    println("Found Valid name!")
                } else {
                    val rand = Random.nextInt(14, 16)
                    val selectRandomName = WidgetItem(Widgets.find(558, rand))
                    selectRandomName.click()
                    delay(Random.nextLong(200, 550))
                }
                //Pick set name in 558,18
                val pickName = WidgetItem(Widgets.find(558, 18))
                pickName.click()
                delay(Random.nextLong(200, 550))
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return !Widgets.isWidgetAvaliable(558, 0)
                    }
                })
                println("Picking name complete")


            }

        }

        class SelectCharOutfit : Job() {
            override suspend fun isValidToRun(): Boolean {
                return Widgets.isWidgetAvaliable(269, 0)
            }

            override suspend fun execute() {
                val randomNumberOfChanges = Random.nextInt(4, 15)
                println("Making $randomNumberOfChanges of changes")
                for (i in 0..randomNumberOfChanges) {
                    val column = Random.nextInt(1, 5)
                    val widgetIndex = Widget.WidgetIndex("269", "0")
                    when (column) {
                        1 -> widgetIndex.childID = (106..112).random().toString()
                        2 -> widgetIndex.childID = (113..119).random().toString()
                        3 -> widgetIndex.childID = arrayListOf(125, 124, 123, 122, 105).random().toString()
                        4 -> widgetIndex.childID = arrayListOf(131, 130, 129, 127, 121).random().toString()
                    }

                    WidgetItem(Widgets.find(widgetIndex.parentID.toInt(), widgetIndex.childID.toInt())).click()
                    delay(Random.nextLong(250, 650))
                }
                //Randomly pick if you are going to be afemale
                if (Random.nextBoolean()) {
                    println("Picking Female")
                    WidgetItem(Widgets.find(269, 139)).click()
                    delay(Random.nextLong(250, 650))
                } else {
                    println("Leaving male")
                }
                //select accept
                WidgetItem(Widgets.find(269, 99)).click()
                delay(Random.nextLong(1250, 2650))
                println("Completed Character outfit")
            }

        }

        class ChatWithGielinorGuide : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "Before you begin, have a read"
                val chatBox = WidgetItem(Widgets.find(263, 1))
                return chatBox.containsText(text)
            }

            override suspend fun execute() {
                println("Time to interact with Gielinor Guide")
                val gielinorGuide = NPC.findNpc("Gielinor Guide")[0]
                gielinorGuide.interact("Talk-to")
                Utils.waitFor(5, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Dialog.isDialogUp()
                    }
                })
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.selectRandomOption()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                println("Interact with Gielinor Guide Complete")
            }

        }

        class OpenOptions : Job() {
            override suspend fun isValidToRun(): Boolean {
                return Tabs.isTabFlashing(Tabs.Tab_Types.Options)
            }

            override suspend fun execute() {
                Tabs.openTab(Tabs.Tab_Types.Options)
                delay(Random.nextLong(1250, 1650))
            }

        }

        class FinalChatWithGielinor : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "On the side"
                val chatBox = WidgetItem(Widgets.find(263, 1))
                return chatBox.containsText(text)
            }

            override suspend fun execute() {
                println("Time to interact with Gielinor Guide")
                val gielinorGuide = NPC.findNpc("Gielinor Guide")[0]
                gielinorGuide.interact("Talk-to")
                Utils.waitFor(5, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Dialog.isDialogUp()
                    }
                })
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                println("Finished final chat with Gielinor")
            }

        }


        //Talk to Gielinor Guide(3308)
        // 4 continues
        // 1 continue at (229,2)
        // Pick option if experience, its been a while, or bran new
        //Continue x3
        // Click options tab

        // Talk to Gielinor Guide(3308)
        // Continue x2
        // Open Doors at location(3098,3107)
        // Tile path (3098,3107),(3103,3103),(3102,3095)
        // GO down to fishing lake around (6848,4800
        // "Talk-to" to "Survival Expert"(8503)
        // Continue 3x
        // Open inventory
        // Click fishing spot ID: 3317
        // Wait till you have a Dialog(10 sec?) This might be optional
        //Click continue
        //Open up Skills
        // "Talk-to" to "Survival Expert"(8503)
        // Continue 3x
        // Chop tree 9730
        // Click contune after you get a log




    }
}