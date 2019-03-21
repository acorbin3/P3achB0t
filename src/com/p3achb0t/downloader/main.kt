package com.p3achb0t.downloader

import com.p3achb0t.analyser.Analyser
import java.util.jar.JarFile


fun main(args: Array<String>){
    val downloader = Downloader()
    val gamePackWithPath = downloader.getGamepack()
    val analyser = Analyser()
    val gamePackJar = JarFile(gamePackWithPath)
    analyser.parseJar(gamePackJar)
}
