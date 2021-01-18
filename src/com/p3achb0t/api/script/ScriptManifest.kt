package com.p3achb0t.api.script
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ScriptManifest(val category: String, val name: String, val author: String, val version: String, val filter: Boolean = false)