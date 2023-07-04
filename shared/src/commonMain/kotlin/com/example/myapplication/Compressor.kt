package com.example.myapplication

expect object Compressor {
    fun compress(request: CompressionRequest): CompressionResponse?
    fun decompress(request: DecompressionRequest): DecompressionResponse?
}