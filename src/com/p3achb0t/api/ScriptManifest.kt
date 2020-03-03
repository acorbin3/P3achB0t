package com.p3achb0t.api
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ScriptManifest(val category: String, val name: String, val author: String)