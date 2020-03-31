package com.p3achb0t.client.scripts.loading

import com.p3achb0t.client.configs.Constants
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.util.jar.JarFile


class LoadScripts {

    val scripts = mutableMapOf<String, ScriptInformation>()
    val loadedFolders = mutableSetOf<String>()

    fun refresh() {
        scripts.clear()
        for (x in loadedFolders) {
            loadJars(x)
        }
    }

    fun loadPath(path: String) {
        loadedFolders.add(path)
        loadJars(path)
    }

    fun removePath(path: String) {
        loadedFolders.remove(path)
        refresh()
    }


    // TODO big ass ugly function
    private fun loadJars(path: String) {
        // Load classes
        findInternalScripts(path)
        // Load Jars
        val files = File(path).listFiles()
        if (files != null) {

            for (file in files) {

                if (file.isFile && file.name.contains(".jar")) {
                    val jar = JarFile(file)
                    println(file.name)
                    val enumeration = jar.entries()
                    while(enumeration.hasMoreElements()) {
                        val entry = enumeration.nextElement()
                        if(entry.name.endsWith(".class")) {
                            val classReader = ClassReader(jar.getInputStream(entry))
                            val classNode = ClassNode()
                            classReader.accept(classNode, 0)
                            addJarScriptToScripts(file, classNode)
                        }
                    }
                }
            }
        } else {
            println("No jars to load")
        }
    }

    private fun addJarScriptToScripts(file: File, classNode: ClassNode) {

        if (classNode.visibleAnnotations == null)
            return

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
                val information = ScriptInformation(file.name, file.path, "${x.values[1]}", "${x.values[3]}", "${x.values[5]}", "${x.values[7]}", type, classNode.name)

                println("[+] added ${file.name}, ${file.path}, ${classNode.name}")

                scripts[file.name] = information
            }
        }
    }

    private fun findInternalScripts(packageName: String) {
        val classLoader = this.javaClass.classLoader
        val resources = classLoader.getResources(packageName)

        resources.asIterator().forEach {
            val file = File(it.file)
            loopOverInternalScriptClasses(file)
        }
    }

    private fun loopOverInternalScriptClasses(file: File) {

        for (x in file.listFiles()) {
            if (x.isDirectory) {
                loopOverInternalScriptClasses(x)
            } else {
                if (x.isFile && x.name.endsWith(".class")) {
                    val classReader = ClassReader(x!!.inputStream())
                    val classNode = ClassNode()
                    classReader.accept(classNode, 0)
                    addJarScriptToScripts(x, classNode)
                }
            }
        }
    }
}

// for tests
fun main() {
    val debug = LoadScripts()
    debug.loadPath("${Constants.USER_DIR}/${Constants.APPLICATION_CACHE_DIR}/${Constants.SCRIPTS_DIR}")
    debug.loadPath("com/p3achb0t/scripts")
    debug.loadPath("com/p3achb0t/scripts_private")
    for (x in debug.scripts.keys) {
        println(x)
    }
}