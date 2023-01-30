import SwiftUI
import shared
import KMPNativeCoroutinesCore
import KMPNativeCoroutinesRxSwift
import RxSwift

struct ContentView: View {
    @ObservedObject var mainViewModel: MainViewModel = MainViewModel()
    
    @Environment(\.colorScheme) private var colorScheme: ColorScheme
    
    var appBackground: some View {
        Color.Surface.ignoresSafeArea()
    }
    
    var body: some View {
        if let nnIsUserLoggedIn = mainViewModel.isUserLoggedIn {
            if (nnIsUserLoggedIn) {
                NavigationView {
                    ZStack {
                        appBackground
                        DashboardScreen()
                    }
                }
            } else {
                NavigationView {
                    ZStack {
                        appBackground
                        AuthScreen()
                    }
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
