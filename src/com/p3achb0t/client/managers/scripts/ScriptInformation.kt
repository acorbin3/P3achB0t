package com.p3achb0t.client.managers.scripts

import com.p3achb0t.api.ScriptManifest
import kotlin.reflect.full.findAnnotation

class ScriptInformation(script: Any) {

    val name: String
    val author: String
    val category: String

    init {
        val annotations = script::class.findAnnotation<ScriptManifest>()
        name = annotations?.name.toString()
        author = annotations?.author.toString()
        category = annotations?.category.toString()
    }

    override fun toString(): String {
        return "[$author, $name, {$category}]"
    }


}