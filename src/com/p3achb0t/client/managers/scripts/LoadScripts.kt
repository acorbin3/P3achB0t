package com.p3achb0t.client.managers.scripts

import com.p3achb0t.client.configs.Constants.Companion.APPLICATION_CACHE_DIR
import com.p3achb0t.client.configs.Constants.Companion.SCRIPTS_DIR
import com.p3achb0t.client.configs.Constants.Companion.USER_DIR
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.util.jar.JarFile


class LoadScripts {

    val scripts = mutableMapOf<String, ScriptInformation>()

    private val path = "$USER_DIR/$APPLICATION_CACHE_DIR/$SCRIPTS_DIR"

    init {
        loadAll()
    }

    fun refresh() {
        scripts.clear()
        loadAll()
    }

    // TODO big ass ugly function
    private fun loadAll() {
        val files = File(path).listFiles()
        if (files != null) {
            for (file in files) {
                val jar = JarFile(file)
                if (file.isFile && file.name.contains(".jar")) {
                    println(file.name)
                    val enumeration = jar.entries()
                    while(enumeration.hasMoreElements()) {
                        val entry = enumeration.nextElement()
                        if(entry.name.endsWith(".class")) {
                            val classReader = ClassReader(jar.getInputStream(entry))
                            val classNode = ClassNode()
                            classReader.accept(classNode, 0)

                            for (x in classNode.visibleAnnotations) {
                                if (x.desc.contains("ScriptManifest")) {

                                    println("${x.values[1]}, ${x.values[3]} ${x.values[5]} ${x.values[7]}")

                                    val type = when {
                                        classNode.superName.contains("DebugScript") -> {
                                            ScriptType.DebugScript
                                        }
                                        classNode.superName.contains("BackgroundScript") -> {
                                            ScriptType.BackgroundScript

                                        }
                                        classNode.superName.contains("AbstractScript") -> {
                                            ScriptType.AbstractScript
                                        }
                                        else -> ScriptType.None
                                    }
                                    val information = ScriptInformation(file.name,"${x.values[1]}", "${x.values[3]}","${x.values[5]}","${x.values[7]}", type, classNode.name)
                                    scripts[file.name] = information
                                }
                            }
                        }
                    }
                }
            }
        } else {
            println("No scripts to load")
        }
    }


}

fun main() {
    val debug = LoadScripts()
}