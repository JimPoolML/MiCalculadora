package appjpm4everyone.usecases

import java.util.regex.Pattern

class UseCasesValidateData {

    /*fun validateEmpty(dataString: String): Boolean{
        return dataString.isEmpty()
    }

    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

    fun isNumPhoneLengthValidate(phone: String): Boolean {
        return phone.length == 10
    }*/

    fun setDecimalValues(position: Int ) : String{
        var value = ""
        when (position){
            5 -> value = "9"
            6 -> value = "8"
            7 -> value = "7"
            10 -> value = "6"
            11 -> value = "5"
            12 -> value = "4"
            15 -> value = "3"
            16 -> value = "2"
            17 -> value = "1"
            20 -> value = "0"
            21 -> value = "."
        }
        return value
    }

    fun setOtherlValues(position: Int ) : String{
        var value = ""
        when (position){
            2 -> value = "C"
            21 -> value = "."
        }
        return value
    }

    fun setArithmetic(position: Int ) : String{
        var value = ""
        when (position){
            4 -> value = "+"
            9 -> value = "-"
            14 -> value = "*"
            19 -> value = "/"
            24 -> value = "="
        }
        return value
    }

    fun validateNumChar(value: String, answer: String): String{
        var newAns = ""
        when (value){
            "0" -> newAns = validateZeros(answer)
            "1", "2", "3", "4", "5", "6", "7", "8", "9" ->  newAns = validateAnotherNumbers(value, answer)
            "." -> newAns = setDotNumber(answer)
            //"C" -> newAns = "0"
        }
        return newAns
    }

    private fun validateAnotherNumbers(value: String, answer: String): String {
        return if (answer.length == 1 && answer.startsWith("0")) {
            value
        }else{
            "$answer$value"
        }
    }

    private fun validateZeros(answer: String): String {
        return if (answer.length == 1 && answer.startsWith("0")) {
            answer
        }else{
            answer + "0"
        }
    }

     private fun setDotNumber(answer: String) : String{
        return if (answer.contains(".") ) {
            answer
        }else{
            "$answer."
        }
    }

}