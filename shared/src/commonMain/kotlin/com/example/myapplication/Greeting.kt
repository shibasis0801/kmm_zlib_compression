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

    fun greet(): String {
        val original = (1..10000).map { "Hello how are you, World!" }.joinToString()

        val compressed = Compressor.compress(CompressionRequest(original))
        val decompressed = Compressor.decompress(DecompressionRequest(compressed!!.base64EncodedString))

        println(original == decompressed?.data)
        println(original)
        println(decompressed?.data)
        return ""
    }
}