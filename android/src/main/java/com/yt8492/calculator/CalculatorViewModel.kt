package com.yt8492.calculator

import androidx.lifecycle.*
import com.yt8492.calculator.common.CalculateResult
import com.yt8492.calculator.common.CalculateTokenParser
import com.yt8492.calculator.common.Calculator
import com.yt8492.calculator.common.ParseResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {
    val expressionLiveData = MutableLiveData<String>()

    val calculateResultLiveData = expressionLiveData.asFlow()
        .map { CalculateTokenParser.parse(it) }
        .filterIsInstance<ParseResult.Success>()
        .map { Calculator.calculate(it.tokens) }
        .filterIsInstance<CalculateResult.Success<out Number>>()
        .map { it.value.toString() }
        .asLiveData()

    fun setResultToInput(): Job = viewModelScope.launch {
        expressionLiveData.value = calculateResultLiveData.value
    }
}