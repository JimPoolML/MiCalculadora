package appjpm4everyone.micalculadora.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import appjpm4everyone.micalculadora.R
import appjpm4everyone.micalculadora.databinding.GridButtonsBinding

class CalcAdapter (var context: Context, var arrayList: ArrayList<ButtonCalc>, private var onGetButton: OnGetButton):
    RecyclerView.Adapter<CalcAdapter.ButtonHolder>() {

    //private lateinit var onGetButton: OnGetButton

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.grid_buttons, parent, false)
        return ButtonHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ButtonHolder, position: Int) {
        //To set the position in array
        var buttonCalc : ButtonCalc = arrayList[position]

        holder.binding!!.btnCe.text = buttonCalc.buttonName
        holder.binding.btnCe.setTextColor(ContextCompat.getColor(context, buttonCalc.btnTextColor))
        holder.binding.btnCe.setBackgroundColor(ContextCompat.getColor(context, buttonCalc.buttonColor))

        holder.binding.btnCe.setOnClickListener{
            onGetButton.onClickButton(position)
        }

    }


    class ButtonHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: GridButtonsBinding? = DataBindingUtil.bind(itemView)
    }


    init{

    }

}