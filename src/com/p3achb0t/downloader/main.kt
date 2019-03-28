package com.p3achb0t.downloader

import com.p3achb0t.analyser.Analyser
import java.applet.Applet
import java.applet.AppletContext
import java.applet.AppletStub
import java.awt.Dimension
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants


class RSLoader:AppletStub {
    var params = Parameters.data.PARAMETER_MAP
    override fun getCodeBase(): URL {
        return URL(params["codebase"])
    }

    override fun getParameter(name: String?): String {
        println("Getting $name : ${params[name]}")
        return params[name]!!
    }

    override fun getAppletContext(): AppletContext {
        return null!!
    }

    override fun appletResize(width: Int, height: Int) {
    }

    override fun getDocumentBase(): URL {
        return URL(params["codebase"])
    }

    override fun isActive(): Boolean {
        return true
    }
}
fun main(args: Array<String>){

    val downloader = Downloader()
//    val gamePackWithPath = downloader.getGamepack()
    val gamePackWithPath = downloader.getLocalGamepack()
    val analyser = Analyser()
    val gamePackJar = JarFile(gamePackWithPath)
    analyser.parseJar(gamePackJar)
    // Getting parameters
    var params = Parameters(1)
    println("Starting Client")
//    val file = File(gamePackWithPath)
//    val urlArray: Array<URL> = Array(1,init = {file.toURI().toURL()})
//    val classLoader = URLClassLoader(urlArray)
//    var game: Applet = classLoader.loadClass("client").newInstance() as Applet
//    game.apply {
//        preferredSize = Dimension(765,503)
//        var loader = RSLoader()
//        game.setStub(loader)
//    }
//    JFrame("Runescape").apply {
//        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
//        add(game)
//        pack()
//        preferredSize = size
//        minimumSize = game.minimumSize
//        setLocationRelativeTo(null)
//        isVisible = true
//    }
//
//    game.apply {
//        init()
//        start()
//    }




    //Title: Old School RuneScape Game


}
