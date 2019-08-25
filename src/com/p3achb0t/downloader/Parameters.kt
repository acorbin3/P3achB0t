package com.p3achb0t.downloader

import java.net.URL
import java.util.HashMap



class Parameters(world: Int){

    val PARAMETER_MAP = HashMap<String, String>()
    private val PARAMETER_BASE_URL = "runescape.com/l=0/jav_config.ws"

    init{
        parse(world)
    }

    fun parse(world:Int){
        val urlText = "http://oldschool$world.$PARAMETER_BASE_URL"
        //println("Getting params from $urlText")
        val connection = URL(urlText).openConnection()
        connection.connect()
        val res = connection.inputStream.bufferedReader().readText()
        PARAMETER_MAP.clear()
        for(line in res.split("\n")){
            if(line.contains("=")){
                var updatedLine = line.replace("param=","")
                var parts = updatedLine.split("=")

                when {
                    parts.size == 1 -> PARAMETER_MAP[parts[0]] = ""
                    parts.size == 2 -> PARAMETER_MAP[parts[0]] = parts[1]
                    parts.size == 3 -> PARAMETER_MAP[parts[0]] = parts[1] + "=" + parts[2]
                    parts.size == 4 -> PARAMETER_MAP[parts[0]] = parts[1] + "=" + parts[2] + "=" + parts[3]
                }
            }
        }

    }
}

