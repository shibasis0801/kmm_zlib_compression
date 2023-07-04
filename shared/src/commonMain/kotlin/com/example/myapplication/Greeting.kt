package com.example.myapplication

import kotlin.random.Random

class Greeting {
    private val platform: Platform = getPlatform()

    fun randomAlphaNumericString(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        return (1..length)
            .map { Random.nextInt(0, chars.length) }
            .map(chars::get)
            .joinToString("")
    }

    fun testCompression(input: String, alsoPrint: Boolean = false) {
        val compressed = Compressor.compress(CompressionRequest(input))
        val decompressed = Compressor.decompress(DecompressionRequest(compressed!!.base64EncodedString))

        println(if (input == decompressed?.data) "Input and Output Match" else "Compression Error")
        if (alsoPrint) {
            println(input)
            println(decompressed?.data)
        }
    }

    fun greet(): String {
        testCompression((1..100000).map { "Hello how are you, World!" }.joinToString())
        testCompression(randomAlphaNumericString(2_000_000))
        return ""
    }
}