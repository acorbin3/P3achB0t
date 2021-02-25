package com.p3achb0t.client.ux

import com.p3achb0t.Main
import com.p3achb0t.api.StopWatch
import com.p3achb0t.api.utils.Logging
import com.p3achb0t.api.wrappers.Cache
import com.p3achb0t.client.accounts.AccountManager
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.injection.InstanceManager
import com.p3achb0t.client.injection.InstanceManagerInterface
import com.p3achb0t.client.injection.ScriptState
import com.p3achb0t.client.ux.prefs.FrameMonitor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import java.awt.Dimension
import java.io.File
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame


class BotManager(var disableAll: Boolean = false, var disableScene: Boolean = false) : JFrame() {

    val botTabBar = BotTabBar()
    val botNavMenu = BotNavigationMenu()

    init {
        val accountsName = AccountManager.accountsJsonFileName.replace(".json","").split("\\").last()
        title = "P3achB0t - $accountsName"
        extendedState = NORMAL
        layout = BorderLayout()
        defaultCloseOperation = EXIT_ON_CLOSE
        iconImage =
            if( File("resources\\icons\\toppng.com-download-peach-690x523.png").exists()) {
                ImageIcon("resources\\icons\\toppng.com-download-peach-690x523.png").image
            } else {
                val stream = Main.javaClass.getResourceAsStream("/toppng.com-download-peach-690x523.png")
                ImageIO.read(stream)
            }

        jMenuBar = botNavMenu
        add(botTabBar)

        pack()
        // TODO("correctly resize main window when tab bar changes size")
        // Set the minimum size after packing to fit child components.
        minimumSize = Dimension(com.p3achb0t.api.Constants.GAME_FIXED_WIDTH + 40, com.p3achb0t.api.Constants.GAME_FIXED_HEIGHT + 195)
        setLocationRelativeTo(null) // Set location after packing to correctly center.
//        FrameMonitor.registerFrame(this, BotManager::class.java.name, x, y, width, height) // Load window size/loc prefs

        isVisible = true

        // load scripts
        GlobalStructs.scripts.loadPath("${Constants.USER_DIR}/${Constants.APPLICATION_CACHE_DIR}/${Constants.SCRIPTS_DIR}")
        GlobalStructs.scripts.loadPath("com/p3achb0t/scripts")
        GlobalStructs.scripts.loadPath("com/p3achb0t/scripts_private")
        GlobalStructs.scripts.loadPath("com/p3achb0t/scripts_private_shared")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val current = LocalDateTime.now()
        val formattedCurrentDate = current.format(formatter)
        val lastChecked = File("lastChecked.txt")
        lastChecked.writeText(formattedCurrentDate)
        botNavMenu.refreshScriptMenu() // lazy fix should be callback instead
    }

    fun getSelectedBotInstance(): BotInstance {
        return botTabBar.selectedComponent as BotInstance
    }

    fun getSelectedInstanceManager(): InstanceManager {
        return getSelectedBotInstance().getInstanceManager()
    }

    fun getSelectedInstanceInterface(): InstanceManagerInterface {
        return getSelectedBotInstance().instanceManagerInterface
    }

    fun startAccounts() {
        var count = 0
        //Set the account for things needed in the InstanceManager
        if(AccountManager.accounts.isNotEmpty()) {
            AccountManager.accounts.forEachIndexed { index, acc ->
                if(!acc.banned) {
                    count += 1
                    GlobalScope.launch { BotInstance(acc, index.toString(), disableAll, disableScene) }
                    sleep(2000) // Wait 100ms for tab to open up
                }
            }
        }

        if(count == 0){
            GlobalScope.launch { BotInstance() }
        }

        //Monitoring thread for each instance to see if it needs to be restarted
        GlobalScope.launch {
            sleep(1000*20)


            while(true){
                var i = 0
                var shouldRestart = false
                var uuidToRestart = ""

                botTabBar.botInstances.forEach { t, u ->
//                    println("Looking at $t")
                    if((u.getInstanceManager().isContextLoaded
                                    && u.getInstanceManager().ctx.client.getGameState() == 1000)
                            || u.getInstanceManager().ctx.mouse.mouseFail){

                        println("Game state 1000, bad")

                        if(u.getInstanceManager().ctx.mouse.mouseFail){
                            println("Mouse fail")
                        }

                        uuidToRestart = u.getInstanceManager().account.uuid
                        shouldRestart = true

                    }
                    if(u.getInstanceManager().isContextLoaded) {
//                        println("getGameState: ${u.getInstanceManager().ctx.client.getGameState()}")
                    }

                    i++
                }
                if(shouldRestart){

                    //We are just going to stop all the script
                    botTabBar.stopScripts(uuidToRestart)

//                    val newUUID = botTabBar.restartBotInstance(uuidToRestart)
//
//                    Logging.error("Wating for old tab to be gone")
//                    val timeout = StopWatch()
//                    while(uuidToRestart in botTabBar.botInstances && timeout.elapsedSec < 45){
//                        sleep(50)
//                    }
//                    sleep(5*1000)
//                    Logging.error("STarting up script again")
//                    //Need to start script back up
//                    botTabBar.botInstances.forEach { t, u ->
//
//                        u.account.serviceScripts.forEach {serviceScript ->
//                            u.getInstanceManager().addServiceScript(serviceScript)
//
//                        }
//
//                        if(u.getInstanceManager().scriptState == ScriptState.Stopped){
//                            Logging.error("Restarting: ${u.account.actionScript}")
//                            u.getInstanceManager().startActionScript(u.account.actionScript, u.account)
//                        }
//                        u.account.debugScripts.forEach {debugScript ->
//                            u.getInstanceManager().addPaintScript(debugScript)
//                        }
//
//                        //Update the Account to the Instance manager
//                    }

                }
                sleep(10_000) // Only check every 10 seconds
            }
        }


        while (botTabBar.botInstances.size == 0){
            sleep(50)
        }
    }

