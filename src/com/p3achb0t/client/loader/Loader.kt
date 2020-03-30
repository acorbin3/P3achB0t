package com.p3achb0t.client.loader

import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.util.Util
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.nio.file.Paths

class Loader {

    fun run() : String {
        return downloadGamePack()
    }

    private fun setup() {

    }

    private fun downloadGamePack() : String {
        val isUptoDate = Util.checkClientRevision(Constants.REVISION, 5000)

        if (isUptoDate && isLocal()) {
            return isLocalGamepack()

        } else {
            // clean -> Download TODO clean
            print("Cleaning up old gamepacks")
            val gamePacksDir = "${Constants.USER_DIR}/${Constants.APPLICATION_CACHE_DIR}/${Constants.JARS_DIR}"
            File(gamePacksDir).deleteRecursively()
            File(gamePacksDir).mkdir()
            download()
            return isLocalGamepack()
        }
    }

    private fun download() {
        val gamepackName = getLatestGamepackName()
        println("Gamepack not found locally...downloading pack")
        val gamepackURL = URL(Constants.GAME_WORLD_URL + "/" + gamepackName)
        val readableByteChannel = Channels.newChannel(gamepackURL.openStream())
        val directory = File(Constants.USER_DIR + "/" + Constants.APPLICATION_CACHE_DIR + "/" + Constants.JARS_DIR)
        if(!directory.exists()){
            directory.mkdirs()
        }
        val fileOutStream = FileOutputStream(Paths.get(Constants.USER_DIR, Constants.APPLICATION_CACHE_DIR, Constants.JARS_DIR, gamepackName).toString())
        val fileChannel = fileOutStream.channel
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE)
        println("$gamepackName downloaded!")
        //return Paths.get(Constants.APPLICATION_CACHE_DIR, Constants.JARS_DIR, gamepackName).toString()

    }

    private fun isLocal() : Boolean {
        File(Paths.get(Constants.USER_DIR, Constants.APPLICATION_CACHE_DIR, Constants.JARS_DIR).toString()).walk().forEach {
            if (it.name.toString().contains(".jar")) {
                return true
            }
        }
        return false
    }

    private fun isLocalGamepack() : String {
        File(Paths.get(Constants.USER_DIR, Constants.APPLICATION_CACHE_DIR, Constants.JARS_DIR).toString()).walk().forEach {
            if (it.name.toString().contains(".jar")) {
                return Paths.get(Constants.USER_DIR, Constants.APPLICATION_CACHE_DIR, Constants.JARS_DIR, it.name).toString()
            }
        }
        return ""
    }

    private fun getLatestGamepackName() : String {
        val dom = URL(Constants.GAME_WORLD_URL)
        val connection = dom.openConnection()
        connection.connect()
        val res = connection.inputStream.bufferedReader().readText()
        for (line in res.split("\n")) {
            if (line.contains("archive=")) {
                val strReplace = "g.*.jar"
                val gamePack = getRegSelection(strReplace, line).replace(".jar", "__v${Constants.REVISION_WITH_SUBVERSION }.jar")
                println("Current game pack: $gamePack")
                return gamePack
            }
        }
        return ""
    }

    private fun getRegSelection(strReplace: String, line: String): String {
        val reg = strReplace.toRegex()
        val res = reg.find(line)?.value
        res.let { return it!! }
    }
}