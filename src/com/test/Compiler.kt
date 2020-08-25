package com.test

import com.p3achb0t.client.configs.Constants
import java.io.ByteArrayOutputStream
import javax.tools.ToolProvider


class Compiler {

    fun compile() {
        val javac = ToolProvider.getSystemJavaCompiler()

        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()
        val rc = javac.run(null, outputStream, errorStream, "${Constants.USER_DIR}/src/ProxySocket.java","-source", "11", "-target", "11", "-d", "C:\\Users\\C0rbin\\Documents\\GitHub\\P3achB0t_gradle\\P3achB0t\\src")
        val rc2 = javac.run(null, outputStream, errorStream, "${Constants.USER_DIR}/src/ProxyConnection.java","-source", "11", "-target", "11"/*, "-d", "bin/"*/)
        //val rc2 = javac.run(null, outputStream, errorStream, "/home/kasper/Runescape/P3achB0t/src/com/p3achb0t/injection/Replace/ProxyConnection.java"/*, "-d", "bin/"*/)


        println("Status $rc")
        println("Status $rc2")
    }

}

fun main() {
    val compiler = Compiler()
    compiler.compile()
}