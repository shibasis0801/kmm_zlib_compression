package dev.shibasis.compression

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

data class CompressionResponse(
    val base64EncodedString: String,
)

data class CompressionRequest(
    val data: String
)

data class DecompressionRequest(
    val base64EncodedString: String
)

data class DecompressionResponse(
    val data: String
)


