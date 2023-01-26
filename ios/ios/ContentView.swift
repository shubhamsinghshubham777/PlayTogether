import SwiftUI
import shared
import KMPNativeCoroutinesRxSwift
import RxSwift

struct ContentView: View {
    let mainViewModel = ViewModelDIHelper().mainViewModel
    @ObservedObject var isUserLoggedIn: ObservableValue<KotlinBoolean>
    
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
                AuthScreen()
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
