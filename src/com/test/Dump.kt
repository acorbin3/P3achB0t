package com.test

import com.p3achb0t.injection.Replace.ProxySocketDump
import java.io.File

class Dump {
    val dump = ProxySocketDump.dump()

    fun dumper() {
        val file = File("ProxySocket.class")
        file.writeBytes(dump)

    }
}

fun main() {
    val dump = Dump()
    dump.dumper()
}