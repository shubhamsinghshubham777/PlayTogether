//
//  PTText.swift
//  PlayTogether
//
//  Created by Shubham Singh on 29/01/23.
//  Copyright © 2023 PlayTogether. All rights reserved.
//

import SwiftUI

struct PTText: View {
    let text: String
    
    var body: some View {
        Text(text)
            .foregroundColor(Color.OnSurface)
    }
}
