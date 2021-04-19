package com.p3achb0t.analyser

import java.math.BigInteger

class DMath {

    companion object {
        private val UNSIGNED_INT_MODULUS = BigInteger.ONE.shiftLeft(Integer.SIZE)
        private val UNSIGNED_LONG_MODULUS = BigInteger.ONE.shiftLeft(java.lang.Long.SIZE)
        fun invert(n: Int): Int = n.toBigInteger().modInverse(UNSIGNED_INT_MODULUS).toInt()
        fun invert(n: Long): Long = n.toBigInteger().modInverse(UNSIGNED_LONG_MODULUS).toLong()

        fun modInverse(value: BigInteger, bits: Int): BigInteger? {
            return try {
                val shift = BigInteger.ONE.shiftLeft(bits)
                value.modInverse(shift)
            } catch (e: ArithmeticException) {
                value
            }
        }

        fun modInverse(value: Int): Int {
            return modInverse(BigInteger.valueOf(value.toLong()), 32)!!.toInt()
        }

        fun modInverse(value: Long): Long {
            return modInverse(BigInteger.valueOf(value), 64)!!.toLong()
        }

        fun modInverse(value: Number?): Number? {
            return if (value is Int) {
                modInverse(value)
            } else if (value is Long) {
                modInverse(value)
            } else {
                throw IllegalArgumentException()
            }
        }

        fun isInversable(value: Int): Boolean {
            return try {
                modInverse(value)
                true
            } catch (ex: ArithmeticException) {
                false
            }
        }

        private fun isInversable(value: Long): Boolean {
            return try {
                modInverse(value)
                true
            } catch (ex: ArithmeticException) {
                false
            }
        }

        fun isInversable(value: Number?): Boolean {
            return if (value is Int) {
                isInversable(value)
            } else if (value is Long) {
                isInversable(value)
            } else {
                throw IllegalArgumentException()
            }
        }

        fun multiply(one: Number, two: Number): Number? {
            assert(one.javaClass == two.javaClass)
            return if (one is Int) {
                one * two as Int
            } else if (one is Long) {
                one * two as Long
            } else {
                throw IllegalArgumentException()
            }
        }

        fun equals(one: Number, two: Int): Boolean {
            return if (one is Long) {
                equals(one, two.toLong() and -0x1)
            } else one.toInt() == two
        }

        fun equals(one: Number, two: Long): Boolean {
            return if (one is Int) {
                equals(one, two.toInt())
            } else one.toLong() == two
        }
    }

}