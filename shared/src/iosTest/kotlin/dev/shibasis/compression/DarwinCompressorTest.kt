package dev.shibasis.compression

import kotlin.test.Test

class DarwinCompressorTest {
    @Test
    fun testSmallStringCompression() {
        val string = "Hello how are you, World!"
        val compressed = Compressor.compress(CompressionRequest(string))
        assert(compressed != null)
        val decompressed = Compressor.decompress(DecompressionRequest(compressed!!.base64EncodedString))
        assert(decompressed != null)
        assert(string == decompressed?.data)
    }
}
