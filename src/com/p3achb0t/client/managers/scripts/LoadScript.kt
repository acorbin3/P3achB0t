package com.p3achb0t.client.managers.scripts

import com.p3achb0t.api.AbstractScript
import com.p3achb0t.client.configs.Constants.Companion.APPLICATION_CACHE_DIR
import com.p3achb0t.client.configs.Constants.Companion.SCRIPTS_ABSTRACT_DIR
import com.p3achb0t.client.configs.Constants.Companion.SCRIPTS_DIR
import com.p3achb0t.client.configs.Constants.Companion.USER_DIR
import com.p3achb0t.scripts.NullScript
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile


class LoadScripts {

    private val scripts = mutableMapOf<String, AbstractScript>()
    private val path = "$USER_DIR/$APPLICATION_CACHE_DIR/$SCRIPTS_DIR/$SCRIPTS_ABSTRACT_DIR"

    init {
        println(path)
        loadAll()
        println(scripts.size)
    }


    fun getScript(name: String) : AbstractScript {
        if(name !in scripts){
            println("ERROR: Didnt find $name. Be sure to use the name from the ScriptManafest")
            println("Possible names:")
            scripts.forEach { t, u ->
                println(t)
            }
        }
        return when {
            name in scripts -> {
                scripts[name]!!
            }
            "$name.jar" in scripts -> {
                scripts["$name.jar"]!!
            }
            else -> {
                NullScript()
            }
        }
    }

    fun addScript(name:String, abstractScript: AbstractScript){
        scripts[name] = abstractScript
    }

    fun load(fileName: String): AbstractScript? {

        val file = File("$path/$fileName")
        val main = findMain(JarFile(file))
        if (main == "") {
            return null
        }
        val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
        val classLoader = URLClassLoader(urlArray)
        return classLoader.loadClass(main).newInstance() as AbstractScript
    }

    fun refresh() {
        loadAll()
    }

    private fun loadAll() {
        val files = File(path).listFiles()
        //If this pathname does not denote a directory, then listFiles() returns null.

        if(files != null) {
            for (file in files) {
                if (file.isFile && file.name.contains(".jar")) {
                    println(file.name)
                    val dscript = load(file.name) ?: continue
                    scripts[file.name] = dscript

                }
            }
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
                if(classNode.superName.contains("AbstractScript")) {
                    return classNode.name
                }
            }
        }
        return ""
    }
}

fun main() {
    val debug = LoadScripts()

}