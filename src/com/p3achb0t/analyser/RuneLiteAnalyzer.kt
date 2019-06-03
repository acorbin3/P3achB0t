package com.p3achb0t.analyser

import com.google.gson.Gson
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

    val analyzers = mutableMapOf<String, RuneLiteJSONClasses.ClassDefinition>()
    val classRefObs = mutableMapOf<String, RuneLiteJSONClasses.ClassDefinition>()


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
        val topic = Gson().fromJson(json, Array<RuneLiteJSONClasses.ClassDefinition>::class.java)
        for (clazz in topic) {
            analyzers[clazz._class] = clazz
            classRefObs[clazz.name] = clazz
        }

        createInterfaces()

        return path + hookDir + hookFileName

    }

    fun createInterfaces() {
        val path = System.getProperty("user.dir")
        for (clazz in analyzers) {
            val fn =
                "C:\\Users\\C0rbin\\IdeaProjects\\P3achB0t\\src\\com\\p3achb0t\\_runelite_interfaces/" + clazz.value._class + ".kt"
            val file = File(fn)
            file.printWriter().use { out ->
                out.println("package com.p3achb0t._runelite_interfaces")
                out.println("")
                if (!clazz.value._super.contains("java/lang/Object") && clazz.value._super in classRefObs) {
                    out.println("interface ${clazz.value._class}: ${classRefObs[clazz.value._super]?._class}{")
                } else {
                    out.println("interface ${clazz.value._class} {")
                }
                for (field in clazz.value.fields) {

                    //x = fields["x"]?.resultValue?.toInt() ?: -1
                    when {
                        field.descriptor == "C" -> out.println("\t fun get_" + field.field + "(): Char")
                        field.descriptor == "[C" -> out.println("\t fun get_" + field.field + "(): Array<Char>")
                        field.descriptor == "B" -> out.println("\t fun get_" + field.field + "(): Byte")
                        field.descriptor == "[B" -> out.println("\t fun get_" + field.field + "(): Array<Byte>")
                        field.descriptor == "[[B" -> out.println("\t fun get_" + field.field + "(): Array<Array<Byte>>")
                        field.descriptor == "[[[B" -> out.println("\t fun get_" + field.field + "(): Array<Array<Array<Byte>>>")
                        field.descriptor == "S" -> out.println("\t fun get_" + field.field + "(): Short")
                        field.descriptor == "[S" -> out.println("\t fun get_" + field.field + "(): Array<Short>")
                        field.descriptor == "[[S" -> out.println("\t fun get_" + field.field + "(): Array<Array<Short>>")
                        field.descriptor == "[[[S" -> out.println("\t fun get_" + field.field + "(): Array<Array<Array<Short>>>")
                        field.descriptor == "Z" -> out.println("\t fun get_" + field.field + "(): Boolean")
                        field.descriptor == "[Z" -> out.println("\t fun get_" + field.field + "(): Array<Boolean>")
                        field.descriptor == "[[Z" -> out.println("\t fun get_" + field.field + "(): Array<Array<Boolean>>")
                        field.descriptor == "[[[Z" -> out.println("\t fun get_" + field.field + "(): Array<Array<Array<Boolean>>>")
                        field.descriptor == "D" -> out.println("\t fun get_" + field.field + "(): Double")
                        field.descriptor == "J" -> out.println("\t fun get_" + field.field + "(): Long")
                        field.descriptor == "[J" -> out.println("\t fun get_" + field.field + "(): Array<Long>")
                        field.descriptor == "F" -> out.println("\t fun get_" + field.field + "(): Float")
                        field.descriptor == "[F" -> out.println("\t fun get_" + field.field + "(): Array<Float>")
                        field.descriptor == "[[F" -> out.println("\t fun get_" + field.field + "(): Array<Array<Float>>")
                        field.descriptor == "I" -> out.println("\t fun get_" + field.field + "(): Int")
                        field.descriptor == "[I" -> out.println("\t fun get_" + field.field + "(): Array<Int>")
                        field.descriptor == "[[I" -> out.println("\t fun get_" + field.field + "(): Array<Array<Int>>")
                        field.descriptor == "[[[I" -> out.println("\t fun get_" + field.field + "(): Array<Array<Array<Int>>>")
                        field.descriptor == "Ljava/lang/String;" -> out.println("\t fun get_" + field.field + "(): String")
                        field.descriptor == "[Ljava/lang/String;" -> out.println("\t fun get_" + field.field + "(): Array<String>")
                        field.descriptor == "[[Ljava/lang/String;" -> out.println("\t fun get_" + field.field + "(): Array<Array<String>>")
                        else -> {
                            val arrayCount = field.descriptor.count { it == '[' }
                            // Remove L and ; and find the interface for the class
                            val trimmedDiscriptor = field.descriptor.substring(1, field.descriptor.length - 1)
                            if (trimmedDiscriptor in classRefObs) {
                                out.println("\t fun get_" + field.field + "(): " + classRefObs[trimmedDiscriptor]?._class)
                            } else {
                                println(field.descriptor)
                                out.println("\t fun get_" + field.field + "(): Any")
                            }

                        }
                    }
                }
                out.println("}")
            }
        }


    }

    //TODO - create interface files
    //TODO - make sure interface files are correctly superred
    //TODO - Interface files have methods
    //TODO - Create code to inject
}