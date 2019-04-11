package com.p3achb0t.downloader

import com.p3achb0t.analyser.DreamBotAnalyzer
import com.p3achb0t.downloader.Main.Data.client
import com.p3achb0t.downloader.Main.Data.dream
import com.p3achb0t.rsclasses.Client
import com.p3achb0t.rsclasses.HashTable
import com.p3achb0t.rsclasses.LinkedList
import com.p3achb0t.rsclasses.Node
import java.applet.Applet
import java.applet.AppletContext
import java.applet.AppletStub
import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.File
import java.lang.reflect.Field
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile
import javax.swing.JFrame
import javax.swing.WindowConstants


class RSLoader:AppletStub {
    var params = Parameters.data.PARAMETER_MAP
    override fun getCodeBase(): URL {
        return URL(params["codebase"])
    }

    override fun getParameter(name: String?): String {
        println("Getting $name : ${params[name]}")
        return params[name]!!
    }

    override fun getAppletContext(): AppletContext = null!!

    override fun appletResize(width: Int, height: Int) {
    }

    override fun getDocumentBase(): URL {
        return URL(params["codebase"])
    }

    override fun isActive(): Boolean {
        return true
    }
}

class CustomApplet : Applet()


fun loopOverCanvasFields(g: Graphics) {
    val x = 50
    var y = 10
    val step = 12

    val clazz = Main.client!!::class.java

    //TODO - PRint out
//    Client.widgetBoundsX client oz
//    Client.widgetBoundsY client ov
//    Client.widgetHeights client oe
//    Client.widgetModelCache iq i
//    Client.widgetNodes client mo
//    Client.widgetWidths client ob
//    Client.widgets gx a
    val widgetBoundXField = clazz.getDeclaredField(
        dream?.analyzers?.get(
            Client::class.java.simpleName
        )?.normalizedFields?.get(
            "widgetBoundsX"
        )?.obsName
    )
    println("widgetBoundsX")
    parseArrayField(clazz, widgetBoundXField, 0)
    val widgetBoundYField = clazz.getDeclaredField(
        dream?.analyzers?.get(
            Client::class.java.simpleName
        )?.normalizedFields?.get(
            "widgetBoundsY"
        )?.obsName
    )
    println("widgetBoundsY")
    parseArrayField(clazz, widgetBoundYField, 0)

    val widgetHeigthsField = clazz.getDeclaredField(
        dream?.analyzers?.get(
            Client::class.java.simpleName
        )?.normalizedFields?.get(
            "widgetHeights"
        )?.obsName
    )
    println("widgetHeights")
    parseArrayField(clazz, widgetHeigthsField, 0)

    val widgetWidthsField = clazz.getDeclaredField(
        dream?.analyzers?.get(
            Client::class.java.simpleName
        )?.normalizedFields?.get(
            "widgetWidths"
        )?.obsName
    )
    println("widgetWidths")
    parseArrayField(clazz, widgetWidthsField, 0)

    val widgetNodesField = clazz.getDeclaredField(
        dream?.analyzers?.get(
            Client::class.java.simpleName
        )?.normalizedFields?.get(
            "widgetNodes"
        )?.obsName
    )
    val sizeOfWidgetNodesField = widgetNodesField.type
    printClazzFields(widgetNodesField.type, 0)
//    parseArrayField(widgetNodesField.type.javaClass, widgetNodesField,0)

//    val widgetModelCacheField = clazz.getDeclaredField(dream?.analyzers?.get(
//        Client::class.java.simpleName)?.normalizedFields?.get(
//        "widgetModelCache")?.obsName)
//    parseArrayField(clazz, widgetModelCacheField,0)
//    getFieldResult(clazz,widgetBoundXField)

//    printClazzFields(clazz, 1)

//    dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.iterator()?.withIndex()
//        ?.forEach { (_, field) ->
//            try {
//                if (field.value.fieldTypeObsName.isNotEmpty()) {
////                println(field.value.fieldTypeObsName + " -> " + field.value.fieldName + " " + field.value.modifier)
//                    val declaredField = client.getDeclaredField(field.value.obsName)
//                    declaredField.isAccessible = true
//                    var res: Any? = null
//                    if (field.value.modifier != 0L) {
//                        res = declaredField.getInt(Main.client) * field.value.modifier.toInt()
//                    } else {
////                    res = declaredField.get(Main.client)
//                    }
//                    g.color = Color.GREEN
//                    g.drawString(field.value.fieldName + " " + res, x, y)
//                    y += step
//                }
//            } catch (e: Exception) {
//
//            }
//        }
}

