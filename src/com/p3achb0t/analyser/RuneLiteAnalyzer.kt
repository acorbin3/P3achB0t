package com.p3achb0t.analyser

import com.google.gson.Gson
import com.p3achb0t.rsclasses.RSClasses
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels

class RuneLiteAnalyzer {
    private var webAddress: String =
        "https://raw.githubusercontent.com/runelite-extended/runelite/master/extended-mixins/hooks.json"
    val hookDir = "\\hooks\\"

    var hookFile = ""
    var hookFileName = "runeliteHooks.txt"

    val analyzers = mutableMapOf<String, RSClasses>()
    val classRefObs = mutableMapOf<String, RSClasses>()
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
        val topic = Gson().fromJson(json, Array<RuneLiteJSONClasses.RuneLiteClass>::class.java)
        val item = topic[0]

        println(topic[0]._class)


        return path + hookDir + hookFileName

    }
}