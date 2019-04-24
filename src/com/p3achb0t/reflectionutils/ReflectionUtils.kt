package com.p3achb0t.reflectionutils

import com.p3achb0t.Main
import com.p3achb0t.rsclasses.*
import java.lang.reflect.Field


fun getWidgetData(widgetIndex: WidgetIndex): Widget {
    val widgetClassName = Main.dream?.analyzers?.get(
        Client::class.java.simpleName
    )?.normalizedFields?.get("widgets")?.fieldTypeObsName
    val widgetFieldName = Main.dream?.analyzers?.get(
        Client::class.java.simpleName
    )?.normalizedFields?.get("widgets")?.obsName

    val widgetClazz = Main.classLoader?.loadClass(widgetClassName)
    val widgetField = widgetClazz?.getDeclaredField(widgetFieldName)
    widgetField?.isAccessible = true
    val widgetObj = widgetField?.get(null)

    val localWidgetData = mutableMapOf<String, RSClasses.Field>()
    //Loop over the arrays to find the widget we care about
    if (widgetObj is Array<*>) {
        widgetObj.forEachIndexed { parentIndex, childArray ->
            if (childArray is Array<*>) {
                childArray.forEachIndexed { childIndex, childItem ->
                    if (widgetIndex.childID != ""
                        && widgetIndex.parentID != ""
                        && widgetIndex.childID != "Widgets"
                        && widgetIndex.parentID != "Widgets"
                        && parentIndex == widgetIndex.parentID.replace("Parent ", "").toInt()
                        && childIndex == widgetIndex.childID.toInt()
                    ) {
                        val v = childItem!!::class.java
                        val currentWidgetFieldHookData = Main.dream?.analyzers?.get(
                            Widget::class.java.simpleName
                        )?.fields!!
                        for (field in v.declaredFields) {
                            if (currentWidgetFieldHookData.contains(field.name)) {
//                                println(field.name + " ->" + currentWidgetFieldHookData[field.name])
                                // Get data
                                currentWidgetFieldHookData[field.name]?.value = getFieldResult(
                                    v,
                                    childItem,
                                    field,
                                    0
                                ).toString()
                                localWidgetData[currentWidgetFieldHookData[field.name]!!.fieldName] = RSClasses.Field()
                                localWidgetData[currentWidgetFieldHookData[field.name]?.fieldName]?.value =
                                    currentWidgetFieldHookData[field.name]?.value.toString()
                            }
                        }
                    }
                }
            }
        }
    }
    return Widget(localWidgetData)
}

fun printClazzFields(clazz: Class<out Any>, classObject: Any, level: Int) {
    val fieldList = Main.dream?.classRefObs?.get(clazz.simpleName)?.fields
    val indent = "\t".repeat(level)
//    println("$indent$$$$$$$$$ DeclaredFields$$$$$$$$$$")
    for (reflectField in clazz.declaredFields) {
        if (fieldList?.contains(reflectField.name)!!) {
            reflectField.isAccessible = true
//            print(indent + reflectField.type.simpleName + " " + fieldList[reflectField.name]?.fieldName)
            var result: Any? = null
            if (!reflectField.type.isArray) {
                result = getFieldResult(clazz, classObject, reflectField, level)
            }
//            println("\t" + result)
            parseArrayField(clazz, classObject, reflectField, level)

        }
    }
//    println("$indent--------Fields--------")
    for (reflectField in clazz.fields) {
        if (fieldList?.contains(reflectField.name)!!) {
            reflectField.isAccessible = true
            val result: Any? = getFieldResult(clazz, classObject, reflectField, level)

//            println(indent + reflectField.type.name + " " + fieldList[reflectField.name]?.fieldName + " " + result)
            parseArrayField(clazz, classObject, reflectField, level)

        }
    }
//    println("$indent@@@@@@@@@@@@@@")
}


fun getIndexFromReflectedArray(
    index: Int,
    classObject: Any,
    reflectField: Field
): String {
    if (reflectField.type.isArray) {
        reflectField.isAccessible = true
        val arrayFields = reflectField.get(classObject)

        if (reflectField.type.componentType.simpleName == "int") {
            return (java.lang.reflect.Array.get(arrayFields, index) as Int).toString()
        } else if (reflectField.type.componentType.simpleName == "String") {
            val item = java.lang.reflect.Array.get(arrayFields, index)
            if (item != null) {
                return item.toString()
            }

        } else if (reflectField.type.componentType.simpleName == "boolean") {
            return (java.lang.reflect.Array.get(arrayFields, index) as Boolean).toString()
        }

    }
    return ""
}

