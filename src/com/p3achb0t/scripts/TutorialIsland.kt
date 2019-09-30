package com.p3achb0t.scripts

import com.p3achb0t._runestar_interfaces.Client
import com.p3achb0t.api.*
import com.p3achb0t.api.user_inputs.Camera
import com.p3achb0t.api.user_inputs.Keyboard
import com.p3achb0t.api.wrappers.*
import com.p3achb0t.api.wrappers.tabs.Equipment
import com.p3achb0t.api.wrappers.tabs.Inventory
import com.p3achb0t.api.wrappers.tabs.Magic
import com.p3achb0t.api.wrappers.tabs.Tabs
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import com.p3achb0t.api.wrappers.widgets.Widgets
import com.p3achb0t.api.Context
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
        println("Running Start2")
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
        jobs.add(MakeBread(ctx, keyboard))
        jobs.add(ExitKitchen(ctx, keyboard))
        jobs.add(TurnOnRun(ctx))
        jobs.add(MoveToNextBuilding(ctx, keyboard))
        jobs.add(TalkToQuestGuide(ctx, keyboard))
        jobs.add(OpenQuestList(ctx))
        jobs.add(TalkToQuestGuide2ndTime(ctx, keyboard))
        jobs.add(GoDownToTheCaves(ctx, keyboard))
        jobs.add(WalkAndTalkToSmitingAndMiningGuide(ctx, keyboard))
        jobs.add(MineTin(ctx, keyboard))
        jobs.add(MineCopper(ctx, keyboard))
        jobs.add(SmeltBronze(ctx))
        jobs.add(TalkToMiningGuideAboutSmiting(ctx))
        jobs.add(MakeBronzeDagger(ctx))
        jobs.add(AfterSmithingMovetoGate(ctx, keyboard))
        jobs.add(TalkToCombatInstructor(ctx, keyboard))
        jobs.add(OpenEquipment(ctx))
        jobs.add(OpenEquipmentStats(ctx))
        jobs.add(EquipBronzeDagger(ctx))
        jobs.add(SpeakWithCombatAfterBronzeDaggerEquipt(ctx, keyboard))
        jobs.add(EquipLongSwordAndShield(ctx))
        jobs.add(OpenCombatTab(ctx))
        jobs.add(GoIntoRatCage(ctx, keyboard))
        jobs.add(MeleeKillRat(ctx, keyboard))
        jobs.add(GoTalkToCombatInstructorFor2ndTime(ctx, keyboard))
        jobs.add(KillRatWithBow(ctx))
        jobs.add(ExitCaves(ctx, keyboard))
        jobs.add(UseBank(ctx))
        jobs.add(CloseBankAndDoPollBooth(ctx))
        jobs.add(DoPollBooth(ctx))
        jobs.add(ClosePollAndMoveOutOfBank(ctx))
        jobs.add(TalkToAccountManager(ctx, keyboard))
        jobs.add(OpenAccountManager(ctx))
        jobs.add(ExitAccountManagerRoom(ctx, keyboard))
        jobs.add(MoveToChapelAndTalkToBrotherBrace(ctx, keyboard))
        jobs.add(OpenPrayerTab(ctx))
        jobs.add(OpenFriendsTab(ctx))
        jobs.add(ExitChapleHouse(ctx, keyboard))
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
            val chatBox = WidgetItem(Widgets.find(ctx, 263, 1), ctx = ctx)
            if (it.isValidToRun(chatBox)) {
                println("Running: ${it.javaClass.name}")
                it.execute()
                println("Completed: ${it.javaClass.name}")
            }
        }
    }

    companion object {
        fun getPercentComplete(ctx: Context): Double {
            // widget for progress 614,18
            val complete = WidgetItem(Widgets.find(ctx, 614, 18), ctx = ctx).widget?.getWidth()?.toDouble()
                    ?: 0.0
            //widget for total 614, 17
            val total = WidgetItem(Widgets.find(ctx, 614, 17), ctx = ctx).widget?.getWidth()?.toDouble() ?: 0.0

            return (complete / total)
        }
    }


    class PickName(val ctx: Context)  : Job(ctx.client) {

        val names = arrayListOf(
                "PapaBadass", "randomBJ", "kamalchettiar", "all_negative_", "s0meguy",
                "shouldidivorce", "kisskross", "ccnelson", "w4rf19ht3r", "lili999", "qwerqtwfnhnqufh"
        )
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return Widgets.isWidgetAvaliable(ctx, 558, 0)
        }

        override suspend fun execute() {
            println("Picking name")
            //Name widget to click into and type a name 558,7
            val nameEntry = WidgetItem(Widgets.find(ctx, 558, 7), ctx = ctx)
            nameEntry.click()
            delay(Random.nextLong(2200, 5550))
            println("Sending keys")
            ctx.keyboard.sendKeys(names.random(), sendReturn = true)
            delay(Random.nextLong(2200, 5550))

            // If not a valid name then random name in the follow selections 558,(14,15,16)
            // Once picked It should say available in 558,12
            val validName = WidgetItem(Widgets.find(ctx, 558, 12), ctx = ctx)
            if (validName.widget?.getText()?.toLowerCase()?.contains("great!")!!) {
                println("Found Valid name!")
            } else {
                val rand = Random.nextInt(14, 16)
                val selectRandomName = WidgetItem(Widgets.find(ctx, 558, rand), ctx = ctx)
                selectRandomName.click()
                delay(Random.nextLong(2200, 5550))
            }
            //Pick set name in 558,18
            val pickName = WidgetItem(Widgets.find(ctx, 558, 18), ctx = ctx)
            pickName.click()
            delay(Random.nextLong(2200, 5550))
            Utils.waitFor(4, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return !Widgets.isWidgetAvaliable(ctx, 558, 0)
                }
            })
            println("Picking name complete")


        }

    }

    class SelectCharOutfit(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return Widgets.isWidgetAvaliable(ctx, 269, 0)
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
                    WidgetItem(Widgets.find(ctx, widgetIndex.parentID.toInt(), widgetIndex.childID.toInt()), ctx = ctx).click()
                    delay(Random.nextLong(250, 650))
                }
            }
            //Randomly pick if you are going to be afemale
            if (Random.nextBoolean()) {
                println("Picking Female")
                WidgetItem(Widgets.find(ctx, 269, 139), ctx = ctx).click()
                delay(Random.nextLong(250, 650))
            } else {
                println("Leaving male")
            }
            //select accept
            WidgetItem(Widgets.find(ctx, 269, "Accept"), ctx = ctx).click()
            delay(Random.nextLong(1250, 2650))
            println("Completed Character outfit")
        }

    }

    class ChatWithGielinorGuide(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Before you begin, have a read"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            println("Time to interact with Gielinor Guide")
            val gielinorGuide = NPCs(ctx).findNpc("Gielinor Guide")[0]
            gielinorGuide.interact("Talk-to")
            Utils.waitFor(5, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Dialog(ctx).isDialogUp()
                }
            })
            Dialog(ctx).continueDialog()
            Dialog(ctx).selectRandomOption()
            delay(Random.nextLong(1250, 1650))
            Dialog(ctx).continueDialog()
            println("Interact with Gielinor Guide Complete")
        }

    }

    class OpenOptions(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return Tabs(ctx).isTabFlashing(Tabs.Tab_Types.Options)
        }

        override suspend fun execute() {
            Tabs(ctx).openTab(Tabs.Tab_Types.Options)
            delay(Random.nextLong(1250, 1650))
        }

    }

    class turnOffRoofsAndSound(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return Players(ctx).getLocal().getGlobalLocation().x == 3098
                    && Players(ctx).getLocal().getGlobalLocation().y == 3107
        }

        override suspend fun execute() {
            Tabs(ctx).openTab(Tabs.Tab_Types.Options)
            delay(Random.nextLong(500, 1500))
            //Display settings(261,1)child 1
            WidgetItem(Widgets.find(ctx, 261, 1)?.getChildren()?.get(1), ctx = ctx).click()
            delay(Random.nextLong(500, 1500))
            //Advanced options(261,35
            WidgetItem(Widgets.find(ctx, 261, 35), ctx = ctx).click()
            delay(Random.nextLong(500, 1500))
            //Turn off roofs(60,14). Texture Id when on is 762
            Widgets.waitTillWidgetNotNull(ctx,60, 14)
            val roofToggle = Widgets.find(ctx, 60, 14)
            if (roofToggle?.getSpriteId2() == 761) {
                WidgetItem(Widgets.find(ctx, 60, 14), ctx = ctx).click()
                delay(Random.nextLong(500, 1500))
            }
            //Close out of Advanced options widget(60,2) child index 3
            WidgetItem(Widgets.find(ctx, 60, 2)?.getChildren()?.get(3), ctx = ctx).click()

            //Turn off music
            //Open audio section
            WidgetItem(Widgets.find(ctx, 261, 1)?.getChildren()?.get(3), ctx = ctx).click()
            delay(Random.nextLong(300, 700))
            WidgetItem(Widgets.find(ctx, 261, 45), ctx = ctx).click()
            delay(Random.nextLong(300, 700))
            WidgetItem(Widgets.find(ctx, 261, 51), ctx = ctx).click()
            delay(Random.nextLong(300, 700))
            WidgetItem(Widgets.find(ctx, 261, 57), ctx = ctx).click()

        }

    }

    class FinalChatWithGielinor(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "On the side"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            println("Time to interact with Gielinor Guide")
            val gielinorGuide = NPCs(ctx).findNpc("Gielinor Guide")[0]
            gielinorGuide.interact("Talk-to")
            Utils.waitFor(5, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Dialog(ctx).isDialogUp()
                }
            })
            Dialog(ctx).continueDialog()
            println("Finished final chat with Gielinor")
        }

    }

    class OpenDoorFromFirstBuilding(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "time to meet your first instructor"
            return dialogWidget.containsText(text)

        }

        override suspend fun execute() {
            println("START: Opening door and walking to fishing spot")
            // Get doors, find one at location(3098,3107), and open it
            val gameObjects = GameObjects(ctx).find(9398)
            val doorLocation = Tile(3098, 3107, ctx = ctx)
            gameObjects.forEach {
                if (it.getGlobalLocation().x == doorLocation.x && it.getGlobalLocation().y == doorLocation.y) {
                    if (!it.isOnScreen()) it.turnTo()
                    it.interact("Open")
                    //Wait till here Tile(3098,3107)
                    Utils.waitFor(4, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return Players(ctx).getLocal().getGlobalLocation() == Tile(3098, 3107, ctx = ctx)
                        }
                    })
                }
            }
        }

    }

    class MoveToFishingSpot(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Follow the path to find the next instructor"
            val chatBox = WidgetItem(Widgets.find(ctx, 263, 1), ctx = ctx)
            val doorLocation = Tile(3098, 3107, ctx = ctx)
            val playerGlobalLoc = Players(ctx).getLocal().getGlobalLocation()

            return chatBox.containsText(text) && (playerGlobalLoc.x == doorLocation.x && playerGlobalLoc.y == doorLocation.y)
        }

        override suspend fun execute() {
            val path = arrayListOf(Tile(3098, 3107, ctx = ctx), Tile(3103, 3103, ctx = ctx), Tile(3102, 3095, ctx = ctx))
            Walking.walkPath(path)
            println("COMPLETE : Opening door and walking to fishing spot")
        }
    }

    class TalkToSurvivalExpertFirstTime(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val survivalExpert = NPCs(ctx).findNpc(8503)
            val text = "Follow the path to find the next instructor"
            val chatBox = WidgetItem(Widgets.find(ctx, 263, 1), ctx = ctx)
            return chatBox.containsText(text) && survivalExpert.size > 0 && survivalExpert[0].isOnScreen()
        }

        override suspend fun execute() {
            val survivalExpert = NPCs(ctx).findNpc(8503)
            survivalExpert[0].talkTo()
            // WAit till the continue is avaliable
            Players(ctx).getLocal().waitTillIdle()

            Dialog(ctx).continueDialog()
        }

    }

    class OpenInvetory(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("To view the item you've been given, you'll")
        }

        override suspend fun execute() {
            Tabs(ctx).openTab(Tabs.Tab_Types.Inventory)
        }

    }

    class CatchSomeShrimp(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "catch some shrimp"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            catchShrimp(ctx)

            if (Tabs(ctx).isTabFlashing(Tabs.Tab_Types.Skills)) {
                Tabs(ctx).openTab(Tabs.Tab_Types.Skills)
            }
        }
        companion object {
            suspend fun catchShrimp(ctx: Context) {
                val shrimps = NPCs(ctx).findNpc(3317)
                shrimps[0].turnTo()
                shrimps[0].interact("Net")
                // Wait till shrimp is in Inventory
                Utils.waitFor(10, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Inventory(ctx).getCount(SHRIMP_ID) > 0
                    }
                })
            }
        }
    }



    class ClickSkillsTab(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "on the flashing bar graph icon near the inventory"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            Tabs(ctx).openTab(Tabs.Tab_Types.Skills)
        }
    }

    class TalkToSurvivalGuideAfterSkillsTab(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "this menu you can view your skills."
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            val survivalExpert = NPCs(ctx).findNpc(8503)
            survivalExpert[0].talkTo()
            // WAit till the continue is avaliable
            Utils.waitFor(4, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Dialog(ctx).isDialogUp()
                }
            })

            Dialog(ctx).continueDialog()

        }

    }

    class ChopTree(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "time to cook your shrimp. However, you require"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            chopTree(ctx)

            Dialog(ctx).continueDialog()


        }
        companion object{
            suspend fun chopTree(ctx: Context) {
                val trees = GameObjects(ctx).find(9730, sortByDistance = true)
                // Should be more than 4, lets pick a random one between 1 and 4
                trees[Random.nextInt(0, 3)].interact("Chop")

                // Wait till we get a log in the invetory.
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Inventory(ctx).getCount(LOGS_ID_2511) > 0
                    }
                })
            }
        }

    }



    class LightLog(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "that you have some logs, it's time"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            // Use tinderbox(590) with logs(2511)
            lightFire(client, ctx)
        }

        companion object{
            suspend fun lightFire(client: Client, ctx: Context) {
                Inventory(ctx).open()
                Inventory(ctx).getItem(590)?.click()
                Inventory(ctx).getItem(LOGS_ID_2511)?.click()
                delay(Random.nextLong(2500, 4500))
                //Wait till hes not doing anything which should mean fire has been made
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(ctx).getLocal().isIdle()
                    }
                })
            }
        }
    }


    class CookShrimp(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Now it's time to get cooking."
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            // Check to make sure we have shrimp, If not go fish for them
            if (Inventory(ctx).getCount(SHRIMP_ID) == 0) {
                CatchSomeShrimp.catchShrimp(ctx)
            }

            var fires = GameObjects(ctx).find(26185, sortByDistance = true)
            //No fire & no logs
            if (fires.size == 0 && Inventory(ctx).getCount(LOGS_ID_2511) == 0) {
                ChopTree.chopTree(ctx)
            }

            //If no fire && have logs, light a fire
            fires = GameObjects(ctx).find( 26185, sortByDistance = true)
            if (fires.size == 0 && Inventory(ctx).getCount(LOGS_ID_2511) > 0) {
                LightLog.lightFire(client, ctx)
            }

            // Check if there is a fire cook the shrimp
            fires = GameObjects(ctx).find(26185, sortByDistance = true)
            if (fires.size > 0) {
                Inventory(ctx).open()
                Inventory(ctx).getItem(SHRIMP_ID)?.click()
                // The fire is an animated object so it thows a NPE when trying to interacte with model.
                if (fires[0].sceneryObject != null) {
                    val point = Calculations.worldToScreen(
                            fires[0].sceneryObject!!.getCenterX(),
                            fires[0].sceneryObject!!.getCenterY(),
                            0,
                            ctx

                    )
                    Interact(ctx).interact(point, "Use")
                }

                //Wait till idle
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(ctx).getLocal().isIdle()
                    }
                })
                Dialog(ctx).continueDialog()
            }
        }
    }

    class OpenGateAfterFishing(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Well done, you've just cooked your first meal!"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            println("START: Going to open gate")
            // Open gate at 3090,3092
            val gateTile = Tile(3090, 3092, 0, ctx)
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
            val gates = GameObjects(ctx).find(gateIDs.random(), sortByDistance = true)
            if (gates.size > 0) {
                gates[0].turnTo()
                gates[0].interact("Open")
                Players(ctx).getLocal().waitTillIdle()
                delay(Random.nextLong(100, 150))
            }
            println("Complete: Going to open gate")
        }

    }

    class MoveToKitchen(val ctx: Context)  : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Follow the path until you get to the door with the yellow arrow above it."
            val percentComplete = getPercentComplete(ctx)
            return dialogWidget.containsText(text) && percentComplete == .196875
        }

        override suspend fun execute() {
            val tile = Tile(3079, 3084, 0, ctx)
            if (tile.distanceTo() > 5) {
                tile.clickOnMiniMap()
                Utils.waitFor(10, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return tile.distanceTo() < 5
                    }
                })
            }

            val gameObjects = GameObjects(ctx).find(9709, sortByDistance = true)
            if (gameObjects.size > 0) {
                gameObjects[0].interact("Open")
            }
        }
    }

    class TalkToMasterChef(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Talk to the chef indicated"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            NPCs(ctx).findNpc(3305)[0].talkTo()

            delay(Random.nextLong(3000, 5000))

            Dialog(ctx).continueDialog()
        }
    }

    class MakeDough(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "This is the base for many meals"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            // Mix water(1929) and flower(2516)
            Inventory(ctx = ctx).getItem(1929)?.click()
            Inventory(ctx = ctx).getItem(2516)?.click()
            delay(Random.nextLong(1250, 1650))
            Dialog(ctx).continueDialog()


        }

    }

    class MakeBread(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Now you have made the dough,"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            //dough is 2307
            //Range is 9736
            Inventory(ctx).open()
            val range = GameObjects(ctx).find(9736)[0]
            Camera(client, keyboard).turnTo(range)
            //TODO - Need to improve ineract when menu is full
            range.interact("Cook Range")
            // Wait till bread in inventory
            Utils.waitFor(4, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return Dialog(ctx).isDialogUp()
                }
            })
            delay(Random.nextLong(1250, 1650))
            Dialog(ctx).continueDialog()

        }

    }

    class ExitKitchen(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "You've baked your first loaf of bread"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            //Location 3073,3090

            val tileNearDoor = Tile(3073, 3090, ctx = ctx)
            if (tileNearDoor.distanceTo() > 4) {
                tileNearDoor.clickOnMiniMap()
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return tileNearDoor.distanceTo() < 4
                    }
                })
            }

            Camera(client, keyboard).turnWest()

            //DOOR 9710
            val door = GameObjects(ctx).find(9710)
            if (door.size > 0) {
                door[0].interact("Open Door")
            }
        }

    }

    class TurnOnRun(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "When navigating the world, you can either run or walk"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            Run(ctx).activateRun()
        }

    }

    class MoveToNextBuilding(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Follow the path to the next guide"
            return dialogWidget.containsText(text) && getPercentComplete(ctx) == .296875
        }

        override suspend fun execute() {
            val walkingPath = arrayListOf(
                Tile(3073, 3103, ctx = ctx), Tile(3074, 3117, ctx = ctx),
                Tile(3079, 3127, ctx = ctx), Tile(3086, 3127, ctx = ctx)
            )
            Walking.walkPath(walkingPath)
            //Open Door(9716)
            Camera(client, keyboard).setHighPitch()
            Camera(client, keyboard).turnSouth()
            val doors = GameObjects(ctx).find("Door", sortByDistance = true)
            if (doors.size > 0) {
                doors[0].interact("Open Door")
            }


        }

    }

    class TalkToQuestGuide(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "It's time to learn about quests!"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            val questGuide = NPCs(ctx).findNpc("Quest Guide")
            if (questGuide.size > 0) {
                if (!questGuide[0].isOnScreen()) Camera(client, keyboard).turnTo(questGuide[0])
                questGuide[0].interact("Talk-to Quest Guide")
                Utils.waitFor(3, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(ctx).getLocal().isIdle()
                    }
                })
                delay(Random.nextLong(100, 150))
                Dialog(ctx).continueDialog()
            }
        }
    }

    class OpenQuestList(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Click on the flashing icon to the left of your inventory"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            Tabs(ctx).openTab(Tabs.Tab_Types.QuestList)
        }

    }

    class TalkToQuestGuide2ndTime(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "This is your quest journal."
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            val questGuide = NPCs(ctx).findNpc("Quest Guide")
            if (questGuide.size > 0) {
                if (!questGuide[0].isOnScreen()) Camera(client, keyboard).turnTo(questGuide[0])
                questGuide[0].interact("Talk-to Quest Guide")
                Utils.waitFor(3, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(ctx).getLocal().isIdle()
                    }
                })
                Dialog(ctx).continueDialog()
            }
        }
    }

    class GoDownToTheCaves(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "It's time to enter some caves"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            Camera(client, keyboard).setHighPitch()
            // Go down ladder
            val ladder = GameObjects(ctx).find("Ladder")
            if (ladder.size > 0) {
                Camera(client, keyboard).turnTo(ladder[0])
                ladder[0].interact("Climb-down Ladder")
                delay(Random.nextLong(3500, 6400))
            }
        }

    }

    class WalkAndTalkToSmitingAndMiningGuide(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Next let's get you a weapon,"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            val walkingPath = arrayListOf(Tile(3079, 9512, ctx = ctx), Tile(3081, 9504, ctx = ctx))
            Walking.walkPath(walkingPath)
            val miningGuide = NPCs(ctx).findNpc("Mining Instructor")
            if (miningGuide.size > 0) {
                Camera(client, keyboard).setHighPitch()
                if (!miningGuide[0].isOnScreen()) miningGuide[0].turnTo()
                miningGuide[0].talkTo()
                delay(Random.nextLong(1250, 3650))
                Dialog(ctx).continueDialog()

            }
        }

    }

    class MineRock{
        companion object{
            suspend fun mineRock(ctx: Context) {
                val rocks = GameObjects(ctx).find("Rocks", sortByDistance = true)
                if (rocks.size > 0) {
                    val oldInventoryCount = Inventory(ctx).getCount()
                    rocks[0].interact("Mine")
                    Utils.waitFor(8, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return oldInventoryCount != Inventory(ctx).getCount()
                        }
                    })
                }
                Dialog(ctx).continueDialog()
            }
        }
    }

    class MineTin(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "It's quite simple really. To mine a rock, all you need"
            return dialogWidget.containsText(text)
                    || dialogWidget.containsText("Now that you have some copper ore,")
        }

        override suspend fun execute() {
            //walk to tile(3076,9505
            //Mine rocks
            val miningspot = Tile(3076, 9505, 0, ctx)
            miningspot.clickOnMiniMap()
            Players(ctx).getLocal().waitTillIdle()
            Camera(client, keyboard).setHighPitch()
            MineRock.mineRock(ctx)
        }
    }



    class MineCopper(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val text = "Now that you have some tin ore"
            return dialogWidget.containsText(text)
        }

        override suspend fun execute() {
            val miningspot = Tile(3085, 9502, 0, ctx)
            if (miningspot.distanceTo() > 5) {
                miningspot.clickOnMiniMap()
                delay(Random.nextLong(3500, 5500))
            }
            Camera(client, keyboard).setHighPitch()
            MineRock.mineRock(ctx)
        }

    }

    class SmeltBronze(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You now have some tin ore and some copper ore.")
        }

        override suspend fun execute() {
            val miningspot = Tile(3079, 9498, 0, ctx)
            if (miningspot.distanceTo() > 3) {

                miningspot.clickOnMiniMap()
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return miningspot.distanceTo() < 3
                    }
                })
            }

            val furnace = GameObjects(ctx).find("Furnace")[0]
            if (!furnace.isOnScreen()) furnace.turnTo()
            furnace.click()
        }

    }

    class TalkToMiningGuideAboutSmiting(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You've made a bronze bar!")
        }

        override suspend fun execute() {

            val miningGuide = NPCs(ctx).findNpc("Mining Instructor")
            if (miningGuide.size > 0) {
                miningGuide[0].turnTo()
                if (Tile(3081, 9504, ctx = ctx).distanceTo() > 4) {
                    Tile(3081, 9504, ctx = ctx).clickOnMiniMap()
                }
                miningGuide[0].talkTo()
                Players(ctx).getLocal().waitTillIdle()
                Dialog(ctx).continueDialog()

            }
        }

    }

    class MakeBronzeDagger(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("To smith you'll need a hammer") || dialogWidget.containsText("Use an anvil to open") || dialogWidget.containsText(
                "Now you have the smithing"
            )
        }

        override suspend fun execute() {
            //Find Anvil
            val anvil = GameObjects(ctx).find("Anvil", sortByDistance = true)
            if (anvil.size > 0) {

                val index = (0..1).random()
                anvil[index].turnTo()
                Inventory(ctx = ctx).open()
                anvil[index].click()
                delay(Random.nextLong(300, 700))
                Players(ctx).getLocal().waitTillIdle()
                //Wait for smiting widgets
                Widgets.waitTillWidgetNotNull(ctx,312, 9)

                val oldInventoryCount = Inventory(ctx).getCount()
                val daggerSmitingPage = WidgetItem(Widgets.find(ctx, 312, 9)?.getChildren()?.get(2), ctx = ctx)
                if (daggerSmitingPage.widget != null) {
                    daggerSmitingPage.click()

                    Utils.waitFor(4, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return oldInventoryCount != Inventory(ctx).getCount()
                        }
                    })
                }


            }
        }

    }

    class AfterSmithingMovetoGate(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Congratulations, you've made your first weapon")
        }

        override suspend fun execute() {
            val walkingPath = arrayListOf(Tile(3086, 9505, ctx = ctx), Tile(3091, 9503, ctx = ctx))
            Walking.walkPath(walkingPath)
            val gate = GameObjects(ctx).find("Gate", sortByDistance = true)
            if (gate.size > 0) {
                Camera(client, keyboard).setHighPitch()
                Camera(client, keyboard).turnEast()
                gate[0].interact("Open")
                Players(ctx).getLocal().waitTillIdle()
            }

        }

    }

    class TalkToCombatInstructor(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("In this area you will find out about melee and ranged combat.")
        }

        override suspend fun execute() {
            Camera(client, keyboard).setHighPitch()
            // Move to combat insturctor
            val tileNearCombatInstructor = Tile(3107, 9509, ctx = ctx)
            if (tileNearCombatInstructor.distanceTo() > 5) {
                tileNearCombatInstructor.clickOnMiniMap()
                val local = Players(ctx).getLocal()
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return tileNearCombatInstructor.distanceTo() < 5 && local.isIdle()
                    }
                })
            }

            //Talk with combat instructor
            val combatInstructor = NPCs(ctx).findNpc("Combat Instructor")
            combatInstructor[0].talkTo()
            Players(ctx).getLocal().waitTillIdle()
            Dialog(ctx).continueDialog()

        }

    }

    class OpenEquipment(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You now have access to a new")
        }

        override suspend fun execute() {
            Tabs(ctx).openTab(Tabs.Tab_Types.Equiptment)
        }

    }

    class OpenEquipmentStats(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("This is your worn inventory")
        }

        override suspend fun execute() {
            Equipment(ctx).open()
            Equipment(ctx).clickButton(Equipment.Companion.Slot.EquiptmentStats)
            delay(Random.nextLong(1500, 2637))
        }

    }

    class EquipBronzeDagger(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You can see what items you are")
        }

        override suspend fun execute() {
            Inventory(ctx = ctx).getItem(1205)?.click()
            delay(Random.nextLong(2500, 4000))
            WidgetItem(Widgets.find(ctx, 84, 4), ctx = ctx).click() // Close out of Equoptment status
        }

    }

    class SpeakWithCombatAfterBronzeDaggerEquipt(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You're now holding your dagger")
        }

        override suspend fun execute() {
            Camera(client, keyboard).setHighPitch()
            //Talk with combat instructor
            val combatInstructor = NPCs(ctx).findNpc("Combat Instructor")
            combatInstructor[0].talkTo()
            Dialog(ctx).continueDialog()

        }

    }

    class EquipLongSwordAndShield(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("To unequip an item, go to your")
        }

        override suspend fun execute() {
            Inventory(ctx = ctx).getItem(1277)?.click()
            delay(Random.nextLong(1500, 2500))
            Inventory(ctx = ctx).getItem(1171)?.click()
            delay(Random.nextLong(1500, 2500))
        }

    }

    class OpenCombatTab(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Click on the flashing crossed")
        }

        override suspend fun execute() {
            Tabs(ctx).openTab(Tabs.Tab_Types.Combat)
        }

    }

    class GoIntoRatCage(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("This is your combat interface. From here,")
        }

        override suspend fun execute() {
            Camera(client, keyboard).setHighPitch()
            //Walk over to tile
            val tileNearGate = Tile(3111, 9519, ctx = ctx)
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
            val gates = GameObjects(ctx).find("Gate", sortByDistance = true)
            if (gates.size > 0) {
                Camera(client, keyboard).turnWest()
                gates[0].interact("Open")
                Utils.waitFor(2, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(ctx).getLocal().isIdle()
                    }
                })
            }

        }

    }

    class MeleeKillRat(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("It's time to slay some rats!")
        }

        override suspend fun execute() {
            Camera(client, keyboard).setHighPitch()
            val rats = NPCs(ctx).findNpc("Giant rat")
            if (rats.size > 0) {
                val randomIndex = (0..5).random()
                rats[randomIndex].interact("Attack")
                Utils.waitFor(20, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(ctx).getLocal().isIdle()
                    }
                })
            }
        }

    }


    class GoTalkToCombatInstructorFor2ndTime(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        val ratCageArea = Area(
                Tile(3109, 9521, ctx = ctx), Tile(3110, 9519, ctx = ctx),
                Tile(3110, 9518, ctx = ctx), Tile(3109, 9516, ctx = ctx), Tile(3109, 9515, ctx = ctx),
                Tile(3108, 9514, ctx = ctx), Tile(3107, 9514, ctx = ctx), Tile(3106, 9513, ctx = ctx),
                Tile(3106, 9512, ctx = ctx), Tile(3105, 9511, ctx = ctx), Tile(3103, 9512, ctx = ctx),
                Tile(3100, 9512, ctx = ctx), Tile(3099, 9514, ctx = ctx), Tile(3098, 9515, ctx = ctx),
                Tile(3097, 9517, ctx = ctx), Tile(3098, 9519, ctx = ctx), Tile(3099, 9521, ctx = ctx),
                Tile(3100, 9522, ctx = ctx), Tile(3101, 9522, ctx = ctx), Tile(3102, 9525, ctx = ctx),
                Tile(3104, 9524, ctx = ctx), Tile(3106, 9522, ctx = ctx), Tile(3108, 9522, ctx = ctx),
                Tile(3109, 9521, ctx = ctx)
        )
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Pass through the gate and talk to the combat")
        }

        override suspend fun execute() {
            Camera(client, keyboard).setHighPitch()
            Camera(client, keyboard).turnEast()
            // Check to see if we are still in the rat cage
            if (ratCageArea.containsOrIntersects(Players(ctx).getLocal().getGlobalLocation())) {
                val gates = GameObjects(ctx).find("Gate", sortByDistance = true)
                if (gates.size > 0) {
                    if (!gates[0].isOnScreen()) {
                        gates[0].clickOnMiniMap()
                        Players(ctx).getLocal().waitTillIdle()
                    }
                    gates[0].interact("Open")
                    Players(ctx).getLocal().waitTillIdle()
                }
            }

            if (!ratCageArea.containsOrIntersects(Players(ctx).getLocal().getGlobalLocation())) {
                val combatInstructor = NPCs(ctx).findNpc("Combat Instructor")
                if (combatInstructor[0].distanceTo() > 5) {
                    combatInstructor[0].clickOnMiniMap()
                    combatInstructor[0].waitTillNearObject()
                }

                combatInstructor[0].talkTo()
                Dialog(ctx).continueDialog()
            }
        }

    }

    class KillRatWithBow(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Now you have a bow and some arrows.")
        }

        override suspend fun execute() {
            Inventory(ctx).open()
            Inventory(ctx = ctx).getItem(841)?.click()
            delay(Random.nextLong(1500, 2500))
            Inventory(ctx = ctx).getItem(882)?.click()
            delay(Random.nextLong(1500, 2500))

            //Move over to a better spot to kill the rats
            val idealSpot = Tile(3110, 9515, ctx = ctx)
            if(idealSpot.distanceTo() > 3){
                idealSpot.clickOnMiniMap()
                Players(ctx).getLocal().waitTillIdle()
            }

            val rats = NPCs(ctx).findNpc("Giant rat")
            if (rats.size > 0) {
                val randomIndex = (0..2).random()
                rats[randomIndex].turnTo()
                rats[randomIndex].interact("Attack")
                delay(Random.nextLong(1000, 1500))
                Players(ctx).getLocal().waitTillIdle()
            }
        }

    }

    class ExitCaves(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You have completed the tasks here")
        }

        override suspend fun execute() {
            Camera(client, keyboard).setHighPitch()
            val tileNearLadder = Tile(3110, 9526, ctx = ctx)
            if (tileNearLadder.distanceTo() > 3) {
                tileNearLadder.clickOnMiniMap()
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return tileNearLadder.distanceTo() < 3
                    }
                })

            }

            val ladder = GameObjects(ctx).find("Ladder", sortByDistance = true)
            if (ladder.size > 0) {
                ladder[0].interact("Climb")
                Players(ctx).getLocal().waitTillIdle()
            }
        }

    }

    class UseBank(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Follow the path and you will come to the front of the building")
        }

        override suspend fun execute() {
            //Sometimes we find out selfs still in the caves, lets exit it
            val caveTile = Tile(3110,9526, ctx = ctx)
            if(caveTile.distanceTo() < 10){
                val ladder = GameObjects(ctx).find("Ladder", sortByDistance = true)
                if (ladder.size > 0) {
                    ladder[0].interact("Climb")
                    Players(ctx).getLocal().waitTillIdle()
                }
            }

            val tileNearBank = Tile(3122, 3123, ctx = ctx)
            if (tileNearBank.distanceTo() > 5) {
                tileNearBank.clickOnMiniMap()
                Utils.waitFor(10, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return tileNearBank.distanceTo() < 3
                    }
                })

            }

            val bankBooth = GameObjects(ctx).find("Bank booth", sortByDistance = true)
            if (bankBooth.size > 0) {
                bankBooth[0].interact("Use")
            }

        }

    }

    class CloseBankAndDoPollBooth(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("This is your bank.")
        }

        override suspend fun execute() {
            Bank(ctx).close()
            PollBooth.openPollBooth(ctx)
        }

    }

    class PollBooth {
        companion object {
            suspend fun openPollBooth(ctx: Context) {
                val pollBooth = GameObjects(ctx).find(26815)
                pollBooth[0].turnTo()
                val pollTile = Tile(3119, 3121, ctx.client.getPlane(), ctx)
                if (pollTile.distanceTo() > 3)
                    Tile(3120, 3121, ctx.client.getPlane(), ctx).clickOnMiniMap()

                pollTile.click()
                delay(Random.nextLong(1500, 2500))
                Dialog(ctx).continueDialog()
            }
             suspend fun closePollWidget(ctx: Context) {
                try {
                    var pollWidget = Widgets.find(ctx, 345, 0)
                    if (pollWidget != null) {
                        val pollExitWidget = WidgetItem(Widgets.find(ctx, 345, 2)?.getChildren()?.get(3), ctx = ctx)
                        pollExitWidget.click()
                    }
                    pollWidget = Widgets.find(ctx, 310, 0)
                    if (pollWidget != null) {
                        val pollExitWidget = WidgetItem(Widgets.find(ctx, 310, 2)?.getChildren()?.get(3), ctx = ctx)
                        pollExitWidget.click()
                    }

                } catch (e: Exception) {
                    println("ERROR: Somthing happened when trying to find the poll widget")
                }
            }
        }
    }

    class DoPollBooth(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Now it's time for a quick look at polls")
        }

        override suspend fun execute() {
            //TODO figure out how to access moving objects OR need to find a more center tile point
            PollBooth.openPollBooth(ctx)
            Dialog(ctx).continueDialog()
            //If poll widget open, Close out of polling booth widget (310,2) child index 3
            PollBooth.closePollWidget(ctx)

        }

    }



    class ClosePollAndMoveOutOfBank(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Polls are run periodically to let the Old School")
        }

        override suspend fun execute() {
            PollBooth.closePollWidget(ctx)
            //Open Door(9721) at location(3125,3124)
            val doors = GameObjects(ctx).find(9721, sortByDistance = true)
            if (doors.isNotEmpty()) {
                doors.forEach {
                    if (it.getGlobalLocation().x == 3125 && it.getGlobalLocation().y == 3124) {
                        it.turnTo()
                        it.interact("Open")
                        Utils.waitFor(6, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return Players(ctx).getLocal().isIdle() && Players(ctx).getLocal().getGlobalLocation().x == 3125
                            }
                        })
                    }
                }
            }
        }

    }

    class TalkToAccountManager(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("The guide here will tell you all about your account.") ||
                    dialogWidget.containsText("This is your Account Management menu")
        }

        override suspend fun execute() {
            val accountManager = NPCs(ctx).findNpc("Account Guide")
            if (accountManager.isNotEmpty()) {
                Camera(client, keyboard).setHighPitch()
                accountManager[0].talkTo()
                delay(Random.nextLong(2500, 4500))
                Dialog(ctx).continueDialog()
            }
        }

    }

    class OpenAccountManager(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Click on the flashing icon to open your Account Management")
        }

        override suspend fun execute() {
            Tabs(ctx).openTab(Tabs.Tab_Types.AccountManagement)
        }

    }

    class ExitAccountManagerRoom(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Continue through the next door.")
        }

        override suspend fun execute() {
            val doors = GameObjects(ctx).find(9722, sortByDistance = true)
            if (doors.isNotEmpty()) {
                doors.forEach {
                    if (it.getGlobalLocation().x == 3130 && it.getGlobalLocation().y == 3124) {
                        Camera(client, keyboard).turnEast()
                        Camera(client, keyboard).setHighPitch()
                        it.interact("Open")
                        Players(ctx).getLocal().waitTillIdle()
                        delay(Random.nextLong(100, 150))
                    }
                }
            }
        }

    }

    class MoveToChapelAndTalkToBrotherBrace(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Follow the path to the chapel")
                    || dialogWidget.containsText("Talk with Brother Brace")
                    || dialogWidget.containsText("These two lists can be very helpful for keeping track")
        }

        override suspend fun execute() {
            var brotherBrace = NPCs(ctx).findNpc("Brother Brace")
            val pathToChapel = arrayListOf(Tile(3132, 3115, ctx = ctx), Tile(3130, 3107, ctx = ctx), Tile(3124, 3106, ctx = ctx))
            if ((brotherBrace.isNotEmpty() && brotherBrace[0].distanceTo() > 13) || brotherBrace.isEmpty())
                Walking.walkPath(pathToChapel)
            Camera(client, keyboard).setHighPitch()
            brotherBrace = NPCs(ctx).findNpc("Brother Brace")
            if (brotherBrace.size > 0) {
                if (!brotherBrace[0].isOnScreen())
                    brotherBrace[0].turnTo()
                brotherBrace[0].talkTo()
                Players(ctx).getLocal().waitTillIdle()
                Dialog(ctx).continueDialog()
            }
        }

    }

    class OpenPrayerTab(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Click on the flashing icon to open the Prayer menu.")
        }

        override suspend fun execute() {
            Tabs(ctx).openTab(Tabs.Tab_Types.Prayer)
        }

    }

    class OpenFriendsTab(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You should now see another new icon. Click on the flashing face")
        }

        override suspend fun execute() {
            Tabs(ctx).openTab(Tabs.Tab_Types.FriendsList)
        }

    }

    class ExitChapleHouse(val ctx: Context, val keyboard: Keyboard) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You're almost finished on tutorial island")
        }

        override suspend fun execute() {
            val doors = GameObjects(ctx).find(9723, sortByDistance = true)
            if (doors.isNotEmpty()) {
                doors.forEach {
                    if (it.getGlobalLocation().x == 3122 && it.getGlobalLocation().y == 3102) {
                        Camera(client, keyboard).turnSouth()
                        Camera(client, keyboard).setHighPitch()
                        it.interact("Open")
                        delay(Random.nextLong(1500, 2500))
                        Players(ctx).getLocal().waitTillIdle()
                    }
                }
            }
        }

    }

    class GoToWizardHouseAndSpeakWithWizard(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Follow the path to the wizard")
                    || dialogWidget.containsText("This is your magic interface")
        }

        override suspend fun execute() {
            val pathToWizardHouse = arrayListOf(
                Tile(3128, 3090, ctx = ctx), Tile(3138, 3087, ctx = ctx),
                Tile(3140, 3087, ctx = ctx)
            )
            if (pathToWizardHouse[2].distanceTo() > 6) {
                Walking.walkPath(pathToWizardHouse)
            }
            val magicInstructor = NPCs(ctx).findNpc("Magic Instructor")
            if (magicInstructor.isNotEmpty()) {
                magicInstructor[0].turnTo()
                magicInstructor[0].talkTo()
                Players(ctx).getLocal().waitTillIdle()
                Dialog(ctx).continueDialog()

            }
        }

    }

    class OpenMagicTab(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("Open up the magic interface")
        }

        override suspend fun execute() {
            Tabs(ctx).openTab(Tabs.Tab_Types.Magic)
        }

    }

    class SelectWindStrikeAndAttackChicken(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You now have some runes.")
        }

        override suspend fun execute() {
            Magic(ctx).cast(Magic.Companion.Spells.Wind_Strike)
            //Attack chicken
            val chickens = NPCs(ctx).findNpc("Chicken")
            if (chickens.isNotEmpty()) {
                val randChick = Random.nextInt(0, chickens.size - 1)
                chickens[randChick].turnTo()
                chickens[randChick].interact("Cast")
                Utils.waitFor(7, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return Players(ctx).getLocal().isIdle() && chickens[randChick].isIdle()
                    }
                })

            }
        }

    }

    class ExitTutIsland(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            return dialogWidget.containsText("You're nearly finished with the tutorial")
        }

        override suspend fun execute() {
            val magicInstructor = NPCs(ctx).findNpc("Magic Instructor")
            if (magicInstructor.isNotEmpty()) {
                if (!magicInstructor[0].isOnScreen()) magicInstructor[0].turnTo()
                magicInstructor[0].talkTo()
                Players(ctx).getLocal().waitTillIdle()
                Dialog(ctx).continueDialog()
                Dialog(ctx).selectionOption("Yes")
                Dialog(ctx).continueDialog()
                Dialog(ctx).selectionOption("No")
                Dialog(ctx).continueDialog()

            }
        }

    }

    class MainlandLogout(val ctx: Context) : Job(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem): Boolean {
            val completedWidget = WidgetItem(Widgets.find(ctx, 193, 2), ctx = ctx)
            return completedWidget.containsText("Welcome to Lumbridge!")
        }

        override suspend fun execute() {
            //Run few different paths and then logout
            val pathNorth = arrayListOf(
                Tile(3234, 3225, ctx = ctx), Tile(3224, 3237, ctx = ctx),
                Tile(3218, 3250, ctx = ctx), Tile(3214, 3262, ctx = ctx)
            )
            val pathEast = arrayListOf(
                Tile(3240, 3225, ctx = ctx), Tile(3256, 3227, ctx = ctx),
                Tile(3258, 3233, ctx = ctx), Tile(3257, 3245, ctx = ctx), Tile(3251, 3257, ctx = ctx)
            )
            val pathSouth = arrayListOf(
                Tile(3235, 3204, ctx = ctx), Tile(3243, 3193, ctx = ctx),
                Tile(3241, 3181, ctx = ctx), Tile(3231, 3175, ctx = ctx), Tile(3238, 3163, ctx = ctx)
            )
            val pathWest = arrayListOf(
                Tile(3223, 3219, ctx = ctx), Tile(3213, 3210, ctx = ctx),
                Tile(3206, 3210, ctx = ctx)
            )
            if (Random.nextBoolean()) {
                println("Walking path random")
                val investigationPaths = arrayListOf(pathNorth, pathSouth, pathEast)
                val path = investigationPaths.random()
                //Walk the path and then come back
                Walking.walkPath(path)
                Walking.walkPath(path, reverse = true)
                Logout(ctx).logout()
            } else {
                println("Walking path west")
                Walking.walkPath(pathWest)
                Logout(ctx).logout()
            }

        }

    }
   
}