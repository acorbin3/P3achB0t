package com.p3achb0t.hook_interfaces

interface RuneScriptReference {
    fun getEventStepValue(): Int
    fun getLocalIntegers(): IntArray
    fun getLocalStrings(): Array<String>
    fun getScriptDef(): RuneScript
}
