package com.p3achb0t.reflectionutils

import com.p3achb0t.Main
import com.p3achb0t.rsclasses.Client
import java.io.*
import java.lang.reflect.Field
import java.util.*


fun <T : Serializable> deepCopy(obj: T?): T? {
    if (obj == null) return null
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(obj)
    oos.close()
    val bais = ByteArrayInputStream(baos.toByteArray())
    val ois = ObjectInputStream(bais)
    @Suppress("unchecked_cast")
    return ois.readObject() as T
}
//TODO - Needs to be updated to the newclass type
//fun getClientData(): Client {
//    return Client(getAllFieldReflectionData(Main.client!!::class.java, Main.client!!))
//}
//TODO - Needs to be updated to the newclass type
//fun getAllFieldReflectionData(
//    clazz: Class<out Any>,
//    classObject: Any
//): MutableMap<String, RSClasses.Field> {
//    val localData = mutableMapOf<String, RSClasses.Field>()
//    val hookData = dream?.classRefObs?.get(
//        clazz.simpleName
//    )?.fields!!
//    for (declaredField in clazz.declaredFields) {
//        if (hookData.count { it.name == declaredField.name }> 0) {
//            val localRes = getFieldResult(
//                clazz,
//                classObject,
//                declaredField,
//                0,
//                false
//            )
//            if (localRes != null) {
//                localData[hookData[declaredField.name]!!.fieldName] = localRes
//            } else {
//                localData[hookData[declaredField.name]!!.fieldName] = RSClasses.Field()
//            }
//        }
//    }
//    return localData
//}
//TODO - Needs to be updated to the newclass type
//fun getItemTableData() {
//    val baseClazz = Client::class.java
//    val fieldName = "itemTable"
//    // TODO - its possible that we are not looking at the correct fields of the static class
//
//    val fieldData = getFieldData(baseClazz, fieldName)
//    val fieldClazz = fieldData!!::class.java
//    println("fieldClazz " + fieldClazz.simpleName)
//    val hookData = dream?.analyzers?.get(
//        HashTable::class.java.simpleName
//    )?.fields
//
//    for (field in fieldClazz.declaredFields) {
//        if (hookData != null && hookData.contains(field.name)) {
////                                println(field.name + " ->" + currentWidgetFieldHookData[field.name])
//            // Get data
//            val res = getFieldResult(
//                fieldClazz,
//                fieldData,
//                field,
//                0
//            )
//            if (res != null) {
//                hookData[field.name] = res
//            }
//            println(res)
//
//        }
//    }
//
//
//}

fun getGroundItemData() {
    val clientClazz = Main.client!!::class.java
    val baseClazz = Client::class.java
    val fieldName = "groundItemList"
    //TODO - Needs to be updated to the newclass type
//    getDeclaredFieldData(baseClazz, fieldName, clientClazz)
}

//fun getLocalNPCData() {
//    val npcs: ArrayList<Npc> = ArrayList()
//    val clientClazz = Main.client!!::class.java
//    val localNpcFieldData = dream?.classRefObs?.get(
//        clientClazz.simpleName
//    )?.normalizedFields?.get("localNPCs")
//    val npcDeclaredField = clientClazz.getDeclaredField(localNpcFieldData?.obsName)
//    npcDeclaredField.isAccessible = true
//    if (npcDeclaredField.type.isArray) {
////        print("We have an array")
//        val res = parseArrayField(Main.client!!, npcDeclaredField, level = 0, displayData = false, recursive = true)
//        for (field in res!!) {
//            if (field is RSClasses.Field) {
//                println(field.fields["name"])
//                npcs.add(Npc(field.fields))
//            }
//        }
//    }
//}

