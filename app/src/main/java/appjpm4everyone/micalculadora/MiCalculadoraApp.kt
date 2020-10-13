package appjpm4everyone.micalculadora

import android.app.Application
import appjpm4everyone.micalculadora.di.component.AppComponent
import appjpm4everyone.micalculadora.di.component.DaggerAppComponent

class MiCalculadoraApp : Application() {

    lateinit var component: AppComponent
    private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .factory()
            .create(this)
    }

}