package appjpm4everyone.micalculadora.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import appjpm4everyone.micalculadora.utils.ScopedViewModel
import appjpm4everyone.usecases.UseCasesValidateData

class MainViewModel(private  val useCasesValidateData: UseCasesValidateData) : ScopedViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        object ShowErrorCurrentPss: UiModel()
        object ShowErrorNewPss: UiModel()
        object NavigationChangePass : UiModel()
        class setNumberValue(val value: String): UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val modelMain: LiveData<UiModel> get() = _model

    private var number1: String = "0"
    private var number2: String = "0"
    private var operator1: String = ""
    private var operator2: String = ""
    private var newValue: String = "0"
    private var flagText = false

    private var flagOperator = false

    init {
        onTest()
    }

    private fun onTest() {
        _model.value = UiModel.NavigationChangePass
    }

    fun onButtonValue(position: Int, answer: String){
        var value = ""
        when(position){
            //Vertical numbers
            5, 6, 7, 10, 11, 12, 15, 16, 17, 20 ->
                value = useCasesValidateData.setDecimalValues(position)
            //Other values
            2, 21 -> value = useCasesValidateData.setOtherlValues(position)
            //Arithmetic
            4, 9, 14, 19, 24 -> value = useCasesValidateData.setArithmetic(position)
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

        if(value=="C"){
            number1 = "0"
            number2 = "0"
            newValue = "0"
            operator1 = ""
            operator2 = ""
            flagText=false
            _model.value = UiModel.setNumberValue(
                number1
            )
        }else if(valueOperator(value)){
            validateOperator(value, answer)

        }else{
            if (operator1.isEmpty()) {
                number1 = useCasesValidateData.validateNumChar(value, answer)
                _model.value = UiModel.setNumberValue(
                    number1
                )
            } else {
                //To avoid override data
                if(!flagText){
                    flagText=true
                    number2 = useCasesValidateData.validateNumChar(value, "")
                }else{
                    number2 = useCasesValidateData.validateNumChar(value, answer)
                }
                _model.value = UiModel.setNumberValue(
                    number2
                )
            }
        }
    }

    private fun valueOperator(value: String): Boolean {
        return value=="+" || value=="-"  || value=="*" || value=="/" || value=="="
    }

    private fun validateOperator(value: String, answer: String) {
        //First assignation
        if(operator1.isEmpty()) {
            when (value) {
                "+" -> operator1 = "+"
                "-" -> operator1 = "-"
                "*" -> operator1 = "*"
                "/" -> operator1 = "/"
            }
        }else{
            when (value) {
                "+" -> operator2 = "+"
                "-" -> operator2 = "-"
                "*" -> operator2 = "*"
                "/" -> operator2 = "/"
                "=" -> operator2 = "="
            }
        }
        //Re assignation of values
        if(operator2.isNotEmpty()){
            flagText=false
            arithmeticTask()
            if(operator1.isNotEmpty()&&operator2.isNotEmpty()&&operator2!="="){
                //save the las operation
                operator1 = operator2
            }
        }
    }

    private fun arithmeticTask() {

        when (operator1) {
            "+" -> addValues()
            "-" -> subtractValues()
            /*"*" -> operator2 = "*"
            "/" -> operator2 = "/"
            "=" -> operator1 = "="*/
        }
        if(newValue.endsWith(".0")){
            newValue = newValue.split(".")[0]
        }
        _model.value = UiModel.setNumberValue(
            newValue
        )
        /*if(operator1=="+"){

        }else{}*/
    }

    private fun addValues() {
        newValue = (number1.toDouble() + number2.toDouble()).toString()
        number1 = number2
        number2 = newValue

    }

    private fun subtractValues() {
        newValue = (number1.toDouble() - number2.toDouble()).toString()
        number1 = number2
        number2 = newValue

    }

}