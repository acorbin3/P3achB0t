package com.p3achb0t.analyser.class_generation

import com.p3achb0t.analyser.runestar.ClassHook


fun cleanType(descriptor: String): String {
    return stripDiscriptorType(stripArray(descriptor))
}

fun isBaseType(descriptor: String): Boolean {
    return when (cleanType(descriptor)) {
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

fun getJavaType(discriptor: String): String {
    return when (discriptor) {
        "C" -> "char"
        "B" -> "byte"
        "S" -> "Short"
        "Z" -> "Boolean"
        "D" -> "double"
        "J" -> "long"
        "I" -> "int"
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
    var updatedType = ""
    if (count > 0) {
        updatedType = if (type == "Int" || type == "Boolean" || type == "Byte" || type == "Short") {
            type + "Array"
        } else {
            "Array<$type>"
        }
    }
    return when (count) {
        1 -> updatedType
        2 -> "Array<$updatedType>"
        3 -> "Array<Array<$updatedType>>"
        4 -> "Array<Array<Array<$updatedType>>>"
        5 -> "Array<Array<Array<Array<$updatedType>>>>"
        else -> type
    }
}

fun getJavaArrayString(count: Int, type: String): String {
    return when (count) {
        1 -> "$type[]"
        2 -> "$type[][]"
        3 -> "$type[][][]"
        4 -> "$type[][][][]"
        5 -> "$type[][][][][]"
        else -> type
    }
}


fun isFieldNameUnique(
    clazz: ClassHook?,
    fieldName: String,
    classRefObs: MutableMap<String, ClassHook>
): Int {
    if (clazz?.fields?.count() == 0) {
        return 0
    }
    var count = 0
    clazz?.fields?.iterator()?.forEach { field ->
        //        println("\t ${field.field}")
        if (field.field == fieldName) {
            count += 1
        }
    }

    val superClass = clazz?.`super`
//    println("$count $superClass")
    if (superClass == "")
        return count
    if (superClass in classRefObs) {
//        println(classRefObs[superClass]?._class)
        return isFieldNameUnique(classRefObs[superClass], fieldName, classRefObs).let { count.plus(it) }
    }
    return count
}
