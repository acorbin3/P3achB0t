package com.p3achb0t.client.ux

import com.p3achb0t.Main
import com.p3achb0t.api.wrappers.Cache
import com.p3achb0t.client.accounts.AccountManager
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.injection.InstanceManager
import com.p3achb0t.client.injection.InstanceManagerInterface
import com.p3achb0t.client.ux.prefs.FrameMonitor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import java.io.File
import java.lang.Thread.sleep
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame


class BotManager : JFrame() {

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
        minimumSize = size // Set the minimum size after packing to fit child components.
        setLocationRelativeTo(null) // Set location after packing to correctly center.
        FrameMonitor.registerFrame(this, BotManager::class.java.name, x, y, width, height) // Load window size/loc prefs
        isVisible = true

        // load scripts
        GlobalStructs.scripts.loadPath("${Constants.USER_DIR}/${Constants.APPLICATION_CACHE_DIR}/${Constants.SCRIPTS_DIR}")
        GlobalStructs.scripts.loadPath("com/p3achb0t/scripts")
        GlobalStructs.scripts.loadPath("com/p3achb0t/scripts_private")
        GlobalStructs.scripts.loadPath("com/p3achb0t/scripts_private_shared")
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
        //Set the account for things needed in the InstanceManager
        if(AccountManager.accounts.isNotEmpty()) {
            AccountManager.accounts.forEachIndexed { index, acc ->
                GlobalScope.launch { BotInstance(acc, index.toString()) }
                sleep(1000) // Wait 100ms for tab to open up
            }
        } else{
            GlobalScope.launch { BotInstance() }
        }


        while (botTabBar.botInstances.size == 0){
            sleep(50)
        }
    }

    fun updateCache() {
        // TODO - Check if there is a .cache up 1 directory, if so copy it down
        // Otherwise create a new one
        var botInstanceKey = ""
        GlobalScope.launch {
            var cacheLoaded = false
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
        print("Waiting till Cache is updated")
        while(!Cache.cacheUpdated){
            sleep(50)
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