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

        val date = SimpleDateFormat("yyyy_MM_dd__HH_mm_ss").format(Date())
        val fn = "$path$date.txt"
        val file = File(fn)

    }

    class LogInternal{

        fun debug(s: String){
            val location = getLocation()
            val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(Date())
            val threadName = Thread.currentThread().name
            val outputString = "$timeStamp [$threadName] DEBUG - $location $s"
            println(outputString)
            file.appendText(outputString + "\n")
//            file.printWriter().use { out ->
//                out.println(outputString)
//                out.flush()
//            }
        }

        fun info(s: String) {

            val location = getLocation()
            val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(Date())

            val outputString = "$timeStamp - INFO - $location $s"
            println(outputString)
            file.appendText(outputString + "\n")
//            file.printWriter().use { out ->
//                out.println(outputString)
//                out.flush()
//            }

        }

        fun error(s: String) {
            val location = getLocation()
            val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(Date())
            val outputString = "$timeStamp - ERROR - $location $s"
            file.appendText(outputString + "\n")
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

