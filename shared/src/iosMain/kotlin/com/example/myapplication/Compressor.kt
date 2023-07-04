package com.example.myapplication

import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.UByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.cstr
import kotlinx.cinterop.interpretCPointer
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.toCValues
import kotlinx.cinterop.toKString
import platform.darwin.ByteVar
import platform.darwin.COMPRESSION_ZLIB
import platform.darwin.compression_decode_buffer
import platform.darwin.compression_encode_buffer
import platform.posix.size_t
import platform.posix.uint8_tVar
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

actual object Compressor {
    private const val capacity = 10_000_000 // 10 MB, needs to be tuned

    @OptIn(ExperimentalUnsignedTypes::class, ExperimentalEncodingApi::class)
    actual fun compress(request: CompressionRequest): CompressionResponse? {
        return try {
            val string = memScoped {
                val inputData = request.data.encodeToByteArray()
                val destinationBuffer = allocArray<UByteVar>(capacity)

                val newSize = compression_encode_buffer(
                    destinationBuffer, capacity.convert(),
                    inputData.toUByteArray().toCValues(), inputData.size.convert(),
                    null,
                    COMPRESSION_ZLIB
                )

                val bytes = destinationBuffer.readBytes(newSize.convert())
                Base64.Default.encode(bytes)
            }

            CompressionResponse(string)
        } catch (e: Exception) { null }
    }

    @OptIn(ExperimentalUnsignedTypes::class, ExperimentalEncodingApi::class)
    actual fun decompress(request: DecompressionRequest): DecompressionResponse? {
        return try {
            val string = memScoped {
                val input = Base64.Default.decode(request.base64EncodedString).also { println(it.size) }

                val buffer = allocArray<UByteVar>(capacity)
                val oldSize = compression_decode_buffer(
                    buffer, capacity.convert(),
                    input.toUByteArray().toCValues(), input.size.convert(),
                    null,
                    COMPRESSION_ZLIB
                )

                buffer.readBytes(oldSize.convert()).decodeToString()
            }

            DecompressionResponse(string)
        } catch (e: Exception) { null }
    }
}