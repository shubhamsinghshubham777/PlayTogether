import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        KoinDIIOSKt.doInitKoinIOS()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
