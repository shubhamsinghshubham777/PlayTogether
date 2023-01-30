//
//  Resources.swift
//  PlayTogether
//
//  Created by Shubham Singh on 29/01/23.
//  Copyright © 2023 PlayTogether. All rights reserved.
//

import Foundation
import SwiftUI

extension Color {
    static var Error: Color  {
        return Color("ColorError")
    }
    static var ErrorContainer: Color  {
        return Color("ColorErrorContainer")
    }
    static var OnError: Color  {
        return Color("ColorOnError")
    }
    static var OnErrorContainer: Color  {
        return Color("ColorOnErrorContainer")
    }
    static var OnPrimary: Color  {
        return Color("ColorOnPrimary")
    }
    static var OnPrimaryContainer: Color  {
        return Color("ColorOnPrimaryContainer")
    }
    static var OnSecondary: Color  {
        return Color("ColorOnSecondary")
    }
    static var OnSecondaryContainer: Color  {
        return Color("ColorOnSecondaryContainer")
    }
    static var OnSurface: Color  {
        return Color("ColorOnSurface")
    }
    static var OnSurfaceVariant: Color  {
        return Color("ColorOnSurfaceVariant")
    }
    static var Primary: Color  {
        return Color("ColorPrimary")
    }
    static var PrimaryContainer: Color  {
        return Color("ColorPrimaryContainer")
    }
    static var Secondary: Color  {
        return Color("ColorSecondary")
    }
    static var SecondaryContainer: Color  {
        return Color("ColorSecondaryContainer")
    }
    static var Surface: Color  {
        return Color("ColorSurface")
    }
    static var SurfaceVariant: Color  {
        return Color("ColorSurfaceVariant")
    }
}

private func localizedString(forKey key: String) -> String {
    var result = Bundle.main.localizedString(forKey: key, value: nil, table: nil)
    
    if result == key {
        result = Bundle.main.localizedString(forKey: key, value: nil, table: "Default")
    }
    
    return result
}

class Strings {
    private init() {}
    static let instance = Strings()
    
    let AuthScreenTitle = localizedString(forKey: "AuthScreenTitle")
    let AuthScreenSubtitle = localizedString(forKey: "AuthScreenSubtitle")
    let AuthScreenRegisterMessage = localizedString(forKey: "AuthScreenRegisterMessage")
    let AuthScreenLoginMessage = localizedString(forKey: "AuthScreenLoginMessage")
    let AuthScreenEmailLabel = localizedString(forKey: "AuthScreenEmailLabel")
    let AuthScreenPasswordLabel = localizedString(forKey: "AuthScreenPasswordLabel")
    let AuthScreenRepeatPasswordLabel = localizedString(forKey: "AuthScreenRepeatPasswordLabel")
    let AuthScreenLoginButtonLabel = localizedString(forKey: "AuthScreenLoginButtonLabel")
    let AuthScreenRegisterButtonLabel = localizedString(forKey: "AuthScreenRegisterButtonLabel")
}

enum Images : String {
    case AppLogo
}

class PTTheme {
    private init() {}
    static let instance = PTTheme()
    
    func applyDarkSystemTheme(isEnabled: Bool) {
        (UIApplication.shared.connectedScenes.first as? UIWindowScene)?
            .windows.first?.overrideUserInterfaceStyle = isEnabled ? .dark : .light
    }
}
