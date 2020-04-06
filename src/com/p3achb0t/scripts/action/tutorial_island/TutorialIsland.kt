package com.p3achb0t.scripts.action.tutorial_island

import com.p3achb0t.api.script.ActionScript
import com.p3achb0t.api.Context
import com.p3achb0t.api.script.ScriptManifest
import com.p3achb0t.api.wrappers.Area
import com.p3achb0t.api.wrappers.Interact
import com.p3achb0t.api.wrappers.Tile
import com.p3achb0t.api.wrappers.Walking
import com.p3achb0t.api.wrappers.tabs.Inventory
import com.p3achb0t.api.wrappers.tabs.Magic
import com.p3achb0t.api.wrappers.tabs.Tabs
import com.p3achb0t.api.wrappers.utils.Calculations
import com.p3achb0t.api.wrappers.utils.Utils
import com.p3achb0t.api.wrappers.widgets.Widget
import com.p3achb0t.api.wrappers.widgets.WidgetID
import com.p3achb0t.api.wrappers.widgets.WidgetItem
import kotlinx.coroutines.delay
import org.apache.commons.lang.time.StopWatch
import java.awt.Color
import java.awt.Graphics
import kotlin.random.Random


private val SHRIMP_ID = 2514
private val LOGS_ID_2511 = 2511
@ScriptManifest("Quests","TutorialIsland","P3aches", "0.1")
class TutorialIsland: ActionScript()  {
    val stopwatch = StopWatch()
    var currentJob = ""
    init {
        //validate = true
    }
    override suspend fun loop() {

        run()
        //Delay between 0-50 ms
        delay((Math.random() * 50).toLong())

    }

    override fun start() {
        try {
            stopwatch.start()
        } catch (e: Exception) {
        }
        println("Running Start")
        println("Running Start2")
    }

    override fun stop() {
        println("Stopping tutorial island script")
    }

    override fun draw(g: Graphics) {
        g.color = Color.black
        g.drawString("Current Runtime: $stopwatch", 10, 450)
        g.drawString(currentJob, 10, 460)
        super.draw(g)
    }



    var isInititilized = false
    val jobs = ArrayList<TutTask>()
    fun init() {

        jobs.add(ICantReachThatDialog(ctx))
        jobs.add(PickName(ctx))
        jobs.add(SelectCharOutfit(ctx))
        jobs.add(ChatWithGielinorGuide(ctx))
        jobs.add(OpenOptions(ctx))
        jobs.add(FinalChatWithGielinor(ctx))
        jobs.add(OpenDoorFromFirstBuilding(ctx))
        jobs.add(TurnOffRoofsAndSound(ctx))
        jobs.add(MoveToFishingSpot(ctx))
        jobs.add(TalkToSurvivalExpertFirstTime(ctx))
        jobs.add(OpenInvetory(ctx))
        jobs.add(CatchSomeShrimp(ctx))
        jobs.add(ClickSkillsTab(ctx))
        jobs.add(TalkToSurvivalGuideAfterSkillsTab(ctx))
        jobs.add(ChopTree(ctx))
        jobs.add(ContinueFromChopTree(ctx))
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
        jobs.add(ContinueDialog(ctx))
        isInititilized = true
    }

    suspend fun run() {
        if (!isInititilized) init()
//        if (!LoggingIntoClient.loggedIn) return
        jobs.forEach {
            val chatBox = WidgetItem(ctx.widgets.find(263, 1), ctx = ctx)
            if (it.isValidToRun(chatBox)) {
                println("Running: ${it.javaClass.name}")
                currentJob = it.javaClass.name
                it.execute()
                println("Completed: ${it.javaClass.name}")
            }
        }
    }

    companion object {
        fun getPercentComplete(ctx: Context): Double {
            // widget for progress 614,18
            val complete = WidgetItem(ctx.widgets.find(614, 18), ctx = ctx).widget?.getWidth()?.toDouble()
                    ?: 0.0
            //widget for total 614, 17
            val total = WidgetItem(ctx.widgets.find(614, 17), ctx = ctx).widget?.getWidth()?.toDouble() ?: 0.0

            return (complete / total)
        }
    }


