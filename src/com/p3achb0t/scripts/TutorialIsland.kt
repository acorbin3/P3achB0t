package com.p3achb0t.scripts

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.*
import com.p3achb0t.api.user_inputs.Camera
//import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.wrappers.*
import com.p3achb0t.api.wrappers.tabs.Equipment
import com.p3achb0t.api.wrappers.tabs.Inventory
import com.p3achb0t.api.wrappers.tabs.Magic
import com.p3achb0t.api.wrappers.tabs.Tabs
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import kotlinx.coroutines.delay
import kotlin.random.Random


private val SHRIMP_ID = 2514
private val LOGS_ID_2511 = 2511
@ScriptManifest("Quests","TutorialIsland","P3aches")
class TutorialIsland: AbstractScript()  {
    override suspend fun loop() {

        
        run()
        //Delay between 0-50 ms
        delay((Math.random() * 50).toLong())

    }

    override suspend fun start() {
        println("Running Start")
    }

    override suspend fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    var isInititilized = false
    val jobs = ArrayList<Job>()
    fun init() {
        
        jobs.add(PickName(ctx))
        jobs.add(SelectCharOutfit(ctx))
        jobs.add(ChatWithGielinorGuide(ctx))
        jobs.add(OpenOptions(ctx))
        jobs.add(FinalChatWithGielinor(ctx))
        jobs.add(OpenDoorFromFirstBuilding(ctx))
        jobs.add(turnOffRoofsAndSound(ctx))
        jobs.add(MoveToFishingSpot(ctx))
        jobs.add(TalkToSurvivalExpertFirstTime(ctx))
        jobs.add(OpenInvetory(ctx))
        jobs.add(CatchSomeShrimp(ctx))
        jobs.add(ClickSkillsTab(ctx))
        jobs.add(TalkToSurvivalGuideAfterSkillsTab(ctx))
        jobs.add(ChopTree(ctx))
        jobs.add(LightLog(ctx))
        jobs.add(CookShrimp(ctx))
        jobs.add(OpenGateAfterFishing(ctx))
        jobs.add(MoveToKitchen(ctx))
        jobs.add(TalkToMasterChef(ctx))
        jobs.add(MakeDough(ctx))
        jobs.add(MakeBread(ctx))
        jobs.add(ExitKitchen(ctx))
        jobs.add(TurnOnRun(ctx))
        jobs.add(MoveToNextBuilding(ctx))
        jobs.add(TalkToQuestGuide(ctx))
        jobs.add(OpenQuestList(ctx))
        jobs.add(TalkToQuestGuide2ndTime(ctx))
        jobs.add(GoDownToTheCaves(ctx))
        jobs.add(WalkAndTalkToSmitingAndMiningGuide(ctx))
        jobs.add(MineTin(ctx))
        jobs.add(MineCopper(ctx))
        jobs.add(SmeltBronze(ctx))
        jobs.add(TalkToMiningGuideAboutSmiting(ctx))
        jobs.add(MakeBronzeDagger(ctx))
        jobs.add(AfterSmithingMovetoGate(ctx))
        jobs.add(TalkToCombatInstructor(ctx))
        jobs.add(OpenEquipment(ctx))
        jobs.add(OpenEquipmentStats(ctx))
        jobs.add(EquipBronzeDagger(ctx))
        jobs.add(SpeakWithCombatAfterBronzeDaggerEquipt(ctx))
        jobs.add(EquipLongSwordAndShield(ctx))
        jobs.add(OpenCombatTab(ctx))
        jobs.add(GoIntoRatCage(ctx))
        jobs.add(MeleeKillRat(ctx))
        jobs.add(GoTalkToCombatInstructorFor2ndTime(ctx))
        jobs.add(KillRatWithBow(ctx))
        jobs.add(ExitCaves(ctx))
        jobs.add(UseBank(ctx))
        jobs.add(CloseBankAndDoPollBooth(ctx))
        jobs.add(DoPollBooth(ctx))
        jobs.add(ClosePollAndMoveOutOfBank(ctx))
        jobs.add(TalkToAccountManager(ctx))
        jobs.add(OpenAccountManager(ctx))
        jobs.add(ExitAccountManagerRoom(ctx))
        jobs.add(MoveToChapelAndTalkToBrotherBrace(ctx))
        jobs.add(OpenPrayerTab(ctx))
        jobs.add(OpenFriendsTab(ctx))
        jobs.add(ExitChapleHouse(ctx))
        jobs.add(GoToWizardHouseAndSpeakWithWizard(ctx))
        jobs.add(OpenMagicTab(ctx))
        jobs.add(SelectWindStrikeAndAttackChicken(ctx))
        jobs.add(ExitTutIsland(ctx))
        jobs.add(MainlandLogout(ctx))
        isInititilized = true
    }

    suspend fun run() {
        if (!isInititilized) init()
//        if (!LoggingIntoClient.loggedIn) return
        jobs.forEach {
            val chatBox = WidgetItem(Widgets.find(ctx, 263, 1), client = ctx)
            if (it.isValidToRun(chatBox)) {
                println("Running: ${it.javaClass.name}")
                it.execute()
                println("Completed: ${it.javaClass.name}")
            }
        }
    }

    companion object {
        fun getPercentComplete(client: Client): Double {
            // widget for progress 614,18
            val complete = WidgetItem(Widgets.find(client, 614, 18), client = client).widget?.getWidth()?.toDouble() ?: 0.0
            //widget for total 614, 17
            val total = WidgetItem(Widgets.find(client, 614, 17), client = client).widget?.getWidth()?.toDouble() ?: 0.0

            return (complete / total)
        }
    }


