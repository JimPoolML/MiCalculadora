package appjpm4everyone.micalculadora.ui.main

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import appjpm4everyone.micalculadora.R
import appjpm4everyone.micalculadora.databinding.ActivityMainBinding
import appjpm4everyone.micalculadora.ui.main.adapters.ButtonCalc
import appjpm4everyone.micalculadora.ui.main.adapters.CalcAdapter
import appjpm4everyone.micalculadora.ui.main.adapters.OnGetButton
import appjpm4everyone.micalculadora.utils.app
import appjpm4everyone.micalculadora.utils.getViewModel
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity(), OnGetButton {

    private lateinit var binding: ActivityMainBinding
    private lateinit var component: MainActivityComponent
    private val viewModel by lazy { getViewModel { component.mainViewModel } }

    //RecyclerView
    private val columns = 5
    private var gridLayoutManager: GridLayoutManager? = null
    private var arrayList: ArrayList<ButtonCalc>? = null
    private var calcAdapter: CalcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //DataBiding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //dagger injection
        component = app.component.plus(MainActivityModule())
        //initUI()
        viewModel.modelMain.observe(this, Observer(::updateUi))

        setRecyclerItems()

    }

    private fun setRecyclerItems() {
        gridLayoutManager = GridLayoutManager(
            applicationContext,
            getDisplayWidth(),
            LinearLayoutManager.VERTICAL,
            false
        )
        //gridLayoutManager = GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        binding.calculatorR.layoutManager = gridLayoutManager
        binding.calculatorR.setHasFixedSize(true)

        //arrayList
        arrayList = ArrayList()
        arrayList = setEachButton()
        calcAdapter = CalcAdapter(applicationContext, arrayList!!, this)
        //set Adapter to recyclerView
        binding.calculatorR.adapter = calcAdapter


    }

    private fun getDisplayWidth(): Int {

        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display? = wm.defaultDisplay
        val outMetrics = DisplayMetrics()
        display?.getMetrics(outMetrics)

        val density = resources.displayMetrics.density
        val dpWidth = outMetrics.widthPixels / density
        return (dpWidth / 75).roundToInt()
    }

    private fun initUI() {
        TODO("Not yet implemented")
    }

    private fun setEachButton(): ArrayList<ButtonCalc> {
        var buttonItems: ArrayList<ButtonCalc> = ArrayList()

        buttonItems.add(ButtonCalc("M+", R.color.blue_button, R.color.black_text))
        buttonItems.add(ButtonCalc("M", R.color.blue_button, R.color.black_text))
        buttonItems.add(ButtonCalc("C", R.color.blue_button, R.color.black_text))
        buttonItems.add(ButtonCalc("CE", R.color.blue_button, R.color.black_text))
        buttonItems.add(ButtonCalc("+", R.color.red_button, R.color.black_text))

        buttonItems.add(ButtonCalc("9", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("8", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("7", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("POT", R.color.orange_button, R.color.black_text))
        buttonItems.add(ButtonCalc("-", R.color.red_button, R.color.black_text))

        buttonItems.add(ButtonCalc("6", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("5", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("4", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("RAIZ", R.color.orange_button, R.color.black_text))
        buttonItems.add(ButtonCalc("*", R.color.red_button, R.color.black_text))

        buttonItems.add(ButtonCalc("3", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("2", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("1", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("%", R.color.orange_button, R.color.black_text))
        buttonItems.add(ButtonCalc("/", R.color.red_button, R.color.black_text))

        buttonItems.add(ButtonCalc("0", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc(".", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("+/-", R.color.orange_button, R.color.black_text))
        buttonItems.add(ButtonCalc("OFF", R.color.off_button, R.color.black_text))
        buttonItems.add(ButtonCalc("=", R.color.red_button, R.color.black_text))

        return buttonItems
    }


    private fun updateUi(uiModel: MainViewModel.UiModel) {
        //if (uiModel is MainViewModel.UiModel.Loading) progressBar.show(this) else progressBar.hideProgress()
        when (uiModel) {
            is MainViewModel.UiModel.setNumberValue -> (
                    showNumberValue(
                        uiModel.value
                    ))
            is MainViewModel.UiModel.NavigationChangePass -> {
                goToNewProcess()
            }
            is MainViewModel.UiModel.ShowErrorCurrentPss -> showErrorCurrentPass()
            is MainViewModel.UiModel.ShowErrorNewPss -> showErrorNewPass()
        }
    }

    private fun showNumberValue(value: String) {
        binding.txtAns.text = value
    }

    override fun onClickButton(position: Int) {
        viewModel.onButtonValue(position, binding.txtAns.text.toString())
        Toast.makeText(this, "Position is: $position", Toast.LENGTH_SHORT).show()
    }

    private fun showErrorNewPass() {
        TODO("Not yet implemented")
    }

    private fun showErrorCurrentPass() {
        TODO("Not yet implemented")
    }

    private fun goToNewProcess() {
        binding.txtAns.text = "0"
    }


}