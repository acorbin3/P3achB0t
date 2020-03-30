package com.p3achb0t.client.scripts.loading

import com.p3achb0t.analyser.ScriptClasses
import com.p3achb0t.api.AbstractScript
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
        loadBuildInScripts()
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
                                    val information = ScriptInformation(file.name, "${x.values[1]}", "${x.values[3]}", "${x.values[5]}", "${x.values[7]}", type, classNode.name)
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

    private fun loadBuildInScripts(){
        println("About to load scripts")
        val privateScripts = ScriptClasses.findAllClasses("com/p3achb0t/scripts_private")
        privateScripts.forEach {
            var scriptName = ""
            var category = ""
            var author = ""
            it.annotations.iterator().forEach {
                var manifest = it.toString()

                if(it.toString().contains("ScriptManifest")){
                    manifest = manifest.replace("@com.p3achb0t.api.ScriptManifest(","")
                    manifest = manifest.replace(")","")
                    manifest = manifest.replace("\"","")
                    println("Looking at manifest: $manifest")
                    val splitManifest = manifest.split(",")
                    category = splitManifest[1].replace("category=", "")
                    scriptName = splitManifest[2].replace("name=", "").strip()
                    author = splitManifest[3].replace("author=", "")
                }
            }
            println("Loading $scriptName")
            scripts[scriptName] = ScriptInformation(name = scriptName, author = author,abstractScript = it.newInstance() as AbstractScript, type=ScriptType.AbstractScript)
        }
        val publicScripts = ScriptClasses.findAllClasses("com/p3achb0t/scripts")
        publicScripts.forEach {
            var scriptName = ""
            var category = ""
            var author = ""
            it.annotations.iterator().forEach {
                var manifest = it.toString()

                if(it.toString().contains("ScriptManifest")){
                    manifest = manifest.replace("@com.p3achb0t.api.ScriptManifest(","")
                    manifest = manifest.replace(")","")
                    manifest = manifest.replace("\"","")
                    println("Looking at manifest: $manifest")
                    val splitManifest = manifest.split(",")
                    category = splitManifest[1].replace("category=", "")
                    scriptName = splitManifest[2].replace("name=", "").strip()
                    author = splitManifest[3].replace("author=", "")
                }
            }
            println("Loading $scriptName")
            scripts[scriptName] = ScriptInformation(name = scriptName, author = author,abstractScript = it.newInstance() as AbstractScript, type=ScriptType.AbstractScript)
        }
    }


}

fun main() {
    val debug = LoadScripts()
}