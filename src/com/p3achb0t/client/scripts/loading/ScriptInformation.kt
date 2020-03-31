package com.p3achb0t.client.scripts.loading

import java.io.File
import java.net.URL
import java.net.URLClassLoader

enum class ScriptType {
    DebugScript,
    AbstractScript,
    BackgroundScript,
    None
}

class ScriptInformation(val fileName: String, val path: String, val category: String, val name: String, val author: String, val version: String, val type: ScriptType, val main: String) {

    fun load(): Any? {
        return if (path.contains(".class")) {
            val loadedClass = Class.forName(main.replace("/","."), true, this.javaClass.classLoader)
            loadedClass.newInstance()
        } else {
            val file = File(path)
            val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
            val classLoader = URLClassLoader(urlArray)
            classLoader.loadClass(main).newInstance()
        }
    }

    override fun toString(): String {
        return "$fileName [$author, $name, {$category}, main: $main type: $type]"
    }
}