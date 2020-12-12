package com.p3achb0t

import com.formdev.flatlaf.FlatDarkLaf
import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.runestar.RuneStarAnalyzer
import com.p3achb0t.api.StopWatch
import com.p3achb0t.api.cache.format.Cache
import com.p3achb0t.client.accounts.AccountManager
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.loader.Loader
import com.p3achb0t.client.util.Util
import com.p3achb0t.client.ux.BotManager
import java.awt.Font
import java.io.File
import java.lang.Thread.sleep
import java.nio.file.Paths
import java.util.jar.JarFile
import javax.swing.JFrame
import javax.swing.UIManager
import javax.swing.plaf.FontUIResource
import kotlin.system.exitProcess


object Main {

    @JvmStatic
    fun main(args: Array<String>) {

        var getNextKey = false
        var getAccountPath = false
        var minimized = false
        args.iterator().forEach {
            if(getNextKey){
                GlobalStructs.db.validationKey = it

                getNextKey = false
            }
            if(it == "-key"){
                getNextKey = true
            }
            if(getAccountPath){
                AccountManager.accountsJsonFileName = it
                println("Account path: $it")
                getAccountPath = false
            }
            if(it.contains("-accounts")){
                getAccountPath = true

            }
            if(it.contains("-m")){
                minimized = true
            }
            println(it)
        }

        AccountManager.loadAccounts()
        //println("validation key: $validationKey")
        // Todo: Temp solution
        update()
        lookAndFeel()
        val botManager = BotManager()
        if(minimized) {
            botManager.state = JFrame.ICONIFIED
        }
        GlobalStructs.botManager = botManager
        botManager.startAccounts()
        botManager.updateCache()
        val timeout = StopWatch()
        while(!com.p3achb0t.api.wrappers.Cache.cacheUpdated && timeout.elapsedSec < 30){
            sleep(50)
        }
        botManager.startScripts()
    }

    private fun lookAndFeel() {
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

    // TODO: Move this somewhere
    private fun update() {
        System.setProperty("user.home", "cache")
        Util.createDirIfNotExist(Paths.get(Constants.APPLICATION_CACHE_DIR, Constants.JARS_DIR).toString())
        // check applet revision
        val revision = Util.checkClientRevision(Constants.REVISION, 5000)
        if (!revision) {
            error("New revision, need to update hooks")
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
    }
}
