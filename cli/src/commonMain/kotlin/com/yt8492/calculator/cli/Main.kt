package com.yt8492.calculator.cli

import com.yt8492.calculator.common.CalculateResult
import com.yt8492.calculator.common.CalculateTokenParser
import com.yt8492.calculator.common.Calculator
import com.yt8492.calculator.common.CalculateTokenParseResult

fun main(args: Array<String>) {
    var previous = ""
    while (true) {
        val expression = previous + readLine()!!
        val tokenParseResult = CalculateTokenParser.parse(expression)
        if (tokenParseResult !is CalculateTokenParseResult.Success) {
            println("Failed to parse input expression.")
            return
        }
        when (val calculateResult = Calculator.calculate(tokenParseResult.tokens)) {
            is CalculateResult.Success<out Number> -> {
                print(calculateResult.value)
                previous = calculateResult.value.toString()
            }
            is CalculateResult.Failure.ParseError -> {
                println("Failed to parse input expression.")
                return
            }
            is CalculateResult.Failure.ArithmeticError -> {
                println("Arithmetic error occurred.")
                return
            }
        }
    }
}
