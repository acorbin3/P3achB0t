package com.p3achb0t.api.script.listeners

interface ChatListener {

    fun notifyMessage(flags: Int, name: String, message: String, prefix: String)
}