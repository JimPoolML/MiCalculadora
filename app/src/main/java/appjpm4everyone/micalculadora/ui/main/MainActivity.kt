package appjpm4everyone.micalculadora.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import appjpm4everyone.micalculadora.R
import appjpm4everyone.micalculadora.databinding.ActivityMainBinding
import appjpm4everyone.micalculadora.utils.app
import appjpm4everyone.micalculadora.utils.getViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var component: MainActivityComponent
    private val viewModel by lazy { getViewModel { component.mainViewModel } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //dagger injection
        component = app.component.plus(MainActivityModule())
        //initUI()
        viewModel.modelMain.observe(this, Observer(::updateUi))

    }

    private fun initUI() {
        TODO("Not yet implemented")
    }


    private fun updateUi(uiModel: MainViewModel.UiModel) {
        //if (uiModel is MainViewModel.UiModel.Loading) progressBar.show(this) else progressBar.hideProgress()
        when (uiModel) {
            is MainViewModel.UiModel.NavigationChangePass -> {
                goToNewProcess()
            }
            is MainViewModel.UiModel.ShowErrorCurrentPss -> showErrorCurrentPass()
            is MainViewModel.UiModel.ShowErrorNewPss -> showErrorNewPass()
        }
    }

    private fun showErrorNewPass() {
        TODO("Not yet implemented")
    }

    private fun showErrorCurrentPass() {
        TODO("Not yet implemented")
    }

    private fun goToNewProcess() {
        binding.txtAns.text =  "Ensayo Dagger ViewModel"
    }
}