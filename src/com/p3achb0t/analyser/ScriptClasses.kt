package com.p3achb0t.analyser

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.util.*
import java.util.jar.JarFile

object ScriptClasses {
    val defaultScripts = arrayListOf("TemplateScript", "NullScript")

    fun findAllDebugClasses(packageName: String): ArrayList<Class<*>>{
        println("Looking at package: $packageName")
        return findAllClasses(packageName, "DebugScript")
    }

    fun findAllClasses(packageName: String, desiredSuperClass: String = "AbstractScript"): ArrayList<Class<*>>{
        println("Looking at package: $packageName")
        val classes: ArrayList<Class<*>> = ArrayList()
        val classLoader = Thread.currentThread().contextClassLoader
        val resources = classLoader.getResources(packageName)
        val dirs: ArrayList<File> = arrayListOf()
        resources.asIterator().forEach {
            println("\t" + it.file)
            var dir = if( File(it.file).exists()) {
                File(it.file)
            }else{

                //Open jar, loop over all classes to find abstract class.
                if(it.file.contains("!")){
                    val jarFilePath = JarFile(it.file.split("!")[0].replace("file:",""))
                    jarFilePath.entries().asIterator().forEach { jarEntry ->
                        if(jarEntry.name.endsWith(".class")){
                            val classReader = ClassReader(jarFilePath.getInputStream(jarEntry))
                            val classNode = ClassNode()
                            classReader.accept(classNode, 0)
                            if(classNode.superName != null
                                    && classNode.superName.contains(desiredSuperClass)
                                    && classNode.name.replace(".class", "")
                                            .split("/").last() !in defaultScripts) {

                                //We want to make sure we are looking at the right package. So we compare each director
                                // structure to make sure it matches given the diresred class path
                                val fullClassSplit = classNode.name.split("/")
                                val desiredClassSplit = packageName . split("/")
                                var goodPackage = true
                                println("fullClassSplit: $fullClassSplit")
                                println("desiredClassSplit: $desiredClassSplit")
                                if( fullClassSplit.size >= desiredClassSplit.size) {
                                    desiredClassSplit.forEachIndexed { index, s ->
                                        println("i: $index   s != fullClassSplit[index]   $s != ${fullClassSplit[index]}")
                                        if (goodPackage && s != fullClassSplit[index]) {
                                            goodPackage = false
                                        }
                                    }
                                }else {
                                    goodPackage = false
                                }

                                if(goodPackage) {
                                    classes.add(Class.forName(classNode.name.replace("/", "."),
                                            true,
                                            Thread.currentThread().contextClassLoader))
                                }
                            }
                        }
                    }
                }
                return classes
            }
            dirs.add(dir)
        }
        dirs.forEach { dir ->
            classes.addAll(findClasses(dir, packageName,desiredSuperClass))
        }
        return classes
    }
    private fun findClasses(directory: File, packageName: String, desiredSuperClass: String): ArrayList<Class<*>> {
        val classes: ArrayList<Class<*>> = ArrayList()
        if (!directory.exists()) {
            return classes
        }
        val files = directory.listFiles()
        for (file in files) {

            if (file.isDirectory) {
                assert(!file.name.contains("."))
                classes.addAll(findClasses(file, packageName + "/" + file.name, desiredSuperClass))
            } else if (file.name.endsWith(".class")) {
                val classReader = ClassReader(file.inputStream())
                val classNode = ClassNode()
                classReader.accept(classNode, 0)
                //Looking for all scripts that extend the abstract class and are not default scripts
                if(classNode.superName != null
                        && classNode.superName.contains(desiredSuperClass)
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