    suspend fun restartSelectedTab(){
        //u.getInstanceManager().account.uuid
        val uuidToRestart = getSelectedBotInstance().getInstanceManager().account.uuid
        //We are just going to stop all the script
        botTabBar.stopScripts(uuidToRestart)
        botTabBar.botInstances.forEach { key, instance ->
            if(key == uuidToRestart) {
                GlobalScope.launch {
                    instance.getInstanceManager().stoppingScriptAndLoggingOut()
                }
            }

        }


        val newUUID = botTabBar.restartBotInstance(uuidToRestart)

        Logging.info("Wating for old tab to be gone")
        val timeout = StopWatch()
        while(uuidToRestart in botTabBar.botInstances && timeout.elapsedSec < 45){
            sleep(50)
        }
        sleep(5*1000)
        Logging.info("STarting up script again")
        //Need to start script back up
        botTabBar.botInstances.forEach { t, u ->

            if(t == newUUID) {
                print("Starting  scripts for $t")
                u.account.serviceScripts.forEach { serviceScript ->
                    u.getInstanceManager().addServiceScript(serviceScript)
                }

                if (u.getInstanceManager().scriptState == ScriptState.Stopped) {
                    Logging.error("Restarting: ${u.account.actionScript}")
                    u.getInstanceManager().startActionScript(u.account.actionScript, u.account)
                }
                u.account.debugScripts.forEach { debugScript ->
                    u.getInstanceManager().addPaintScript(debugScript)
                }
            }

        }
    }

    fun updateCache() {
        // TODO - Check if there is a .cache up 1 directory, if so copy it down
        // Otherwise create a new one
        var botInstanceKey = ""
        GlobalScope.launch {
            var cacheLoaded = false
            sleep(5000)
            botTabBar.botInstances.iterator().forEach {
                if (!cacheLoaded) {
                    botInstanceKey = it.key

                    //Wait till the ctx is initialized
                    while(!it.value.instanceManagerInterface.getManager().isContextLoaded){
                        delay(50)
                    }
                    println("About to update cache")
                    it.value.instanceManagerInterface.getManager().ctx.cache.updateCache()
                    cacheLoaded = true
                }
            }
        }
        while(botInstanceKey.isEmpty()){
            sleep(50)
        }
        println("Waiting till Cache is updated")
        while(!Cache.cacheUpdated){
            sleep(50)
        }
    }

    fun stopAllRunningAccounts(){
        println("Stopping all scripts")
        botTabBar.botInstances.forEach { key, instance ->
            GlobalScope.launch {
                instance.getInstanceManager().stoppingScriptAndLoggingOut()
            }

        }
    }
    
    fun startScripts() {
        println("Starting scripts")
        if(AccountManager.accounts.isNotEmpty()) {
            AccountManager.accounts.forEach {
                botTabBar.botInstances.forEach { key, instance ->
                    if (it.uuid == instance.sessionToken) {

                        it.serviceScripts.forEach {serviceScript ->
                            instance.getInstanceManager().addServiceScript(serviceScript)

                        }

                        //Update the Account to the Instance manager
                        if(it.startActionScriptAutomatically) {
                            println("Starting ${it.actionScript} for ${it.username}")
                            instance.getInstanceManager().startActionScript(it.actionScript, it)
                        }
                        it.debugScripts.forEach {debugScript ->
                            instance.getInstanceManager().addPaintScript(debugScript)
                        }

                    }
                }

                sleep(1000*2) // 2 sec per account wait
            }
        }
    }
}