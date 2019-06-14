package com.p3achb0t.analyser

import com.google.gson.Gson
import com.p3achb0t.class_generation.createInterfaces
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels

class RuneLiteAnalyzer {
    private var webAddress: String =
        "https://raw.githubusercontent.com/runelite-extended/runelite/eafb024f16821160aab966114962711d7a4d355e/extended-mixins/hooks.json"
    val hookDir = "\\hooks\\"

    var hookFile = ""
    var hookFileName = "runeliteHooks.txt"

    val analyzers = mutableMapOf<String, RuneLiteJSONClasses.ClassDefinition>()
    val classRefObs = mutableMapOf<String, RuneLiteJSONClasses.ClassDefinition>()


    fun getHooks(): String {

        //Download gamepack
        println("Getting Hooks from Dreambot")
        val url = URL(webAddress)
        val readableByteChannel = Channels.newChannel(url.openStream())
        val path = System.getProperty("user.dir")
        val fileOutStream = FileOutputStream(path + hookDir + hookFileName)
        val fileChannel = fileOutStream.channel
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE)
        println("downloaded!")
        hookFile = path + hookDir + hookFileName
        val file = File(hookFile)

        var json = file.readText() // your json value here
        json = json.replace("class", "_class")
        json = json.replace("super", "_super")
        val topic = Gson().fromJson(json, Array<RuneLiteJSONClasses.ClassDefinition>::class.java)
        for (clazz in topic) {
            analyzers[clazz._class] = clazz
            classRefObs[clazz.name] = clazz
        }
        val folder = "./\\_runelite_interfaces/"
        val _package = "com.p3achb0t._runelite_interfaces"
        createInterfaces(folder, _package, analyzers, classRefObs)

        return path + hookDir + hookFileName

    }







    //DONE - create interface files
    //Done - make sure interface files are correctly superred
    //DONE 06/09/2019 - Interface files have methods
    //DONE 6/14/2019 - Create code to inject
}