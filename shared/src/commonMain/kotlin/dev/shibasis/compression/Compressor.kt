package dev.shibasis.compression

expect object Compressor {
    fun compress(request: CompressionRequest): CompressionResponse?
    fun decompress(request: DecompressionRequest): DecompressionResponse?
}
