package appjpm4everyone.micalculadora.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import appjpm4everyone.micalculadora.R
import appjpm4everyone.micalculadora.databinding.GridButtonsBinding

class CalcAdapter(
    var context: Context,
    var arrayList: ArrayList<ButtonCalc>,
    private var onGetButton: OnGetButton,
    var landscape: Boolean
):
    RecyclerView.Adapter<CalcAdapter.ButtonHolder>() {


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
        //Duplicate zero button in landscape
        /*if(position==27 && landscape) {

           *//* val width = holder.binding.btnCe.width
            holder.binding.btnCe.width = 145*//*

            holder.binding.btnCe.measure(0, 0)
            val width = holder.binding.btnCe.measuredWidth
            holder.binding.btnCe.measure(width*2, holder.binding.btnCe.measuredHeight)

           *//* textView.measure(0, 0);       //must call measure!
            textView.getMeasuredHeight(); //get height*//*
        }*/


        holder.binding.btnCe.setOnClickListener{
            if(position==23 && !landscape) {
                //Nothing
            }else{
                onGetButton.onClickButton(position)
            }
        }

    }


    class ButtonHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: GridButtonsBinding? = DataBindingUtil.bind(itemView)
    }


    init{

    }

}