package com.p3achb0t.client.util

import java.io.File
import java.net.URL
import java.net.URLClassLoader

class JarLoader {

    /*
    GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
        LDC "."
        INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
     */
    companion object {
        var s = "none" // SOCKS5;192.241.68.142:8000 or none
        //val s = "SOCKS5;207.188.176.165:58185" //  or none 1
        //val s = "SOCKS5;207.188.177.110:58185" //  or none 2
        //val s = "SOCKS5;207.188.178.120:58185" //  or none 3
        //val s = "SOCKS5;207.188.179.251:58185" //  or none 4
        //val s = "SOCKS5;38.127.135.48:58185" //  or none 5
        //val s = "SOCKS5;38.127.144.118:58185" //  or none 6
        //val s = "SOCKS5;38.127.145.114:58185" //  or none 7
        //val s = "SOCKS5;38.141.62.111:58185" //  or none 8
        //val s = "SOCKS5;38.64.51.190:58185" //  or none 9
        //val s = "SOCKS5;38.69.2.44:58185" //  or none 10
        fun load(path: String, main: String, proxy: String = "none"): Any? {
            val file = File(path)
            s = proxy
            val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
            val classLoader = URLClassLoader(urlArray)
            val cArg = arrayOfNulls<Class<*>>(1) //Our constructor has 3 arguments

            cArg[0] = String::class.java //Second argument is of *object* type String
            return classLoader.loadClass(main)?.getDeclaredConstructor(*cArg)?.newInstance(proxy)
        }

    }
}


/*
GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
    LDC "."
    INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
 */