    class PickName(client: Client)  : Job(client) {

        val names = arrayListOf(
                "PapaBadass", "randomBJ", "kamalchettiar", "all_negative_", "s0meguy",
                "shouldidivorce", "kisskross", "ccnelson", "w4rf19ht3r", "lili999", "qwerqtwfnhnqufh"
        )
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return Widgets.isWidgetAvaliable(client, 558, 0)
        }

        override suspend fun execute() {
            println("Picking name")
            //Name widget to click into and type a name 558,7
            val nameEntry = WidgetItem(Widgets.find(client, 558, 7), client = client)
            nameEntry.click()
            delay(Random.nextLong(2200, 5550))
            
            //Keyboard.sendKeys(names.random(), sendReturn = true)
            delay(Random.nextLong(2200, 5550))

            // If not a valid name then random name in the follow selections 558,(14,15,16)
            // Once picked It should say available in 558,12
            val validName = WidgetItem(Widgets.find(client, 558, 12), client = client)
            if (validName.widget?.getText()?.toLowerCase()?.contains("great!")!!) {
                println("Found Valid name!")
            } else {
                val rand = Random.nextInt(14, 16)
                val selectRandomName = WidgetItem(Widgets.find(client, 558, rand), client = client)
                selectRandomName.click()
                delay(Random.nextLong(2200, 5550))
            }
            //Pick set name in 558,18
            val pickName = WidgetItem(Widgets.find(client, 558, 18), client = client)
            pickName.click()
            delay(Random.nextLong(2200, 5550))
            Utils.waitFor(4, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return !Widgets.isWidgetAvaliable(client, 558, 0)
                }
            })
            println("Picking name complete")


        }

    }

    class SelectCharOutfit(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return Widgets.isWidgetAvaliable(client, 269, 0)
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
                for (i in 0..Random.nextInt(5)) {
                    WidgetItem(Widgets.find(client, widgetIndex.parentID.toInt(), widgetIndex.childID.toInt()), client = client).click()
                    delay(Random.nextLong(250, 650))
                }
            }
            //Randomly pick if you are going to be afemale
            if (Random.nextBoolean()) {
                println("Picking Female")
                WidgetItem(Widgets.find(client, 269, 139), client = client).click()
                delay(Random.nextLong(250, 650))
            } else {
                println("Leaving male")
            }
            //select accept
            WidgetItem(Widgets.find(client, 269, 99), client = client).click()
            delay(Random.nextLong(1250, 2650))
            println("Completed Character outfit")
        }

    }

    class ChatWithGielinorGuide(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Before you begin, have a read"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            println("Time to interact with Gielinor Guide")
            val gielinorGuide = NPCs(client).findNpc("Gielinor Guide")[0]
            gielinorGuide.interact("Talk-to")
            Utils.waitFor(5, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Dialog(client).isDialogUp()
                }
            })
            Dialog(client).continueDialog()
            Dialog(client).selectRandomOption()
            delay(Random.nextLong(1250, 1650))
            Dialog(client).continueDialog()
            println("Interact with Gielinor Guide Complete")
        }

    }

    class OpenOptions(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return Tabs(client).isTabFlashing(Tabs.Tab_Types.Options)
        }

        override suspend fun execute() {
            Tabs(client).openTab(Tabs.Tab_Types.Options)
            delay(Random.nextLong(1250, 1650))
        }

    }

    class turnOffRoofsAndSound(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return Players(client).getLocal().getGlobalLocation().x == 3098
                    && Players(client).getLocal().getGlobalLocation().y == 3107
        }

        override suspend fun execute() {
            Tabs(client).openTab(Tabs.Tab_Types.Options)
            delay(Random.nextLong(500, 1500))
            //Display settings(261,1)child 1
            WidgetItem(Widgets.find(client, 261, 1)?.getChildren()?.get(1), client = client).click()
            delay(Random.nextLong(500, 1500))
            //Advanced options(261,35
            WidgetItem(Widgets.find(client, 261, 35), client = client).click()
            delay(Random.nextLong(500, 1500))
            //Turn off roofs(60,14). Texture Id when on is 762
            Widgets.waitTillWidgetNotNull(client,60, 14)
            val roofToggle = Widgets.find(client, 60, 14)
            if (roofToggle?.getSpriteId2() == 761) {
                WidgetItem(Widgets.find(client, 60, 14), client = client).click()
                delay(Random.nextLong(500, 1500))
            }
            //Close out of Advanced options widget(60,2) child index 3
            WidgetItem(Widgets.find(client, 60, 2)?.getChildren()?.get(3), client = client).click()

            //Turn off music
            //Open audio section
            WidgetItem(Widgets.find(client, 261, 1)?.getChildren()?.get(3), client = client).click()
            delay(Random.nextLong(300, 700))
            WidgetItem(Widgets.find(client, 261, 45), client = client).click()
            delay(Random.nextLong(300, 700))
            WidgetItem(Widgets.find(client, 261, 51), client = client).click()
            delay(Random.nextLong(300, 700))
            WidgetItem(Widgets.find(client, 261, 57), client = client).click()

        }

    }

    class FinalChatWithGielinor(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "On the side"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            println("Time to interact with Gielinor Guide")
            val gielinorGuide = NPCs(client).findNpc("Gielinor Guide")[0]
            gielinorGuide.interact("Talk-to")
            Utils.waitFor(5, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Dialog(client).isDialogUp()
                }
            })
            Dialog(client).continueDialog()
            println("Finished final chat with Gielinor")
        }

    }

    class OpenDoorFromFirstBuilding(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "time to meet your first instructor"
            return dialogWidget.containsText(text)

        }

        override suspend fun execute() {
            println("START: Opening door and walking to fishing spot")
            // Get doors, find one at location(3098,3107), and open it
            val gameObjects = GameObjects(client).find(9398)
            val doorLocation = Tile(3098, 3107,client = client)
            gameObjects.forEach {
                if (it.getGlobalLocation().x == doorLocation.x && it.getGlobalLocation().y == doorLocation.y) {
                    if (!it.isOnScreen()) it.turnTo()
                    it.interact("Open")
                    //Wait till here Tile(3098,3107)
                    Utils.waitFor(4, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return Players(client).getLocal().getGlobalLocation() == Tile(3098, 3107,client = client)
                        }
                    })
                }
            }
        }

    }

    class MoveToFishingSpot(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Follow the path to find the next instructor"
            val chatBox = WidgetItem(Widgets.find(client, 263, 1), client = client)
            val doorLocation = Tile(3098, 3107,client = client)
            val playerGlobalLoc = Players(client).getLocal().getGlobalLocation()

            return chatBox.containsText(text) && (playerGlobalLoc.x == doorLocation.x && playerGlobalLoc.y == doorLocation.y)
        }

        override suspend fun execute() {
            val path = arrayListOf(Tile(3098, 3107,client = client), Tile(3103, 3103,client = client), Tile(3102, 3095,client = client))
            Walking.walkPath(path)
            println("COMPLETE : Opening door and walking to fishing spot")
        }
    }

    class TalkToSurvivalExpertFirstTime(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val survivalExpert = NPCs(client).findNpc(8503)
            val text = "Follow the path to find the next instructor"
            val chatBox = WidgetItem(Widgets.find(client, 263, 1), client = client)
            return chatBox.containsText(text) && survivalExpert.size > 0 && survivalExpert[0].isOnScreen()
        }

        override suspend fun execute() {
            val survivalExpert = NPCs(client).findNpc(8503)
            survivalExpert[0].talkTo()
            // WAit till the continue is avaliable
            Players(client).getLocal().waitTillIdle()

            Dialog(client).continueDialog()
        }

    }

    class OpenInvetory(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("To view the item you've been given, you'll")
        }

        override suspend fun execute() {
            Tabs(client).openTab(Tabs.Tab_Types.Inventory)
        }

    }

    class CatchSomeShrimp(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "catch some shrimp"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            catchShrimp(client)

            if (Tabs(client).isTabFlashing(Tabs.Tab_Types.Skills)) {
                Tabs(client).openTab(Tabs.Tab_Types.Skills)
            }
        }
        companion object {
            suspend fun catchShrimp(client: Client) {
                val shrimps = NPCs(client).findNpc(3317)
                shrimps[0].turnTo()
                shrimps[0].interact("Net")
                // Wait till shrimp is in Inventory
                Utils.waitFor(10, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Inventory(client).getCount(SHRIMP_ID) > 0
                    }
                })
            }
        }
    }



    class ClickSkillsTab(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "on the flashing bar graph icon near the inventory"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            Tabs(client).openTab(Tabs.Tab_Types.Skills)
        }
    }

    class TalkToSurvivalGuideAfterSkillsTab(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "this menu you can view your skills."
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            val survivalExpert = NPCs(client).findNpc(8503)
            survivalExpert[0].talkTo()
            // WAit till the continue is avaliable
            Utils.waitFor(4, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Dialog(client).isDialogUp()
                }
            })

            Dialog(client).continueDialog()

        }

    }

    class ChopTree(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "time to cook your shrimp. However, you require"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            chopTree(client)

            Dialog(client).continueDialog()


        }
        companion object{
            suspend fun chopTree(client: Client) {
                val trees = GameObjects(client).find(9730, sortByDistance = true)
                // Should be more than 4, lets pick a random one between 1 and 4
                trees[Random.nextInt(0, 3)].interact("Chop")

                // Wait till we get a log in the invetory.
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Inventory(client).getCount(LOGS_ID_2511) > 0
                    }
                })
            }
        }

    }



    class LightLog(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "that you have some logs, it's time"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            // Use tinderbox(590) with logs(2511)
            lightFire(client)
        }

        companion object{
            suspend fun lightFire(client: Client) {
                Inventory(client).open()
                Inventory(client).getItem(590)?.click()
                Inventory(client).getItem(LOGS_ID_2511)?.click()
                delay(Random.nextLong(2500, 4500))
                //Wait till hes not doing anything which should mean fire has been made
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(client).getLocal().isIdle()
                    }
                })
            }
        }
    }


    class CookShrimp(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Now it's time to get cooking."
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            // Check to make sure we have shrimp, If not go fish for them
            if (Inventory(client).getCount(SHRIMP_ID) == 0) {
                CatchSomeShrimp.catchShrimp(client)
            }

            var fires = GameObjects(client).find(26185, sortByDistance = true)
            //No fire & no logs
            if (fires.size == 0 && Inventory(client).getCount(LOGS_ID_2511) == 0) {
                ChopTree.chopTree(client)
            }

            //If no fire && have logs, light a fire
            fires = GameObjects(client).find( 26185, sortByDistance = true)
            if (fires.size == 0 && Inventory(client).getCount(LOGS_ID_2511) > 0) {
                LightLog.lightFire(client)
            }

            // Check if there is a fire cook the shrimp
            fires = GameObjects(client).find(26185, sortByDistance = true)
            if (fires.size > 0) {
                Inventory(client).open()
                Inventory(client).getItem(SHRIMP_ID)?.click()
                // The fire is an animated object so it thows a NPE when trying to interacte with model.
                if (fires[0].sceneryObject != null) {
                    val point = Calculations.worldToScreen(
                            fires[0].sceneryObject!!.getCenterX(),
                            fires[0].sceneryObject!!.getCenterY(),
                            0,
                            client

                    )
                    Interact(client).interact(point, "Use")
                }

                //Wait till idle
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(client).getLocal().isIdle()
                    }
                })
                Dialog(client).continueDialog()
            }
        }
    }

    class OpenGateAfterFishing(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Well done, you've just cooked your first meal!"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            println("START: Going to open gate")
            // Open gate at 3090,3092
            val gateTile = Tile(3090, 3092, 0,client = client)
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
            val gates = GameObjects(client).find(gateIDs.random(), sortByDistance = true)
            if (gates.size > 0) {
                gates[0].turnTo()
                gates[0].interact("Open")
                Players(client).getLocal().waitTillIdle()
                delay(Random.nextLong(100, 150))
            }
            println("Complete: Going to open gate")
        }

    }

    class MoveToKitchen(client: Client)  : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Follow the path until you get to the door with the yellow arrow above it."
            val percentComplete = getPercentComplete(client)
            return dialogWidget.containsText(text) && percentComplete == .196875
        }

        override suspend fun execute() {
            val tile = Tile(3079, 3084, 0,client = client)
            if (tile.distanceTo() > 5) {
                tile.clickOnMiniMap()
                Utils.waitFor(10, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return tile.distanceTo() < 5
                    }
                })
            }

            val gameObjects = GameObjects(client).find(9709, sortByDistance = true)
            if (gameObjects.size > 0) {
                gameObjects[0].interact("Open")
            }
        }
    }

    class TalkToMasterChef(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Talk to the chef indicated"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            NPCs(client).findNpc(3305)[0].talkTo()

            delay(Random.nextLong(3000, 5000))

            Dialog(client).continueDialog()
        }
    }

    class MakeDough(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "This is the base for many meals"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            // Mix water(1929) and flower(2516)
            Inventory(client).getItem(1929)?.click()
            Inventory(client).getItem(2516)?.click()
            delay(Random.nextLong(1250, 1650))
            Dialog(client).continueDialog()


        }

    }

    class MakeBread(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Now you have made the dough,"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            //dough is 2307
            //Range is 9736
            Inventory(client).open()
            val range = GameObjects(client).find(9736)[0]
            Camera(client).turnTo(range)
            //TODO - Need to improve ineract when menu is full
            range.interact("Cook Range")
            // Wait till bread in inventory
            Utils.waitFor(4, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Dialog(client).isDialogUp()
                }
            })
            delay(Random.nextLong(1250, 1650))
            Dialog(client).continueDialog()

        }

    }

    class ExitKitchen(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "You've baked your first loaf of bread"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            //Location 3073,3090

            val tileNearDoor = Tile(3073, 3090,client = client)
            if (tileNearDoor.distanceTo() > 4) {
                tileNearDoor.clickOnMiniMap()
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return tileNearDoor.distanceTo() < 4
                    }
                })
            }

            Camera(client).turnWest()

            //DOOR 9710
            val door = GameObjects(client).find(9710)
            if (door.size > 0) {
                door[0].interact("Open Door")
            }
        }

    }

    class TurnOnRun(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "When navigating the world, you can either run or walk"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            Run(client).activateRun()
        }

    }

    class MoveToNextBuilding(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Follow the path to the next guide"
            return dialogWidget.containsText(text) && getPercentComplete(client) == .296875
        }

        override suspend fun execute() {
            val walkingPath = arrayListOf(
                Tile(3073, 3103,client = client), Tile(3074, 3117,client = client),
                Tile(3079, 3127,client = client), Tile(3086, 3127,client = client)
            )
            Walking.walkPath(walkingPath)
            //Open Door(9716)
            Camera(client).setHighPitch()
            Camera(client).turnSouth()
            val doors = GameObjects(client).find("Door", sortByDistance = true)
            if (doors.size > 0) {
                doors[0].interact("Open Door")
            }


        }

    }

    class TalkToQuestGuide(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "It's time to learn about quests!"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            val questGuide = NPCs(client).findNpc("Quest Guide")
            if (questGuide.size > 0) {
                if (!questGuide[0].isOnScreen()) Camera(client).turnTo(questGuide[0])
                questGuide[0].interact("Talk-to Quest Guide")
                Utils.waitFor(3, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(client).getLocal().isIdle()
                    }
                })
                delay(Random.nextLong(100, 150))
                Dialog(client).continueDialog()
            }
        }
    }

    class OpenQuestList(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Click on the flashing icon to the left of your Inventory(client)."
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            Tabs(client).openTab(Tabs.Tab_Types.QuestList)
        }

    }

    class TalkToQuestGuide2ndTime(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "This is your quest journal."
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            val questGuide = NPCs(client).findNpc("Quest Guide")
            if (questGuide.size > 0) {
                if (!questGuide[0].isOnScreen()) Camera(client).turnTo(questGuide[0])
                questGuide[0].interact("Talk-to Quest Guide")
                Utils.waitFor(3, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(client).getLocal().isIdle()
                    }
                })
                Dialog(client).continueDialog()
            }
        }
    }

    class GoDownToTheCaves(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "It's time to enter some caves"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            Camera(client).setHighPitch()
            // Go down ladder
            val ladder = GameObjects(client).find("Ladder")
            if (ladder.size > 0) {
                Camera(client).turnTo(ladder[0])
                ladder[0].interact("Climb-down Ladder")
                delay(Random.nextLong(3500, 6400))
            }
        }

    }

    class WalkAndTalkToSmitingAndMiningGuide(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Next let's get you a weapon,"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            val walkingPath = arrayListOf(Tile(3079, 9512,client = client), Tile(3081, 9504,client = client))
            Walking.walkPath(walkingPath)
            val miningGuide = NPCs(client).findNpc("Mining Instructor")
            if (miningGuide.size > 0) {
                Camera(client).setHighPitch()
                if (!miningGuide[0].isOnScreen()) miningGuide[0].turnTo()
                miningGuide[0].talkTo()
                delay(Random.nextLong(1250, 3650))
                Dialog(client).continueDialog()

            }
        }

    }

    class MineRock{
        companion object{
            suspend fun mineRock(client: Client) {
                val rocks = GameObjects(client).find("Rocks", sortByDistance = true)
                if (rocks.size > 0) {
                    val oldInventoryCount = Inventory(client).getCount()
                    rocks[0].interact("Mine")
                    Utils.waitFor(8, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return oldInventoryCount != Inventory(client).getCount()
                        }
                    })
                }
                Dialog(client).continueDialog()
            }
        }
    }

    class MineTin(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "It's quite simple really. To mine a rock, all you need"
            return dialogWidget.containsText(text)
                    || dialogWidget.containsText("Now that you have some copper ore,")
        }

        override suspend fun execute() {
            //walk to tile(3076,9505
            //Mine rocks
            val miningspot = Tile(3076, 9505, 0,client = client)
            miningspot.clickOnMiniMap()
            Players(client).getLocal().waitTillIdle()
            Camera(client).setHighPitch()
            MineRock.mineRock(client)
        }
    }



    class MineCopper(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Now that you have some tin ore"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            val miningspot = Tile(3085, 9502, 0,client = client)
            if (miningspot.distanceTo() > 5) {
                miningspot.clickOnMiniMap()
                delay(Random.nextLong(3500, 5500))
            }
            Camera(client).setHighPitch()
            MineRock.mineRock(client)
        }

    }

    class SmeltBronze(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You now have some tin ore and some copper ore.")
        }

        override suspend fun execute() {
            val miningspot = Tile(3079, 9498, 0,client = client)
            if (miningspot.distanceTo() > 3) {

                miningspot.clickOnMiniMap()
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return miningspot.distanceTo() < 3
                    }
                })
            }

            val furnace = GameObjects(client).find("Furnace")[0]
            if (!furnace.isOnScreen()) furnace.turnTo()
            furnace.click()
        }

    }

    class TalkToMiningGuideAboutSmiting(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You've made a bronze bar!")
        }

        override suspend fun execute() {

            val miningGuide = NPCs(client).findNpc("Mining Instructor")
            if (miningGuide.size > 0) {
                miningGuide[0].turnTo()
                if (Tile(3081, 9504,client = client).distanceTo() > 4) {
                    Tile(3081, 9504,client = client).clickOnMiniMap()
                }
                miningGuide[0].talkTo()
                Players(client).getLocal().waitTillIdle()
                Dialog(client).continueDialog()

            }
        }

    }

    class MakeBronzeDagger(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("To smith you'll need a hammer") || dialogWidget.containsText("Use an anvil to open") || dialogWidget.containsText(
                "Now you have the smithing"
            )
        }

        override suspend fun execute() {
            //Find Anvil
            val anvil = GameObjects(client).find("Anvil", sortByDistance = true)
            if (anvil.size > 0) {

                val index = (0..1).random()
                anvil[index].turnTo()
                Inventory(client).open()
                anvil[index].click()
                delay(Random.nextLong(300, 700))
                Players(client).getLocal().waitTillIdle()
                //Wait for smiting widgets
                Widgets.waitTillWidgetNotNull(client,312, 9)

                val oldInventoryCount = Inventory(client).getCount()
                val daggerSmitingPage = WidgetItem(Widgets.find(client, 312, 9)?.getChildren()?.get(2), client = client)
                if (daggerSmitingPage.widget != null) {
                    daggerSmitingPage.click()

                    Utils.waitFor(4, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return oldInventoryCount != Inventory(client).getCount()
                        }
                    })
                }


            }
        }

    }

    class AfterSmithingMovetoGate(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Congratulations, you've made your first weapon")
        }

        override suspend fun execute() {
            val walkingPath = arrayListOf(Tile(3086, 9505,client = client), Tile(3091, 9503,client = client))
            Walking.walkPath(walkingPath)
            val gate = GameObjects(client).find("Gate", sortByDistance = true)
            if (gate.size > 0) {
                Camera(client).setHighPitch()
                Camera(client).turnEast()
                gate[0].interact("Open")
                Players(client).getLocal().waitTillIdle()
            }

        }

    }

    class TalkToCombatInstructor(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("In this area you will find out about melee and ranged combat.")
        }

        override suspend fun execute() {
            Camera(client).setHighPitch()
            // Move to combat insturctor
            val tileNearCombatInstructor = Tile(3107, 9509,client = client)
            if (tileNearCombatInstructor.distanceTo() > 5) {
                tileNearCombatInstructor.clickOnMiniMap()
                val local = Players(client).getLocal()
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return tileNearCombatInstructor.distanceTo() < 5 && local.isIdle()
                    }
                })
            }

            //Talk with combat instructor
            val combatInstructor = NPCs(client).findNpc("Combat Instructor")
            combatInstructor[0].talkTo()
            Players(client).getLocal().waitTillIdle()
            Dialog(client).continueDialog()

        }

    }

    class OpenEquipment(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You now have access to a new")
        }

        override suspend fun execute() {
            Tabs(client).openTab(Tabs.Tab_Types.Equiptment)
        }

    }

    class OpenEquipmentStats(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("This is your worn Inventory(client).")
        }

        override suspend fun execute() {
            Equipment(client).open()
            Equipment(client).clickButton(Equipment.Companion.Slot.EquiptmentStats)
            delay(Random.nextLong(1500, 2637))
        }

    }

    class EquipBronzeDagger(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You can see what items you are")
        }

        override suspend fun execute() {
            Inventory(client).getItem(1205)?.click()
            delay(Random.nextLong(2500, 4000))
            WidgetItem(Widgets.find(client, 84, 4), client = client).click() // Close out of Equoptment status
        }

    }

    class SpeakWithCombatAfterBronzeDaggerEquipt(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You're now holding your dagger")
        }

        override suspend fun execute() {
            Camera(client).setHighPitch()
            //Talk with combat instructor
            val combatInstructor = NPCs(client).findNpc("Combat Instructor")
            combatInstructor[0].talkTo()
            Dialog(client).continueDialog()

        }

    }

    class EquipLongSwordAndShield(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("To unequip an item, go to your")
        }

        override suspend fun execute() {
            Inventory(client).getItem(1277)?.click()
            delay(Random.nextLong(1500, 2500))
            Inventory(client).getItem(1171)?.click()
            delay(Random.nextLong(1500, 2500))
        }

    }

    class OpenCombatTab(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Click on the flashing crossed")
        }

        override suspend fun execute() {
            Tabs(client).openTab(Tabs.Tab_Types.Combat)
        }

    }

    class GoIntoRatCage(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("This is your combat interface. From here,")
        }

        override suspend fun execute() {
            Camera(client).setHighPitch()
            //Walk over to tile
            val tileNearGate = Tile(3111, 9519,client = client)
            if (tileNearGate.distanceTo() > 5) {
                tileNearGate.clickOnMiniMap()
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return tileNearGate.distanceTo() < 5
                    }
                })
            }

            //Enter cage
            val gates = GameObjects(client).find("Gate", sortByDistance = true)
            if (gates.size > 0) {
                Camera(client).turnWest()
                gates[0].interact("Open")
                Utils.waitFor(2, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(client).getLocal().isIdle()
                    }
                })
            }

        }

    }

    class MeleeKillRat(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("It's time to slay some rats!")
        }

        override suspend fun execute() {
            Camera(client).setHighPitch()
            val rats = NPCs(client).findNpc("Giant rat")
            if (rats.size > 0) {
                val randomIndex = (0..5).random()
                rats[randomIndex].interact("Attack")
                Utils.waitFor(20, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(client).getLocal().isIdle()
                    }
                })
            }
        }

    }


    class GoTalkToCombatInstructorFor2ndTime(client: Client) : Job(client) {
        val ratCageArea = Area(
                Tile(3109, 9521), Tile(3110, 9519),
                Tile(3110, 9518), Tile(3109, 9516), Tile(3109, 9515),
                Tile(3108, 9514), Tile(3107, 9514), Tile(3106, 9513),
                Tile(3106, 9512), Tile(3105, 9511), Tile(3103, 9512),
                Tile(3100, 9512), Tile(3099, 9514), Tile(3098, 9515),
                Tile(3097, 9517), Tile(3098, 9519), Tile(3099, 9521),
                Tile(3100, 9522), Tile(3101, 9522), Tile(3102, 9525),
                Tile(3104, 9524), Tile(3106, 9522), Tile(3108, 9522),
                Tile(3109, 9521)
        )
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Pass through the gate and talk to the combat")
        }

        override suspend fun execute() {
            Camera(client).setHighPitch()
            Camera(client).turnEast()
            // Check to see if we are still in the rat cage
            if (ratCageArea.containsOrIntersects(Players(client).getLocal().getGlobalLocation())) {
                val gates = GameObjects(client).find("Gate", sortByDistance = true)
                if (gates.size > 0) {
                    if (!gates[0].isOnScreen()) {
                        gates[0].clickOnMiniMap()
                        Players(client).getLocal().waitTillIdle()
                    }
                    gates[0].interact("Open")
                    Players(client).getLocal().waitTillIdle()
                }
            }

            if (!ratCageArea.containsOrIntersects(Players(client).getLocal().getGlobalLocation())) {
                val combatInstructor = NPCs(client).findNpc("Combat Instructor")
                if (combatInstructor[0].distanceTo() > 5) {
                    combatInstructor[0].clickOnMiniMap()
                    combatInstructor[0].waitTillNearObject()
                }

                combatInstructor[0].talkTo()
                Dialog(client).continueDialog()
            }
        }

    }

    class KillRatWithBow(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Now you have a bow and some arrows.")
        }

        override suspend fun execute() {
            Inventory(client).open()
            Inventory(client).getItem(841)?.click()
            delay(Random.nextLong(1500, 2500))
            Inventory(client).getItem(882)?.click()
            delay(Random.nextLong(1500, 2500))

            //Move over to a better spot to kill the rats
            val idealSpot = Tile(3110,9515,client = client)
            if(idealSpot.distanceTo() > 3){
                idealSpot.clickOnMiniMap()
                Players(client).getLocal().waitTillIdle()
            }

            val rats = NPCs(client).findNpc("Giant rat")
            if (rats.size > 0) {
                val randomIndex = (0..2).random()
                rats[randomIndex].turnTo()
                rats[randomIndex].interact("Attack")
                delay(Random.nextLong(1000, 1500))
                Players(client).getLocal().waitTillIdle()
            }
        }

    }

    class ExitCaves(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You have completed the tasks here")
        }

        override suspend fun execute() {
            Camera(client).setHighPitch()
            val tileNearLadder = Tile(3110, 9526,client = client)
            if (tileNearLadder.distanceTo() > 3) {
                tileNearLadder.clickOnMiniMap()
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return tileNearLadder.distanceTo() < 3
                    }
                })

            }

            val ladder = GameObjects(client).find("Ladder", sortByDistance = true)
            if (ladder.size > 0) {
                ladder[0].interact("Climb")
                Players(client).getLocal().waitTillIdle()
            }
        }

    }

    class UseBank(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Follow the path and you will come to the front of the building")
        }

        override suspend fun execute() {
            val tileNearBank = Tile(3122, 3123,client = client)
            if (tileNearBank.distanceTo() > 5) {
                tileNearBank.clickOnMiniMap()
                Utils.waitFor(10, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return tileNearBank.distanceTo() < 3
                    }
                })

            }

            val bankBooth = GameObjects(client).find("Bank booth", sortByDistance = true)
            if (bankBooth.size > 0) {
                bankBooth[0].interact("Use")
            }

        }

    }

    class CloseBankAndDoPollBooth(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("This is your bank.")
        }

        override suspend fun execute() {
            Bank(client).close()
            PollBooth.openPollBooth(client)
        }

    }

    class PollBooth {
        companion object {
            suspend fun openPollBooth(client: Client) {
                val pollBooth = GameObjects(client).find(26815)
                pollBooth[0].turnTo()
                val pollTile = Tile(3119, 3121, client.getPlane(),client = client)
                if (pollTile.distanceTo() > 3)
                    Tile(3120, 3121, client.getPlane(),client = client).clickOnMiniMap()

                pollTile.click()
                delay(Random.nextLong(1500, 2500))
                Dialog(client).continueDialog()
            }
             suspend fun closePollWidget(client: Client) {
                try {
                    var pollWidget = Widgets.find(client, 345, 0)
                    if (pollWidget != null) {
                        val pollExitWidget = WidgetItem(Widgets.find(client, 345, 2)?.getChildren()?.get(3), client = client)
                        pollExitWidget.click()
                    }
                    pollWidget = Widgets.find(client, 310, 0)
                    if (pollWidget != null) {
                        val pollExitWidget = WidgetItem(Widgets.find(client, 310, 2)?.getChildren()?.get(3), client = client)
                        pollExitWidget.click()
                    }

                } catch (e: Exception) {
                    println("ERROR: Somthing happened when trying to find the poll widget")
                }
            }
        }
    }

    class DoPollBooth(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Now it's time for a quick look at polls")
        }

        override suspend fun execute() {
            //TODO figure out how to access moving objects OR need to find a more center tile point
            PollBooth.openPollBooth(client)
            Dialog(client).continueDialog()
            //If poll widget open, Close out of polling booth widget (310,2) child index 3
            PollBooth.closePollWidget(client)

        }

    }



    class ClosePollAndMoveOutOfBank(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Polls are run periodically to let the Old School")
        }

        override suspend fun execute() {
            PollBooth.closePollWidget(client)
            //Open Door(9721) at location(3125,3124)
            val doors = GameObjects(client).find(9721, sortByDistance = true)
            if (doors.isNotEmpty()) {
                doors.forEach {
                    if (it.getGlobalLocation().x == 3125 && it.getGlobalLocation().y == 3124) {
                        it.turnTo()
                        it.interact("Open")
                        Utils.waitFor(6, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return Players(client).getLocal().isIdle() && Players(client).getLocal().getGlobalLocation().x == 3125
                            }
                        })
                    }
                }
            }
        }

    }

    class TalkToAccountManager(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("The guide here will tell you all about your account.") ||
                    dialogWidget.containsText("This is your Account Management menu")
        }

        override suspend fun execute() {
            val accountManager = NPCs(client).findNpc("Account Guide")
            if (accountManager.isNotEmpty()) {
                Camera(client).setHighPitch()
                accountManager[0].talkTo()
                delay(Random.nextLong(2500, 4500))
                Dialog(client).continueDialog()
            }
        }

    }

    class OpenAccountManager(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Click on the flashing icon to open your Account Management")
        }

        override suspend fun execute() {
            Tabs(client).openTab(Tabs.Tab_Types.AccountManagement)
        }

    }

    class ExitAccountManagerRoom(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Continue through the next door.")
        }

        override suspend fun execute() {
            val doors = GameObjects(client).find(9722, sortByDistance = true)
            if (doors.isNotEmpty()) {
                doors.forEach {
                    if (it.getGlobalLocation().x == 3130 && it.getGlobalLocation().y == 3124) {
                        Camera(client).turnEast()
                        Camera(client).setHighPitch()
                        it.interact("Open")
                        Players(client).getLocal().waitTillIdle()
                        delay(Random.nextLong(100, 150))
                    }
                }
            }
        }

    }

    class MoveToChapelAndTalkToBrotherBrace(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Follow the path to the chapel")
                    || dialogWidget.containsText("Talk with Brother Brace")
                    || dialogWidget.containsText("These two lists can be very helpful for keeping track")
        }

        override suspend fun execute() {
            var brotherBrace = NPCs(client).findNpc("Brother Brace")
            val pathToChapel = arrayListOf(Tile(3132, 3115,client = client), Tile(3130, 3107,client = client), Tile(3124, 3106,client = client))
            if ((brotherBrace.isNotEmpty() && brotherBrace[0].distanceTo() > 13) || brotherBrace.isEmpty())
                Walking.walkPath(pathToChapel)
            Camera(client).setHighPitch()
            brotherBrace = NPCs(client).findNpc("Brother Brace")
            if (brotherBrace.size > 0) {
                if (!brotherBrace[0].isOnScreen())
                    brotherBrace[0].turnTo()
                brotherBrace[0].talkTo()
                Players(client).getLocal().waitTillIdle()
                Dialog(client).continueDialog()
            }
        }

    }

    class OpenPrayerTab(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Click on the flashing icon to open the Prayer menu.")
        }

        override suspend fun execute() {
            Tabs(client).openTab(Tabs.Tab_Types.Prayer)
        }

    }

    class OpenFriendsTab(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You should now see another new icon. Click on the flashing face")
        }

        override suspend fun execute() {
            Tabs(client).openTab(Tabs.Tab_Types.FriendsList)
        }

    }

    class ExitChapleHouse(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You're almost finished on tutorial island")
        }

        override suspend fun execute() {
            val doors = GameObjects(client).find(9723, sortByDistance = true)
            if (doors.isNotEmpty()) {
                doors.forEach {
                    if (it.getGlobalLocation().x == 3122 && it.getGlobalLocation().y == 3102) {
                        Camera(client).turnSouth()
                        Camera(client).setHighPitch()
                        it.interact("Open")
                        delay(Random.nextLong(1500, 2500))
                        Players(client).getLocal().waitTillIdle()
                    }
                }
            }
        }

    }

    class GoToWizardHouseAndSpeakWithWizard(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Follow the path to the wizard")
                    || dialogWidget.containsText("This is your magic interface")
        }

        override suspend fun execute() {
            val pathToWizardHouse = arrayListOf(
                Tile(3128, 3090,client = client), Tile(3138, 3087,client = client),
                Tile(3140, 3087,client = client)
            )
            if (pathToWizardHouse[2].distanceTo() > 6) {
                Walking.walkPath(pathToWizardHouse)
            }
            val magicInstructor = NPCs(client).findNpc("Magic Instructor")
            if (magicInstructor.isNotEmpty()) {
                magicInstructor[0].turnTo()
                magicInstructor[0].talkTo()
                Players(client).getLocal().waitTillIdle()
                Dialog(client).continueDialog()

            }
        }

    }

    class OpenMagicTab(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Open up the magic interface")
        }

        override suspend fun execute() {
            Tabs(client).openTab(Tabs.Tab_Types.Magic)
        }

    }

    class SelectWindStrikeAndAttackChicken(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You now have some runes.")
        }

        override suspend fun execute() {
            Magic(client).cast(Magic.Companion.Spells.Wind_Strike)
            //Attack chicken
            val chickens = NPCs(client).findNpc("Chicken")
            if (chickens.isNotEmpty()) {
                val randChick = Random.nextInt(0, chickens.size - 1)
                chickens[randChick].turnTo()
                chickens[randChick].interact("Cast")
                Utils.waitFor(7, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(client).getLocal().isIdle() && chickens[randChick].isIdle()
                    }
                })

            }
        }

    }

    class ExitTutIsland(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You're nearly finished with the tutorial")
        }

        override suspend fun execute() {
            val magicInstructor = NPCs(client).findNpc("Magic Instructor")
            if (magicInstructor.isNotEmpty()) {
                if (!magicInstructor[0].isOnScreen()) magicInstructor[0].turnTo()
                magicInstructor[0].talkTo()
                Players(client).getLocal().waitTillIdle()
                Dialog(client).continueDialog()
                Dialog(client).selectionOption("Yes")
                Dialog(client).continueDialog()
                Dialog(client).selectionOption("No")
                Dialog(client).continueDialog()

            }
        }

    }

    class MainlandLogout(client: Client) : Job(client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val completedWidget = WidgetItem(Widgets.find(client, 193, 2), client = client)
            return completedWidget.containsText("Welcome to Lumbridge!")
        }

        override suspend fun execute() {
            //Run few different paths and then logout
            val pathNorth = arrayListOf(
                Tile(3234, 3225,client = client), Tile(3224, 3237,client = client),
                Tile(3218, 3250,client = client), Tile(3214, 3262,client = client)
            )
            val pathEast = arrayListOf(
                Tile(3240, 3225,client = client), Tile(3256, 3227,client = client),
                Tile(3258, 3233,client = client), Tile(3257, 3245,client = client), Tile(3251, 3257,client = client)
            )
            val pathSouth = arrayListOf(
                Tile(3235, 3204,client = client), Tile(3243, 3193,client = client),
                Tile(3241, 3181,client = client), Tile(3231, 3175,client = client), Tile(3238, 3163,client = client)
            )
            val pathWest = arrayListOf(
                Tile(3223, 3219,client = client), Tile(3213, 3210,client = client),
                Tile(3206, 3210,client = client)
            )
            if (Random.nextBoolean()) {
                println("Walking path random")
                val investigationPaths = arrayListOf(pathNorth, pathSouth, pathEast)
                val path = investigationPaths.random()
                //Walk the path and then come back
                Walking.walkPath(path)
                Walking.walkPath(path, reverse = true)
                Logout(client).logout()
            } else {
                println("Walking path west")
                Walking.walkPath(pathWest)
                Logout(client).logout()
            }

        }

    }
   
}