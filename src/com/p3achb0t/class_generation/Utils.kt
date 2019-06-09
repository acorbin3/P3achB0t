package com.p3achb0t.class_generation

import com.p3achb0t.analyser.RuneLiteJSONClasses

fun isBaseType(discriptor: String): Boolean {
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

fun getType(discriptor: String): String {
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

fun stripDiscriptorType(descriptor: String): String {
    return if (descriptor.contains(";")) {
        descriptor.substring(1, descriptor.length - 1)
    } else {
        descriptor
    }
}

fun stripArray(descriptor: String): String {
    val arrayCount = descriptor.count { it == '[' }
    return descriptor.substring(arrayCount, descriptor.length)
}

fun getArrayString(count: Int, type: String): String {
    return when (count) {
        1 -> "Array<$type>"
        2 -> "Array<Array<$type>>"
        3 -> "Array<Array<Array<$type>>>"
        4 -> "Array<Array<Array<Array<$type>>>>"
        5 -> "Array<Array<Array<Array<Array<$type>>>>>"
        else -> type
    }
}

fun isFieldNameUnique(
    clazz: RuneLiteJSONClasses.ClassDefinition?,
    fieldName: String,
    classRefObs: MutableMap<String, RuneLiteJSONClasses.ClassDefinition>
): Int {
    if (clazz?.fields?.count() == 0) {
        return 0
    }
    var count = 0
    clazz?.fields?.iterator()?.forEach { field ->
        println("\t ${field.field}")
        if (field.field == fieldName) {
            count += 1
        }
    }

    val superClass = clazz?._super
    println("$count $superClass")
    if (superClass == "")
        return count
    if (superClass in classRefObs) {
        println(classRefObs[superClass]?._class)
        return isFieldNameUnique(classRefObs[superClass], fieldName, classRefObs).let { count.plus(it) }
    }
    return count
}