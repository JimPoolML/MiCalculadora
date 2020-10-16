package appjpm4everyone.micalculadora.ui.main

import android.content.Context
import android.content.res.Configuration
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

    //Landscape
    private var isLandscape: Boolean = false

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
        initUI()
        viewModel.modelMain.observe(this, Observer(::updateUi))

        savedInstanceState?.getCharSequence("answer")

    }
    private fun initUI() {
        setRecyclerItems()
    }

    private fun setRecyclerItems() {
        gridLayoutManager = GridLayoutManager(
            applicationContext,
            getDisplayWidth(),
            LinearLayoutManager.VERTICAL,
            false
        )
        //Disable Scroll
        binding.calculatorR.isNestedScrollingEnabled = false
        //gridLayoutManager = GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        binding.calculatorR.layoutManager = gridLayoutManager
        binding.calculatorR.setHasFixedSize(true)


        //arrayList
        arrayList = ArrayList()
        val orientation = resources.configuration.orientation
        arrayList = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            isLandscape = true
            setLandscapeEachButton()
        } else {
            // In portrait
            isLandscape = false
            setPortraitEachButton()
        }
        //arrayList = setPortraitEachButton()
        calcAdapter = CalcAdapter(applicationContext, arrayList!!, this, isLandscape)
        //set Adapter to recyclerView
        binding.calculatorR.adapter = calcAdapter


        calcAdapter?.notifyDataSetChanged()


    }

    private fun getDisplayWidth(): Int {

        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display? = wm.defaultDisplay
        val outMetrics = DisplayMetrics()
        display?.getMetrics(outMetrics)

        val density = resources.displayMetrics.density
        val dpWidth = outMetrics.widthPixels / density
        val orientation = resources.configuration.orientation
        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            isLandscape = true
            (dpWidth / 100).roundToInt()
        } else {
            // In portrait
            isLandscape = false
            (dpWidth / 75).roundToInt()
        }

    }

    private fun setPortraitEachButton(): ArrayList<ButtonCalc> {
        var buttonItems: ArrayList<ButtonCalc> = ArrayList()

        buttonItems.add(ButtonCalc("M+", R.color.blue_button, R.color.black_text))
        buttonItems.add(ButtonCalc("M", R.color.blue_button, R.color.black_text))
        buttonItems.add(ButtonCalc("C", R.color.blue_button, R.color.black_text))
        buttonItems.add(ButtonCalc("CE", R.color.blue_button, R.color.black_text))
        buttonItems.add(ButtonCalc("+", R.color.red_button, R.color.black_text))

        buttonItems.add(ButtonCalc("9", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("8", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("7", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc(getString(R.string.pow2), R.color.orange_button, R.color.black_text))
        buttonItems.add(ButtonCalc("-", R.color.red_button, R.color.black_text))

        buttonItems.add(ButtonCalc("6", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("5", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc("4", R.color.gray_button, R.color.black_text))
        buttonItems.add(ButtonCalc(getString(R.string.sqrt), R.color.orange_button, R.color.black_text))
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

    private fun setLandscapeEachButton(): ArrayList<ButtonCalc>? {

        var buttonItems: ArrayList<ButtonCalc> = ArrayList()

        buttonItems.add(ButtonCalc("MC", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc("X²", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc("√", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc("7", R.color.lands_white_button, R.color.blue_text))

        buttonItems.add(ButtonCalc("8", R.color.lands_white_button, R.color.blue_text))
        buttonItems.add(ButtonCalc("9", R.color.lands_white_button, R.color.blue_text))
        buttonItems.add(ButtonCalc("÷", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc("C", R.color.lands_blue_button, R.color.white_text))

        buttonItems.add(ButtonCalc("MR", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc(getString(R.string.sin), R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc("%", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc("4", R.color.lands_white_button, R.color.blue_text))

        buttonItems.add(ButtonCalc("5", R.color.lands_white_button, R.color.blue_text))
        buttonItems.add(ButtonCalc("6", R.color.lands_white_button, R.color.blue_text))
        buttonItems.add(ButtonCalc("X", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc(getString(R.string.del), R.color.lands_blue_button, R.color.white_text))

        buttonItems.add(ButtonCalc("M+", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc(getString(R.string.cos), R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc("1/X", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc("1", R.color.lands_white_button, R.color.blue_text))

        buttonItems.add(ButtonCalc("2", R.color.lands_white_button, R.color.blue_text))
        buttonItems.add(ButtonCalc("3", R.color.lands_white_button, R.color.blue_text))
        buttonItems.add(ButtonCalc("-", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc("+/-", R.color.lands_blue_button, R.color.white_text))

        buttonItems.add(ButtonCalc("M-", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc(getString(R.string.tan), R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc("π", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc("0", R.color.lands_white_button, R.color.blue_text))

        buttonItems.add(ButtonCalc(".", R.color.lands_white_button, R.color.blue_text))
        buttonItems.add(ButtonCalc("+", R.color.lands_blue_button, R.color.white_text))
        buttonItems.add(ButtonCalc("=", R.color.lands_blue_button, R.color.white_text))

        return buttonItems

    }

    private fun updateUi(uiModel: MainViewModel.UiModel) {
        when (uiModel) {
            is MainViewModel.UiModel.SetNumberValue -> (
                    showNumberValue(
                        uiModel.value
                    ))
            is MainViewModel.UiModel.SetInitValue -> {
                goToNewProcess()
            }
        }
    }

    private fun showNumberValue(value: String) {
        binding.txtAns.text = value
    }

    override fun onClickButton(position: Int) {
        viewModel.onButtonValue(position, binding.txtAns.text.toString(), isLandscape)
    }


    private fun goToNewProcess() {
        binding.txtAns.text = "0"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence("answer", binding.txtAns.text)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getCharSequence("answer", binding.txtAns.text)
    }
}