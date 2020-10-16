package appjpm4everyone.micalculadora.ui.main

import appjpm4everyone.usecases.UseCasesLandscapeValidateData
import appjpm4everyone.usecases.UseCasesValidateData
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainActivityModule {

    @Provides
    fun MainViewModelProvider(useCasesValidateData: UseCasesValidateData, useCasesLandscapeValidateData : UseCasesLandscapeValidateData): MainViewModel {
        return MainViewModel(useCasesValidateData, useCasesLandscapeValidateData
        )
    }

    @Provides
    fun validateDataProvider() = UseCasesValidateData()

    @Provides
    fun validateLandscapeDataProvider() = UseCasesLandscapeValidateData()
}

@Subcomponent(modules = [(MainActivityModule::class)])
interface MainActivityComponent {
    val mainViewModel: MainViewModel
}