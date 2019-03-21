package com.p3achb0t.analyser

import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.tree.ClassNode
import java.util.jar.JarFile

class Analyser{

    fun parseJar(jar: JarFile){
        val enumeration = jar.entries()
        var canvasNode:ClassNode? = null
        while(enumeration.hasMoreElements()){
            val entry = enumeration.nextElement()
            if(entry.name.endsWith(".class")){
                val classReader = ClassReader(jar.getInputStream(entry))
                val classNode = ClassNode()
                classReader.accept(classNode, ClassReader.SKIP_DEBUG)// Missing | ClassReader.SKIP_FRAMES
                println(classNode.name + " " + classNode.superName)
                if(classNode.superName == "java/awt/Canvas"){
                    canvasNode = classNode
                }
            }
        }
        canvasNode.let { print(it?.name + " " + it?.superName) }

    }
}