//fun getLocalPlayersData() {
//    val players: ArrayList<Player> = ArrayList()
//    val clientClazz = Main.client!!::class.java
//    val fieldData = dream?.classRefObs?.get(
//        clientClazz.simpleName
//    )?.normalizedFields?.get("players")
//    val declaredField = clientClazz.getDeclaredField(fieldData?.obsName)
//    declaredField.isAccessible = true
//    if (declaredField.type.isArray) {
////        print("We have an array")
//        val res = parseArrayField(Main.client!!, declaredField, level = 0, displayData = false, recursive = true)
//
//        for (field in res!!) {
//            if (field is RSClasses.Field) {
//                println(field)
////            println(field.fields["name"]?.resultValue)
//                players.add(Player(field.fields))
//            }
//        }
//    }
//
//}

fun getGroundItems() {

}
//TODO - Needs to be updated to the newclass type
//fun getRegion() {
//    val clientClazz = Main.client!!::class.java
//    val baseClazz = Client::class.java
//    val fieldName = "region"
//    val result = getDeclaredFieldData(baseClazz, fieldName, clientClazz, displayData = true)
//
////    val declaredField = getDeclaredField(baseClazz, fieldName)
////    declaredField?.isAccessible = true
////    val superClazz = declaredField?.type
////    val fieldList = dream?.classRefObs?.get(superClazz?.simpleName)?.fields
////    val localFieldtData = mutableMapOf<String, RSClasses.Field>()
////    if(superClazz != null) {
////        println("Declared Fields:")
////        for (dField in superClazz.declaredFields) {
////            if(fieldList?.contains(dField.name)!!) {
////                val superClass = dField.type.superclass
////                if(superClass != null)
////                    println(superClass.toString() + " " + dField.type.simpleName + " " + dField.name + "->" + fieldList[dField.name]?.fieldName)
////                else
////                    println(dField.type.simpleName + " " + dField.name + "->" + fieldList[dField.name]?.fieldName)
////
////                val res = getFieldResult(
////                    superClazz,
////                    Main.client!!,
////                    dField,
////                    level = 0,
////                    recursive = true,
////                    displayData = false
////                )
////                val localFieldName = fieldList[dField.name]?.fieldName
////                if(localFieldName != null && res != null) {
////                    localFieldtData[localFieldName] = res
////                }
////            }
////        }
////    }
//
//    val reg = result?.get(0)
//    if (reg is RSClasses.Field) {
//        val region = Region(reg.fields)
//    }
//}
//TODO - Needs to be updated to the newclass type
//private fun getDeclaredFieldData(
//    baseClazz: Class<Client>,
//    fieldName: String,
//    clientClazz: Class<out Applet>,
//    displayData: Boolean = false,
//    recursive: Boolean = true
//): ArrayList<Any>? {
//    var arrayRes: ArrayList<Any>? = ArrayList()
//    val declaredField = getDeclaredField(baseClazz, fieldName)
//    declaredField?.isAccessible = true
//    if (declaredField?.type?.isArray!!) {
//        arrayRes =
//            parseArrayField(Main.client!!, declaredField, level = 0, displayData = displayData, recursive = recursive)
//        for (field in arrayRes!!) {
//            println(field)
//        }
//    } else {
//        val res = getFieldResult(
//            clientClazz,
//            Main.client!!,
//            declaredField,
//            level = 0,
//            recursive = recursive,
//            displayData = displayData
//        )
////        println(res)
//        res?.let { arrayRes?.add(it) }
//    }
//    return arrayRes
//}
//TODO - Needs to be updated to the newclass type
//fun getWidgetData(widgetIndex: WidgetIndex): Widget {
//    val baseClazz = Client::class.java
//    val fieldName = "widgets"
//
//
//    val widgetObj = getFieldData(baseClazz, fieldName)
//
//    val localWidgetData = mutableMapOf<String, RSClasses.Field>()
//    //Loop over the arrays to find the widget we care about
//    if (widgetObj is Array<*>) {
//        widgetObj.forEachIndexed { parentIndex, childArray ->
//            if (childArray is Array<*>) {
//                childArray.forEachIndexed { childIndex, childItem ->
//                    if (widgetIndex.childID != ""
//                        && widgetIndex.parentID != ""
//                        && widgetIndex.childID != "Widgets"
//                        && widgetIndex.parentID != "Widgets"
//                        && parentIndex == widgetIndex.parentID.replace("Parent ", "").toInt()
//                        && childIndex == widgetIndex.childID.toInt()
//                    ) {
//                        val v = childItem!!::class.java
//                        val currentWidgetFieldHookData = dream?.analyzers?.get(
//                            Widget::class.java.simpleName
//                        )?.fields
//                        for (field in v.declaredFields) {
//                            if (currentWidgetFieldHookData != null && currentWidgetFieldHookData.count { it.name == field.name }>0) {
////                                println(field.name + " ->" + currentWidgetFieldHookData[field.name])
//                                // Get data
//                                val res = getFieldResult(
//                                    v,
//                                    childItem,
//                                    field,
//                                    0
//                                )
//                                if (res != null) {
//                                    currentWidgetFieldHookData[field.name] = res
//                                }
//                                localWidgetData[currentWidgetFieldHookData[field.name]!!.fieldName] = RSClasses.Field()
//                                localWidgetData[currentWidgetFieldHookData[field.name]?.fieldName]?.resultValue =
//                                    currentWidgetFieldHookData[field.name]?.resultValue.toString()
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//    return Widget(localWidgetData)
//}
//TODO - Needs to be updated to the newclass type
//private fun getFieldData(
//    baseClazz: Class<*>,
//    fieldName: String
//): Any? {
//    val declaredField = getDeclaredField(baseClazz, fieldName)
//    declaredField?.isAccessible = true
//    return declaredField?.get(null)
//}
//TODO - Needs to be updated to the newclass type
//private fun getDeclaredField(baseClazz: Class<*>, fieldName: String): Field? {
//    val fieldTypeName = dream?.analyzers?.get(
//        baseClazz.simpleName
//    )?.normalizedFields?.get(fieldName)?.fieldTypeObsName
//    val obsFieldName = dream?.analyzers?.get(
//        baseClazz.simpleName
//    )?.normalizedFields?.get(fieldName)?.obsName
//
//    val clazz = Main.classLoader?.loadClass(fieldTypeName)
//    //println("CLazz: " + clazz?.name)
////    for (field in clazz?.declaredFields!!) {
////        //println("\t" + field.type.toString() + " " + field.name)
////    }
//    val declaredField = clazz?.getDeclaredField(obsFieldName)
//    return declaredField
//}
//TODO - Needs to be updated to the newclass type
//fun printClazzFields(
//    clazz: Class<out Any>,
//    classObject: Any,
//    level: Int,
//    recursive: Boolean = false,
//    displayData: Boolean = false
//): RSClasses.Field {
//    val mainField = RSClasses.Field()
//    val fieldList = dream?.classRefObs?.get(clazz.simpleName)?.fields
//    val indent = "\t".repeat(level)
//    if (displayData) {
//        val strippedType = clazz.simpleName.removeSuffix("[]")
//        val className = dream?.classRefObs?.get(
//            strippedType
//        )?.className
//        println("$indent Class - ${clazz.simpleName} $className")
//        println("$indent$$$$$$$$$ DeclaredFields$$$$$$$$$$")
//    }
//    for (reflectField in clazz.declaredFields) {
//
//        if (fieldList != null && fieldList.contains(reflectField.name)) {
//            mainField.fields[fieldList[reflectField.name]?.fieldName!!] = RSClasses.Field()
//            reflectField.isAccessible = true
//            if (displayData)
//                print(indent + reflectField.type.superclass + " " + reflectField.type.simpleName + " " + fieldList[reflectField.name]?.fieldName)
//            val nextLevel = level + 1
//            if (!reflectField.type.isArray) {
//
//                val fieldRes = getFieldResult(clazz, classObject, reflectField, nextLevel, recursive = recursive)
//                if (fieldRes != null)
//                    mainField.fields[fieldList[reflectField.name]?.fieldName!!] = fieldRes
//            } else {
//                mainField.fields[fieldList[reflectField.name]?.fieldName!!]?.isArray = true
//                mainField.fields[fieldList[reflectField.name]?.fieldName!!]?.arrayData =
//                    parseArrayField(
//                        classObject,
//                        reflectField,
//                        nextLevel,
//                        displayData = displayData,
//                        recursive = recursive
//                    )
//            }
//            if (displayData)
//                println(indent + mainField.fields[fieldList[reflectField.name]?.fieldName!!])
//
//
//        }
//    }
//    return mainField
//}


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

