package com.p3achb0t.client.loader

import java.applet.AppletStub
import java.net.MalformedURLException
import java.net.URL

class RSAppletStub(private val parameters: Map<String, String>) : AppletStub {
    private val appletContext: RSAppletContext = RSAppletContext()
    private var active = false

    override fun isActive(): Boolean {
        // Lets everything know that it is alive
        return active
    }

    fun setActive(active: Boolean) {
        // A setter method for us so we can say we've started the Applet
        this.active = active
    }

    override fun getDocumentBase(): URL {
        // Return the codebase parameter from our parameter map, the document base is exactly the same as the codebase
        try {
            return URL(parameters["codebase"])
        } catch (e: MalformedURLException) {
            throw IllegalArgumentException("Invalid Document Base URL")
        }
    }

    override fun getCodeBase(): URL {
        // Return the codebase parameter from our parameter map
        try {
            return URL(parameters["codebase"])
        } catch (e: MalformedURLException) {
            throw IllegalArgumentException("Invalid Code Base URL")
        }

    }

    override fun getParameter(name: String): String? {
        // Get the requested parameter from the map
        return if (parameters.containsKey(name)) parameters[name] else null
    }

    override fun getAppletContext(): RSAppletContext {
        // Return our instance of RSAppletContext so we can fake the environment
        return appletContext
    }

    override fun appletResize(width: Int, height: Int) {
        // So the environment can set the applet size
        val applet = getAppletContext().getApplet("main")
        applet?.resize(width, height)
    }
}