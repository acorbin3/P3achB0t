package com.test

import org.objectweb.asm.ClassReader
import org.objectweb.asm.util.ASMifier
import org.objectweb.asm.util.TraceClassVisitor
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintWriter

class BytecodePrinter {

    fun generateBytecode() {
        val file = File("/home/kasper/Runescape/P3achB0t/src/com/test/ProxySocket2.class")
        val cr = ClassReader(file.inputStream())
        val asmStream = ByteArrayOutputStream()
        cr.accept(TraceClassVisitor(null, ASMifier(), PrintWriter(asmStream)), 2)
        var depth = 0
        var output = asmStream.toString().split("\n").map {
            if (it.trim().endsWith("}")) depth--
            val s = " ".repeat(depth * 4) + it
            if (it.trim().endsWith("{")) depth++
            s
        }.joinToString("\n")
        println(output)
    }
}

fun main() {
    val b = BytecodePrinter()
    b.generateBytecode()
}