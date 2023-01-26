//
//  LoginScreen.swift
//  ios
//
//  Created by Shubham Singh on 26/01/23.
//  Copyright © 2023 playtogether. All rights reserved.
//

import SwiftUI
import RxSwift
import shared
import sharedSwift
import KMPNativeCoroutinesRxSwift

struct AuthScreen: View {
    @State var email: String = ""
    @State var password: String = ""
    @State var repeatPassword: String = ""
    let authViewModel = ViewModelDIHelper().authViewModel
    @ObservedObject var loginState: ObservableValue<UIState<AuthResponse>>
    @ObservedObject var registerState: ObservableValue<UIState<AuthResponse>>
    
    init() {
        loginState = ObservableValue(
            flow: authViewModel.loginStateNative,
            initialValue: authViewModel.loginStateNativeValue
        )
        registerState = ObservableValue(
            flow: authViewModel.registerStateNative,
            initialValue: authViewModel.registerStateNativeValue
        )
    }
    
    var body: some View {
        VStack {
            TextField(text: $email, label: { Text("E-mail") })
            
            SecureField(text: $password, label: { Text("Password") })
            
            SecureField(text: $repeatPassword, label: { Text("Repeat Password") })
            
            switch(UIStateKs(registerState.value)) {
            case .loading: ProgressView()
            default: Button(action: {
                if (password == repeatPassword) {
                    authViewModel.register(email: email, password: password)
                }
            }, label: { Text("Register") })
            .disabled(password != repeatPassword)
            }
            
            Text(registerState.value.description)

            switch(UIStateKs(registerState.value)) {
            case .failure(let error): Text(error.description())
            default: EmptyView()
            }
            
        }
    }
}

struct AuthScreen_Previews: PreviewProvider {
    static var previews: some View {
        AuthScreen()
    }
}
