package com.p3achb0t.client.util

import java.io.File
import java.net.URL
import java.net.URLClassLoader

class JarLoader {

    companion object {
        fun load(path: String, main: String): Any? {
            val file = File(path)
            val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
            val classLoader = URLClassLoader(urlArray)
            return classLoader.loadClass(main)?.newInstance()
        }
    }
}