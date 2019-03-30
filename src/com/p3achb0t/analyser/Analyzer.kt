package com.p3achb0t.analyser

import com.p3achb0t.rsclasses.*
import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.tree.ClassNode
import java.util.jar.JarFile


class Analyser{

    fun parseJar(jar: JarFile){
        val enumeration = jar.entries()
        val classes = mutableMapOf<String,ClassNode>()
        val analyzers = mutableMapOf<String,RSClasses>()
        while(enumeration.hasMoreElements()){
            val entry = enumeration.nextElement()
            if(entry.name.endsWith(".class")){
                val classReader = ClassReader(jar.getInputStream(entry))
                val classNode = ClassNode()
                classReader.accept(classNode, ClassReader.SKIP_DEBUG)// Missing | ClassReader.SKIP_FRAMES

                classes[classNode.name] = classNode


                //Checking for SceneRespondRequest
            }
        }

        // Add analyzers
        analyzers[Node.deobName] = Node()
        analyzers[Client.deobName] = Client()
        analyzers[Canvas.deobName] = Canvas()
        analyzers[CacheNode.deobName] = CacheNode()
        analyzers[Renderable.deobName] = Renderable()
        analyzers[Actor.deobName] = Actor()
        analyzers[Player.deobName] = Player()
        analyzers[Npc.deobName] = Npc()
        analyzers[AnimatedObject.deobName] = AnimatedObject()


        for(obClass in classes){
            val classNode = obClass.value
            for(analyzerObj in analyzers){
                val analyzer = analyzerObj.value
                if(!analyzer.found){
                    analyzer.analyze(classNode, analyzers)
                }
            }
        }
        // Second pass to pick up classes that are in a hierarchy
        for(obClass in classes){
            val classNode = obClass.value
            for(analyzerObj in analyzers){
                val analyzer = analyzerObj.value
                if(!analyzer.found){
                    analyzer.analyze(classNode, analyzers)
                }
            }
        }

        for(obClass in classes){
            val classNode = obClass.value
            for(analyzerObj in analyzers){
                val analyzer = analyzerObj.value
                if(!analyzer.found){
                    analyzer.analyze(classNode, analyzers)
                }
            }
        }
        for(obClass in classes){
            val classNode = obClass.value
            for(analyzerObj in analyzers){
                val analyzer = analyzerObj.value
                if(!analyzer.found){
                    analyzer.analyze(classNode, analyzers)
                }
            }
        }
    }
}