package com.p3achb0t.server.packets

import kotlin.experimental.and
import kotlin.experimental.or

abstract class Packet {

    abstract fun config() : Byte

    abstract fun type() : Byte

    abstract fun payload() : Collection<Byte>

    fun length() : Int {
        return 2 + payload().size
    }

    fun build() : ByteArray {
        val packet = mutableListOf<Byte>()

        packet.add(type())
        packet.add(config()) // should make a function for config
        packet.addAll(encodeByteInteger(payload().size))
        packet.addAll(payload())
        return packet.toByteArray()
    }

    private fun encodeByteInteger(length: Int) : Collection<Byte> {
        var int = length
        val array = mutableListOf<Byte>()
        var encodedByte: Byte = 0
        do {
            encodedByte = (int % 128).toByte()

            int /= 128

            if (int > 0) {
                encodedByte = encodedByte or 128.toByte()
            }

            array.add(encodedByte)
        } while (int > 0)

        return array
    }

    private fun decodeByteInteger(array: ByteArray) : Int {
        var multiplier = 1

        var value = 0
        var encodedByte: Byte
        var index = 0

        do {
            encodedByte = array[index]
            index++

            value += (encodedByte and 127.toByte()) * multiplier

            if (multiplier > 128*128*128) {
                throw Error("Malformed Variable Byte Integer")
            }

            multiplier *= 128

        } while ((encodedByte and 128.toByte()) != 0.toByte())

        return value
    }

}

fun main() {

    decodeByteInteger(encodeByteInteger(1000))
}


fun encodeByteInteger(length: Int) : ByteArray {
    var int = length
    val array = mutableListOf<Byte>()
    var encodedByte: Byte = 0
    do {
        encodedByte = (int % 128).toByte()

        int /= 128

        // if there are more data to encode, set the top bit of this byte

        if (int > 0) {
            encodedByte = encodedByte or 128.toByte()
        }

        array.add(encodedByte)
    } while (int > 0)

    for (i in array) {
        print("${i.toUByte()} ")
    }
    return array.toByteArray()
}

fun decodeByteInteger(array: ByteArray) {
    var multiplier = 1

    var value = 0
    var encodedByte: Byte
    var index = 0

    do {
        encodedByte = array[index]
        index++

        value += (encodedByte and 127.toByte()) * multiplier

        if (multiplier > 128*128*128) {
            throw Error("Malformed Variable Byte Integer")
        }

        multiplier *= 128

    } while ((encodedByte and 128.toByte()) != 0.toByte())

    println()
    println("${value}")


}