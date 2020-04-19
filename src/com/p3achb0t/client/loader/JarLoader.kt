package com.p3achb0t.client.loader

import com.p3achb0t.client.configs.Constants
import java.applet.Applet
import java.io.File
import java.net.URL
import java.net.URLClassLoader

object JarLoader {

    var proxy = "none"

    fun load(world: Int, proxy: String = "none"): Applet? {
        return try {
            val file = File("./${Constants.APPLICATION_CACHE_DIR}/${Constants.INJECTED_JAR_NAME}")
            val urlArray: Array<URL> = Array(1, init = { file.toURI().toURL() })
            val classLoader = URLClassLoader(urlArray)
            val cArg = arrayOfNulls<Class<*>>(1) //Our constructor has 3 arguments

            cArg[0] = String::class.java //Second argument is of *object* type String

            println("Using proxy: $proxy | Last Proxy: ${this.proxy}")
            this.proxy = proxy

            val applet = classLoader.loadClass("client")?.getDeclaredConstructor(*cArg)?.newInstance(proxy) as Applet

            val rsAppletStub = RSAppletStub(ConfigReader(world).read())

            applet.setStub(rsAppletStub)
            rsAppletStub.appletContext.setApplet(applet)
            rsAppletStub.appletResize(Constants.MIN_GAME_WIDTH, Constants.MIN_GAME_HEIGHT)

            applet.init()
            applet
        } catch (e: Exception) {
            e.printStackTrace()
            println("Failed to load Jar.")
            null
        }
    }

}


/*
GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
    LDC "."
    INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
 */