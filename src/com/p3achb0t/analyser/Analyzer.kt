package com.p3achb0t.analyser

import com.p3achb0t.Main.Data.dream
import com.p3achb0t.rsclasses.*
import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.Opcodes.*
import jdk.internal.org.objectweb.asm.tree.*
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream


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
                classReader.accept(classNode, 0)
                if (classNode.interfaces.size > 0) {

//                    println("${classNode.name} Interfaces")
                }
                for (_interface in classNode.interfaces) {
//                    println("\t $_interface")
                }
                classes[classNode.name] = classNode


                //Checking for SceneRespondRequest
            }
        }

        //TODO add interface to client
        classes["client"]?.interfaces?.add("com/p3achb0t/hook_interfaces/Client")
        classes["client"]?.methods?.listIterator()?.forEach { method ->
            println(method.name + " " + method.desc)
//            for(inst in method.instructions)
//                println("\t" + inst.opcode + " " + inst.type)
        }
        val fieldName = dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.get("gameState")?.obsName
        val signature = classes["client"]?.fields?.find { it.name == fieldName }?.signature
        val getGameStateMethod = MethodNode(ACC_PUBLIC, "get_gameState", "()I", signature, null)


        val classNodeName =
            dream?.analyzers?.get(Client::class.java.simpleName)?.fields?.get("gameState")?.fieldTypeObsName

        val fieldDescriptor = "I"
        val isStatic = classes["client"]?.fields?.find { it.name == fieldName }?.access?.and(ACC_STATIC) != 0
        val fieldType = if (isStatic) GETSTATIC else GETFIELD

        println("$classNodeName $fieldName $fieldDescriptor $fieldType $signature")
        getGameStateMethod.instructions.insert(InsnNode(IRETURN))
        getGameStateMethod.instructions.insert(FieldInsnNode(fieldType, classNodeName, fieldName, fieldDescriptor))

        getGameStateMethod.instructions.insert(VarInsnNode(ALOAD, 0))
        getGameStateMethod.visitMaxs(3, 3)
        getGameStateMethod.visitEnd()


        classes["client"]?.methods?.add(getGameStateMethod)
//        getGameStateMethod.
        val out = JarOutputStream(FileOutputStream(File("./injected_jar.jar")))
        for (classNode in classes.values) {
            val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)
            println(classNode.name)
            classNode.accept(cw)
            out.putNextEntry(JarEntry(classNode.name + ".class"))
            out.write(cw.toByteArray())
        }
        out.flush()
        out.close()



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

        for (analyzerObj in analyzers) {
            for (obClass in classes) {
            val classNode = obClass.value
                val analyzer = analyzerObj.value
                if(!analyzer.found){
                    analyzer.analyze(classNode, analyzers)
                }
            }
        }

    }
}