fun parseArrayField(
    clazz: Class<out Any>,
    classObject: Any,
    reflectField: Field,
    level: Int
) {
    if (reflectField.type.isArray) {
        val indent = "\t".repeat(level)

        reflectField.isAccessible = true
        val value = reflectField.get(classObject) ?: return
        var arrayLength = java.lang.reflect.Array.getLength(value)
        val arrayFields = reflectField.get(classObject)
        var resultList = "["
//        println("$indent\tComponent type: " + reflectField.type.componentType.simpleName + " Array size $arrayLength")
        if (arrayLength > 100) {
            arrayLength = 100
        }
        if (arrayLength > 0) {
            for (i in 0 until arrayLength) {
                if (reflectField.type.componentType.simpleName == "int") {
                    resultList += (java.lang.reflect.Array.get(arrayFields, i) as Int).toString() + ", "
                } else if (reflectField.type.componentType.simpleName == "String") {
                    val item = java.lang.reflect.Array.get(arrayFields, i)
                    if (item != null) {
                        resultList += "$item, "
                    }

                } else if (reflectField.type.componentType.simpleName == "boolean") {
                    resultList += (java.lang.reflect.Array.get(arrayFields, i) as Boolean).toString() + ", "
                } else if (!reflectField.type.componentType.isArray) {

                    val field = java.lang.reflect.Array.get(arrayFields, i)

                    if (field != null) {
//                        println("$indent\t Diving deeper into " + reflectField.type.componentType.simpleName)
                        for (f in field.javaClass.fields) {
                            resultList += getFieldResult(
                                field.javaClass,
                                field,
                                f,
                                level
                            ) as String + ", "
                        }
                    } else {
                        resultList += "null,"
                    }
                } else {// This an array with in an array

                }

            }
            if (resultList.isNotEmpty()) {
//                println("$indent\t$resultList]")
            }
        }
    }
}

fun getFieldResult(
    clazz: Class<out Any>,
    reflectField: Field
): Any? {
    val fieldList = Main.dream?.classRefObs?.get(clazz.simpleName)?.fields
    var result: Any? = null
    if (reflectField.type.simpleName == "int") {
        result = reflectField.getInt(clazz)
        if (fieldList?.get(reflectField.name)?.modifier != 0L) {
            result *= fieldList?.get(reflectField.name)?.modifier?.toInt()!!
        }
    } else if (reflectField.type.simpleName == " boolean") {
        result = reflectField.getBoolean(clazz)
    } else if (reflectField.type.simpleName == " string") {
        result = reflectField.get(clazz).toString()
    }
    return result
}

fun getFieldResult(
    clazz: Class<out Any>,
    classObject: Any,
    reflectField: Field,
    level: Int
): Any? {
    val fieldList = Main.dream?.classRefObs?.get(clazz.simpleName)?.fields
    var result: String = ""
    val indent = "\t".repeat(level)
    reflectField.isAccessible = true
    if (reflectField.type.isPrimitive) {
//        println("$indent res: ${reflectField.get(classObject)}")
        result = ""
    }
    if (reflectField.type.simpleName == "int") {
        var compute = reflectField.getInt(classObject)
//        print("$indent BeforeMOd: $compute")
        if (fieldList?.get(reflectField.name)?.modifier != null) {
            compute *= fieldList.get(reflectField.name)?.modifier?.toInt()!!
            result = compute.toString()
//            println(" After -> $result")
        }
    } else if (reflectField.type.simpleName == "boolean") {
        result = reflectField.getBoolean(classObject).toString()
    } else if (reflectField.type.simpleName == "string" || reflectField.type.simpleName == "String") {
        val obj = reflectField.get(classObject)
        if (obj != null)
            result = obj.toString()
    } else {
        //If its a type we dont have, skip it
        if (reflectField.type.isArray) {
            parseArrayField(clazz, classObject, reflectField, level)
        } else if (!Main.dream?.classRefObs?.contains(reflectField.type.simpleName)!!) {
//            println("$indent didnt find ${reflectField.type.simpleName}")

            return result
        }
        val nextClassObject = reflectField.get(classObject)
        if (reflectField.type.simpleName != null && nextClassObject != null) {
            reflectField.isAccessible = true
//            println("$indent Different Class type " + reflectField.type.simpleName)
            val superClazz = reflectField.type
            // Dont dive into Node Classes
            val list = listOf(
//                dream?.analyzers?.get(Node::class.java.simpleName)?.obsName,
                Main.dream?.analyzers?.get(LinkedList::class.java.simpleName)?.obsName
//                dream?.analyzers?.get(HashTable::class.java.simpleName)?.obsName
            )
            if (!list.contains(superClazz.simpleName))
                printClazzFields(superClazz, nextClassObject, level + 1)
        }

    }
    return result
}