fun <T : Any> getFields(t: T): List<Field> {
    val fields = ArrayList<Field>()
    var clazz: Class<*> = t.javaClass
    print("Class structure: ")
    while (clazz != Any::class.java) {
        print("->${clazz.name}")
        fields.addAll(Arrays.asList(*clazz.declaredFields))
        clazz = clazz.superclass
    }
    println("")
    return fields
}

//TODO - Needs to be updated to the newclass type
//fun parseArrayField(
//    classObject: Any,
//    reflectField: Field,
//    level: Int,
//    displayData: Boolean = false,
//    recursive: Boolean = false
//): ArrayList<Any>? {
//    val arrayRes = ArrayList<Any>()
//    if (reflectField.type.isArray) {
//        val indent = "\t".repeat(level)
//
//        reflectField.isAccessible = true
//        val value = reflectField.get(classObject) ?: return null
//        val arrayLength = java.lang.reflect.Array.getLength(value)
//        val arrayFields = reflectField.get(classObject)
//        var resultList = "["
//        if (displayData) {
//            println("$indent\tComponent type: " + reflectField.type.componentType.simpleName + " Array size $arrayLength")
//        }
//        val superNestedArray = RSClasses.Field("")
//        val _4dBoolArray = ArrayList<ArrayList<ArrayList<ArrayList<Boolean>>>>()
//
//        if (arrayLength > 0) {
//            for (i in 0 until arrayLength) {
//
//                if (reflectField.type.componentType.simpleName == "int") {
//                    val res = (java.lang.reflect.Array.get(arrayFields, i) as Int).toString()
//                    resultList += "$res, "
//                    arrayRes.add(RSClasses.Field(res))
//                } else if (reflectField.type.componentType.simpleName == "String") {
//                    val item = java.lang.reflect.Array.get(arrayFields, i)
//                    if (item != null) {
//                        resultList += "$item, "
//                        arrayRes.add(RSClasses.Field(item.toString()))
//                    }
//
//                } else if (reflectField.type.componentType.simpleName == "boolean") {
//                    val res = (java.lang.reflect.Array.get(arrayFields, i) as Boolean).toString()
//                    resultList += "$res, "
//                    arrayRes.add(RSClasses.Field(res))
//                } else if (!reflectField.type.componentType.isArray) {
//
//                    val field = java.lang.reflect.Array.get(arrayFields, i)
//
//                    if (field != null) {
//                        if (displayData) {
//                            println("$indent\t Diving deeper into " + reflectField.type.componentType.simpleName)
//                        }
//
//                        val fieldResults = mutableMapOf<String, RSClasses.Field?>()
//                        // Get all the super class declared fields values
//                        getSuperDeclaredFields(field, level, fieldResults)
//                        // Get the current class declared field values
//                        for (f in field.javaClass.declaredFields) {
//                            val rsField = getFieldResult(
//                                field.javaClass,
//                                field,
//                                f,
//                                level,
//                                recursive = recursive,
//                                displayData = displayData
//                            )
//                            if (rsField?.fields != null) {
//                                fieldResults[rsField.fieldName] = rsField.copy()
//                                fieldResults.putAll(rsField.fields)
//                            }
//                        }
//                        // Add 1 RS field that includes all the files from fieldResults
//                        val result = RSClasses.Field("")
//                        result.fields.putAll(fieldResults)
//                        val result2 = result.copy()
//                        for (f in result.fields) {
//                            result2.fields[f.key] = f.value?.copy()
//                        }
//                        arrayRes.add(result2)
//
//                    } else {
//                        //Skipping null objects
//                        //resultList += "null,"
//                    }
//                } else {// This an array with in an array
//                    val parentArrayItem = java.lang.reflect.Array.get(arrayFields, i)
//                    val parentArray = ArrayList<Any>()
//                    superNestedArray.arrayData?.add(parentArray)
//
//                    //array within array
//                    if (parentArrayItem is Array<*>) {
//                        parentArrayItem.forEachIndexed { parentIndex, childItem ->
//                            if (childItem is Array<*>) {//3D+ array
//                                val nestedChildArray = ArrayList<Any>()
//                                parentArray.add(nestedChildArray)
//
//                                childItem.forEachIndexed { child_1_index, child_2_item ->
//                                    if (child_2_item != null && child_2_item.javaClass.isArray && child_2_item is BooleanArray) { //4D+ Array
//                                        val nestedChildArray_2 = ArrayList<Any>()
////                                        print(child_2_item.)
//                                        nestedChildArray.add(nestedChildArray_2)
//
//                                        (child_2_item).forEachIndexed { child_2_index, child_3_item ->
//                                            //4D array
//                                            nestedChildArray_2.add(child_2_index, child_3_item)
////                                            print("[$i,$parentIndex,$child_1_index,$child_2_index] $child_3_item ")
//                                        }
//                                    } else {//3D Array
//                                        if (child_2_item != null) {
//                                            val v = child_2_item::class.java
//                                            val hookData = dream?.analyzers?.get(
//                                                Tile::class.java.simpleName
//                                            )?.fields
//                                            for (field in v.declaredFields) {
//                                                if (hookData != null && hookData.contains(field.name)) {
//                                                    // Get data
//                                                    val res = getFieldResult(
//                                                        v,
//                                                        child_2_item,
//                                                        field,
//                                                        0,
//                                                        displayData = displayData,
//                                                        recursive = recursive
//                                                    )
//                                                    if (res != null) {
//                                                        hookData[field.name] = res
//                                                    }
//                                                }
//                                            }
//                                            //TODO - Check to see if its a tile
//                                            // TODO - If tile, creat Tile object and add to 3d array
////                                        print("[$i,$parentIndex,$child_1_index] $child_2_item ")
//
//                                            parentArray.add(parentIndex, nestedChildArray.add(child_1_index, true))
//                                        }
//                                    }
//                                }
//                            } else {//2D array
//                                print("[$i,$parentIndex]$childItem ")
//                                parentArray.add(parentIndex, true)
//                            }
//                        }
//                    }
////                    parseArrayField(classObject, field.f, displayData = displayData, recursive = recursive)
//                }
//
//            }
//            if (resultList.isNotEmpty()) {
//                if (displayData) {
//                    println("$indent\t$resultList]")
//                }
//            }
//        }
//    }
//    return arrayRes
//}
//TODO - Needs to be updated to the newclass type
//private fun getSuperDeclaredFields(
//    field: Any,
//    level: Int,
//    fieldResults: MutableMap<String, RSClasses.Field?>
//) {
//    var clazz: Class<*> = field.javaClass
//    while (clazz != Any::class.java) {
//        for (f in clazz.declaredFields) {
//            val rsField = getFieldResult(
//                clazz,
//                field,
//                f,
//                level,
//                recursive = false,
//                displayData = false
//            )
//            if (rsField?.fields != null) {
//                fieldResults[rsField.fieldName] = rsField
//            }
//        }
////                            fields.addAll(Arrays.asList(*clazz.declaredFields))
//        clazz = clazz.superclass
//    }
//}
//TODO - Needs to be updated to the newclass type
//fun getFieldResult(
//    clazz: Class<out Any>,
//    classObject: Any,
//    reflectField: Field,
//    level: Int,
//    recursive: Boolean = false,
//    displayData: Boolean = false
//): RSClasses.Field? {
//    val fieldList = dream?.classRefObs?.get(clazz.simpleName)?.fields
//    val indent = "\t".repeat(level)
//    val rsField = fieldList?.get(reflectField.name)
//
//    reflectField.isAccessible = true
//    if (reflectField.type.isPrimitive) {
//        reflectField.type
//        if (displayData)
//            println(
//                "$indent ${reflectField.name}[${fieldList?.get(reflectField.name)?.fieldName}] res: ${reflectField.get(
//                    classObject
//                )}"
//            )
//    } else {
//        if (displayData) {
//            val strippedType = reflectField.type.simpleName.removeSuffix("[]")
//            val className = dream?.classRefObs?.get(
//                strippedType
//            )?.className
//            println(
//                "$indent ($level) Non prim - (${reflectField.type}-$className)${reflectField.name}[${fieldList?.get(
//                    reflectField.name
//                )?.fieldName}]  res: ${reflectField.get(
//                    classObject
//                )}"
//            )
//        }
//    }
//
//    if (reflectField.type.simpleName == "int") {
//        var compute = reflectField.getInt(classObject)
//        if (displayData)
//            print("$indent BeforeMOd: $compute")
//        if (fieldList?.get(reflectField.name)?.modifier != null) {
//            if (fieldList[reflectField.name]?.modifier?.toInt()!! != 0)
//                compute *= fieldList[reflectField.name]?.modifier?.toInt()!!
//            rsField?.resultValue = compute.toString()
//
//        }
//        if (displayData)
//            println(" After -> $compute")
//    } else if (reflectField.type.simpleName == "boolean") {
//        rsField?.resultValue = reflectField.getBoolean(classObject).toString()
//        if (displayData)
//            println(indent + reflectField.getBoolean(classObject).toString())
//    } else if (reflectField.type.simpleName == "string" || reflectField.type.simpleName == "String") {
//        val obj = reflectField.get(classObject)
//        if (obj != null) {
//            rsField?.resultValue = obj.toString()
//            if (displayData)
//                println(indent + obj.toString())
//        }
//
//    } else if (reflectField.type.simpleName == "long") {
//        var compute = reflectField.getLong(classObject)
//        if (displayData)
//            print("$indent BeforeMOd: $compute")
//        if (fieldList?.get(reflectField.name)?.modifier != null) {
//            compute *= fieldList[reflectField.name]?.modifier!!
//            rsField?.resultValue = compute.toString()
//        }
//        if (displayData)
//            println(" After -> $compute")
//    } else {
//        if (recursive) {
//            //If its a type we dont have, skip it
//            if (reflectField.type.isArray) {
//                rsField?.isArray = true
//                rsField?.arrayData = parseArrayField(classObject, reflectField, level)
//            } else if (!Main.dream?.classRefObs?.contains(reflectField.type.simpleName)!!) {
//                if (displayData)
//                    println("$indent didnt find ${reflectField.type.simpleName}")
//
//                return rsField
//            }
//            //Getting super class declared fields
//            if (level == 0) {
//                if (rsField?.fields != null) {
//                    getSuperDeclaredFields(reflectField, level, rsField.fields)
//                }
//            }
//            val nextClassObject = reflectField.get(classObject)
//            if (reflectField.type.simpleName != null && nextClassObject != null) {
//                reflectField.isAccessible = true
//                if (displayData) {
//                    val strippedType = reflectField.type.simpleName.removeSuffix("[]")
//                    println(
//                        "$indent ($level) Different Class type " + reflectField.type.simpleName + " " + dream?.classRefObs?.get(
//                            strippedType
//                        )?.className
//                    )
//                }
//                val superClazz = reflectField.type
//                // Dont dive into Node Classes
//                val list = listOf(
//                    dream?.analyzers?.get(Node::class.java.simpleName)?.obsName,
//                    dream?.analyzers?.get(LinkedList::class.java.simpleName)?.obsName,
//                    dream?.analyzers?.get(HashTable::class.java.simpleName)?.obsName
//                )
//
//                if (!list.contains(superClazz.simpleName)) {
//                    val nextLevel = level + 1
//                    return printClazzFields(
//                        superClazz,
//                        nextClassObject,
//                        nextLevel,
//                        displayData = displayData,
//                        recursive = recursive
//                    )
//                }
//            }
//        }
//
//    }
//    return rsField
//}