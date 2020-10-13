package appjpm4everyone.micalculadora.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import appjpm4everyone.micalculadora.utils.ScopedViewModel
import appjpm4everyone.usecases.UseCasesValidateData

class MainViewModel(useCasesValidateData: UseCasesValidateData) : ScopedViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        object ShowErrorCurrentPss: UiModel()
        object ShowErrorNewPss: UiModel()
        object NavigationChangePass : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val modelMain: LiveData<UiModel> get() = _model

    init {
        onTest()
    }

    private fun onTest() {
        _model.value = UiModel.NavigationChangePass
    }

}