private fun printClazzFields(clazz: Class<out Any>, level: Int) {
    val fieldList = dream?.classRefObs?.get(clazz.simpleName)?.fields
    val indent = "\t".repeat(level)
    println("$indent$$$$$$$$$ DeclaredFields$$$$$$$$$$")
    for (reflectField in clazz.declaredFields) {
        if (fieldList?.contains(reflectField.name)!!) {
            reflectField.isAccessible = true
            print(indent + reflectField.type.simpleName + " " + fieldList[reflectField.name]?.fieldName)
            var result: Any? = null
            if (!reflectField.type.isArray) {
                result = getFieldResult(client!!::class.java, reflectField, level)
            }
            println("\t" + result)
            parseArrayField(clazz, reflectField, level)

        }
    }
    println("$indent--------Fields--------")
    for (reflectField in clazz.fields) {
        if (fieldList?.contains(reflectField.name)!!) {
            reflectField.isAccessible = true
            val result: Any? = getFieldResult(clazz, reflectField, level)

            println(indent + reflectField.type.name + " " + fieldList[reflectField.name]?.fieldName + " " + result)
            parseArrayField(clazz, reflectField, level)

        }
    }
    println("$indent@@@@@@@@@@@@@@")
}

private fun parseArrayField(clazz: Class<out Any>, reflectField: Field, level: Int) {
    if (reflectField.type.isArray) {
        val indent = "\t".repeat(level)
        println("$indent\tComponent type: " + reflectField.type.componentType.simpleName)
        reflectField.isAccessible = true
        val arrayLength = java.lang.reflect.Array.getLength(reflectField.get(clazz))
        val arrayFields = reflectField.get(clazz)
        var resultList = "["
        if (arrayLength > 0) {
            for (i in 0..(arrayLength - 1)) {
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
                }

            }
            if (resultList.isNotEmpty()) {
                println("$indent\t$resultList]")
            }
        }
    }
}

private fun getFieldResult(
    clazz: Class<out Any>,
    reflectField: Field
): Any? {
    val fieldList = dream?.classRefObs?.get(clazz.simpleName)?.fields
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

private fun getFieldResult(
    clazz: Class<out Any>,
    reflectField: Field,
    level: Int
): Any? {
    val fieldList = dream?.classRefObs?.get(clazz.simpleName)?.fields
    var result: Any? = null
    val indent = "\t".repeat(level)
    reflectField.isAccessible = true
    if (reflectField.type.simpleName == "int") {
        result = reflectField.getInt(clazz)
        if (fieldList?.get(reflectField.name)?.modifier != 0L) {
            result *= fieldList?.get(reflectField.name)?.modifier?.toInt()!!
        }
    } else if (reflectField.type.simpleName == " boolean") {
        result = reflectField.getBoolean(clazz)
    } else if (reflectField.type.simpleName == " string") {
        result = reflectField.get(clazz).toString()
    } else {
        if (reflectField.type.simpleName != null) {
            reflectField.isAccessible = true
            println("$indent Different Class type " + reflectField.type.simpleName)
            val superClazz = reflectField.type
            // Dont dive into Node Classes
            val list = listOf(
                dream?.analyzers?.get(Node::class.java.simpleName)?.obsName,
                dream?.analyzers?.get(LinkedList::class.java.simpleName)?.obsName,
                dream?.analyzers?.get(HashTable::class.java.simpleName)?.obsName
            )
            if (!list.contains(superClazz.simpleName))
                printClazzFields(clazz, level + 1)
        }

    }
    return result
}

class CustomCanvas(var oldCanvasHash: Int) : Canvas() {

    var counter = 0

    val image = BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB)

    override fun getGraphics(): Graphics {
        val g = image.graphics
        g.color = Color.GREEN
//        g.drawString("RS-HAcking $counter", 50,50)
        if (counter % (30 * 50) == 0) {
            loopOverCanvasFields(g)
        }
        super.getGraphics().drawImage(image, 0, 0, null)
        counter += 1

        return g
    }

    override fun hashCode(): Int {
        return oldCanvasHash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CustomCanvas

        if (oldCanvasHash != other.oldCanvasHash) return false
        if (counter != other.counter) return false
        if (image != other.image) return false

        return true
    }

}

