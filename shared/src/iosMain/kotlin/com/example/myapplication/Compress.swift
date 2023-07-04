// Function to compress a string
func compressString(_ input: String) -> Data? {
    guard let inputData = input.data(using: .utf8) else { return nil }

    let destinationBuffer = UnsafeMutablePointer<UInt8>.allocate(capacity: inputData.count * 2)
    let compressedSize = compression_encode_buffer(destinationBuffer, inputData.count * 2, [UInt8](inputData), inputData.count, nil, COMPRESSION_ZLIB)

    guard compressedSize != 0 else {
        destinationBuffer.deallocate()
        return nil
    }

    let compressedData = Data(bytes: destinationBuffer, count: compressedSize)
//    destinationBuffer.deallocate()
    return compressedData
}

// Function to decompress data
func decompressData(_ compressedData: Data) -> String? {
    let destinationBuffer = UnsafeMutablePointer<UInt8>.allocate(capacity: compressedData.count * 4)
    let decompressedSize = compression_decode_buffer(destinationBuffer, compressedData.count * 4, [UInt8](compressedData), compressedData.count, nil, COMPRESSION_ZLIB)

    guard decompressedSize != 0 else {
//        destinationBuffer.deallocate()
        return nil
    }

    let decompressedData = Data(bytes: destinationBuffer, count: decompressedSize)
    destinationBuffer.deallocate()
    return String(data: decompressedData, encoding: .utf8)
}