package com.example.myapplication

import kotlinx.cinterop.UByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.toCValues
import platform.darwin.COMPRESSION_ZLIB
import platform.darwin.compression_decode_buffer
import platform.darwin.compression_encode_buffer
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalUnsignedTypes::class, ExperimentalEncodingApi::class)
actual object Compressor {
    private const val capacity = 10_000_000 // 10 MB, needs to be tuned

    actual fun compress(request: CompressionRequest): CompressionResponse? {
        return try {
            memScoped {
                val inputData = request.data.encodeToByteArray()
                val destinationBuffer = allocArray<UByteVar>(capacity)

                val newSize = compression_encode_buffer(
                    destinationBuffer, capacity.convert(),
                    inputData.toUByteArray().toCValues(), inputData.size.convert(),
                    null,
                    COMPRESSION_ZLIB
                )

                val bytes = destinationBuffer.readBytes(newSize.convert())
                val base64EncodedString = Base64.Default.encode(bytes)
                CompressionResponse(base64EncodedString)
            }
        } catch (e: Exception) { null }
    }

    actual fun decompress(request: DecompressionRequest): DecompressionResponse? {
        return try {
            memScoped {
                val input = Base64.Default.decode(request.base64EncodedString).also { println(it.size) }

                val destinationBuffer = allocArray<UByteVar>(capacity)
                val oldSize = compression_decode_buffer(
                    destinationBuffer, capacity.convert(),
                    input.toUByteArray().toCValues(), input.size.convert(),
                    null,
                    COMPRESSION_ZLIB
                )

                val normalString = destinationBuffer.readBytes(oldSize.convert()).decodeToString()
                DecompressionResponse(normalString)
            }
        } catch (e: Exception) { null }
    }
}