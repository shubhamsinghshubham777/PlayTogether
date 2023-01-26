import SwiftUI
import shared
import KMPNativeCoroutinesRxSwift
import RxSwift

struct ContentView: View {
    let mainViewModel = ViewModelDIHelper().mainViewModel
    let isUserLoggedIn: ObservableValue<KotlinBoolean>
    
    init() {
        isUserLoggedIn = ObservableValue(flow: mainViewModel.isUserLoggedInNative, initialValue: KotlinBoolean(bool: mainViewModel.isUserLoggedInNativeValue))
    }
    
    var body: some View {
        if (isUserLoggedIn.value.boolValue) {
            NavigationView {
                Text("Dashboard Screen")
            }
        } else {
            NavigationView {
                Text("Login Screen")
            }
        }
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
