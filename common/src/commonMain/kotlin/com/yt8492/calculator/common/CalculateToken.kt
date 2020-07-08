package com.yt8492.calculator.common

sealed class CalculateToken {
    sealed class Number : CalculateToken() {
        data class Integer(val value: Long) : Number()
        data class Real(val value: Double) : Number()

        fun toCalculateResult(): CalculateResult.Success<out kotlin.Number> {
            return when (this) {
                is Integer -> CalculateResult.Success.Integer(this.value)
                is Real -> CalculateResult.Success.Real(this.value)
            }
        }
    }
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
