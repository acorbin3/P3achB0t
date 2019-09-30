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
            val cArg = arrayOfNulls<Class<*>>(1) //Our constructor has 3 arguments

            cArg[0] = String::class.java //Second argument is of *object* type String
            val s = "none" // SOCKS5;185.244.192.119:7670 or none
            return classLoader.loadClass(main)?.getDeclaredConstructor(*cArg)?.newInstance(s)
        }
    }
}


/*
GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
    LDC "."
    INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
 */