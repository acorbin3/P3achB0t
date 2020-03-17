package com.p3achb0t.client.scripts.loading

import com.p3achb0t.client.configs.Constants
import java.io.File
import java.net.URL
import java.net.URLClassLoader

enum class ScriptType {
    DebugScript,
    AbstractScript,
    BackgroundScript,
    None
}

class ScriptInformation(val fileName: String, val category: String, val name: String, val author: String, val version: String, val type: ScriptType, val main: String) {

    private val path = "${Constants.USER_DIR}/${Constants.APPLICATION_CACHE_DIR}/${Constants.SCRIPTS_DIR}"


    fun load(): Any? {
        val file = File("$path/$fileName")
        val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
        val classLoader = URLClassLoader(urlArray)
        return classLoader.loadClass(main).newInstance()
    }


    override fun toString(): String {
        return "$fileName [$author, $name, {$category}, main: $main type: $type]"
    }

}

/*
class ScriptInformation(script: Any) {

    val name: String
    val author: String
    val category: String
    val type: ScriptType
    val main: String

    init {
        val annotations = script::class.findAnnotation<ScriptManifest>()
        name = annotations?.name.toString()
        author = annotations?.author.toString()
        category = annotations?.category.toString()
        main = script::class.toString()
        type = findType(script)
    }

    override fun toString(): String {
        return "[$author, $name, {$category}, main: $main type: $type]"
    }

    private fun findType(script: Any) : ScriptType {
        val s = script::class.superclasses

        when {
            s.contains(DebugScript::class)-> {
                return ScriptType.DebugScript
            }
            s.contains(AbstractScript::class)-> {
                return ScriptType.AbstractScript
            }
            s.contains(BackgroundScript::class)-> {
                return ScriptType.BackgroundScript
            }
        }
        return ScriptType.None
    }


}
 */