    class ContinueDialog(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "Click here to continue"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            ctx.dialog.continueDialog()
        }
    }

    class ICantReachThatDialog(val ctx: Context): TutTask(ctx.client){
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return WidgetItem(ctx.widgets.find(162,44),ctx=ctx).containsText("I can't reach that!")
        }

        override suspend fun execute() {
            WidgetItem(ctx.widgets.find(162,45), ctx=ctx).click()
        }

    }

    class PickName(val ctx: Context)  : TutTask(ctx.client) {

        private val names = arrayListOf(
                "rpg", "fonic", "dancingnancie", "raven321", "georgemagoo", "Darkshadow46236", "stevay1", "ThisLittlePiggy", "joanp62", "tiddler",
                "amit007", "appuru_guru", "hygmnv", "geric03", "marvelmyles", "prairiemama", "blackman_w_hepatitis", "reaper654", "markupman", "Pixelsilk",
                "wealthy-wise-guy", "TimmahC", "korthj", "DrWyckoff", "nurta", "rezonq3", "ghostinc", "bizwerk", "Simonvjara", "ImSprunging",
                "Vongole", "fuchila", "wakjek", "njr831", "terryinsullivan", "lightyears", "Hanebambel", "internetentrophy", "zachman123", "DocAtmo",
                "Snlcoa", "anon11", "preeminence", "HotShotCity", "oznur45", "gg77bird", "Scarf", "neyse1986", "China_Baby", "ANDRESARS",
                "scaldin", "I-_-_-I", "eyeowedubbyaaye", "ksny68", "pairoducks", "etx", "croryf", "Wolfshawk", "HMMcKamikaze", "createjoy",
                "SSCAR348", "Humourless", "Vettexl", "babettem", "foschieviola", "fl1ntlock", "dlvanhorn", "HappyRabbit", "jy24", "juanow",
                "Johnnyduck", "darlingt", "esteban9556", "minja", "hkrl", "errantmonk", "ibesek", "Ayoubi", "PopsiclePete", "dawnbroke",
                "exa24", "batgirlwriting", "Definitive-arts", "felechialoates", "globalzero", "Berk888", "cclorance", "bdic132", "heav85", "hetathia",
                "Kalibek", "D1ABoLiK", "liquidgraph", "kcwolf1985", "twat-in-a-hat", "huganic", "31122008PASQUALE", "EcstaticForSure", "ProfessorX", "stavisbay",
                "te88star", "largo87", "360photo", "cooldick017", "Rich-the-Lionheart", "missbutler", "reemixx", "gcgirl", "lookatmeimposting", "rjfrisina",
                "bigtoe1340", "Johhnnywright", "linkylou", "darrylbooth", "OjibwayMigisi", "skyjet27", "bootsnwrnglrs", "GoalSetting1", "l33p", "princessunicorn",
                "willadean", "vickey_habib", "Tatterhood", "dmazzoni", "mattmck22", "Jermx", "_walker_", "pima54", "limpa", "moomooo1",
                "Matchu23", "donald236", "wilshire123", "reasonwatch", "rockluck", "texashealthpro", "los200", "ongkal", "voetip", "yodi",
                "ahmedkabir", "Doshaza", "tuun537", "c1791y", "uncle_max", "spinysdisease22", "fiebon46", "iidbb76", "x1897e", "datasync",
                "tnei10", "jiuberto", "oko75", "thelist", "ttxkk75", "reaper0033", "hengun", "dda45", "overshard", "Lanatio",
                "Mondonguito", "temporary_insanity", "qqkqq54", "qyqy221", "mac256", "nest69", "melapolis", "osieh2", "sunl86", "ozhoom",
                "ttthekkking", "ikoblik", "many23", "y7933k", "helios1111", "_lowell", "sirfink", "princess729", "elcaminocarwash", "FlipCa",
                "kone62", "sulochani", "blcv12", "ShawnB", "Konig", "cyberadventurer", "RKash1960", "applebeesfan1984", "ahmadkabir", "mathforthewin",
                "mrg00ddude", "bea_gabrielle", "kools", "Rationalwoman", "scienceisgod", "andrewtr89", "hafizx", "landiss77", "Cuba5259", "fafb",
                "dfgdg", "ldvt", "ahmadkelby", "undercommoner", "spookybill", "guruatmoneyassistant", "codemonk", "johnkelly00", "reesan", "hollywooddental"
        )
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return ctx.widgets.isWidgetAvaliable(558, 0)
        }

        override suspend fun execute() {
            println("Picking name")
            //Name widget to click into and type a name 558,7
            val nameEntry = WidgetItem(ctx.widgets.find(558, 7), ctx = ctx)
            nameEntry.click()
            delay(Random.nextLong(2200, 5550))
            println("Sending keys")
            ctx.keyboard.sendKeys(names.random(), sendReturn = true)
            delay(Random.nextLong(2200, 5550))

            // If not a valid name then random name in the follow selections 558,(14,15,16)
            // Once picked It should say available in 558,12
            val validName = WidgetItem(ctx.widgets.find(558, 12), ctx = ctx)
            if (validName.widget?.getText()?.toLowerCase()?.contains("great!")!!) {
                println("Found Valid name!")
            } else {
                val rand = Random.nextInt(14, 16)
                val selectRandomName = WidgetItem(ctx.widgets.find(558, rand), ctx = ctx)
                selectRandomName.click()
                delay(Random.nextLong(2200, 5550))
            }
            //Pick set name in 558,18
            val pickName = WidgetItem(ctx.widgets.find(558, 18), ctx = ctx)
            pickName.click()
            delay(Random.nextLong(2200, 5550))
            Utils.waitFor(4, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return !ctx.widgets.isWidgetAvaliable(558, 0)
                }
            })
            println("Picking name complete")


        }

    }

    class SelectCharOutfit(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return ctx.widgets.isWidgetAvaliable(269, 0)
        }

        override suspend fun execute() {
            val randomNumberOfChanges = Random.nextInt(3, 10)
            println("Making $randomNumberOfChanges of changes")
            for (i in 0..randomNumberOfChanges) {
                val column = Random.nextInt(1, 4)//DO NOT CHANGE THIS
                val widgetIndex = Widget.WidgetIndex("269", "0")
                when (column) {
                    1 -> widgetIndex.childID = (106..112).random().toString()
                    2 -> widgetIndex.childID = (113..119).random().toString()
                    3 -> widgetIndex.childID = arrayListOf(125, 124, 123, 122, 105).random().toString()
                    4 -> widgetIndex.childID = arrayListOf(131, 130, 129, 127, 121).random().toString()
                }
                println("Clicking widget (${widgetIndex.parentID},${widgetIndex.childID})")
                for (j in 0..Random.nextInt(5)) {
                    WidgetItem(ctx.widgets.find(widgetIndex.parentID.toInt(), widgetIndex.childID.toInt()), ctx = ctx).click()
                    delay(Random.nextLong(250, 650))
                }
            }
            //Randomly pick if you are going to be afemale
            if (Random.nextBoolean()) {
                println("Picking Female")
                WidgetItem(ctx.widgets.find(269, 139), ctx = ctx).click()
                delay(Random.nextLong(250, 650))
            } else {
                println("Leaving male")
            }
            //select accept
            WidgetItem(ctx.widgets.find(269, "Accept"), ctx = ctx).click()
            delay(Random.nextLong(1250, 2650))
            println("Completed Character outfit")
        }

    }

    class ChatWithGielinorGuide(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "Before you begin, have a read"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            println("Time to interact with Gielinor Guide")
            val gielinorGuide = ctx.npcs.findNpc("Gielinor Guide")[0]
            if(!ctx.dialog.isContinueAvailable())
                gielinorGuide.interact("Talk-to")
            Utils.waitFor(5, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return ctx.dialog.isDialogUp()
                }
            })
            ctx.dialog.continueDialog()
            ctx.dialog.selectRandomOption()
            delay(Random.nextLong(1250, 1650))
            ctx.dialog.continueDialog()
            println("Interact with Gielinor Guide Complete")
        }

    }

    class OpenOptions(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val chatBox = WidgetItem(ctx.widgets.find(263, 1), ctx = ctx)
            val text = "Options menu"
            return chatBox.containsText(text) && ctx.tabs.getOpenTab() != Tabs.Tab_Types.Options
        }

        override suspend fun execute() {
            ctx.tabs.openTab(Tabs.Tab_Types.Options)
            delay(Random.nextLong(1250, 1650))
        }

    }

    class TurnOffRoofsAndSound(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return ctx.players.getLocal().getGlobalLocation().x == 3098
                    && ctx.players.getLocal().getGlobalLocation().y == 3107
        }

        override suspend fun execute() {
            ctx.tabs.openTab(Tabs.Tab_Types.Options)
            delay(Random.nextLong(500, 1500))
            //Display settings(261,1)child 1
            WidgetItem(ctx.widgets.find(261, 1)?.getChildren()?.get(1), ctx = ctx).click()
            delay(Random.nextLong(500, 1500))
            //Advanced options(261,35
            WidgetItem(ctx.widgets.find(261, 35), ctx = ctx).click()
            delay(Random.nextLong(500, 1500))
            //Turn off roofs(60,14). Texture Id when on is 762
            ctx.widgets.waitTillWidgetNotNull(60, 14)
            val roofToggle = ctx.widgets.find(60, 14)
            if (roofToggle?.getSpriteId2() == 761) {
                WidgetItem(ctx.widgets.find(60, 14), ctx = ctx).click()
                delay(Random.nextLong(500, 1500))
            }
            //Close out of Advanced options widget(60,2) child index 3
            WidgetItem(ctx.widgets.find(60, 2)?.getChildren()?.get(3), ctx = ctx).click()

            //Turn off music
            //Open audio section
            WidgetItem(ctx.widgets.find(261, 1)?.getChildren()?.get(3), ctx = ctx).click()
            delay(Random.nextLong(300, 700))
            WidgetItem(ctx.widgets.find(261, 39), ctx = ctx).click()
            delay(Random.nextLong(300, 700))
            WidgetItem(ctx.widgets.find(261, 45), ctx = ctx).click()
            delay(Random.nextLong(300, 700))
            WidgetItem(ctx.widgets.find(261, 51), ctx = ctx).click()

        }

    }

    class FinalChatWithGielinor(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "On the side"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            println("Time to interact with Gielinor Guide")
            val gielinorGuide = ctx.npcs.findNpc("Gielinor Guide")[0]
            if(!ctx.dialog.isContinueAvailable())
                gielinorGuide.interact("Talk-to")
            Utils.waitFor(5, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return ctx.dialog.isDialogUp()
                }
            })
            ctx.dialog.continueDialog()
            println("Finished final chat with Gielinor")
        }

    }

    class OpenDoorFromFirstBuilding(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "time to meet your first instructor"
            return dialogWidget?.containsText(text) ?: false

        }

        override suspend fun execute() {
            println("START: Opening door and walking to fishing spot")
            // Get doors, find one at location(3098,3107), and open it
            val gameObjects = ctx.gameObjects.find(9398)
            val doorLocation = Tile(3098, 3107, ctx = ctx)
            gameObjects.forEach {
                if (it.getGlobalLocation().x == doorLocation.x && it.getGlobalLocation().y == doorLocation.y) {
                    it.turnTo()
                    it.interact("Open")
                    //Wait till here Tile(3098,3107)
                    Utils.waitFor(4, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return ctx.players.getLocal().getGlobalLocation() == Tile(3098, 3107, ctx = ctx)
                        }
                    })
                }
            }
        }

    }

    class MoveToFishingSpot(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "Follow the path to find the next instructor"
            val chatBox = WidgetItem(ctx.widgets.find(263, 1), ctx = ctx)
            val doorLocation = Tile(3098, 3107, ctx = ctx)
            val playerGlobalLoc = ctx.players.getLocal().getGlobalLocation()

            return chatBox.containsText(text) && (playerGlobalLoc.x == doorLocation.x && playerGlobalLoc.y == doorLocation.y)
        }

        override suspend fun execute() {
            val path = arrayListOf(Tile(3098, 3107, ctx = ctx), Tile(3103, 3103, ctx = ctx), Tile(3102, 3095, ctx = ctx))
            Walking.walkPath(path)
            println("COMPLETE : Opening door and walking to fishing spot")
        }
    }

    class TalkToSurvivalExpertFirstTime(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val survivalExpert = ctx.npcs.findNpc(8503)
            val text = "Follow the path to find the next instructor"
            val chatBox = WidgetItem(ctx.widgets.find(263, 1), ctx = ctx)
            return chatBox.containsText(text) && survivalExpert.size > 0 && survivalExpert[0].isOnScreen()
        }

        override suspend fun execute() {
            val survivalExpert = ctx.npcs.findNpc(8503)
            if (!survivalExpert[0].isOnScreen()) survivalExpert[0].turnTo()
            if(!ctx.dialog.isContinueAvailable())
                survivalExpert[0].talkTo()
            // WAit till the continue is avaliable
            ctx.players.getLocal().waitTillIdle()

            ctx.dialog.continueDialog()
        }

    }

    class OpenInvetory(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("click on the flashing backpack icon") ?: false && ctx.tabs.getOpenTab() != Tabs.Tab_Types.Inventory
        }

        override suspend fun execute() {
            ctx.tabs.openTab(Tabs.Tab_Types.Inventory)
            delay(Random.nextLong(1000, 2000)) // Adding delay Because flashing tab returns right away causing the system to close the tab
        }

    }

    class CatchSomeShrimp(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "catch some shrimp"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            catchShrimp(ctx)

            if (ctx.tabs.isTabFlashing(Tabs.Tab_Types.Skills)) {
                ctx.tabs.openTab(Tabs.Tab_Types.Skills)
                delay(Random.nextLong(1000, 2000)) // Adding delay Because flashing tab returns right away causing the system to close the tab
            }
        }
        companion object {
            suspend fun catchShrimp(ctx: Context) {
                val shrimps = ctx.npcs.findNpc(3317)
                shrimps[0].turnTo()
                shrimps[0].interact("Net")
                // Wait till shrimp is in Inventory
                Utils.waitFor(10, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return ctx.inventory.getCount(SHRIMP_ID) > 0
                    }
                })
            }
        }
    }



    class ClickSkillsTab(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "on the flashing bar graph icon near the inventory"
            return dialogWidget?.containsText(text) ?: false && ctx.tabs.getOpenTab() != Tabs.Tab_Types.Skills
        }

        override suspend fun execute() {
            ctx.tabs.openTab(Tabs.Tab_Types.Skills)
            delay(Random.nextLong(1000, 2000)) // Adding delay Because flashing tab returns right away causing the system to close the tab
        }
    }

    class TalkToSurvivalGuideAfterSkillsTab(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "this menu you can view your skills"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            val survivalExpert = ctx.npcs.findNpc(8503)
            if(!ctx.dialog.isContinueAvailable())
                survivalExpert[0].talkTo()
            // WAit till the continue is avaliable
            Utils.waitFor(4, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return ctx.dialog.isDialogUp()
                }
            })

            ctx.dialog.continueDialog()

        }

    }

    class ChopTree(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "time to cook your shrimp. However, you require"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            chopTree(ctx)

            ctx.dialog.continueDialog()


        }
        companion object{
            suspend fun chopTree(ctx: Context) {
                val trees = ctx.gameObjects.find(9730, sortByDistance = true)
                // Should be more than 4, lets pick a random one between 1 and 4
                trees[Random.nextInt(0, 3)].interact("Chop")

                // Wait till we get a log in the invetory.
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return ctx.inventory.getCount(LOGS_ID_2511) > 0
                    }
                })
            }
        }

    }


    class ContinueFromChopTree(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "You manage to cut some logs"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            ctx.dialog.continueDialog()
        }

    }

    class LightLog(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "that you have some logs, it's time"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            // Use tinderbox(590) with logs(2511)
            lightFire(ctx)
        }

        companion object{
            suspend fun lightFire(ctx: Context) {
                ctx.inventory.open()
                //Check to see if we are standing on a fire, if so just use it to cook the shrimp.
                // Check if there is a fire cook the shrimp
                val fires = ctx.gameObjects.find(26185, sortByDistance = true)
                if (fires.size > 0) {
                    ctx.inventory.open()
                    ctx.inventory.getItem(SHRIMP_ID)?.click()
                    // The fire is an animated object so it thows a NPE when trying to interact with model.
                    if (fires[0].sceneryObject != null) {
                        val point = Calculations.worldToScreen(
                                fires[0].sceneryObject!!.getCenterX(),
                                fires[0].sceneryObject!!.getCenterY(),
                                0,
                                ctx

                        )
                        Interact(ctx).interact(point, "Use Raw shrimps -> Fire")
                    }

                    //Wait till idle
                    ctx.players.getLocal().waitTillIdle()
                    delay(100)
                    ctx.dialog.continueDialog()
                }

                ctx.inventory.getItem(590)?.click()
                ctx.inventory.getItem(LOGS_ID_2511)?.click()
                delay(Random.nextLong(2500, 4500))
                //Wait till hes not doing anything which should mean fire has been made
                ctx.players.getLocal().waitTillIdle()
            }
        }
    }


    class CookShrimp(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "Now it's time to get cooking."
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            // Check to make sure we have shrimp, If not go fish for them
            if (ctx.inventory.getCount(SHRIMP_ID) == 0) {
                CatchSomeShrimp.catchShrimp(ctx)
            }

            var fires = ctx.gameObjects.find(26185, sortByDistance = true)
            //No fire & no logs
            if (fires.size == 0 && ctx.inventory.getCount(LOGS_ID_2511) == 0) {
                ChopTree.chopTree(ctx)
            }

            //If no fire && have logs, light a fire
            fires = ctx.gameObjects.find(26185, sortByDistance = true)
            if (fires.size == 0 && ctx.inventory.getCount(LOGS_ID_2511) > 0) {
                LightLog.lightFire(ctx)
            }

            // Check if there is a fire cook the shrimp
            fires = ctx.gameObjects.find(26185, sortByDistance = true)
            if (fires.size > 0) {
                ctx.inventory.open()
                ctx.inventory.getItem(SHRIMP_ID)?.click()
                // The fire is an animated object so it thows a NPE when trying to interact with model.
                if (fires[0].sceneryObject != null) {
                    val point = Calculations.worldToScreen(
                            fires[0].sceneryObject!!.getCenterX(),
                            fires[0].sceneryObject!!.getCenterY(),
                            0,
                            ctx

                    )
                    Interact(ctx).interact(point, "Use Raw shrimps -> Fire")
                }

                //Wait till idle
                ctx.players.getLocal().waitTillIdle()
                delay(100)
                ctx.dialog.continueDialog()
            }
        }
    }

    class OpenGateAfterFishing(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "Well done, you've just cooked your first meal!"
            return dialogWidget?.containsText(text) ?: false
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
            val gates = ctx.gameObjects.find(gateIDs.random(), sortByDistance = true)
            if (gates.size > 0) {
                gates[0].turnTo()
                gates[0].interact("Open")
                ctx.players.getLocal().waitTillIdle()
                delay(Random.nextLong(100, 150))
            }
            println("Complete: Going to open gate")
        }

    }

    class MoveToKitchen(val ctx: Context)  : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "Follow the path until you get to the door with the yellow arrow above it."
            val percentComplete = getPercentComplete(ctx)
            return dialogWidget?.containsText(text) ?: false && percentComplete == .196875
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

            val gameObjects = ctx.gameObjects.find(9709, sortByDistance = true)
            if (gameObjects.size > 0) {
                gameObjects[0].interact("Open")
                ctx.players.getLocal().waitTillIdle()
            }
        }
    }

    class TalkToMasterChef(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "Talk to the chef indicated"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            if(!ctx.dialog.isContinueAvailable())
                ctx.npcs.findNpc(3305)[0].talkTo()

            delay(Random.nextLong(3000, 5000))

            ctx.dialog.continueDialog()
        }
    }

    class MakeDough(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "This is the base for many meals"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            // Mix water(1929) and flower(2516)
            Inventory(ctx = ctx).getItem(1929)?.click()
            Inventory(ctx = ctx).getItem(2516)?.click()
            delay(Random.nextLong(1250, 1650))
            ctx.dialog.continueDialog()


        }

    }

    class MakeBread(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "Now you have made the dough,"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            //dough is 2307
            //Range is 9736
            ctx.inventory.open()
            val range = ctx.gameObjects.find(9736)[0]
            ctx.camera.turnTo(range)
            range.interact("Cook Range")
            // Wait till bread in inventory
            Utils.waitFor(4, object : Utils.Condition {
                override suspend fun accept(): Boolean {
                    delay(100)
                    return ctx.dialog.isDialogUp()
                }
            })
            delay(Random.nextLong(1250, 1650))
            ctx.dialog.continueDialog()

        }

    }

    class ExitKitchen(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "You've baked your first loaf of bread"
            return dialogWidget?.containsText(text) ?: false
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

            ctx.camera.turnWest()

            //DOOR 9710
            val door = ctx.gameObjects.find(9710)
            if (door.size > 0) {
                door[0].interact("Open Door")
                ctx.players.getLocal().waitTillIdle()
            }
        }

    }

    class TurnOnRun(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "When navigating the world, you can either run or walk"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            ctx.run.clickRunButton()
            ctx.run.activateRun()
            delay(Random.nextLong(1500, 2500))
        }

    }

    class MoveToNextBuilding(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "Follow the path to the next guide"
            return dialogWidget?.containsText(text) ?: false && getPercentComplete(ctx) == .296875
        }

        override suspend fun execute() {
            val walkingPath = arrayListOf(
                Tile(3073, 3103, ctx = ctx), Tile(3074, 3117, ctx = ctx),
                Tile(3079, 3127, ctx = ctx), Tile(3086, 3127, ctx = ctx)
            )
            Walking.walkPath(walkingPath)
            //Open Door(9716)
            ctx.camera.setHighPitch()
            ctx.camera.turnSouth()
            val doors = ctx.gameObjects.find("Door", sortByDistance = true)
            if (doors.size > 0) {
                doors[0].interact("Open Door")
                ctx.players.getLocal().waitTillIdle()
            }


        }

    }

    class TalkToQuestGuide(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "It's time to learn about quests!"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            val questGuide = ctx.npcs.findNpc("Quest Guide")
            if (questGuide.size > 0) {
                if (!questGuide[0].isOnScreen()) ctx.camera.turnTo(questGuide[0])
                if(!ctx.dialog.isContinueAvailable())
                    questGuide[0].interact("Talk-to Quest Guide")
                ctx.players.getLocal().waitTillIdle()
                delay(Random.nextLong(100, 150))
                ctx.dialog.continueDialog()
            }
        }
    }

    class OpenQuestList(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "Click on the flashing icon to the left of your inventory"
            return dialogWidget?.containsText(text) ?: false && ctx.tabs.getOpenTab() != Tabs.Tab_Types.QuestList
        }

        override suspend fun execute() {
            ctx.tabs.openTab(Tabs.Tab_Types.QuestList)
            delay(Random.nextLong(1000, 2000)) // Adding delay Because flashing tab returns right away causing the system to close the tab
        }

    }

    //TODO - Add a node if some how we make our way upstairs to go back down

    class TalkToQuestGuide2ndTime(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "This is your quest journal."
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            val questGuide = ctx.npcs.findNpc("Quest Guide")
            if (questGuide.size > 0) {
                if (!questGuide[0].isOnScreen()) ctx.camera.turnTo(questGuide[0])
                if(!ctx.dialog.isContinueAvailable())
                    questGuide[0].interact("Talk-to Quest Guide")
                ctx.players.getLocal().waitTillIdle()
                ctx.dialog.continueDialog()
            }
        }
    }

    class GoDownToTheCaves(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "It's time to enter some caves"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            ctx.camera.setHighPitch()
            // Go down ladder
            val ladder = ctx.gameObjects.find("Ladder")
            if (ladder.size > 0) {
                ctx.camera.turnTo(ladder[0])
                ladder[0].interact("Climb-down Ladder")
                ctx.players.getLocal().waitTillIdle()
//                delay(Random.nextLong(3500, 6400))
            }
        }

    }

    class WalkAndTalkToSmitingAndMiningGuide(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "Next let's get you a weapon,"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            val walkingPath = arrayListOf(Tile(3079, 9512, ctx = ctx), Tile(3081, 9504, ctx = ctx))
            Walking.walkPath(walkingPath)
            val miningGuide = ctx.npcs.findNpc("Mining Instructor")
            if (miningGuide.size > 0) {
                ctx.camera.setHighPitch()
                if (!miningGuide[0].isOnScreen()) miningGuide[0].turnTo()
                if(!ctx.dialog.isContinueAvailable())
                    miningGuide[0].talkTo()
                delay(Random.nextLong(1250, 3650))
                ctx.dialog.continueDialog()

            }
        }

    }

    class MineRock{
        companion object{
            suspend fun mineRock(ctx: Context) {
                val rocks = ctx.gameObjects.find("Rocks", sortByDistance = true)
                if (rocks.size > 0) {
                    val oldInventoryCount = ctx.inventory.getCount()
                    rocks[0].interact("Mine")
                    ctx.players.getLocal().waitTillIdle()
                    Utils.waitFor(8, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return oldInventoryCount != ctx.inventory.getCount()
                        }
                    })
                }
                ctx.dialog.continueDialog()
            }
        }
    }

    class MineTin(val ctx: Context ) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "It's quite simple really. To mine a rock, all you need"
            return dialogWidget?.containsText(text) ?: false
                    || dialogWidget?.containsText("Now that you have some copper ore,") ?: false
        }

        override suspend fun execute() {
            //walk to tile(3076,9505
            //Mine rocks
            val miningspot = Tile(3076, 9505, 0, ctx)
            miningspot.clickOnMiniMap()
            ctx.players.getLocal().waitTillIdle()
            ctx.camera.setHighPitch()
            MineRock.mineRock(ctx)
        }
    }



    class MineCopper(val ctx: Context ) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val text = "Now that you have some tin ore"
            return dialogWidget?.containsText(text) ?: false
        }

        override suspend fun execute() {
            val miningspot = Tile(3085, 9502, 0, ctx)
            if (miningspot.distanceTo() > 5) {
                miningspot.clickOnMiniMap()
                delay(Random.nextLong(3500, 5500))
            }
            ctx.camera.setHighPitch()
            MineRock.mineRock(ctx)
        }

    }

    //TODO - There has been a case where the furnance is clicked on after the bar is been made & the dialog blocks
    // continuing into the next node
    class SmeltBronze(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("You now have some tin ore and some copper ore.") ?: false
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

            val furnace = ctx.gameObjects.find("Furnace")[0]
            if (!furnace.isOnScreen()) furnace.turnTo()
            furnace.click()
            delay(100)
            ctx.players.getLocal().waitTillIdle()
            //TODO- somtime we keep clicking here and it can mess us up
        }

    }

    class TalkToMiningGuideAboutSmiting(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("You've made a bronze bar!") ?: false
        }

        override suspend fun execute() {

            val miningGuide = ctx.npcs.findNpc("Mining Instructor")
            if (miningGuide.size > 0) {
                miningGuide[0].turnTo()
                if (Tile(3081, 9504, ctx = ctx).distanceTo() > 4) {
                    Tile(3081, 9504, ctx = ctx).clickOnMiniMap()
                }
                if(!ctx.dialog.isContinueAvailable())
                    miningGuide[0].talkTo()
                ctx.players.getLocal().waitTillIdle()
                ctx.dialog.continueDialog()

            }
        }

    }

    class MakeBronzeDagger(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("To smith you'll need a hammer") ?: false
                    || dialogWidget?.containsText("Use an anvil to open")?: false
                    || dialogWidget?.containsText("Now you have the")?: false
        }

        override suspend fun execute() {
            //Find Anvil
            val anvil = ctx.gameObjects.find("Anvil")
            if (anvil.size > 0) {

                val index = (0..1).random()
                anvil[index].turnTo()
                Inventory(ctx = ctx).open()
                anvil[index].click()
                delay(Random.nextLong(300, 700))
                ctx.players.getLocal().waitTillIdle()
                //Wait for smiting widgets
                ctx.widgets.waitTillWidgetNotNull(312, 9)

                val oldInventoryCount = ctx.inventory.getCount()
                val daggerSmitingPage = WidgetItem(ctx.widgets.find(312, 9)?.getChildren()?.get(2), ctx = ctx)
                if (daggerSmitingPage.widget != null) {
                    daggerSmitingPage.click()

                    Utils.waitFor(4, object : Utils.Condition {
                        override suspend fun accept(): Boolean {
                            delay(100)
                            return oldInventoryCount != ctx.inventory.getCount()
                        }
                    })
                }


            }
        }

    }

    class AfterSmithingMovetoGate(val ctx: Context ) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Congratulations, you've made your first weapon") ?: false
        }

        override suspend fun execute() {
            val walkingPath = arrayListOf(Tile(3086, 9505, ctx = ctx), Tile(3091, 9503, ctx = ctx))
            Walking.walkPath(walkingPath)
            val gate = ctx.gameObjects.find("Gate", sortByDistance = true)
            if (gate.size > 0) {
                ctx.camera.setHighPitch()
                ctx.camera.turnEast()
                gate[0].interact("Open")
                delay(100)
                //TODO - This somehow keeps clicking gate. Figure out how to make this better
                ctx.players.getLocal().waitTillIdle()
            }

        }

    }

    class TalkToCombatInstructor(val ctx: Context ) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("In this area you will find out about melee and ranged combat.") ?: false
        }

        override suspend fun execute() {
            ctx.camera.setHighPitch()
            // Move to combat insturctor
            val tileNearCombatInstructor = Tile(3107, 9509, ctx = ctx)
            if (tileNearCombatInstructor.distanceTo() > 5) {
                tileNearCombatInstructor.clickOnMiniMap()
                val local = ctx.players.getLocal()
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return tileNearCombatInstructor.distanceTo() < 5 && local.isIdle()
                    }
                })
            }

            //Talk with combat instructor
            val combatInstructor = ctx.npcs.findNpc("Combat Instructor")
            if(!ctx.dialog.isContinueAvailable())
                combatInstructor[0].talkTo()
            ctx.players.getLocal().waitTillIdle()
            ctx.dialog.continueDialog()

        }

    }

    class OpenEquipment(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("You now have access to a new") ?: false && ctx.tabs.getOpenTab() != Tabs.Tab_Types.Equiptment
        }

        override suspend fun execute() {
            ctx.tabs.openTab(Tabs.Tab_Types.Equiptment)
            delay(Random.nextLong(1000, 2000)) // Adding delay Because flashing tab returns right away causing the system to close the tab
        }

    }

    class OpenEquipmentStats(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("This is your worn inventory") ?: false
        }

        override suspend fun execute() {
            ctx.equipment.open()
            WidgetItem(ctx.widgets.find(WidgetID.EQUIPMENT_GROUP_ID, 1), ctx=ctx).click()
            //ctx.equipment.clickButton(Equipment.Companion.Slot.EquiptmentStats)
            delay(Random.nextLong(1500, 2637))
        }

    }

    class EquipBronzeDagger(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("You can see what items you are") ?: false
        }

        override suspend fun execute() {
            Inventory(ctx = ctx).getItem(1205)?.click()
            delay(Random.nextLong(2500, 4000))
            WidgetItem(ctx.widgets.find(84, 4), ctx = ctx).click() // Close out of Equoptment status
        }

    }

    class SpeakWithCombatAfterBronzeDaggerEquipt(val ctx: Context ) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("You're now holding your dagger") ?: false
        }

        override suspend fun execute() {
            ctx.camera.setHighPitch()
            //Talk with combat instructor
            val combatInstructor = ctx.npcs.findNpc("Combat Instructor")
            if(!ctx.dialog.isContinueAvailable())
                combatInstructor[0].talkTo()
            ctx.dialog.continueDialog()

        }

    }

    class EquipLongSwordAndShield(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("To unequip an item, go to your") ?: false
        }

        override suspend fun execute() {
            Inventory(ctx = ctx).getItem(1277)?.click()
            delay(Random.nextLong(1500, 2500))
            Inventory(ctx = ctx).getItem(1171)?.click()
            delay(Random.nextLong(1500, 2500))
        }

    }

    class OpenCombatTab(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Click on the flashing crossed") ?: false && ctx.tabs.getOpenTab() != Tabs.Tab_Types.Combat
        }

        override suspend fun execute() {
            ctx.tabs.openTab(Tabs.Tab_Types.Combat)
            delay(Random.nextLong(1000, 2000)) // Adding delay Because flashing tab returns right away causing the system to close the tab
        }

    }

    class GoIntoRatCage(val ctx: Context ) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("This is your combat interface. From here,") ?: false
        }

        override suspend fun execute() {
            ctx.camera.setHighPitch()
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
            val gates = ctx.gameObjects.find("Gate", sortByDistance = true)
            if (gates.size > 0) {
                ctx.camera.turnWest()
                gates[0].interact("Open")
                ctx.players.getLocal().waitTillIdle()
            }

        }

    }

    class MeleeKillRat(val ctx: Context ) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("It's time to slay some rats!") ?: false || dialogWidget?.containsText("attack the rat") ?: false
        }

        override suspend fun execute() {
            ctx.camera.setHighPitch()
            val rats = ctx.npcs.findNpc("Giant rat")
            if (rats.size > 0) {
                val randomIndex = (0..5).random()
                if (ctx.players.getLocal().isIdle()) {
                    rats[randomIndex].interact("Attack")
                }
                ctx.players.getLocal().waitTillIdle()
            }
        }

    }


    class GoTalkToCombatInstructorFor2ndTime(val ctx: Context) : TutTask(ctx.client) {
        private val ratCageArea = Area(
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
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Pass through the gate and talk to the combat") ?: false
        }

        override suspend fun execute() {
            ctx.camera.setHighPitch()
            ctx.camera.turnEast()
            // Check to see if we are still in the rat cage
            if (ratCageArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())) {
                val gates = ctx.gameObjects.find("Gate", sortByDistance = true)
                if (gates.size > 0) {
                    if (!gates[0].isOnScreen()) {
                        gates[0].clickOnMiniMap()
                        ctx.players.getLocal().waitTillIdle()
                    }
                    gates[0].interact("Open")
                    ctx.players.getLocal().waitTillIdle()
                }
            }

            if (!ratCageArea.containsOrIntersects(ctx.players.getLocal().getGlobalLocation())) {
                val combatInstructor = ctx.npcs.findNpc("Combat Instructor")
                if (combatInstructor[0].distanceTo() > 5) {
                    combatInstructor[0].clickOnMiniMap()
                    combatInstructor[0].waitTillNearObject()
                }

                if(!ctx.dialog.isContinueAvailable())
                    combatInstructor[0].talkTo()
                ctx.dialog.continueDialog()
            }
        }

    }

    class KillRatWithBow(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Now you have a bow and some arrows.") ?: false
        }

        override suspend fun execute() {
            ctx.inventory.open()
            Inventory(ctx = ctx).getItem(841)?.click()
            delay(Random.nextLong(1500, 2500))
            Inventory(ctx = ctx).getItem(882)?.click()
            delay(Random.nextLong(1500, 2500))

            //Move over to a better spot to kill the rats
            val idealSpot = Tile(3110, 9515, ctx = ctx)
            if(idealSpot.distanceTo() > 3){
                idealSpot.clickOnMiniMap()
                ctx.players.getLocal().waitTillIdle()
            }

            val rats = ctx.npcs.findNpc("Giant rat")
            if (rats.size > 0) {
                val randomIndex = (0..2).random()
                rats[randomIndex].turnTo()
                if (ctx.players.getLocal().isIdle()) {
                    rats[randomIndex].interact("Attack")
                }
                ctx.players.getLocal().waitTillIdle()

            }
        }

    }


    class ExitCaves(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("You have completed the tasks here") ?: false
        }

        override suspend fun execute() {
            ctx.camera.setHighPitch()
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

            val ladder = ctx.gameObjects.find("Ladder", sortByDistance = true)
            if (ladder.size > 0) {
                ladder[0].interact("Climb")
                ctx.players.getLocal().waitTillIdle()
                //We are expecting to be really far from the ladder so lets wait till then
                Utils.waitFor(4, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return ladder[0].distanceTo() > 20
                    }
                })
            }
        }

    }

    class UseBank(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Follow the path and you will come to the front of the building") ?: false
        }

        override suspend fun execute() {
            //TODO - sometimes we lick on the ladder once we are out of the caves and that brings us back down. Need to check
            //Sometimes we find out selfs still in the caves, lets exit it
            val caveTile = Tile(3110,9526, ctx = ctx)
            if(caveTile.distanceTo() < 10){
                val ladder = ctx.gameObjects.find("Ladder", sortByDistance = true)
                if (ladder.size > 0) {
                    ladder[0].interact("Climb")
                    ctx.players.getLocal().waitTillIdle()
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

            val bankBooth = ctx.gameObjects.find("Bank booth", sortByDistance = true)
            if (bankBooth.size > 0) {
                bankBooth[0].interact("Use")
            }

        }

    }

    class CloseBankAndDoPollBooth(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("This is your bank.") ?: false
        }

        override suspend fun execute() {
            ctx.bank.close()
            PollBooth.openPollBooth(ctx)
        }

    }

    class PollBooth {
        companion object {
            suspend fun openPollBooth(ctx: Context) {
                val pollBooth = ctx.gameObjects.find(26815)
                pollBooth[0].turnTo()

                val pollTile = Tile(3119, 3121, ctx.players.getLocal().player.getPlane(), ctx)
                if (pollTile.distanceTo() > 3)
                    Tile(3120, 3121, ctx.client.getPlane(), ctx).clickOnMiniMap()

                pollTile.click()
                delay(Random.nextLong(1500, 2500))
                ctx.dialog.continueDialog()
            }
             suspend fun closePollWidget(ctx: Context) {
                try {
                    var pollWidget = ctx.widgets.find(345, 0)
                    if (pollWidget != null) {
                        val pollExitWidget = WidgetItem(ctx.widgets.find(345, 2)?.getChildren()?.get(3), ctx = ctx)
                        pollExitWidget.click()
                    }
                    pollWidget = ctx.widgets.find(310, 0)
                    if (pollWidget != null) {
                        val pollExitWidget = WidgetItem(ctx.widgets.find(310, 2)?.getChildren()?.get(3), ctx = ctx)
                        pollExitWidget.click()
                    }

                } catch (e: Exception) {
                    println("ERROR: Somthing happened when trying to find the poll widget")
                }
            }
        }
    }

    class DoPollBooth(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Now it's time for a quick look at polls") ?: false
        }

        override suspend fun execute() {
            //TODO figure out how to access moving objects OR need to find a more center tile point
            PollBooth.openPollBooth(ctx)
            ctx.dialog.continueDialog()
            //If poll widget open, Close out of polling booth widget (310,2) child index 3
            PollBooth.closePollWidget(ctx)

        }

    }



    class ClosePollAndMoveOutOfBank(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Polls are run periodically to let the Old School") ?: false
        }

        override suspend fun execute() {
            PollBooth.closePollWidget(ctx)
            //Open Door(9721) at location(3125,3124)
            val doors = ctx.gameObjects.find(9721, sortByDistance = true)
            if (doors.isNotEmpty()) {
                doors.forEach {
                    if (it.getGlobalLocation().x == 3125 && it.getGlobalLocation().y == 3124) {
                        it.turnTo()
                        it.interact("Open")
                        Utils.waitFor(6, object : Utils.Condition {
                            override suspend fun accept(): Boolean {
                                delay(100)
                                return ctx.players.getLocal().isIdle() && ctx.players.getLocal().getGlobalLocation().x == 3125
                            }
                        })
                    }
                }
            }
        }

    }

    class TalkToAccountManager(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("The guide here will tell you all about your account.") ?: false ||
                    dialogWidget?.containsText("This is your Account Management menu") ?: false
        }

        override suspend fun execute() {
            val accountManager = ctx.npcs.findNpc("Account Guide")
            if (accountManager.isNotEmpty()) {
                ctx.camera.setHighPitch()
                if(!ctx.dialog.isContinueAvailable())
                    accountManager[0].talkTo()
//                delay(Random.nextLong(2500, 4500))
                ctx.dialog.continueDialog()
            }
        }

    }

    class OpenAccountManager(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Click on the flashing icon to open your Account Management") ?: false && ctx.tabs.getOpenTab() != Tabs.Tab_Types.AccountManagement
        }

        override suspend fun execute() {
            ctx.tabs.openTab(Tabs.Tab_Types.AccountManagement)
            delay(Random.nextLong(1000, 2000)) // Adding delay Because flashing tab returns right away causing the system to close the tab
        }

    }

    class ExitAccountManagerRoom(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Continue through the next door.") ?: false
        }

        override suspend fun execute() {
            val doors = ctx.gameObjects.find(9722, sortByDistance = true)
            if (doors.isNotEmpty()) {
                doors.forEach {
                    if (it.getGlobalLocation().x == 3130 && it.getGlobalLocation().y == 3124) {
                        ctx.camera.turnEast()
                        ctx.camera.setHighPitch()
                        it.interact("Open")
                        ctx.players.getLocal().waitTillIdle()
                        delay(Random.nextLong(100, 150))
                    }
                }
            }
        }

    }

    class MoveToChapelAndTalkToBrotherBrace(val ctx: Context ) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Follow the path to the chapel") ?: false
                    || dialogWidget?.containsText("Talk with Brother Brace") ?: false
                    || dialogWidget?.containsText("These two lists can be very helpful for keeping track") ?: false
        }

        override suspend fun execute() {
            var brotherBrace = ctx.npcs.findNpc("Brother Brace")
            val pathToChapel = arrayListOf(Tile(3132, 3115, ctx = ctx), Tile(3130, 3107, ctx = ctx), Tile(3124, 3106, ctx = ctx))
            if ((brotherBrace.isNotEmpty() && brotherBrace[0].distanceTo() > 13) || brotherBrace.isEmpty())
                Walking.walkPath(pathToChapel)
            ctx.camera.setHighPitch()
            brotherBrace = ctx.npcs.findNpc("Brother Brace")
            if (brotherBrace.size > 0) {
                if (!brotherBrace[0].isOnScreen())
                    brotherBrace[0].turnTo()
                if(!ctx.dialog.isContinueAvailable())
                    brotherBrace[0].talkTo()
                ctx.players.getLocal().waitTillIdle()
                ctx.dialog.continueDialog()
            }
        }

    }

    class OpenPrayerTab(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Click on the flashing icon to open the Prayer menu.") ?: false && ctx.tabs.getOpenTab() != Tabs.Tab_Types.Prayer
        }

        override suspend fun execute() {
            ctx.tabs.openTab(Tabs.Tab_Types.Prayer)
            delay(Random.nextLong(1000, 2000)) // Adding delay Because flashing tab returns right away causing the system to close the tab
        }

    }

    class OpenFriendsTab(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("You should now see another new icon. Click on the flashing face") ?: false && ctx.tabs.getOpenTab() != Tabs.Tab_Types.FriendsList
        }

        override suspend fun execute() {
            ctx.tabs.openTab(Tabs.Tab_Types.FriendsList)
            delay(Random.nextLong(1000, 2000)) // Adding delay Because flashing tab returns right away causing the system to close the tab
        }

    }

    class ExitChapleHouse(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("You're almost finished on tutorial island") ?: false
        }

        override suspend fun execute() {
            val doors = ctx.gameObjects.find(9723, sortByDistance = true)
            if (doors.isNotEmpty()) {
                doors.forEach {
                    if (it.getGlobalLocation().x == 3122 && it.getGlobalLocation().y == 3102) {
                        ctx.camera.turnSouth()
                        ctx.camera.setHighPitch()
                        it.interact("Open")
                        delay(Random.nextLong(1500, 2500))
                        ctx.players.getLocal().waitTillIdle()
                    }
                }
            }
        }

    }

    class GoToWizardHouseAndSpeakWithWizard(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Follow the path to the wizard") ?: false
                    || dialogWidget?.containsText("This is your magic interface") ?: false
        }

        override suspend fun execute() {
            val pathToWizardHouse = arrayListOf(
                Tile(3128, 3090, ctx = ctx), Tile(3138, 3087, ctx = ctx),
                Tile(3140, 3087, ctx = ctx)
            )
            if (pathToWizardHouse[2].distanceTo() > 6) {
                Walking.walkPath(pathToWizardHouse)
            }
            val magicInstructor = ctx.npcs.findNpc("Magic Instructor")
            if (magicInstructor.isNotEmpty()) {
                if (!magicInstructor[0].isOnScreen()) magicInstructor[0].turnTo()
                if(!ctx.dialog.isContinueAvailable())
                    magicInstructor[0].talkTo()
                ctx.players.getLocal().waitTillIdle()
                ctx.dialog.continueDialog()

            }
        }

    }

    class OpenMagicTab(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("Open up the magic interface") ?: false && ctx.tabs.getOpenTab() != Tabs.Tab_Types.Magic
        }

        override suspend fun execute() {
            ctx.tabs.openTab(Tabs.Tab_Types.Magic)
            delay(Random.nextLong(1000, 2000)) // Adding delay Because flashing tab returns right away causing the system to close the tab
        }

    }

    class SelectWindStrikeAndAttackChicken(val ctx: Context) : TutTask(ctx.client) {

        private val mageHutArea = Area(
                Tile(3138, 3091, ctx = ctx), Tile(3141, 3091, ctx = ctx),
                Tile(3142, 3090, ctx = ctx), Tile(3143, 3089, ctx = ctx), Tile(3143, 3084, ctx = ctx),
                Tile(3140, 3084, ctx = ctx), Tile(3140, 3089, ctx = ctx), Tile(3139, 3090, ctx = ctx),
                ctx = ctx
        )

        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("You now have some runes.") ?: false
        }

        override suspend fun execute() {
            //Make sure we are near the chicken cage
            val rightInFrontOfTheGate = Tile(3140, 3091,0,ctx=ctx)
            if(rightInFrontOfTheGate.distanceTo() > 2  && !mageHutArea.isPlayerInArea()){
                rightInFrontOfTheGate.clickOnMiniMap()
                ctx.players.getLocal().waitTillIdle()
            }
            ctx.magic.castSpell(Magic.Companion.Spells.WIND_STRIKE)
            //Attack chicken
            val chickens = ctx.npcs.findNpc("Chicken")
            if (chickens.isNotEmpty()) {
                val randChick = Random.nextInt(0, chickens.size - 1)
                chickens[randChick].swingTo()
                ctx.camera.setHighPitch()
                chickens[randChick].interact("Cast")
                Utils.waitFor(5, object : Utils.Condition {
                    override suspend fun accept(): Boolean {
                        delay(100)
                        return ctx.players.getLocal().isIdle() && chickens[randChick].isIdle()
                    }
                })

            }
        }

    }

    class ExitTutIsland(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            return dialogWidget?.containsText("You're nearly finished with the tutorial") ?: false
        }

        override suspend fun execute() {
            val magicInstructor = ctx.npcs.findNpc("Magic Instructor")
            if (magicInstructor.isNotEmpty()) {
                if (!magicInstructor[0].isOnScreen()) magicInstructor[0].turnTo()
                if(!ctx.dialog.isContinueAvailable())
                    magicInstructor[0].talkTo()
                ctx.players.getLocal().waitTillIdle()
                ctx.dialog.continueDialog()
                ctx.dialog.selectionOption("Yes")
                ctx.dialog.continueDialog()
                ctx.dialog.selectionOption("No")
                ctx.dialog.continueDialog()

            }
        }

    }

    class MainlandLogout(val ctx: Context) : TutTask(ctx.client) {
        override suspend fun isValidToRun(dialogWidget: WidgetItem?): Boolean {
            val completedWidget = WidgetItem(ctx.widgets.find(193, 2), ctx = ctx)
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
                ctx.worldHop.logout()
            } else {
                println("Walking path west")
                Walking.walkPath(pathWest)
                ctx.worldHop.logout()
            }

        }

    }
   
}
