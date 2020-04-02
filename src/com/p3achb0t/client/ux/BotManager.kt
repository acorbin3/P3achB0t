package com.p3achb0t.client.ux

import com.formdev.flatlaf.FlatDarkLaf
import com.p3achb0t.Main
import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.runestar.RuneStarAnalyzer
import com.p3achb0t.api.wrappers.Cache
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.loader.Loader
import com.p3achb0t.client.util.Util
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import java.awt.Font
import java.io.File
import java.lang.Thread.sleep
import java.nio.file.Paths
import java.util.jar.JarFile
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.UIManager
import javax.swing.plaf.FontUIResource


class BotManager : JFrame() {

    init {
        iconImage = if( File("resources\\icons\\toppng.com-download-peach-690x523.png").exists()) {
            ImageIcon("resources\\icons\\toppng.com-download-peach-690x523.png").image
        }else{
            val stream = Main.javaClass.getResourceAsStream("/toppng.com-download-peach-690x523.png")
            ImageIO.read(stream)
        }
        title = "P3achb0t"
        defaultCloseOperation = EXIT_ON_CLOSE
        this.layout = BorderLayout()

        val navMenu = BotNavigationMenu()
        jMenuBar = navMenu
        add(GlobalStructs.botTabBar)

        setLocationRelativeTo(null)
        extendedState = JFrame.NORMAL;

        pack()
        isVisible = true

        // load scripts
        GlobalStructs.scripts.loadPath("${Constants.USER_DIR}/${Constants.APPLICATION_CACHE_DIR}/${Constants.SCRIPTS_DIR}")
        GlobalStructs.scripts.loadPath("com/p3achb0t/scripts")
        GlobalStructs.scripts.loadPath("com/p3achb0t/scripts_private")
        navMenu.refreshScriptMenu() // lazy fix should be callback instead
    }

}

fun setup() {
    System.setProperty("user.home", "cache")
    Util.createDirIfNotExist(Paths.get(Constants.APPLICATION_CACHE_DIR, Constants.JARS_DIR).toString())
    // check applet revision
    val revision = Util.checkClientRevision(Constants.REVISION, 3000)
    if (!revision) {
        println("New revision, need to update hooks")
    }

    //Check to see if we have an injected JAR for the specific revision
    //Handle case where missing injection Jar
    //Download new Gamepack
    //Run analyzer and inject new gamepack
    if(!File("${Constants.APPLICATION_CACHE_DIR}/${Constants.INJECTED_JAR_NAME}").exists()) {
        val loader = Loader()
        val gamePackWithPath = loader.run()
        val gamePackJar = JarFile(gamePackWithPath)
        println("Using $gamePackWithPath")
        val runeStar = RuneStarAnalyzer()
        runeStar.loadHooks()
        runeStar.parseJar(gamePackJar)
        Analyser().createInjectedJar(gamePackJar, runeStar)
    }

    lookAndFeel()

    //fire up accounts
    if(GlobalStructs.accountManager.accounts.isNotEmpty()) {
        GlobalStructs.accountManager.accounts.forEach {
            GlobalScope.launch { BotInstance().initBot(it) }
            sleep(1000*3) // Wait 3 seconds for tab to open up
            //Now wait for instance thats related to this account to be loaded.
            GlobalStructs.botTabBar.botInstances.forEach { key, instance ->
                if(instance.getInstanceManager().loginHandler.account.username == it.username){
                    println("Waiting to game state is 10")
                    while (instance.getInstanceManager().ctx?.client?.getGameState() != 10){
                        print(" ${instance.getInstanceManager().ctx?.client?.getGameState()}")
                        sleep(50)
                    }
                    //Start script
                    if(it.script.isNotEmpty() && it.startAutomatically){
                        //instance.instanceManagerInterface?.getManager()?.startActionScript()
                        sleep(5000) // Wait 5 seconds between scripts
                    }
                }
            }


        }
    }else{
        GlobalScope.launch { BotInstance().initBot() }
    }


    while (GlobalStructs.botTabBar.botInstances.size == 0){
        sleep(50)
    }

    // Check if there is a .cache up 1 directory, if so copy it down
    // Otherwise create a new one
    var botInstanceKey = ""
    GlobalScope.launch {
        var cacheLoaded = false
        GlobalStructs.botTabBar.botInstances.iterator().forEach {
            if (!cacheLoaded) {
                botInstanceKey = it.key

                //Wait till the ctx is initialized
                while(it.value.instanceManagerInterface?.getManager()?.ctx == null){
                    delay(50)
                }
                println("About to update cache")
                it.value.instanceManagerInterface?.getManager()?.ctx?.cache?.updateCache()
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

fun lookAndFeel() {
    UIManager.setLookAndFeel(FlatDarkLaf())
    for ((key) in UIManager.getDefaults()) {
        val value = UIManager.get(key)
        if (value != null && value is FontUIResource) {
            val fr = value
            val f = FontUIResource(Font("Courier Bold", Font.BOLD, 12))
            UIManager.put(key, f)
        }
    }
}