package com.p3achb0t

import com.p3achb0t.downloader.Parameters
import java.applet.AppletContext
import java.applet.AppletStub
import java.net.URL

class RSLoader(val world: Int) : AppletStub {
    private val w = Parameters(world)
    var params = w.PARAMETER_MAP
    override fun getCodeBase(): URL {
        return URL(params["codebase"])
    }

    override fun getParameter(name: String?): String {
        //println("Getting $name : ${params[name]}")
        return params[name]!!
    }

    override fun getAppletContext(): AppletContext = null!!

    override fun appletResize(width: Int, height: Int) {
    }

    override fun getDocumentBase(): URL {
        return URL(params["codebase"])
    }

    override fun isActive(): Boolean {
        return true
    }
}