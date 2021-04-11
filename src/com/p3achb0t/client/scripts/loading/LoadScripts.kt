package com.p3achb0t.client.scripts.loading

import com.p3achb0t.client.configs.GlobalStructs
import com.p3achb0t.client.util.sha256
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.jar.JarFile

class LoadScripts {

    val scriptsInformation = mutableMapOf<String, ScriptInformation>()
    val loadedFolders = mutableSetOf<String>()

    //If this is empty, load all the scripts. If filled in, limt to only these scripts
//    val limitedScripts = arrayListOf<String>("Corp", "CorpMain","Zulrah","ZulrahMain","ZulrahMule","ZulrahGearer", "GEAlcher", "WalkToGEMain","WalkToGE", "CrumbleAlch")
    val limitedScripts = arrayListOf<String>()
    var validated = false

    fun refresh() {
        GlobalStructs.db.validationKey
        scriptsInformation.clear()
        for (x in loadedFolders) {
            loadJars(x)
        }
    }

    fun findLoadedScript(scriptNameOrFileName: String): ScriptInformation? {
        var script: ScriptInformation? = null
        scriptsInformation.forEach { t, scriptInformation ->
            if (t == scriptNameOrFileName || scriptNameOrFileName == scriptInformation.name) {
                script = scriptInformation
            }
        }
        return script
    }

    fun loadPath(path: String) {
        loadedFolders.add(path)
        loadJars(path)
    }

    fun removePath(path: String) {
        loadedFolders.remove(path)
        refresh()
    }

    private fun loadJars(path: String) {
        // Load classes
        findInternalScripts(path)
        // Load Jars
        loopJarFiles(path)
    }

    private fun loopJarFiles(path: String) {
        val files = File(path).listFiles() ?: return
        for (file in files) {

            if (file.isDirectory) {
                loopJarFiles(file.absolutePath)
            } else {
                if (file.isFile && file.name.contains(".jar")) {
                    val jar = JarFile(file)
                    //println(file.name)
                    val enumeration = jar.entries()
                    while (enumeration.hasMoreElements()) {
                        val entry = enumeration.nextElement()
                        if (entry.name.endsWith(".class")) {
                            val classReader = ClassReader(jar.getInputStream(entry))
                            val classNode = ClassNode()
                            classReader.accept(classNode, 0)
                            addJarScriptToScripts(file, classNode)
                        }
                    }
                }
            }
        }
    }

