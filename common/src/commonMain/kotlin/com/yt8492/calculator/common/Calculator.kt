package com.yt8492.calculator.common

sealed class CalculateResult {
    sealed class Success<T : Number> : CalculateResult() {
        abstract val value: T

        data class Integer(override val value: Long) : Success<Long>()
        data class Real(override val value: Double) : Success<Double>()
    }
    sealed class Failure : CalculateResult() {
        object ParseError : Failure()
        object ArithmeticError : Failure()
    }
}

private data class ShuntingYard(
    val operatorStack: List<CalculateToken>,
    val outputQueue: List<CalculateToken>
)

object Calculator {
    fun calculate(
        tokens: List<CalculateToken>
    ): CalculateResult {
        val shuntingYard = tokens.fold(ShuntingYard(listOf(), listOf())) { acc, token ->
            when (token) {
                is CalculateToken.Number -> {
                    acc.copy(outputQueue = acc.outputQueue + token)
                }
                is CalculateToken.Operator -> {
                    acc.operatorStack.takeWhile { it is CalculateToken.Operator && token <= it }
                        .fold(acc) { a, op ->
                            ShuntingYard(
                                a.operatorStack.drop(1),
                                a.outputQueue + op
                            )
                        }
                        .let {
                            it.copy(operatorStack = listOf(token) + it.operatorStack)
                        }
                }
                is CalculateToken.Bracket.Left -> {
                    acc.copy(operatorStack = listOf(token) + acc.operatorStack)
                }
                is CalculateToken.Bracket.Right -> {
                    if (!acc.operatorStack.any { it is CalculateToken.Bracket.Left }) {
                        return CalculateResult.Failure.ParseError
                    }
                    acc.operatorStack
                        .takeWhile { it !is CalculateToken.Bracket.Left }
                        .fold(acc) { a, op ->
                            ShuntingYard(
                                a.operatorStack.drop(1),
                                a.outputQueue + op
                            )
                        }.let {
                            it.copy(operatorStack = it.operatorStack.drop(1))
                        }
                }
            }
        }
        val tokens = shuntingYard.outputQueue + shuntingYard.operatorStack
        return calculateReversePolish(tokens)
    }

    private fun calculateReversePolish(tokens: List<CalculateToken>): CalculateResult {
        try {
            val result = tokens.fold(listOf<CalculateResult.Success<out Number>>()) { acc, token ->
                when (token) {
                    is CalculateToken.Number -> {
                        listOf(token.toCalculateResult()) + acc
                    }
                    is CalculateToken.Operator -> {
                        val b = acc[0].value
                        val a = acc[1].value
                        val stack = acc.drop(2)
                        if (a is Long && b is Long) {
                            when (token) {
                                is CalculateToken.Operator.Plus -> {
                                    listOf(CalculateResult.Success.Integer(a + b)) + stack
                                }
                                is CalculateToken.Operator.Minus -> {
                                    listOf(CalculateResult.Success.Integer(a - b)) + stack
                                }
                                is CalculateToken.Operator.Times -> {
                                    listOf(CalculateResult.Success.Integer(a * b)) + stack
                                }
                                is CalculateToken.Operator.Divide -> {
                                    if (a % b == 0L) {
                                        listOf(CalculateResult.Success.Integer(a / b)) + stack
                                    } else {
                                        listOf(CalculateResult.Success.Real(a.toDouble() / b)) + stack
                                    }
                                }
                            }
                        } else {
                            when (token) {
                                is CalculateToken.Operator.Plus -> {
                                    listOf(CalculateResult.Success.Real(a.toDouble() + b.toDouble())) + stack
                                }
                                is CalculateToken.Operator.Minus -> {
                                    listOf(CalculateResult.Success.Real(a.toDouble() - b.toDouble())) + stack
                                }
                                is CalculateToken.Operator.Times -> {
                                    listOf(CalculateResult.Success.Real(a.toDouble() * b.toDouble())) + stack
                                }
                                is CalculateToken.Operator.Divide -> {
                                    listOf(CalculateResult.Success.Real(a.toDouble() / b.toDouble())) + stack
                                }
                            }
                        }
                    }
                    is CalculateToken.Bracket -> {
                        return CalculateResult.Failure.ParseError
                    }
                }
            }.firstOrNull() ?: run {
                return CalculateResult.Failure.ParseError
            }
            return result
        } catch (e: ArithmeticException) {
            return CalculateResult.Failure.ArithmeticError
        } catch (e: IndexOutOfBoundsException) {
            return CalculateResult.Failure.ParseError
        }
    }
}
