import SwiftUI
import shared


struct ContentView: View {

    var body: some View {
        Text("Hello")
    }
}


@main
struct iOSApp: App {
    init() {
//        Greeting.greet()
        let stringToCompress = "Hello how are you, World!"
        if let compressedData = SwiftCompressor.compressString(stringToCompress) {
            print("Compressed Data: \(compressedData)")
            if let decompressedString = SwiftCompressor.decompressData(compressedData) {
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