    private fun findInternalScripts(packageName: String) {
        val classLoader = this.javaClass.classLoader
        val resources = classLoader.getResources(packageName)
        val defaultScripts = arrayListOf("TemplateScript", "NullScript")
        println("Looking at path: $packageName")
        if (File(packageName).exists()) {
            File(packageName).listFiles()?.iterator()?.forEachRemaining {
                println(it)
            }
        } else {
            GlobalScope.launch {
                val current = LocalDateTime.now()

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formattedCurrentDate = current.format(formatter)
                val lastChecked = File("lastChecked.txt")
                var shouldCheck = true
                if (lastChecked.exists()) {
                    val text = lastChecked.readText()
                    if (text == formattedCurrentDate) {
                        shouldCheck = false
                    }
                } else {
                    lastChecked.createNewFile()
                }
                if (shouldCheck) {
                    val curDir = System.getProperty("user.dir")
                    val fullPath = "$curDir/src/$packageName"
                    File(fullPath).walkTopDown().forEach {
                        if (it.toString().endsWith(".kt")) {
                            val fileContent = it.bufferedReader().readText()
                            val sha256 = fileContent.sha256()
                            val name = it.toString().substringAfter("src\\")
                            val dbHash = GlobalStructs.db.getFileHash(name)
                            if (dbHash.isNotEmpty() && dbHash != "null") {
                                if (dbHash != sha256) {
                                    GlobalStructs.db.pushFile(it, sha256)
                                }
                            } else {
                                GlobalStructs.db.pushFile(it, sha256)
                            }
                        }
                    }
                }
            }
        }


        resources.asIterator().forEach {
            if (File(it.file).exists()) {
                val file = File(it.file)
                loopOverInternalScriptClasses(file)
            } else {
                //Open jar, loop over all classes to find abstract class.
                if (it.file.contains("!")) {

                    val jarFilePath = JarFile(it.file.split("!")[0].replace("file:", ""))

                    println("Lookin at this path: $jarFilePath")
                    jarFilePath.entries().asIterator().forEach { jarEntry ->
                        if (jarEntry.name.endsWith(".class")) {
                            val classReader = ClassReader(jarFilePath.getInputStream(jarEntry))
                            val classNode = ClassNode()
                            classReader.accept(classNode, 0)

                            if (classNode.superName != null
                                && (classNode.superName.contains("ActionScript")
                                        || classNode.superName.contains("PaintScript")
                                        || classNode.superName.contains("ServiceScript"))
                                && classNode.name.replace(".class", "")
                                    .split("/").last() !in defaultScripts
                            ) {
                                //We want to make sure we are looking at the right package. So we compare each director
                                // structure to make sure it matches given the desired class path
                                val fullClassSplit = classNode.name.split("/")
                                val desiredClassSplit = packageName.split("/")
                                var goodPackage = true
                                desiredClassSplit.forEachIndexed { index, s ->
                                    if (s != fullClassSplit[index]) {
                                        goodPackage = false
                                    }
                                }

                                //Filtering out the non needed scripts
                                if (limitedScripts.isNotEmpty()
                                    && classNode.superName.contains("ActionScript")
                                    && classNode.name.replace(".class", "")
                                        .split("/").last() !in limitedScripts
                                ) {
                                    goodPackage = false
                                    println("Filtering out the non needed scripts: ${classNode.name}")
                                }

                                if (goodPackage) {
                                    addJarScriptToScripts(File(jarEntry.name), classNode)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loopOverInternalScriptClasses(file: File) {
        val files = file.listFiles() ?: return
        for (possibleScript in files) {
            if (possibleScript.isDirectory) {
                loopOverInternalScriptClasses(possibleScript)
            } else {
                if (possibleScript.isFile && possibleScript.name.endsWith(".class")) {
                    val classReader = ClassReader(possibleScript!!.inputStream())
                    val classNode = ClassNode()
                    classReader.accept(classNode, 0)
                    addJarScriptToScripts(possibleScript, classNode)
                }
            }
        }
    }

    private fun addJarScriptToScripts(file: File, classNode: ClassNode) {

        if (classNode.visibleAnnotations == null)
            return

        for (x in classNode.visibleAnnotations) {
            if (x.desc.contains("ScriptManifest")) {

                //println("${x.values[1]}, ${x.values[3]} ${x.values[5]} ${x.values[7]}")

                val type = when {
                    classNode.superName.contains("PaintScript") -> {
                        ScriptType.PaintScript
                    }
                    classNode.superName.contains("ServiceScript") -> {
                        ScriptType.ServiceScript
                    }
                    classNode.superName.contains("ActionScript") -> {
                        ScriptType.ActionScript
                    }
                    else -> ScriptType.None
                }

                val scriptManifestName = x.values[3] as String
                if ((!scriptManifestName.toLowerCase().contains("private") ||
                            (scriptManifestName.toLowerCase().contains("private")
                                    && GlobalStructs.db.isPrivateScriptsValidated))
                ) {
                    if (limitedScripts.isEmpty()
                        //Limiting the action scripts
                        || (limitedScripts.isNotEmpty()
                                && type == ScriptType.ActionScript
                                && x.values[3] in limitedScripts)
                        || (limitedScripts.isNotEmpty()
                                && type != ScriptType.ActionScript)
                    ) {
                        scriptsInformation[file.name] = if (!file.name.contains(".jar")) {
                            //Adding the class for internally compiled scripts
                            ScriptInformation(
                                file.name,
                                file.path,
                                "${x.values[1]}",
                                "${x.values[3]}",
                                "${x.values[5]}",
                                "${x.values[7]}",
                                type,
                                classNode.name,
                                Class.forName(classNode.name.replace("/", "."))
                            )
                        } else {
                            ScriptInformation(
                                file.name,
                                file.path,
                                "${x.values[1]}",
                                "${x.values[3]}",
                                "${x.values[5]}",
                                "${x.values[7]}",
                                type,
                                classNode.name
                            )
                        }
                        println("[+] added ${file.name}, ${file.path}, ${classNode.name}")
                    }
                }
            }
        }
    }
}

// for tests
fun main() {
    val scripts = LoadScripts::class.java.classLoader.getResources("com/p3achb0t/scripts").toList()

    scripts.forEach { println(File(it.file).absolutePath) }

    //Files.walk(Paths.get("C:/Users/Laurence/Desktop/Coding Projects/P3achB0t/target/classes/com/p3achb0t/scripts")).forEach(::println)

    /*val debug = LoadScripts()
    debug.loadPath("${Constants.USER_DIR}/${Constants.APPLICATION_CACHE_DIR}/${Constants.SCRIPTS_DIR}")
    debug.loadPath("com/p3achb0t/scripts")
    debug.loadPath("com/p3achb0t/scripts_private")
    for (x in debug.scriptsInformation.keys) {
        println(x)
    }
    debug.removePath("${Constants.USER_DIR}/${Constants.APPLICATION_CACHE_DIR}/${Constants.SCRIPTS_DIR}")
    println("----------- REMOVED FOLDER -----------")
    for (x in debug.scriptsInformation.keys) {
        println(x)
    }*/
}