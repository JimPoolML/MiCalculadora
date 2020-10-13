package appjpm4everyone.micalculadora.di.component

import android.app.Application
import appjpm4everyone.micalculadora.di.module.AppModule
import appjpm4everyone.micalculadora.di.module.SdkModule
import appjpm4everyone.micalculadora.ui.main.MainActivityComponent
import appjpm4everyone.micalculadora.ui.main.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, SdkModule::class])
interface AppComponent {

    fun plus(module: MainActivityModule): MainActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

}