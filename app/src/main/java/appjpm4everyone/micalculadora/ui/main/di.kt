package appjpm4everyone.micalculadora.ui.main

import appjpm4everyone.usecases.UseCasesValidateData
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainActivityModule {

    @Provides
    fun MainViewModelProvider(useCasesValidateData: UseCasesValidateData): MainViewModel {
        return MainViewModel(useCasesValidateData
        )
    }

    @Provides
    fun validateDataProvider() = UseCasesValidateData()
}

@Subcomponent(modules = [(MainActivityModule::class)])
interface MainActivityComponent {
    val mainViewModel: MainViewModel
}