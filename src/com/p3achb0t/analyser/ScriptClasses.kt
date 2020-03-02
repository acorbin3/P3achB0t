package com.p3achb0t.analyser

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.util.*

object ScriptClasses {
    fun findAllClasses(packageName: String): ArrayList<Class<*>>{
        val classes: ArrayList<Class<*>> = ArrayList()
        val classLoader = Thread.currentThread().contextClassLoader
        val resources = classLoader.getResources(packageName)
        val dirs: ArrayList<File> = arrayListOf()
        resources.asIterator().forEach {
            dirs.add(File(it.file))
        }
        dirs.forEach { dir ->
            classes.addAll(findClasses(dir, packageName))
        }
        return classes
    }
    private fun findClasses(directory: File, packageName: String): ArrayList<Class<*>> {
        val classes: ArrayList<Class<*>> = ArrayList()
        if (!directory.exists()) {
            return classes
        }
        val files = directory.listFiles()
        for (file in files) {
            val defaultScripts = arrayListOf("TemplateScript", "NullScript")

            if (file.isDirectory) {
                assert(!file.name.contains("."))
                classes.addAll(findClasses(file, packageName + "/" + file.name))
            } else if (file.name.endsWith(".class")) {
                val classReader = ClassReader(file.inputStream())
                val classNode = ClassNode()
                classReader.accept(classNode, 0)
                //Looking for all scripts that extend the abstract class and are not default scripts
                if(classNode.superName != null
                        && classNode.superName.contains("AbstractScript")
                        && file.name.replace(".class", "") !in defaultScripts) {
//                    println("Adding: $packageName/${file.name}")
                    classes.add(Class.forName(packageName.replace("/", ".")
                            + '.' + file.name.substring(0, file.name.length - 6),true,Thread.currentThread().contextClassLoader))
                }
            }
        }
        return classes
    }
}