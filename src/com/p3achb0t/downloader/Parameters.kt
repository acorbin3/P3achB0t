package com.p3achb0t.downloader

import java.net.URL
import java.util.HashMap



class Parameters(world: Int){

    private val PARAMETER_BASE_URL = "runescape.com/l=0/jav_config.ws"

    init{
        parse(world)
    }
    object data{
        val PARAMETER_MAP = HashMap<String, String>()
    }
    fun parse(world:Int){
        val urlText = "http://oldschool$world.$PARAMETER_BASE_URL"
        println("Getting params from $urlText")
        val connection = URL(urlText).openConnection()
        connection.connect()
        val res = connection.inputStream.bufferedReader().readText()
        data.PARAMETER_MAP.clear()
        for(line in res.split("\n")){
            if(line.contains("=")){
                var updatedLine = line.replace("param=","")
                var parts = updatedLine.split("=")

                when {
                    parts.size == 1 -> data.PARAMETER_MAP[parts[0]] = ""
                    parts.size == 2 -> data.PARAMETER_MAP[parts[0]] = parts[1]
                    parts.size == 3 -> data.PARAMETER_MAP[parts[0]] = parts[1] + "=" + parts[2]
                    parts.size == 4 -> data.PARAMETER_MAP[parts[0]] = parts[1] + "=" + parts[2] + "=" + parts[3]
                }
            }
        }

    }
}

