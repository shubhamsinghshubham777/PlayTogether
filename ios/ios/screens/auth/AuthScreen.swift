//
//  AuthScreen.swift
//  PlayTogether
//
//  Created by Shubham Singh on 28/01/23.
//  Copyright © 2023 PlayTogether. All rights reserved.
//

import SwiftUI
import shared

struct AuthScreen: View {
    @Environment(\.verticalSizeClass) var heightSizeClass: UserInterfaceSizeClass?
    @Environment(\.horizontalSizeClass) var widthSizeClass: UserInterfaceSizeClass?
    
    let authViewModel = ViewModelDIHelper().authViewModel
    @ObservedObject var loginState: ObservableValue<UIState<AuthResponse>>
    @ObservedObject var registerState: ObservableValue<UIState<AuthResponse>>
    
    @State var email: String = ""
    @FocusState var isEmailTfFocused: Bool
    
    @State var password: String = ""
    @FocusState var isPasswordTfFocused: Bool
    
    @State var repeatPassword: String = ""
    @FocusState var isRepeatPasswordTfFocused: Bool
    
    @State var isRegistering: Bool = false
    
    let buttonPressAnimation: Animation = .easeInOut(duration: 0.175)
    
    @State var isAppIconAnimating: Bool = true
    
    @State var isAuthButtonPressed: Bool = false
    
    let screenSize = UIScreen.main.bounds
    let constants = Constants()
    let authConstants = Constants.Auth()
    
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
    
    private struct TextFieldModifier: ViewModifier {
        var isFocused: Bool
        
        func body(content: Content) -> some View {
            content
                .frame(width: 300)
                .autocapitalization(.none)
                .accentColor(Color.Primary)
                .padding(.all, 12)
                .overlay(
                    RoundedRectangle(cornerRadius: 4)
                        .stroke(
                            (isFocused ? Color.Primary : Color.OnSurface).opacity(0.4),
                            lineWidth: isFocused ? 2 : 1
                        )
                        .animation(
                            .easeInOut(duration: 0.2),
                            value: isFocused
                        )
                )
        }
    }
    
    var appLogo: some View {
        Image(Images.AppLogo.rawValue)
            .resizable()
            .scaledToFit()
            .frame(width: 200)
            .blur(radius: isAppIconAnimating ? 5 : 0)
            .scaleEffect(isAppIconAnimating ? 0.7 : 1)
    }
    
    var secondarySection: some View {
        VStack(spacing: 16) {
            Spacer(minLength: 24)
            
            Text(constants.AppGreeting)
                .font(.largeTitle.bold())
                .foregroundColor(.primary)
            
            Text(constants.AppGreetingSupportingText)
                .font(.headline)
                .foregroundColor(.secondary)
                .multilineTextAlignment(.center)
            
            TextField(
                text: $email,
                label: { Text(authConstants.EmailLabel) }
            )
            .focused($isEmailTfFocused)
            .keyboardType(.emailAddress)
            .modifier(TextFieldModifier(isFocused: isEmailTfFocused))
            .padding(.top, 16)
            
            SecureField(text: $password, label: { Text(authConstants.PasswordLabel) })
                .focused($isPasswordTfFocused)
                .modifier(TextFieldModifier(isFocused: isPasswordTfFocused))
            
            if isRegistering {
                SecureField(text: $repeatPassword, label: { Text(authConstants.RepeatPasswordLabel) })
                    .focused($isRepeatPasswordTfFocused)
                    .modifier(TextFieldModifier(isFocused: isRepeatPasswordTfFocused))
                    .transition(.move(edge: .top).combined(with: .opacity))
            }
            
            HStack {
                let areCredentialsValid = authViewModel.areCredentialsValid(
                    email: email,
                    password: password,
                    repeatPassword: repeatPassword,
                    isRegistering: isRegistering
                )
                
                Button(action: {}, label: {
                    if (loginState.value == UIStateLoading() || registerState.value == UIStateLoading()) {
                        ProgressView()
                    } else {
                        Text(isRegistering ? authConstants.RegisterButtonLabel : authConstants.LoginButtonLabel)
                                .gesture(
                                    DragGesture(minimumDistance: 0)
                                        .onChanged({ _ in withAnimation(buttonPressAnimation) { isAuthButtonPressed = true } })
                                        .onEnded({ _ in
                                            withAnimation(buttonPressAnimation) { isAuthButtonPressed = false }
                                            isRegistering ? authViewModel.register(email: email, password: password) :
                                            authViewModel.login(email: email, password: password)
                                        })
                                )
                    }
                })
                .padding(12)
                .background(Color.Primary
                    .opacity(areCredentialsValid ? 1 : 0.25)
                    .opacity(isAuthButtonPressed ? 0.5 : 1)
                )
                .foregroundColor(Color.OnPrimary)
                .clipShape(RoundedRectangle(cornerRadius: 8))
                .shadow(radius: isAuthButtonPressed ? 0 : 8)
                .disabled(!areCredentialsValid)
                
                Spacer()
                
                Button(
                    action: {
                        withAnimation { isRegistering = !isRegistering }
                    },
                    label: {
                        Text(isRegistering ? authConstants.RegisterAuthTypeMessage : authConstants.LoginAuthTypeMessage)
                    }
                )
                .foregroundColor(Color.Primary)
            }
            .frame(width: 330, height: 50)
            .padding(.vertical, 16)
            
            switch(UIStateKs(loginState.value)) {
            case .failure(let exception): Text(exception.exception.message ?? "")
                    .font(.caption)
                    .foregroundColor(.Error)
            default: EmptyView()
            }
            
            switch(UIStateKs(registerState.value)) {
            case .failure(let exception): Text(exception.exception.message ?? "")
                    .font(.caption)
                    .foregroundColor(.Error)
            default: EmptyView()
            }
        }
    }
    
    var body: some View {
        ZStack {
            LinearGradient(
                colors: [
                    .PrimaryContainer,
                    .Surface,
                ],
                startPoint: .top,
                endPoint: .bottom
            )
            .edgesIgnoringSafeArea(.all)
            
            if (heightSizeClass == .regular) {
                ScrollView {
                    VStack(spacing: 16) {
                        Spacer(minLength: screenSize.height * 0.05)
                        appLogo
                        secondarySection
                    }
                    .padding(.horizontal, 32)
                }
            } else {
                HStack {
                    appLogo
                    Spacer()
                    ScrollView {
                        secondarySection
                    }
                }
                .padding(.horizontal, 40)
            }
        }
        .onAppear {
            withAnimation(.spring(response: 2, dampingFraction: 0.5)) { isAppIconAnimating = false }
        }
    }
}

struct AuthScreen_Previews: PreviewProvider {
    static var previews: some View {
        AuthScreen()
            .previewDevice("iPhone 14 Pro")
        
        AuthScreen()
            .previewDevice("iPhone SE (3rd generation)")
        
        AuthScreen()
            .previewDevice("iPad (10th generation)")
    }
}
