import com.yt8492.calculator.common.CalculateResult
import com.yt8492.calculator.common.CalculateTokenParser
import com.yt8492.calculator.common.Calculator
import com.yt8492.calculator.common.ParseResult
import kotlinx.css.*
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import styled.css
import styled.styledButton
import styled.styledDiv
import styled.styledInput

private val calculatorComponent = functionalComponent<RProps> {
    val (expression, setExpression) = useState("")

    styledDiv {
        styledInput {
            attrs.value = expression

            attrs.onChangeFunction = {
                val target = it.target as HTMLInputElement
                setExpression(target.value)
            }

            css {
                boxSizing = BoxSizing.borderBox
                width = 100.pct
                padding(LinearDimension("0"), 1.rem)
                textAlign = TextAlign.right
                fontSize = 48.px
                flexGrow = 1.0
            }
        }
        styledDiv {
            otherButton("C") { setExpression("") }
            otherButton("(") { setExpression("$expression(") }
            otherButton(")") { setExpression("$expression)") }
            operatorButton("÷") { setExpression("$expression÷") }
            numButton("7") { setExpression("${expression}7") }
            numButton("8") { setExpression("${expression}8") }
            numButton("9") { setExpression("${expression}9") }
            operatorButton("×") { setExpression("$expression×") }
            numButton("4") { setExpression("${expression}4") }
            numButton("5") { setExpression("${expression}5") }
            numButton("6") { setExpression("${expression}6") }
            operatorButton("-") { setExpression("$expression-") }
            numButton("1") { setExpression("${expression}1") }
            numButton("2") { setExpression("${expression}2") }
            numButton("3") { setExpression("${expression}3") }
            operatorButton("+") { setExpression("$expression+") }
            numButton("00") { setExpression("${expression}00") }
            numButton("0") { setExpression("${expression}0") }
            numButton(".") { setExpression("${expression}.") }
            operatorButton("=") {
                val parseResult = CalculateTokenParser.parse(expression)
                if (parseResult is ParseResult.Success) {
                    val calculateResult = Calculator.calculate(parseResult.tokens)
                    if (calculateResult is CalculateResult.Success<out Number>) {
                        setExpression(calculateResult.value.toString())
                    }
                }
            }

            css {
                boxSizing = BoxSizing.borderBox
                width = 100.pct
                height = 100.pct
                display = Display.grid
                gridTemplateRows = GridTemplateRows.repeat("5, 1fr")
                gridTemplateColumns = GridTemplateColumns.repeat("4, 1fr")
                gap = Gap("1px")
                flexGrow = 5.0
            }
        }

        css {
            width = 320.px
            height = 480.px
            display = Display.flex
            flexDirection = FlexDirection.column
        }
    }
}

fun RBuilder.calculatorComponent() {
    child(calculatorComponent)
}

private fun RBuilder.numButton(value: String, onClick: (Event) -> Unit) {
    baseButton(value, Color("#CFCFCF"), Color.black, onClick)
}

private fun RBuilder.operatorButton(value: String, onClick: (Event) -> Unit) {
    baseButton(value, Color("#189861"), Color.white, onClick)
}

private fun RBuilder.otherButton(value: String, onClick: (Event) -> Unit) {
    baseButton(value, Color("#9F9F9F"), Color.white, onClick)
}

private fun RBuilder.baseButton(value: String, background: Color, textColor: Color, onClick: (Event) -> Unit) {
    styledButton {
        + value
        attrs.onClickFunction = onClick

        css {
            boxSizing = BoxSizing.borderBox
            padding = "0"
            backgroundColor = background
            color = textColor
            fontSize = 24.px
            textAlign = TextAlign.center
        }
    }
}
