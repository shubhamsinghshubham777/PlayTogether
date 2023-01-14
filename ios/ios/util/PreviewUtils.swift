//
//  PreviewUtils.swift
//  ios
//
//  Created by Shubham Singh on 01/01/23.
//  Copyright © 2023 playtogether. All rights reserved.
//

import Foundation
import SwiftUI

extension View {
    class func injected() {
#if DEBUG
    @objc class func injected() {
        let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene
        windowScene?.windows.first?.rootViewController =
                UIHostingController(rootView: previews)
    }
#endif
    }
}
