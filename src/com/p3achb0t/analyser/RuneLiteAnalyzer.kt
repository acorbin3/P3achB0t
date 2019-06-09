package com.p3achb0t.analyser

import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels

class RuneLiteAnalyzer {
    private var webAddress: String =
        "https://raw.githubusercontent.com/runelite-extended/runelite/eafb024f16821160aab966114962711d7a4d355e/extended-mixins/hooks.json"
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

    private fun isBaseType(discriptor: String): Boolean {
        return when (discriptor) {
            "C" -> true
            "B" -> true
            "S" -> true
            "Z" -> true
            "D" -> true
            "J" -> true
            "I" -> true
            "F" -> true
            "java/lang/String" -> true
            else -> false
        }
    }

    private fun getType(discriptor: String): String {
        return when (discriptor) {
            "C" -> "Char"
            "B" -> "Byte"
            "S" -> "Short"
            "Z" -> "Boolean"
            "D" -> "Double"
            "J" -> "Long"
            "I" -> "Int"
            "F" -> "Float"
            "java/lang/String" -> "String"
            else -> discriptor
        }
    }

    private fun getArrayString(count: Int, type: String): String {
        return when (count) {
            1 -> "Array<$type>"
            2 -> "Array<Array<$type>>"
            3 -> "Array<Array<Array<$type>>>"
            4 -> "Array<Array<Array<Array<$type>>>>"
            5 -> "Array<Array<Array<Array<Array<$type>>>>>"
            else -> type
        }
    }

    private fun isFieldNameUnique(clazz: RuneLiteJSONClasses.ClassDefinition, fieldName: String): Boolean {
        return clazz.fields.count { it.field == fieldName } == 0
    }

    fun createInterfaces() {
        val path = System.getProperty("user.dir")

        // Delete files
        val folder = File("C:\\Users\\C0rbin\\IdeaProjects\\P3achB0t\\src\\com\\p3achb0t\\_runelite_interfaces/")
        folder.deleteRecursively()
        folder.mkdir()
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

                    val arrayCount = field.descriptor.count { it == '[' }
                    var updatedDescriptor = field.descriptor
                    //Trim array brackets
                    if (arrayCount > 0) {
                        updatedDescriptor = field.descriptor.substring(arrayCount, field.descriptor.length)
//                        println("Removing array: ${field.descriptor}->$updatedDescriptor")
                    }
                    //Trim L; for long types
                    if (field.descriptor.contains(";")) {
                        updatedDescriptor = updatedDescriptor.substring(1, updatedDescriptor.length - 1)
//                        println("Removing array: ${field.descriptor}->$updatedDescriptor")

                    }
//                    println("Array Count: $arrayCount $updatedDescriptor    ${field.descriptor}")

                    if (updatedDescriptor in classRefObs) {
                        out.println(
                            "\t fun get_" + field.field + "(): ${getArrayString(
                                arrayCount,
                                getType(classRefObs[updatedDescriptor]?._class!!)
                            )}"
                        )
                    } else {
                        if (isBaseType(updatedDescriptor)) {
                            //Check to ensure that there are no fields with the same name in the super class
                            if (clazz.value._super in classRefObs) {
//                                print("Checking ${field.field} in class ${clazz.value._class} file is in ${classRefObs[clazz.value._super]!!._class}")
                                if (isFieldNameUnique(classRefObs[clazz.value._super]!!, field.field)) {
                                    out.println(
                                        "\t fun get_${field.field}(): ${getArrayString(
                                            arrayCount,
                                            getType(updatedDescriptor)
                                        )}"
                                    )
                                } else {
                                    println(
                                        "^^^^^Not Unique \"\\t fun get_${clazz.value._class}_${field.field}(): ${getArrayString(
                                            arrayCount,
                                            getType(updatedDescriptor)
                                        )}"
                                    )
                                    out.println(
                                        "\t fun get_${clazz.value._class}_${field.field}(): ${getArrayString(
                                            arrayCount,
                                            getType(updatedDescriptor)
                                        )}"
                                    )
                                }
                            } else {
                                out.println(
                                    "\t fun get_${field.field}(): ${getArrayString(
                                        arrayCount,
                                        getType(updatedDescriptor)
                                    )}"
                                )
                            }
                        } else {
                            out.println("\t fun get_" + field.field + "(): Any")
                        }
                    }
                }
                out.println("}")
            }
        }


    }

    //DONE - create interface files
    //Done - make sure interface files are correctly superred
    //DONE 06/09/2019 - Interface files have methods
    //TODO - Create code to inject
}