package com.p3achb0t.api.listeners

interface ChatListener {

    fun notifyMessage(flags: Int, name: String, message: String, prefix: String)
}