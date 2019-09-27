package com.test

import java.io.ByteArrayOutputStream
import javax.tools.ToolProvider


class Compiler {

    fun compile() {
        val javac = ToolProvider.getSystemJavaCompiler()
        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val rc = javac.run(null, outputStream, errorStream, "/home/kasper/Runescape/P3achB0t/src/ProxySocket.java", "-d", "/home/kasper/Runescape/P3achB0t/src/")
        val rc2 = javac.run(null, outputStream, errorStream, "/home/kasper/Runescape/P3achB0t/src/ProxyConnection.java"/*, "-d", "bin/"*/)
        //val rc2 = javac.run(null, outputStream, errorStream, "/home/kasper/Runescape/P3achB0t/src/com/p3achb0t/injection/Replace/ProxyConnection.java"/*, "-d", "bin/"*/)


        println("Status $rc")
    }

}

fun main() {
    val compiler = Compiler()
    compiler.compile()
}