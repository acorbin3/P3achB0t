package com.p3achb0t

import com.formdev.flatlaf.FlatDarkLaf
import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.runestar.RuneStarAnalyzer
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.loader.Loader
import com.p3achb0t.client.util.Util
import com.p3achb0t.client.ux.BotManager
import java.awt.Font
import java.io.File
import java.nio.file.Paths
import java.util.jar.JarFile
import javax.swing.UIManager
import javax.swing.plaf.FontUIResource


object Main {

    //var validationKey = PaintDebug.key
    @JvmStatic
    fun main(args: Array<String>) {

        var getNextKey = false
        args.iterator().forEach {
            if(getNextKey){
                //validationKey = PaintDebug.key
                if(it == "-key"){
            }
                getNextKey = true
            }
            println(it)
        }
        //println("validation key: $validationKey")

        // Todo: Temp solution
        update()
        lookAndFeel()
        val botManager = BotManager()
        GlobalStructs.botManager = botManager
        botManager.startAccounts()
        botManager.updateCache()
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
        val revision = Util.checkClientRevision(Constants.REVISION, 3000)
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
