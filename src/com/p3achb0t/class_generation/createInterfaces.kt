package com.p3achb0t.class_generation

import com.p3achb0t.analyser.RuneLiteJSONClasses
import java.io.File
import java.io.PrintWriter

fun createInterfaces(
    path: String,
    _package: String,
    analyzers: MutableMap<String, RuneLiteJSONClasses.ClassDefinition>,
    classRefObs: MutableMap<String, RuneLiteJSONClasses.ClassDefinition>
) {

    // Delete files
    val folder = File(path)
    folder.deleteRecursively()
    folder.mkdir()
    for (clazz in analyzers) {
        val fn = path + clazz.value._class + ".kt"
        val file = File(fn)
        file.printWriter().use { out ->
            out.println("package " + _package)
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

                var returnType: String
                if (updatedDescriptor in classRefObs) {
                    returnType = getType(classRefObs[updatedDescriptor]?._class!!)
                    genFunction(field, clazz, classRefObs, out, arrayCount, returnType)
                } else {
                    if (isBaseType(updatedDescriptor)) {
                        //Check to ensure that there are no fields with the same name in the super class
                        returnType = getType(updatedDescriptor)

                        if (clazz.value._super in classRefObs) {
                            genFunction(field, clazz, classRefObs, out, arrayCount, returnType)
                        } else {
                            out.println(
                                "\tfun get${field.field.capitalize()}(): ${getArrayString(
                                    arrayCount,
                                    returnType
                                )}"
                            )
                        }
                    } else {

                        out.println("\tfun get" + field.field.capitalize() + "(): Any")
                    }
                }
            }
            out.println("}")
        }
    }
}

private fun genFunction(
    field: RuneLiteJSONClasses.FieldDefinition,
    clazz: MutableMap.MutableEntry<String, RuneLiteJSONClasses.ClassDefinition>,
    classRefObs: MutableMap<String, RuneLiteJSONClasses.ClassDefinition>,
    out: PrintWriter,
    arrayCount: Int,
    returnType: String
): String {
    var functionName = ""
//    println("Checking ${field.field} in class ${clazz.value._class} file is in ${classRefObs[clazz.value._super]?._class}")
    val fieldCount = isFieldNameUnique(classRefObs[clazz.value._super], field.field, classRefObs)
    if (fieldCount == 0) {
        out.println(
            "\tfun get${field.field.capitalize()}(): ${getArrayString(
                arrayCount,
                returnType
            )}"
        )
        functionName = "get${field.field.capitalize()}"
    } else {
        //TODO - update internal fields to include the class

        val lField = classRefObs[clazz.value.name]?.fields?.find { it.field == field.field }
        field.field = "${clazz.value._class}_${field.field}"
        lField?.field = field.field
        out.println(
            "\tfun get${field.field.capitalize()}(): ${getArrayString(
                arrayCount,
                returnType
            )}"
        )
        functionName = "get${field.field.capitalize()}"
    }
    return functionName
}