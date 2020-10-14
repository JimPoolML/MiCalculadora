package appjpm4everyone.micalculadora.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import appjpm4everyone.micalculadora.utils.ScopedViewModel
import appjpm4everyone.usecases.UseCasesValidateData

class MainViewModel(private val useCasesValidateData: UseCasesValidateData) : ScopedViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        object ShowErrorCurrentPss : UiModel()
        object ShowErrorNewPss : UiModel()
        object NavigationChangePass : UiModel()
        class setNumberValue(val value: String) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val modelMain: LiveData<UiModel> get() = _model

    private var number1: String = "0"
    private var number2: String = "0"
    private var operator1: String = ""
    private var operator2: String = ""
    private var newValue: String = ""
    private var flagText = false

    private var flagOperator = false

    init {
        onTest()
    }

    private fun onTest() {
        _model.value = UiModel.NavigationChangePass
    }

    fun onButtonValue(position: Int, answer: String) {
        var value = ""
        when (position) {
            //Vertical numbers
            5, 6, 7, 10, 11, 12, 15, 16, 17, 20 ->
                value = useCasesValidateData.setDecimalValues(position)
            //Other values
            2, 21, 22 -> value = useCasesValidateData.setOtherlValues(position)
            //Arithmetic
            4, 8, 9, 13, 14, 18, 19, 24 -> value = useCasesValidateData.setArithmetic(position)
            else ->
                value = ""
        }
        verifyNumAnswer(value, answer)
        /*when(number) {
            0 -> println("Invalid number")
            1, 2 -> println("Number too low")
            3 -> println("Number correct")
            4 -> println("Number too high, but acceptable")
            else -> println("Number too high")
        }*/
    }

    private fun verifyNumAnswer(value: String, answer: String) {

        if (value == "C") {
            clearValues()
            _model.value = UiModel.setNumberValue(
                number1
            )
        } else if (valueOperator(value)) {
            validateOperator(value, answer)
        } else if (value == "+/-") {
            changeSign(answer)
        } else {
                if (operator1.isEmpty()) {
                    number1 = useCasesValidateData.validateNumChar(value, answer)
                    _model.value = UiModel.setNumberValue(
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
                    _model.value = UiModel.setNumberValue(
                        number2
                    )
                }
            }
    }

    private fun changeSign(answer: String) {
        if(operator1.isEmpty()){
            number1 = newSign(answer)
            _model.value = UiModel.setNumberValue(
                number1
            )
        }else{
            newValue = newSign(answer)
            _model.value = UiModel.setNumberValue(
                newValue
            )
        }
    }

    private fun newSign(answer: String): String{
        return if(answer=="ERROR"){
            "0"
        }else if(answer.startsWith("-")){
            answer.split("-")[1]
        }else{
            "-$answer"
        }
    }

    private fun valueOperator(value: String): Boolean {
        return value == "+" || value == "-" || value == "*" || value == "/" || value == "POT"
                || value == "ROOT" || value == "%" || value == "="
    }

    private fun validateOperator(value: String, answer: String) {
        //If the user take a bad values
        if (answer == "ERROR") {
            clearValues()
        } else {
            //First assignation
            if (operator1.isEmpty()) {
                operator1 = value
                /*when (value) {
                    "+" -> operator1 = "+"
                    "-" -> operator1 = "-"
                    "*" -> operator1 = "*"
                    "/" -> operator1 = "/"
                    "POT" -> operator1 = "POT"
                }*/
            } else {
                operator2 = value
                /*when (value) {
                    "+" -> operator2 = "+"
                    "-" -> operator2 = "-"
                    "*" -> operator2 = "*"
                    "/" -> operator2 = "/"
                    "=" -> operator2 = "="
                    "POT" -> operator1 = "POT"
                }*/
            }
            //Single operators
            if (singleOperators(operator1)) {
                flagText = false
                arithmeticTask()
            } else
            //Re assignation of values
                if (operator2.isNotEmpty()) {
                    flagText = false
                    arithmeticTask()
                    if (operator1.isNotEmpty() && operator2.isNotEmpty() && operator2 != "=") {
                        //save the las operation
                        operator1 = operator2
                    }
                }
        }
    }

    private fun singleOperators(operator1: String): Boolean {
        return operator1 == "POT" || operator1 == "ROOT" || operator1 == "%"
    }

    private fun arithmeticTask() {

        when (operator1) {
            "+" -> addValues()
            "-" -> subtractValues()
            "*" -> multiplyValues()
            "/" -> divideValues()
            "POT" -> x2()
            "ROOT" -> sqrt()
            "%" -> percentage()
            /*"/" -> operator2 = "/"
            "=" -> operator1 = "="*/
        }
        if (newValue.endsWith(".0")) {
            newValue = newValue.split(".")[0]
        }
        _model.value = UiModel.setNumberValue(
            newValue
        )
        //Clean data
        if (newValue == "ERROR") {
            newValue = ""
        }
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
            (number1.toDouble()/100).toString()
        } else {
            (newValue.toDouble()/100).toString()
        }
        avoidErrors()
    }

    private fun avoidErrors() {
        if (newValue == "Infinity" || newValue=="NaN") {
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
    }

}