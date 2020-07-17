//
//  Buttons.swift
//  ios
//
//  Created by megane on 2020/07/17.
//  Copyright © 2020 yt8492. All rights reserved.
//

import Foundation
import SwiftUI

struct NumButton: View {
    
    let labelText: String
    let clickListener: () -> Void
    
    var body: some View {
        Button(action: clickListener) {
            Text(labelText)
                .font(.system(size: 48))
        }
        .foregroundColor(.black)
    }
}

struct OperatorButton: View {

    let labelText: String
    let clickListener: () -> Void
    
    var body: some View {
        Button(action: clickListener) {
            Text(labelText)
                .font(.system(size: 48))
        }
        .foregroundColor(.green)
    }
}

struct OtherButton: View {

    let labelText: String
    let clickListener: () -> Void
    
    var body: some View {
        Button(action: clickListener) {
            Text(labelText)
                .font(.system(size: 48))
        }
        .foregroundColor(.gray)
    }
}
