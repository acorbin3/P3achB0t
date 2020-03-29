package com.p3achb0t.api.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

open class Logging {
    val logger = LogInternal()

    class LogInternal{

        fun debug(s: String){
            val location = getLocation()
            val timeStamp = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(Date())
            val time = timeStamp
            val threadName = Thread.currentThread().name
            println("$time [$threadName] DEBUG - $location $s")
        }
        fun info(s: String){

            val location = getLocation()
            val time = DateTimeFormatter.ISO_INSTANT.format(Instant.now()) ?: ""
            println("$time INFO - $location $s")
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

