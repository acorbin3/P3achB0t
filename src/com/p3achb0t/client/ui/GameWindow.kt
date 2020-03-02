package com.p3achb0t.client.ui

import com.p3achb0t.analyser.Analyser
import com.p3achb0t.analyser.ScriptClasses
import com.p3achb0t.analyser.runestar.RuneStarAnalyzer
import com.p3achb0t.api.AbstractScript
import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.loader.Loader
import com.p3achb0t.client.managers.Manager
import com.p3achb0t.client.ui.components.GameMenu
import com.p3achb0t.client.util.Util
import kotlinx.coroutines.delay
import java.awt.Dimension
import java.io.File
import java.nio.file.Paths
import java.util.jar.JarFile
import javax.swing.ImageIcon
import javax.swing.JFrame


class GameWindow : JFrame() {
    var index = 0
    var manager: Manager

    init {

        iconImage =  ImageIcon("resources\\icons\\toppng.com-download-peach-690x523.png").image
        title = "P3achb0t"
        defaultCloseOperation = EXIT_ON_CLOSE
        //preferredSize = Dimension(765, 503)
        focusTraversalKeysEnabled = true
        size = Dimension(850, 710)
        manager = Manager()
        jMenuBar = GameMenu(manager)

        add(manager.tabManager)
        setLocationRelativeTo(null)

        validate()
        isVisible = true

        println("About to load scripts")
        val privateScripts = ScriptClasses.findAllClasses("com/p3achb0t/scripts_private")
        privateScripts.forEach {
            val nameSplit = it.name.split(".")
            val name = nameSplit[nameSplit.size - 2]
            println("Loading $name")
            manager.loadedScripts.addScript(name, it.newInstance() as AbstractScript)
        }
        val scripts = ScriptClasses.findAllClasses("com/p3achb0t/scripts")
        scripts.forEach {
            val nameSplit = it.name.split(".")
            val name = nameSplit[nameSplit.size - 2]
            println("Loading $name")
            manager.loadedScripts.addScript(name, it.newInstance() as AbstractScript)
        }
        //Load all scripts
//        manager.loadedScripts.addScript("TutorialIsland", TutorialIsland())

        //Load each account in a different tab
        if(manager.accountManager.accounts.isNotEmpty()) {
            manager.accountManager.accounts.forEach {
                manager.tabManager.addInstance(account = it)
            }
        }else{
            manager.tabManager.addInstance()
        }
    }

    suspend fun run() {
        //Waiting till game has been loaded and then revalidate
        manager.tabManager.clients.forEach {
            //Check to see if we are ready to login
            while(it.client.getScript().ctx.client.getGameState() != 10){
                delay(50)
            }
            //Only start the script if its a real script. otherwise we dont want to start the null script
            if(it.account.script.isNotEmpty() && it.account.startAutomatically)
                it.client.startScript()
        }

        println("Validating client")
        for (i in 0..10) {
            manager.tabManager.getSelected().client.getApplet().repaint()
        }
    }

    fun setup() {
    }

}

// A setup function should not be placed here
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
}
