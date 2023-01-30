//
//  MainViewModel.swift
//  PlayTogether
//
//  Created by Shubham Singh on 29/01/23.
//  Copyright © 2023 PlayTogether. All rights reserved.
//

import Foundation
import shared
import RxSwift
import KMPNativeCoroutinesRxSwift

class MainViewModel : ObservableObject {
    private let sharedMainViewModel = ViewModelDIHelper().mainViewModel
    
    @Published var isUserLoggedIn: Bool? = nil
    var isUserLoggedInDisposable: Disposable? = nil
    
    @Published var isDarkModeOn: Bool? = nil
    var isDarkModeOnDisposable: Disposable? = nil
    
    private let ptTheme = PTTheme.instance
    
    init() {
        isUserLoggedInDisposable = createObservable(for: sharedMainViewModel.isUserLoggedInNative)
            .observe(on: MainScheduler.instance)
            .subscribe(
                onNext: { isUserLoggedIn in
                    self.isUserLoggedIn = isUserLoggedIn?.boolValue
                }
            )
        
        isDarkModeOnDisposable = createObservable(for: sharedMainViewModel.isDarkModeOnNative)
            .observe(on: MainScheduler.instance)
            .subscribe(
                onNext: { isDarkModeOn in
                    self.isDarkModeOn = isDarkModeOn?.boolValue
                    self.ptTheme.applyDarkSystemTheme(isEnabled: self.isDarkModeOn ?? false)
                }
            )
    }
    
    func setIsDarkThemeOn(isDarkThemeOn: Bool) {
        sharedMainViewModel.setIsDarkThemeOn(isDarkThemeOn: isDarkThemeOn)
    }
    
    deinit {
        isUserLoggedInDisposable?.dispose()
        isUserLoggedInDisposable = nil
        
        isDarkModeOnDisposable?.dispose()
        isDarkModeOnDisposable = nil
    }
}
