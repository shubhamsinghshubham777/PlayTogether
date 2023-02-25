import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        KoinDIKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
