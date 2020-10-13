package appjpm4everyone.micalculadora.ui.main.adapters

class ButtonCalc {

    var buttonName: String? = null
    var buttonColor: Int = 0
    var btnTextColor: Int = 0

    constructor(buttonName: String?, buttonColor: Int, btnTextColor: Int) {
        this.buttonName = buttonName
        this.buttonColor = buttonColor
        this.btnTextColor = btnTextColor
    }

}