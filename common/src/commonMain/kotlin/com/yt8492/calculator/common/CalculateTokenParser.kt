package com.yt8492.calculator.common

import com.github.h0tk3y.betterParse.lexer.DefaultTokenizer
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import kotlin.native.concurrent.ThreadLocal

sealed class ParseResult {
    data class Success(val tokens: List<CalculateToken>) : ParseResult()
    object Failure : ParseResult()
}

@ThreadLocal
object CalculateTokenParser {
    private val whiteSpace = regexToken("""\s+""", ignore = true)
    private val integer = regexToken("""-?\d+""")
    private val real = regexToken("""-?\d+\.\d+""")
    private val plus = literalToken("+")
    private val minus = literalToken("-")
    private val times = regexToken("""[*×]""")
    private val divide = regexToken("""[/÷]""")
    private val leftBracket = literalToken("(")
    private val rightBracket = literalToken(")")
    private val tokenizer = DefaultTokenizer(listOf(
        whiteSpace,
        real,
        integer,
        plus,
        minus,
        times,
        divide,
        leftBracket,
        rightBracket
    ))

    fun parse(input: String): ParseResult {
        try {
            val tokens = tokenizer.tokenize(input)
                .filter { !it.type.ignored }
                .map {
                    when (it.type) {
                        integer -> CalculateToken.Number.Integer(it.text.toLong())
                        real -> CalculateToken.Number.Real(it.text.toDouble())
                        plus -> CalculateToken.Operator.Plus
                        minus -> CalculateToken.Operator.Minus
                        times -> CalculateToken.Operator.Times
                        divide -> CalculateToken.Operator.Divide
                        leftBracket -> CalculateToken.Bracket.Left
                        rightBracket -> CalculateToken.Bracket.Right
                        else -> throw IllegalArgumentException("token not match")
                    }
                }
                .fold(listOf<CalculateToken>()) { acc, token ->
                    if (token is CalculateToken.Number && acc.lastOrNull() is CalculateToken.Number) {
                        acc + CalculateToken.Operator.Plus + token
                    } else {
                        acc + token
                    }
                }
            return ParseResult.Success(tokens)
        } catch (e: IllegalArgumentException) {
            return ParseResult.Failure
        }
    }
}
