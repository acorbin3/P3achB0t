package com.p3achb0t.injection.class_generation

import com.p3achb0t.analyser.runestar.ClassHook
import com.p3achb0t.analyser.runestar.FieldHook
import java.io.File
import java.io.PrintWriter

fun createRunestarInterfaces(
    path: String,
    _package: String,
    analyzers: MutableMap<String, ClassHook>,
    classRefObs: MutableMap<String, ClassHook>
) {

    // Delete files
    val folder = File(path)
//    folder.deleteRecursively()
    folder.mkdir()
    for (clazz in analyzers) {
        val fn = path + clazz.value.`class` + ".kt"
        val file = File(fn)
        file.printWriter().use { out ->
            out.println("package " + _package)
            out.println("")
            if (!clazz.value.`super`.contains("java/lang/Object") && clazz.value.`super` in classRefObs) {
                if(clazz.value.fields.isEmpty())
                    out.println("interface ${clazz.value.`class`} : ${classRefObs[clazz.value.`super`]?.`class`}")
                else
                    out.println("interface ${clazz.value.`class`} : ${classRefObs[clazz.value.`super`]?.`class`} {")
            }
            else if(clazz.value.fields.isEmpty()){
                out.println("interface ${clazz.value.`class`}")
            }
            else {
                out.println("interface ${clazz.value.`class`} {")
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
                    returnType = getType(classRefObs[updatedDescriptor]?.`class`!!)
                    genFunction(field, clazz, classRefObs, out, arrayCount, returnType)
                } else {
                    if (isBaseType(updatedDescriptor)) {
                        //Check to ensure that there are no fields with the same name in the super class
                        returnType = getType(updatedDescriptor)

                        if (clazz.value.`super` in classRefObs) {
                            genFunction(field, clazz, classRefObs, out, arrayCount, returnType)
                        } else {
                            out.println(
                                "    fun get${field.field.capitalize()}(): ${getArrayString(
                                    arrayCount,
                                    returnType
                                )}"
                            )
                        }
                    } else {

                        out.println("    fun get" + field.field.capitalize() + "(): Any")
                    }
                }
            }
            if(clazz.value.fields.isNotEmpty()){ out.println("}")}
        }
    }
}

private fun genFunction(
    field: FieldHook,
    clazz: MutableMap.MutableEntry<String, ClassHook>,
    classRefObs: MutableMap<String, ClassHook>,
    out: PrintWriter,
    arrayCount: Int,
    returnType: String
): String {
    var functionName = ""
//    println("Checking ${field.field} in class ${clazz.value.`class`} file is in ${classRefObs[clazz.value.`super`]?.`class`}")
    val fieldCount = isFieldNameUnique(classRefObs[clazz.value.`super`], field.field, classRefObs)
    if (fieldCount == 0) {
        out.println(
            "    fun get${field.field.capitalize()}(): ${getArrayString(
                arrayCount,
                returnType
            )}"
        )
        functionName = "get${field.field.capitalize()}"
    } else {

        val lField = classRefObs[clazz.value.name]?.fields?.find { it.field == field.field }
        field.field = "${clazz.value.`class`}_${field.field}"
        lField?.field = field.field
        out.println(
            "    fun get${field.field.capitalize()}(): ${getArrayString(
                arrayCount,
                returnType
            )}"
        )
        functionName = "get${field.field.capitalize()}"
    }
    return functionName
}