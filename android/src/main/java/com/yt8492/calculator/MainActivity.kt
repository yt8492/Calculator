package com.yt8492.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.yt8492.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        val viewModel: CalculatorViewModel by viewModels()
        binding.viewModel = viewModel
        val inputConnection = binding.expressionEditText.onCreateInputConnection(EditorInfo())
        binding.expressionEditText.inputType = InputType.TYPE_NULL
        binding.keyTable
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterNot { it.id == R.id.calculateResultTextView }
            .forEach {
                when (it.id) {
                    R.id.keyBack -> {
                        it.setOnClickListener {
                            inputConnection.deleteSurroundingText(1, 0)
                        }
                    }
                    R.id.keyClear -> {
                        it.setOnClickListener {
                            binding.expressionEditText.setText("")
                            binding.calculateResultTextView.text = ""
                        }
                    }
                    R.id.keyEqual -> {
                        it.setOnClickListener {
                            viewModel.setResultToInput()
                        }
                    }
                    else -> {
                        it.setOnClickListener {
                            val textView = it as? TextView ?: return@setOnClickListener
                            inputConnection.commitText(textView.text, textView.text.length)
                        }
                    }
                }
            }
    }
}
