package appjpm4everyone.usecases

class UseCasesLandscapeValidateData {
    fun setDecimalValues(position: Int ) : String{
        var value = ""
        when (position){
            3 -> value = "7"
            4 -> value = "8"
            5 -> value = "9"
            11 -> value = "4"
            12 -> value = "5"
            13 -> value = "6"
            19 -> value = "1"
            20 -> value = "2"
            21 -> value = "3"
            27 -> value = "0"
            28 -> value = "."
        }
        return value
    }

    fun setOtherlValues(position: Int ) : String{
        var value = ""
        when (position){
            0 -> value = "MC"
            16 -> value = "M+"
            8 -> value = "MR"
            7 -> value = "C"
            15 -> value = "CE"
            28 -> value = "."
            23 -> value = "+/-"
            24 -> value = "M-"
            26 -> value = "PI"
        }
        return value
    }

    fun setArithmetic(position: Int ) : String{
        var value = ""
        when (position){
            29 -> value = "+"
            22 -> value = "-"
            14 -> value = "*"
            6 -> value = "/"
            30 -> value = "="
            //Others operators
            1 -> value = "POT"
            2 -> value = "ROOT"
            10 -> value = "%"
            18 -> value = "1/X"
            //Trigonometry
            9 -> value = "SIN"
            17 -> value = "COS"
            25 -> value = "TAN"
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
        return if ( (answer=="ERROR") || (answer.length == 1 && answer.startsWith("0")) ) {
            value
        }else{
            "$answer$value"
        }
    }

    private fun validateZeros(answer: String): String {
        return if ((answer=="ERROR") || (answer.length == 1 && answer.startsWith("0")) ) {
            answer
        }else{
            answer + "0"
        }
    }

    private fun setDotNumber(answer: String) : String{
        return when {
            answer.contains(".") -> {
                answer
            }
            answer=="ERROR" -> {
                "0."
            }
            else -> {
                "$answer."
            }
        }
    }
}