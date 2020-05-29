package com.p3achb0t.api.utils

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

open class Logging {

    val logger = LogInternal()

    companion object{

        val path = ".logs/"
        val folder = File( path)
        init {
            folder.mkdirs()
        }

        val fileFormatter = SimpleDateFormat("yyyy_MM_dd__HH_mm_ss")
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = formatter.format(Date())
        val fn = "$path$date.txt"
        val file = File(fn)

    }

    class LogInternal{

        fun debug(s: String){
            val outputString = "${formatter.format(Date())} [${Thread.currentThread().name}] DEBUG - ${getLocation()} $s"
            println(outputString)
//            file.appendText(outputString + "\n")
//            file.printWriter().use { out ->
//                out.println(outputString)
//                out.flush()
//            }
        }

        fun info(s: String) {

            val outputString = "${formatter.format(Date())} - INFO - ${getLocation()} $s"
            println(outputString)
//            file.appendText(outputString + "\n")
//            file.printWriter().use { out ->
//                out.println(outputString)
//                out.flush()
//            }

        }

        fun error(s: String) {
            val outputString = "${formatter.format(Date())} - ERROR - ${getLocation()} $s"
            println(outputString)
//            file.appendText(outputString + "\n")
//            file.printWriter().use { out ->
//                out.println(outputString)
//                out.flush()
//            }
        }

        private fun getLocation(): String {
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
}

