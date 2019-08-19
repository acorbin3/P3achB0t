package com.p3achb0t.class_generation

import com.p3achb0t.analyser.RuneLiteJSONClasses
import java.io.File
import java.io.PrintWriter

fun createJavaInterfaces(
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
        val fn = path + clazz.value._class + ".java"
        val file = File(fn)
        file.printWriter().use { out ->
            out.println("package $_package;")
            out.println("")
            if (!clazz.value._super.contains("java/lang/Object") && clazz.value._super in classRefObs) {
                out.println("public interface ${clazz.value._class} extends ${classRefObs[clazz.value._super]?._class}{")
            } else {
                out.println("public interface ${clazz.value._class} {")
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

                var returnType = ""
                if (updatedDescriptor in classRefObs) {
                    returnType = getJavaType(classRefObs[updatedDescriptor]?._class!!)
                    genFunction(field, clazz, classRefObs, out, arrayCount, returnType)
                } else {
                    if (isBaseType(updatedDescriptor)) {
                        //Check to ensure that there are no fields with the same name in the super class
                        returnType = getJavaType(updatedDescriptor)

                        if (clazz.value._super in classRefObs) {
                            genFunction(field, clazz, classRefObs, out, arrayCount, returnType)
                        } else {
                            out.println("\t${getJavaArrayString(arrayCount,returnType)} get${field.field.capitalize()}();")
                        }
                    } else {
                        out.println("\tvoid get${field.field.capitalize()}();")
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
) {
//    println("Checking ${field.field} in class ${clazz.value._class} file is in ${classRefObs[clazz.value._super]?._class}")
    val fieldCount = isFieldNameUnique(classRefObs[clazz.value._super], field.field, classRefObs)
    if (fieldCount == 0) {
        out.println("\t${getJavaArrayString(arrayCount,returnType)} get${field.field.capitalize()}();")
    } else {
        //TODO - update internal fields to include the class

        val lField = classRefObs[clazz.value.name]?.fields?.find { it.field == field.field }
        field.field = "${clazz.value._class.capitalize()}${field.field.capitalize()}"
        lField?.field = field.field
//        println(
//            "^^^^^Not Unique \"\\t fun get_${field.field}(): ${getJavaArrayString(
//                arrayCount,
//                returnType
//            )}"
//        )
        out.println("\t${getJavaArrayString(arrayCount,returnType)} get${field.field.capitalize()}();")
    }
}