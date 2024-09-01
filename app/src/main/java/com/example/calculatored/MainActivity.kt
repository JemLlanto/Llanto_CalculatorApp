package com.example.calculatored

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sequenceView: TextView
    private lateinit var display: TextView
    private var operator: String = ""
    private var firstValue: String = ""
    private var isOperatorSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.textView)
        sequenceView = findViewById(R.id.sequenceView)

        val buttons = listOf(
            findViewById<Button>(R.id.AC),
            findViewById<Button>(R.id.plusMinus),
            findViewById<Button>(R.id.modulo),
            findViewById<Button>(R.id.divide),
            findViewById<Button>(R.id.seven),
            findViewById<Button>(R.id.eight),
            findViewById<Button>(R.id.nine),
            findViewById<Button>(R.id.times),
            findViewById<Button>(R.id.four),
            findViewById<Button>(R.id.five),
            findViewById<Button>(R.id.six),
            findViewById<Button>(R.id.minus),
            findViewById<Button>(R.id.one),
            findViewById<Button>(R.id.two),
            findViewById<Button>(R.id.three),
            findViewById<Button>(R.id.plus),
            findViewById<Button>(R.id.zero),
            findViewById<Button>(R.id.decimal),
            findViewById<Button>(R.id.equal)
        )

        buttons.forEach { button ->
            button.setOnClickListener { onButtonClick(button) }
        }
    }

    private fun onButtonClick(button: Button) {
        val buttonText = button.text.toString()

        when (buttonText) {
            "AC" -> {
                clear()
                sequenceView.text = "" // Clear the sequence as well
            }
            "±", "%", "÷", "x", "-", "+", "." -> {
                handleOperator(buttonText)
            }
            "=" -> calculateResult()
            else -> {
                updateSequence(buttonText)
                appendNumber(buttonText)
                enableOperators()
            }
        }
    }

    private fun updateSequence(input: String) {
        sequenceView.append(input)
    }

    private fun clear() {
        display.text = "0"
        operator = ""
        firstValue = ""
        isOperatorSelected = false
        enableOperators()
    }

    private fun handleOperator(op: String) {
        if (isOperatorSelected) {
            // Update the operator in the sequence view and operator variable
            sequenceView.text = sequenceView.text.toString().dropLast(1) + op
            operator = op
        } else {
            if (firstValue.isEmpty()) {
                firstValue = display.text.toString()
            } else {
                // Calculate the current result before updating the operator
                calculateResult()
                firstValue = display.text.toString()
            }
            operator = op
            isOperatorSelected = true
            updateSequence(op)
            disableOperators()
        }
    }


    private fun calculateResult() {
        if (firstValue.isNotEmpty() && operator.isNotEmpty()) {
            val secondValue = display.text.toString()

            val result = when (operator) {
                "+" -> firstValue.toDouble() + secondValue.toDouble()
                "-" -> firstValue.toDouble() - secondValue.toDouble()
                "x" -> firstValue.toDouble() * secondValue.toDouble()
                "÷" -> firstValue.toDouble() / secondValue.toDouble()
                else -> 0.0
            }

            // Format result: remove decimal point if there's no decimal part
            display.text = if (result % 1.0 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }

            operator = ""
            isOperatorSelected = false
            enableOperators()
        }
    }

    private fun appendNumber(number: String) {
        if (display.text == "0" || isOperatorSelected) {
            display.text = number
            isOperatorSelected = false
        } else {
            display.text = display.text.toString().plus(number)
        }
    }

    private fun enableOperators() {
        findViewById<Button>(R.id.plusMinus).isEnabled = true
        findViewById<Button>(R.id.modulo).isEnabled = true
        findViewById<Button>(R.id.divide).isEnabled = true
        findViewById<Button>(R.id.times).isEnabled = true
        findViewById<Button>(R.id.minus).isEnabled = true
        findViewById<Button>(R.id.plus).isEnabled = true
        findViewById<Button>(R.id.equal).isEnabled = true
    }

    private fun disableOperators() {
        findViewById<Button>(R.id.plusMinus).isEnabled = false
        findViewById<Button>(R.id.modulo).isEnabled = false
        findViewById<Button>(R.id.divide).isEnabled = false
        findViewById<Button>(R.id.times).isEnabled = false
        findViewById<Button>(R.id.minus).isEnabled = false
        findViewById<Button>(R.id.plus).isEnabled = false
        findViewById<Button>(R.id.equal).isEnabled = false
    }
}
