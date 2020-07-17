package com.yt8492.calculator.cli

import com.yt8492.calculator.common.CalculateResult
import com.yt8492.calculator.common.CalculateTokenParser
import com.yt8492.calculator.common.Calculator
import com.yt8492.calculator.common.CalculateTokenParseResult
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType

fun main(args: Array<String>) {
    val argParser = ArgParser("Calculator")
    val expression by argParser.argument(ArgType.String, description = "expression")
    argParser.parse(args)
    val tokenParseResult = CalculateTokenParser.parse(expression)
    if (tokenParseResult !is CalculateTokenParseResult.Success) {
        println("Failed to parse input expression.")
        return
    }
    when (val calculateResult = Calculator.calculate(tokenParseResult.tokens)) {
        is CalculateResult.Success<out Number> -> {
            println(calculateResult.value)
        }
        is CalculateResult.Failure.ParseError -> {
            println("Failed to parse input expression.")
        }
        is CalculateResult.Failure.ArithmeticError -> {
            println("Arithmetic error occurred.")
        }
    }
}