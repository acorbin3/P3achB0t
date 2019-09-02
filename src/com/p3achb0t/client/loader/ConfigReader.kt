package com.p3achb0t.client.loader

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.util.ArrayList
import java.util.HashMap

class ConfigReader @JvmOverloads constructor(private val url: String = "http://oldschool.runescape.com/jav_config.ws") {

    private fun readConfig(): Array<String> {
        // Create the stream and reader so we can dispose of it nicely in the finally
        // Yes I could have used the new try with resources but #yolo
        var inputStream: InputStream? = null
        var bufferedReader: BufferedReader? = null
        // Create what we will return, the lines of the config to make it easier to parse
        val lines = ArrayList<String>()
        try {
            // Create the URL instance to read the file, open the stream and init the reader
            val configUrl = URL(url)
            inputStream = configUrl.openStream()
            bufferedReader = BufferedReader(InputStreamReader(inputStream!!))

            // Read the config line by line and throw it into what we are going to return

            bufferedReader.readLines().forEach {
                lines.add(it)
            }
            /*while (line != null) {
                lines.add(line)
                line = bufferedReader.readLine()
            }*/


        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                // Close everything up nicely
                inputStream?.close()
                bufferedReader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        // Convert to array and return it
        return lines.toTypedArray()
    }

    fun read(): Map<String, String> {
        // Read the config file
        val page = readConfig()
        // Create the parameter map we want to return
        val map = HashMap<String, String>()
        for (parameter in page) {
            var p = parameter
            // Cleanse the string as we don't need "param=" or "msg="
            p = p.replace("param=", "").replace("msg=", "")
            // Split the string on the "=" sign and limit the split to 2 in case some of the parameters use the "=" sign
            val splitParameter = p.split("=".toRegex(), 2).toTypedArray()
            // Check if the value is empty and add an empty parameter with the name
            if (splitParameter.size == 1)
                map[splitParameter[0]] = ""
            // Check there is a value and add the parameter with the value
            if (splitParameter.size == 2)
                map[splitParameter[0]] = splitParameter[1]
        }
        // return our parameters
        return map
    }
}