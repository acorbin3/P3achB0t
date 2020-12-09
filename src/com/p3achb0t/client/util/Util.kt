package com.p3achb0t.client.util

import com.p3achb0t.client.configs.Constants
import com.p3achb0t.client.util.Util.Companion.createDefaultConfig
import java.io.File
import java.io.InputStream
import java.net.Socket
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.MessageDigest
import java.math.BigInteger


fun String.sha256(): String {
    val md = MessageDigest.getInstance("SHA-256")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

class Util {

    companion object {
        fun checkClientRevision(revision: Int, timeout: Int): Boolean {

            try {
                val socket = Socket(Constants.GAME_WORLD_BASE, 43594)
                socket.getOutputStream().write(byteArrayOf(15, (revision shr 24 and 0xFF).toByte(), (revision shr 16 and 0xFF).toByte(), (revision shr 8 and 0xFF).toByte(), (revision and 0xFF).toByte()))
                socket.soTimeout = timeout
                val response = socket.getInputStream().read()
                socket.close()
                if (response != 0) {
                    return false
                }
                return true
            }catch (e: Exception){
                return true
            }
        }

        fun createDirIfNotExist(path: String) {
            if (!Files.exists(Paths.get(path ))) {
                File(path).mkdirs()
            }
        }

        fun createAllDirs() {
            createDirIfNotExist(Path.of(Constants.USER_DIR, Constants.APPLICATION_CACHE_DIR, Constants.JARS_DIR).toString())
            createDirIfNotExist("cache")
        }

        fun readConfig(path: String) : String {
            println("Looking for config here: $path")
            val file = File(path)
            if(!file.exists()){
                println("Could not find $path")
                createDefaultConfig(path)
                return ""
            }
            val ins: InputStream = file.inputStream()
            val content = ins.readBytes().toString(Charset.defaultCharset())
            println(content)
            return content

        }

        fun createDefaultConfig(path: String){

            /*
            val file = File(path)
            println("Parth: " + file.path)
            println("Parent: " + file.parent)
            println("Full path: " + file.absoluteFile)

            File(file.parent).mkdirs()
            println()
            file.createNewFile()
            val gsonPretty = GsonBuilder().setPrettyPrinting().create()
            val jsonAccountPretty = gsonPretty.toJson(arrayOf(Account()))
            file.writeText(jsonAccountPretty)

            val readme = File(file.parent + "/readme.txt")
            readme.createNewFile()
            readme.writeText("Proxy example would be : SOCKS5;185.244.192.119:7670 or none")
            readme.appendText("Anything that is not provided in the json will be defaulted to the following:")
            readme.appendText(jsonAccountPretty)

            */
        }
    }


}
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        createDefaultConfig("test/account.json")
    }
}