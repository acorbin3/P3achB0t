package com.p3achb0t.scripts

import com.p3achb0t.api.Calculations
import com.p3achb0t.api.Utils
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.wrappers.*
import com.p3achb0t.api.wrappers.tabs.Inventory
import com.p3achb0t.api.wrappers.tabs.Tabs
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import com.p3achb0t.hook_interfaces.Widget
import kotlinx.coroutines.delay
import kotlin.random.Random


private val SHRIMP_ID = 2514
private val LOGS_ID_2511 = 2511

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
            jobs.add(OpenDoorFromFirstBuilding())
            jobs.add(MoveToFishingSpot())
            jobs.add(TalkToSurvivalExpertFirstTime())
            jobs.add(CatchSomeShrimp())
            jobs.add(ClickSkillsTab())
            jobs.add(TalkToSurvivalGuideAfterSkillsTab())
            jobs.add(ChopTree())
            jobs.add(LightLog())
            jobs.add(CookShrimp())
            jobs.add(OpenGateAfterFishing())
            jobs.add(MoveToKitchen())
            jobs.add(TalkToMasterChef())
            jobs.add(MakeDough())
            isInititilized = true
        }

        suspend fun run() {
            if (!isInititilized) init()
            jobs.forEach {
                if (it.isValidToRun()) it.execute()
            }
        }

        fun getPercentComplete(): Double {
            // widget for progress 614,18
            val complete = WidgetItem(Widgets.find(614, 18)).widget?.getWidth()?.toDouble() ?: 0.0
            //widget for total 614, 17
            val total = WidgetItem(Widgets.find(614, 17)).widget?.getWidth()?.toDouble() ?: 0.0

            return (complete / total)
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
                delay(Random.nextLong(2200, 5550))

                Keyboard.sendKeys(names.random(), sendReturn = true)
                delay(Random.nextLong(2200, 5550))

                // If not a valid name then random name in the follow selections 558,(14,15,16)
                // Once picked It should say available in 558,12
                val validName = WidgetItem(Widgets.find(558, 12))
                if (validName.widget?.getText()?.toLowerCase()?.contains("great!")!!) {
                    println("Found Valid name!")
                } else {
                    val rand = Random.nextInt(14, 16)
                    val selectRandomName = WidgetItem(Widgets.find(558, rand))
                    selectRandomName.click()
                    delay(Random.nextLong(2200, 5550))
                }
                //Pick set name in 558,18
                val pickName = WidgetItem(Widgets.find(558, 18))
                pickName.click()
                delay(Random.nextLong(2200, 5550))
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
                val randomNumberOfChanges = Random.nextInt(4, 35)
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
                return findTextInGuideBox(text)
            }

            override suspend fun execute() {
                println("Time to interact with Gielinor Guide")
                val gielinorGuide = NPCs.findNpc("Gielinor Guide")[0]
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
                return findTextInGuideBox(text)
            }

            override suspend fun execute() {
                println("Time to interact with Gielinor Guide")
                val gielinorGuide = NPCs.findNpc("Gielinor Guide")[0]
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

        class OpenDoorFromFirstBuilding : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "time to meet your first instructor"
                return findTextInGuideBox(text)

            }

            override suspend fun execute() {
                println("START: Opening door and walking to fishing spot")
                // Get doors, find one at location(3098,3107), and open it
                val gameObjects = GameObjects.find(9398)
                val doorLocation = Tile(3098, 3107)
                gameObjects.forEach {
                    if (it.getGlobalLocation().x == doorLocation.x && it.getGlobalLocation().y == doorLocation.y) {
                        it.interact("Open")
                        //Wait till here Tile(3098,3107)
                        Utils.waitFor(4, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return Players.getLocal().getGlobalLocation() == Tile(3098, 3107)
                            }
                        })
                    }
                }
            }

        }

        class MoveToFishingSpot : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "Follow the path to find the next instructor"
                val chatBox = WidgetItem(Widgets.find(263, 1))
                val doorLocation = Tile(3098, 3107)
                val playerGlobalLoc = Players.getLocal().getGlobalLocation()

                return chatBox.containsText(text) && (playerGlobalLoc.x == doorLocation.x && playerGlobalLoc.y == doorLocation.y)
            }

            override suspend fun execute() {
                val path = arrayListOf(Tile(3098, 3107), Tile(3103, 3103), Tile(3102, 3095))
                Walking.walkPath(path)
                println("COMPLETE : Opening door and walking to fishing spot")
            }
        }

        class TalkToSurvivalExpertFirstTime : Job() {
            override suspend fun isValidToRun(): Boolean {
                val survivalExpert = NPCs.findNpc(8503)
                val text = "Follow the path to find the next instructor"
                val chatBox = WidgetItem(Widgets.find(263, 1))
                return chatBox.containsText(text) && survivalExpert.size > 0 && survivalExpert[0].isOnScreen()
            }

            override suspend fun execute() {
                val survivalExpert = NPCs.findNpc(8503)
                survivalExpert[0].talkTo()
                // WAit till the continue is avaliable
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Dialog.isDialogUp()
                    }
                })
                delay(Random.nextLong(3000, 5000))

                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()

                var tabFlashing = false
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        tabFlashing = Tabs.isTabFlashing(Tabs.Tab_Types.Inventory)
                        return tabFlashing
                    }
                })

                if (tabFlashing) {
                    Tabs.openTab(Tabs.Tab_Types.Inventory)
                }
            }

        }

        class CatchSomeShrimp : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "catch some shrimp"
                return findTextInGuideBox(text)
            }

            override suspend fun execute() {
                catchShrimp()

                if (Tabs.isTabFlashing(Tabs.Tab_Types.Skills)) {
                    Tabs.openTab(Tabs.Tab_Types.Skills)
                }
            }
        }

        private suspend fun catchShrimp() {
            val shrimps = NPCs.findNpc(3317)
            shrimps[0].interact("Net")
            // Wait till shrimp is in Inventory
            Utils.waitFor(10, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Inventory.getCount(SHRIMP_ID) > 0
                }
            })
        }

        class ClickSkillsTab : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "on the flashing bar graph icon near the inventory"
                return findTextInGuideBox(text)
            }

            override suspend fun execute() {
                Tabs.openTab(Tabs.Tab_Types.Skills)
            }
        }

        class TalkToSurvivalGuideAfterSkillsTab : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "this menu you can view your skills."
                return findTextInGuideBox(text)
            }

            override suspend fun execute() {
                val survivalExpert = NPCs.findNpc(8503)
                survivalExpert[0].talkTo()
                // WAit till the continue is avaliable
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Dialog.isDialogUp()
                    }
                })
                delay(Random.nextLong(3000, 5000))

                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()

            }

        }

        class ChopTree : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "time to cook your shrimp. However, you require"
                return findTextInGuideBox(text)
            }

            override suspend fun execute() {
                chopTree()

                Dialog.continueDialog()


            }

        }

        private suspend fun chopTree() {
            val trees = GameObjects.find(9730, sortDistance = true)
            // Should be more than 4, lets pick a random one between 1 and 4
            trees[Random.nextInt(0, 3)].interact("Chop")

            // Wait till we get a log in the invetory.
            Utils.waitFor(4, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Inventory.getCount(LOGS_ID_2511) > 0
                }
            })
        }

        class LightLog : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "that you have some logs, it's time"
                return findTextInGuideBox(text)
            }

            override suspend fun execute() {
                // Use tinderbox(590) with logs(2511)
                lightFire()
            }

        }

        private suspend fun lightFire() {
            Inventory.open()
            Inventory.getItem(590)?.click()
            Inventory.getItem(LOGS_ID_2511)?.click()
            delay(Random.nextLong(2500, 4500))
            //Wait till hes not doing anything which should mean fire has been made
            Utils.waitFor(4, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Players.getLocal().player.getAnimation() == -1
                }
            })
        }

        class CookShrimp : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "Now it's time to get cooking."
                return findTextInGuideBox(text)
            }

            override suspend fun execute() {
                // Check to make sure we have shrimp, If not go fish for them
                if (Inventory.getCount(SHRIMP_ID) == 0) {
                    catchShrimp()
                }

                var fires = GameObjects.find(26185, sortDistance = true)
                //No fire & no logs
                if (fires.size == 0 && Inventory.getCount(LOGS_ID_2511) == 0) {
                    chopTree()
                }

                //If no fire && have logs, light a fire
                fires = GameObjects.find(26185, sortDistance = true)
                if (fires.size == 0 && Inventory.getCount(LOGS_ID_2511) > 0) {
                    lightFire()
                }

                // Check if there is a fire cook the shrimp
                fires = GameObjects.find(26185, sortDistance = true)
                if (fires.size > 0) {
                    Inventory.open()
                    Inventory.getItem(SHRIMP_ID)?.click()
                    // The fire is an animated object so it thows a NPE when trying to interacte with model.
                    if (fires[0].gameObject != null) {
                        val point = Calculations.worldToScreen(
                            fires[0].gameObject!!.getX(),
                            fires[0].gameObject!!.getY(),
                            0
                        )
                        Interact.interact(point, "Use")
                    }

                    //Wait till idle
                    Utils.waitFor(4, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return Players.getLocal().player.getAnimation() == -1
                        }
                    })
                    Dialog.continueDialog()
                }
            }
        }

        class OpenGateAfterFishing : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "Well done, you've just cooked your first meal!"
                return findTextInGuideBox(text)
            }

            override suspend fun execute() {
                println("START: Going to open gate")
                // Open gate at 3090,3092
                val gateTile = Tile(3090, 3092, 0)
                println("Onscreen? ${gateTile.isOnScreen()}")
                if (gateTile.distanceTo() > 5) {
                    gateTile.clickOnMiniMap()
                    Utils.waitFor(10, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return gateTile.distanceTo() < 5
                        }
                    })
                }

                //Open gate at 9708 or 9470
                val gateIDs = arrayOf(9708, 9470)
                val gates = GameObjects.find(gateIDs.random(), sortDistance = true)
                if (gates.size > 0) {
                    gates[0].interact("Open")
                }
                println("Complete: Going to open gate")
            }

        }

        class MoveToKitchen : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "Follow the path until you get to the door with the yellow arrow above it."
                val percentComplete = getPercentComplete()
                return findTextInGuideBox(text) && percentComplete == .196875
            }

            override suspend fun execute() {
                val tile = Tile(3079, 3084, 0)
                if (tile.distanceTo() > 5) {
                    tile.clickOnMiniMap()
                    Utils.waitFor(10, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return tile.distanceTo() < 5
                        }
                    })
                }

                val gameObjects = GameObjects.find(9709, sortDistance = true)
                if (gameObjects.size > 0) {
                    gameObjects[0].interact("Open")
                }
            }
        }

        class TalkToMasterChef : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "Talk to the chef indicated"
                return findTextInGuideBox(text)
            }

            override suspend fun execute() {
                NPCs.findNpc(3305)[0].talkTo()

                delay(Random.nextLong(3000, 5000))

                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()
            }
        }

        class MakeDough : Job() {
            override suspend fun isValidToRun(): Boolean {
                val text = "This is the base for many meals"
                return findTextInGuideBox(text)
            }

            override suspend fun execute() {
                // Mix water(1929) and flower(2516)
                Inventory.getItem(1929)?.click()
                Inventory.getItem(2516)?.click()
                delay(Random.nextLong(1250, 1650))
                Dialog.continueDialog()


            }

        }

        private fun findTextInGuideBox(text: String): Boolean {
            val chatBox = WidgetItem(Widgets.find(263, 1))
            val textFound = chatBox.containsText(text)
            return textFound
        }






    }
}