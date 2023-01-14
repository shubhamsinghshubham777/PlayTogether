import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        Text("Hello, world!")
    }
}

class ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
    
#if DEBUG
    @objc class func injected() {
        let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene
        windowScene?.windows.first?.rootViewController =
        UIHostingController(rootView: previews)
    }
#endif
}
