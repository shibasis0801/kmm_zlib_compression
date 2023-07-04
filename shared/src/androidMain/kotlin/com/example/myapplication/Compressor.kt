package com.example.myapplication

actual object Compressor {
    actual fun compress(request: CompressionRequest): CompressionResponse? {
        return null
    }

    actual fun decompress(request: DecompressionRequest): DecompressionResponse? {
        return null
    }

    actual fun test() {}
}