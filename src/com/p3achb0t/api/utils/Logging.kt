package com.p3achb0t.api.utils

import com.p3achb0t.api.utils.Logging.LogInternal.Companion.createFile
import com.p3achb0t.client.accounts.AccountManager
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

open class Logging {

    val logger = LogInternal()

    companion object{

        val path = ".logs/"
        val folder = File( path)


        val fileFormatter = SimpleDateFormat("yyyy_MM_dd__HH_mm_ss")

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val date = formatter.format(Date())
        val fileDate = fileFormatter.format(Date())
        var account = ""
        var jsonFile = AccountManager.accountsJsonFileName.replace(".json","")
        var fn = "$path/${jsonFile.split("\\").last()}__$fileDate.txt"
        var file = File(fn)
        var dumpToAFile = false
        var fileCreated = true
        init {
            if(jsonFile.contains("/")){
                jsonFile = jsonFile.split("/").last()
                fn = "$path/${jsonFile.split("/").last()}__$fileDate.txt"
                file = File(fn)
            }
            folder.mkdirs()
        }

        fun error(s: String) {
            createFile()
            val outputString = "${formatter.format(Date())} - ERROR - ${getLocation()} $s"
            println(outputString)
            if(dumpToAFile) {
                file.appendText(outputString + "\n")
//                file.printWriter().use { out ->
//                    out.println(outputString)
//                    out.flush()
//                }
            }
        }
        fun info(s: String) {
            createFile()
            val outputString = "${formatter.format(Date())} - INFO - ${getLocation()} $s"
            println(outputString)
            if(dumpToAFile) {
                file.appendText(outputString + "\n")
//                file.printWriter().use { out ->
//                    out.println(outputString)
//                    out.flush()
//                }
            }

        }

        fun getLocation(): String {
            val stElements = Thread.currentThread().stackTrace
            //stElements.reverse()
            var klass = ""
            var method = ""
            stElements.iterator().forEach {
                if (klass.isEmpty()
                        && !it.className.contains("kotlin")
                        && !it.className.contains("ScriptManager")
                        && !it.className.contains("Thread")
                        && !it.className.contains("Logging")) {
                    klass = it.className.split(".").last()
                    method = it.methodName + ":" + it.lineNumber
                    return@forEach

                }
            }
            return "$klass.$method"
        }
    }

    class LogInternal{
        companion object {
            fun createFile() {
                if (!fileCreated) {

                    account = AccountManager.accounts.first().username.split("@").first()
                    fn = "$path/${account}__$fileDate.txt"
                    file = File(fn)
                    fileCreated = true

                }
            }
        }

        fun debug(s: String){
            createFile()
            val outputString = "${formatter.format(Date())} [${Thread.currentThread().name}] DEBUG - ${getLocation()} $s"
            println(outputString)
            if(dumpToAFile) {
                file.appendText(outputString + "\n")
//                file.printWriter().use { out ->
//                    out.println(outputString)
//                    out.flush()
//                }
            }
        }

        fun info(s: String) {
            createFile()
            val outputString = "${formatter.format(Date())} - INFO - ${getLocation()} $s"
            println(outputString)
            if(dumpToAFile) {
                file.appendText(outputString + "\n")
//                file.printWriter().use { out ->
//                    out.println(outputString)
//                    out.flush()
//                }
            }

        }

        fun error(s: String) {
            createFile()
            val outputString = "${formatter.format(Date())} - ERROR - ${getLocation()} $s"
            println(outputString)
            if(dumpToAFile) {
                file.appendText(outputString + "\n")
//                file.printWriter().use { out ->
//                    out.println(outputString)
//                    out.flush()
//                }
            }
        }

    }
}

