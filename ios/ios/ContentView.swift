//
//  ContentView.swift
//  ios
//
//  Created by megane on 2020/07/16.
//  Copyright © 2020 yt8492. All rights reserved.
//

import SwiftUI
import common

struct ContentView: View {
    
    @State
    var expression: String = "0"
    
    func addToken(token: String) {
        if expression == "0" && token != "." {
            expression = token
        } else {
            expression += token
        }
    }
    
    func clear() {
        expression = "0"
    }
    
    var body: some View {
        VStack {
            Text(expression)
                .font(.system(size: 48))
                .lineLimit(1)
                .frame(idealWidth: .infinity, alignment: .center)
                .multilineTextAlignment(.trailing)
            Spacer()
            HStack {
                Spacer()
                VStack {
                    OtherButton(labelText: "C") {
                        self.clear()
                    }
                    Spacer()
                    NumButton(labelText: "7") {
                        self.addToken(token: "7")
                    }
                    Spacer()
                    NumButton(labelText: "4") {
                        self.addToken(token: "4")
                    }
                    Spacer()
                    NumButton(labelText: "1") {
                        self.addToken(token: "1")
                    }
                    Spacer()
                    NumButton(labelText: "0") {
                        self.addToken(token: "0")
                    }
                }
                .frame(maxHeight: .infinity)
                Spacer()
                VStack {
                    OtherButton(labelText: "(") {
                        self.addToken(token: "(")
                    }
                    Spacer()
                    NumButton(labelText: "8") {
                        self.addToken(token: "8")
                    }
                    Spacer()
                    NumButton(labelText: "5") {
                        self.addToken(token: "5")
                    }
                    Spacer()
                    NumButton(labelText: "2") {
                        self.addToken(token: "2")
                    }
                    Spacer()
                    NumButton(labelText: "00") {
                        self.addToken(token: "00")
                    }
                }
                .frame(maxHeight: .infinity)
                Spacer()
                VStack {
                    OtherButton(labelText: ")") {
                        self.addToken(token: ")")
                    }
                    Spacer()
                    NumButton(labelText: "9") {
                        self.addToken(token: "9")
                    }
                    Spacer()
                    NumButton(labelText: "6") {
                        self.addToken(token: "6")
                    }
                    Spacer()
                    NumButton(labelText: "3") {
                        self.addToken(token: "3")
                    }
                    Spacer()
                    NumButton(labelText: ".") {
                        self.addToken(token: ".")
                    }
                }
                .frame(maxHeight: .infinity)
                Spacer()
                VStack {
                    OperatorButton(labelText: "÷") {
                        self.addToken(token: "÷")
                    }
                    Spacer()
                    OperatorButton(labelText: "×") {
                        self.addToken(token: "×")
                    }
                    Spacer()
                    OperatorButton(labelText: "-") {
                        self.addToken(token: "-")
                    }
                    Spacer()
                    OperatorButton(labelText: "+") {
                        self.addToken(token: "+")
                    }
                    Spacer()
                    OperatorButton(labelText: "=") {
                        guard let parseResult = CalculateTokenParser().parse(input: self.expression) as? CalculateTokenParseResult.Success else {
                            return
                        }
                        let calculateResult = Calculator().calculate(tokens: parseResult.tokens)
                        if let integer = calculateResult as? CalculateResultSuccessInteger {
                            self.expression = integer.value.stringValue
                        } else if let real = calculateResult as? CalculateResultSuccessReal {
                            self.expression = real.value.stringValue
                        }
                    }
                }
                .frame(maxHeight: .infinity)
                Spacer()
            }
            .frame(maxWidth: .infinity)
            .padding(.all)
            Spacer()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
