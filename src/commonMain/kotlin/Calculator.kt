sealed class CalculateToken {
    data class Num(val n: Double) : CalculateToken()
    sealed class Operator : CalculateToken(), Comparable<Operator> {
        object Plus : Operator()
        object Minus : Operator()
        object Times : Operator()
        object Divide : Operator()

        override fun compareTo(other: Operator): Int {
            return when (this) {
                is Plus, is Minus -> {
                    if (other == Plus || other == Minus) {
                        0
                    } else {
                        -1
                    }
                }
                is Times, is Divide -> {
                    if (other == Times || other == Divide) {
                        0
                    } else {
                        1
                    }
                }
            }
        }
    }

    sealed class Bracket : CalculateToken() {
        object Left : Bracket()
        object Right : Bracket()
    }
}

sealed class CalculateResult {
    data class Success(val n: Double) : CalculateResult()
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
                is CalculateToken.Num -> {
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
                            }
                }
            }
        }
        val tokens = shuntingYard.outputQueue + shuntingYard.operatorStack
        return calculateReversePolish(tokens)
    }

    private fun calculateReversePolish(tokens: List<CalculateToken>): CalculateResult {
        try {
            var stack = listOf<Double>()
            tokens.forEach {
                when (it) {
                    is CalculateToken.Num -> {
                        stack = listOf(it.n) + stack
                    }
                    is CalculateToken.Operator -> {
                        val a = stack[0]
                        val b = stack[1]
                        stack = stack.drop(2)
                        when (it) {
                            is CalculateToken.Operator.Plus -> {
                                stack = listOf(a + b) + stack
                            }
                            is CalculateToken.Operator.Minus -> {
                                stack = listOf(a - b) + stack
                            }
                            is CalculateToken.Operator.Times -> {
                                stack = listOf(a * b) + stack
                            }
                            is CalculateToken.Operator.Divide -> {
                                stack = listOf(a / b) + stack
                            }
                        }
                    }
                    is CalculateToken.Bracket -> {
                        return CalculateResult.Failure.ParseError
                    }
                }
            }
            return CalculateResult.Success(stack[0])
        } catch (e: ArithmeticException) {
            return CalculateResult.Failure.ArithmeticError
        } catch (e: IndexOutOfBoundsException) {
            return CalculateResult.Failure.ParseError
        }
    }
}
