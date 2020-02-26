package com.p3achb0t.client.managers.scripts

import com.p3achb0t.api.DebugScript
import com.p3achb0t.client.configs.Constants.Companion.APPLICATION_CACHE_DIR
import com.p3achb0t.client.configs.Constants.Companion.SCRIPTS_DEBUG_DIR
import com.p3achb0t.client.configs.Constants.Companion.SCRIPTS_DIR
import com.p3achb0t.client.configs.Constants.Companion.USER_DIR
import com.p3achb0t.scripts.paint_debug.PaintDebug
import com.p3achb0t.scripts.WidgetExplorerDebug
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile


class LoadDebugScripts {

    private val debugScripts = mutableMapOf<String, DebugScript>()
    private val path = "$USER_DIR/$APPLICATION_CACHE_DIR/$SCRIPTS_DIR/$SCRIPTS_DEBUG_DIR"

    init {
        println(path)
        //Add the WidgetExplorer debug script first
        debugScripts[WidgetExplorerDebug.scriptName] = WidgetExplorerDebug()
        debugScripts[PaintDebug.scriptName] = PaintDebug()
        loadAll()
    }


    fun getScript(name: String) : DebugScript {
        return if("$name.jar" in debugScripts)
            debugScripts["$name.jar"]!!
        else
            debugScripts["$name"]!!
    }

    fun load(fileName: String): DebugScript? {

        val file = File("$path/$fileName")
        val main = findMain(JarFile(file))
        if (main == "") {
            return null
        }
        val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
        val classLoader = URLClassLoader(urlArray)
        return classLoader.loadClass(main).newInstance() as DebugScript
    }

    fun refresh() {
        loadAll()
    }

    private fun loadAll() {
        val files = File(path).listFiles()
        //If this pathname does not denote a directory, then listFiles() returns null.

        if (files != null) {
            for (file in files) {
                if (file.isFile && file.name.contains(".jar")) {
                    println(file.name)
                    val dscript = load(file.name) ?: continue
                    debugScripts[file.name] = dscript
                }
            }
        } else {
            println("No loaded scripts right now")
        }
    }

    private fun findMain(jar: JarFile) : String {
        val enumeration = jar.entries()
        while(enumeration.hasMoreElements()) {
            val entry = enumeration.nextElement()
            if(entry.name.endsWith(".class")) {
                val classReader = ClassReader(jar.getInputStream(entry))
                val classNode = ClassNode()
                classReader.accept(classNode, 0)
                if(classNode.superName.contains("DebugScript")) {
                    return classNode.name
                }
            }
        }
        return ""
    }
}

fun main() {
    val debug = LoadDebugScripts()

}