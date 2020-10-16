package appjpm4everyone.micalculadora.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import appjpm4everyone.micalculadora.utils.ScopedViewModel
import appjpm4everyone.usecases.UseCasesLandscapeValidateData
import appjpm4everyone.usecases.UseCasesValidateData
import kotlin.math.abs

class MainViewModel(
    private val useCasesValidateData: UseCasesValidateData,
    private val useCasesLandscapeValidateData: UseCasesLandscapeValidateData
) : ScopedViewModel() {

    sealed class UiModel {
        object SetInitValue : UiModel()
        class SetNumberValue(val value: String) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val modelMain: LiveData<UiModel> get() = _model
    val NUMBER_PI = kotlin.math.PI
    val delta = 0.0000000000000001


    private var landscape: Boolean = false
    private var number1: String = "0"
    private var number2: String = "0"
    private var operator1: String = ""
    private var operator2: String = ""
    private var newValue: String = ""
    private var flagText = false

    //To set M's buttons
    private var stateMemory = ""
    private var numberMemory: String = ""

    private var flagOperator: Boolean = false

    init {
        onTest()
    }

    private fun onTest() {
        _model.value = UiModel.SetInitValue
    }

    fun onButtonValue(position: Int, answer: String, landscape: Boolean) {
        //Constructor
        this.landscape = landscape
        var value = ""
        if (landscape) {
            value = when (position) {
                //Vertical numbers
                3, 4, 5, 11, 12, 13, 19, 20, 21, 27 ->
                    useCasesLandscapeValidateData.setDecimalValues(position)
                //Other values
                0, 7, 8, 15, 16, 23, 24, 26, 28 -> useCasesLandscapeValidateData.setOtherlValues(
                    position
                )
                //Arithmetic
                1, 2, 6, 9, 10, 14, 17, 18, 22, 25, 29, 30 -> useCasesLandscapeValidateData.setArithmetic(
                    position
                )
                else ->
                    ""

            }
        } else {
            when (position) {
                //Vertical numbers
                5, 6, 7, 10, 11, 12, 15, 16, 17, 20 ->
                    value = useCasesValidateData.setDecimalValues(position)
                //Other values
                0, 1, 2, 3, 21, 22 -> value = useCasesValidateData.setOtherlValues(position)
                //Arithmetic
                4, 8, 9, 13, 14, 18, 19, 24 -> value = useCasesValidateData.setArithmetic(position)
                else ->
                    value = ""
            }

        }
        verifyNumAnswer(value, answer)
    }

    private fun verifyNumAnswer(value: String, answer: String) {

        if (valueAnotherOperator(value)) {
            validateAnotherOperator(value, answer)
        } else if (valueOperator(value)) {
            setFlagOperator(value)
            validateOperator(value, answer)
        } else if (validateNumber(value)) {
            setNumberOperator(value)
            if (operator1.isEmpty()) {
                //number1 = useCasesValidateData.validateNumChar(value, answer)
                if (!flagText) {
                    flagText = true
                    number1 = useCasesValidateData.validateNumChar(value, "")
                } else {
                    number1 = useCasesValidateData.validateNumChar(value, answer)
                }
                _model.value = UiModel.SetNumberValue(
                    number1
                )
            } else {
                //To avoid override data
                if (!flagText) {
                    flagText = true
                    number2 = useCasesValidateData.validateNumChar(value, "")
                } else {
                    number2 = useCasesValidateData.validateNumChar(value, answer)
                }
                _model.value = UiModel.SetNumberValue(
                    number2
                )
            }
        }
    }

    private fun valueAnotherOperator(value: String): Boolean {
        return value == "MC" || value == "M+" || value == "MR" || value == "CE" ||
                value == "C" || value == "+/-" || value == "M-" || value == "PI"
    }

    private fun validateAnotherOperator(value: String, answer: String) {
        if (answer == "ERROR") {
            clearValues()
            _model.value = UiModel.SetNumberValue(
                "0"
            )
        } else {
            anotherTask(value, answer)
        }
    }

    private fun anotherTask(value: String, answer: String) {
        when (value) {
            "MC" -> clearMemory()
            "M+" -> setPositiveMemoryValues(answer)
            "MR" -> setMemoryValues(answer)
            "C" -> clearScreen()
            "CE" -> clearLastValue()
            "+/-" -> changeSign(answer)
            "M-" -> setNegativeMemoryValues(answer)
            "PI" -> assignPI()
        }
    }

    private fun assignPI() {
        if (operator1.isEmpty()) {
            number1 = NUMBER_PI.toString()
            _model.value = UiModel.SetNumberValue(
                number1
            )
        } else {
            newValue = NUMBER_PI.toString()
            _model.value = UiModel.SetNumberValue(
                newValue
            )
        }
    }

    private fun setNegativeMemoryValues(answer: String) {
        if (answer.isNotEmpty() && (answer != "ERROR" || answer != "NaN")) {
            if (stateMemory.isEmpty() && numberMemory.isEmpty()) {
                numberMemory = answer
                stateMemory = "M"
            } else if (stateMemory.isNotEmpty()) {
                stateMemory = "M-"
            }
        }
    }

    private fun clearMemory() {
        stateMemory = ""
        numberMemory = ""
    }

    private fun setPositiveMemoryValues(answer: String) {
        if (answer.isNotEmpty() && (answer != "ERROR" || answer != "NaN")) {
            if (stateMemory.isEmpty() && numberMemory.isEmpty()) {
                numberMemory = answer
                stateMemory = "M"
            } else if (stateMemory.isNotEmpty()) {
                stateMemory = "M+"
            }
        }
    }

    private fun setMemoryValues(answer: String) {
        if (answer.isNotEmpty() && (answer != "ERROR" || answer != "NaN")) {
            if (stateMemory == "M+" && numberMemory.isNotEmpty()) {
                //memory state is M+
                newValue = (numberMemory.toDouble() + answer.toDouble()).toString()
                numberMemory = newValue
                stateMemory = "M+"
                avoidErrors()
                stateMemory = "M+"
                if (newValue.endsWith(".0")) {
                    newValue = newValue.split(".")[0]
                }
                _model.value = UiModel.SetNumberValue(
                    newValue
                )
            } else if (stateMemory == "M-" && numberMemory.isNotEmpty()) {
                //memory state is M+
                newValue = (-numberMemory.toDouble() - answer.toDouble()).toString()
                numberMemory = newValue
                stateMemory = "M-"
                avoidErrors()
                stateMemory = "M-"
                if (newValue.endsWith(".0")) {
                    newValue = newValue.split(".")[0]
                }
                _model.value = UiModel.SetNumberValue(
                    newValue
                )
            }
        }
    }

    private fun clearScreen() {
        clearValues()
        _model.value = UiModel.SetNumberValue(
            number1
        )
    }

    private fun clearLastValue() {
        if (operator1 == "") {
            clearScreen()
        } else {
            number2 = "0"
            newValue = ""
            operator2 = ""
            flagText = false
            _model.value = UiModel.SetNumberValue(
                number2
            )
        }
    }

    private fun changeSign(answer: String) {
        if (operator1.isEmpty()) {
            number1 = newSign(answer)
            _model.value = UiModel.SetNumberValue(
                number1
            )
        } else {
            newValue = newSign(answer)
            _model.value = UiModel.SetNumberValue(
                newValue
            )
        }
    }


    private fun newSign(answer: String): String {
        return if (answer == "ERROR") {
            "0"
        } else if (answer.startsWith("-")) {
            answer.split("-")[1]
        } else {
            "-$answer"
        }
    }

    private fun valueOperator(value: String): Boolean {
        return value == "+" || value == "-" || value == "*" || value == "/" || value == "POT"
                || value == "ROOT" || value == "%" || value == "=" || value == "1/X"
                || value == "SIN" || value == "COS" || value == "TAN"
    }

    private fun setFlagOperator(value: String) {
        if (value == "+" || value == "-" || value == "*" || value == "/") {
            flagOperator = true
        } else if (value == "=") {
            flagOperator = false
        }
        flagText = false
    }

    private fun validateNumber(value: String): Boolean {
        return value == "0" || value == "1" || value == "2" || value == "3" || value == "4" ||
                value == "5" || value == "6" || value == "7" || value == "8" || value == "9" || value == "."
    }

    private fun setNumberOperator(value: String) {
        if (!flagOperator) {
            newValue = ""
            operator1 = ""
            operator2 = ""
        }


    }

    private fun validateOperator(value: String, answer: String) {
        //If the user take a bad values
        if (answer == "ERROR") {
            clearValues()
        } else {
            //First assignation
            if (operator1.isEmpty()) {
                operator1 = value
            } else {
                operator2 = value
            }
            //Single operators
            if (singleOperators(operator1) && operator2.isEmpty()) {
                flagText = false
                arithmeticTask(operator1)
            } else
            //Re assignation of values
                if (operator2.isNotEmpty()) {
                    //Avoid
                    //if(flagOperator) {
                    flagText = false
                    //Test if is a single operator
                    if (singleOperators(operator2)) {
                        arithmeticTask(operator2)
                    } else {
                        arithmeticTask(operator1)
                    }
                    if (operator1.isNotEmpty() && operator2.isNotEmpty() && operator2 != "=") {
                        //save the las operation
                        operator1 = operator2
                    }
                    //}
                }
        }
    }

    private fun singleOperators(operator1: String): Boolean {
        return operator1 == "POT" || operator1 == "ROOT" || operator1 == "%" || operator1 == "1/X"
                || operator1 == "SIN" || operator1 == "COS" || operator1 == "TAN"
    }

    private fun arithmeticTask(operator1: String) {

        when (operator1) {
            "+" -> addValues()
            "-" -> subtractValues()
            "*" -> multiplyValues()
            "/" -> divideValues()
            "POT" -> x2()
            "ROOT" -> sqrt()
            "%" -> percentage()
            "1/X" -> invertX()
            "SIN" -> sinX()
            "COS" -> cosX()
            "TAN" -> tanX()
        }
        if (newValue.endsWith(".0")) {
            newValue = newValue.split(".")[0]
        }
        _model.value = UiModel.SetNumberValue(
            newValue
        )
        //Clean data
        if (newValue == "ERROR") {
            newValue = ""
        }
    }

    private fun invertX() {
        newValue = if (newValue.isEmpty()) {
            (1 / number1.toDouble()).toString()
        } else {
            (1 / newValue.toDouble()).toString()
        }
        avoidErrors()
    }


    private fun addValues() {
        newValue = if (newValue.isEmpty()) {
            (number1.toDouble() + number2.toDouble()).toString()
        } else {
            (newValue.toDouble() + number2.toDouble()).toString()
        }
        avoidErrors()
    }

    private fun subtractValues() {
        newValue = if (newValue.isEmpty()) {
            (number1.toDouble() - number2.toDouble()).toString()
        } else {
            (newValue.toDouble() - number2.toDouble()).toString()
        }
        avoidErrors()
    }

    private fun multiplyValues() {
        newValue = if (newValue.isEmpty()) {
            (number1.toDouble() * number2.toDouble()).toString()
        } else {
            (newValue.toDouble() * number2.toDouble()).toString()
        }
        avoidErrors()
    }

    private fun divideValues() {
        newValue = if (newValue.isEmpty()) {
            (number1.toDouble() / number2.toDouble()).toString()
        } else {
            (newValue.toDouble() / number2.toDouble()).toString()
        }
        avoidErrors()
    }

    private fun x2() {
        newValue = if (newValue.isEmpty()) {
            (number1.toDouble() * number1.toDouble()).toString()
        } else {
            (newValue.toDouble() * newValue.toDouble()).toString()
        }
        avoidErrors()
    }

    private fun sqrt() {
        newValue = if (newValue.isEmpty()) {
            (kotlin.math.sqrt(number1.toDouble())).toString()
        } else {
            (kotlin.math.sqrt(newValue.toDouble())).toString()
        }
        avoidErrors()
    }

    private fun percentage() {
        newValue = if (newValue.isEmpty()) {
            (number1.toDouble() / 100).toString()
        } else {
            (newValue.toDouble() / 100).toString()
        }
        avoidErrors()
    }

    //Trigonometry
    private fun sinX() {
        newValue = if (newValue.isEmpty()) {
            number1 = changeRadians(number1)
            (kotlin.math.sin(number1.toDouble())).toString()
        } else {
            newValue = changeRadians(newValue)
            (kotlin.math.sin(newValue.toDouble())).toString()
        }
        avoidErrors()
    }

    private fun cosX() {
        newValue = if (newValue.isEmpty()) {
            number1 = changeRadians(number1)
            (kotlin.math.cos(number1.toDouble())).toString()
        } else {
            newValue = changeRadians(newValue)
            (kotlin.math.cos(newValue.toDouble())).toString()
        }
        avoidErrors()
    }

    private fun tanX() {
        newValue = if (newValue.isEmpty()) {
            if (abs(number1.toDouble() % 90) < delta) {
                "Infinity"
            } else {
                number1 = changeRadians(number1)
                (kotlin.math.tan(number1.toDouble())).toString()
            }
            //((kotlin.math.sin(number1.toDouble()))/(kotlin.math.cos(number1.toDouble()))).toString()
            //(kotlin.math.tan(number1.toDouble())).toString()
        } else {
            if (abs(newValue.toDouble() % 90) < delta) {
                "Infinity"
            } else {
                newValue = changeRadians(newValue)
                (kotlin.math.tan(newValue.toDouble())).toString()
            }
            //((kotlin.math.sin(newValue.toDouble()))/(kotlin.math.cos(newValue.toDouble()))).toString()
            //(kotlin.math.tan(newValue.toDouble())).toString()
        }
        avoidErrors()
    }

    private fun changeRadians(number: String): String {
        return (number.toDouble() * (NUMBER_PI / 180)).toString()
    }

    private fun avoidErrors() {
        if (newValue == "Infinity" || newValue == "NaN") {
            clearValues()
            newValue = "ERROR"
        } else {
            number1 = number2
            number2 = newValue
        }
    }

    private fun clearValues() {
        number1 = "0"
        number2 = "0"
        newValue = ""
        operator1 = ""
        operator2 = ""
        flagText = false
        flagOperator = false
    }

}