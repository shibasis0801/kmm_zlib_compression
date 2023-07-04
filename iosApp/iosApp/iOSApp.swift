import SwiftUI

@main
struct iOSApp: App {
    init() {
        let stringToCompress = "Hello how are you, World!"
        if let compressedData = compressString(stringToCompress) {
            print("Compressed Data: \(compressedData)")
            if let decompressedString = decompressData(compressedData) {
                print("Decompressed String: \(decompressedString)")
            }
        }
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}

    

}