class Main(game: Applet) {
    init {
        client = game
    }

    companion object Data {
        var client: Applet? = null
        var dream: DreamBotAnalyzer? = null
        var classLoader: ClassLoader? = null
    }
}

fun main(args: Array<String>){

    val downloader = Downloader()
////    val gamePackWithPath = downloader.getGamepack()
    val gamePackWithPath = downloader.getLocalGamepack()
//    val analyser = Analyser()
    val gamePackJar = JarFile(gamePackWithPath)
//    analyser.parseJar(gamePackJar)
    val dream = DreamBotAnalyzer()

    dream.getDreamBotHooks()
    dream.parseHooks()
    dream.parseJar(gamePackJar)


    // Getting parameters
    Parameters(1)
    println("Starting Client")
    val file = File(gamePackWithPath)
    val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
    Main.classLoader = URLClassLoader(urlArray)
    val game: Applet = Main.classLoader?.loadClass("client")?.newInstance() as Applet
    Main(game)
    Main.dream = dream

    val client = game::class.java


    game.apply {
        preferredSize = Dimension(765, 503)
        val loader = RSLoader()
        game.setStub(loader)
    }
    val jFrame = JFrame()
    jFrame.title = "Runescape"

    jFrame.apply {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        add(game)
        pack()
        preferredSize = size
        minimumSize = game.minimumSize
        setLocationRelativeTo(null)
        isVisible = true
    }

    game.apply {
        init()
        start()

        println(client.name)
        val currentWorldObs = dream.analyzers[Client::class.java.simpleName]?.fields?.get("currentWorld")?.obsName
        val modifer = dream.analyzers[Client::class.java.simpleName]?.fields?.get("currentWorld")?.modifier?.toInt()
        println(modifer)

        dream.classRefObs["client"]?.fields?.get("currentWorld")?.obsName
        val worldField = Main.client!!::class.java.getDeclaredField(currentWorldObs)
        worldField.isAccessible = true

        val worldNum = worldField.getInt(game) * modifer!!
        println("currentWorld :$worldNum")

    }

    game.apply {

        val canvasType = dream.analyzers[Client::class.java.simpleName]?.fields?.get("canvas")?.fieldTypeObsName
        val canvasFieldName = dream.analyzers[Client::class.java.simpleName]?.fields?.get("canvas")?.obsName

        println("canvas " + canvasType + " Field name : $canvasFieldName")
        var loaded = false
        //Wait till canvas is not null so we can replace it
        while (!loaded) {
            try {

                val canvasClazz = Main.classLoader?.loadClass(canvasType)
                val canvasField = canvasClazz?.getDeclaredField(canvasFieldName)
                canvasField?.isAccessible = true
                val oldCanvas = canvasField?.get(game) as Canvas // Needed to have Applet instead of null
                game.remove(oldCanvas)
                val customCanvas = CustomCanvas(oldCanvas.hashCode())
                for (ml in oldCanvas.mouseListeners) {
                    customCanvas.addMouseListener(ml)
                }
                for (ml in oldCanvas.mouseMotionListeners) {
                    customCanvas.addMouseMotionListener(ml)
                }
                for (kl in oldCanvas.keyListeners) {
                    customCanvas.addKeyListener(kl)
                }
                for (fl in oldCanvas.focusListeners) {
                    customCanvas.addFocusListener(fl)
                }
                canvasField.set(game, customCanvas) // Needed to have Applet instead of null
                game.add(customCanvas)
                loaded = true
                break

            } catch (e: Exception) {
                println("Error hooking canvas $e ")
                for (item in e.stackTrace) {
                    println(item.toString())
                }
            }
        }
    }












    //Title: Old School RuneScape Game


}
