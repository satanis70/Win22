package com.example.win22.adapters

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.win22.R
import com.example.win22.model.BettingModel

class RecyclerAdapter(val list: List<BettingModel>, val onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<RecyclerAdapter.MainHolder>() {
    inner class MainHolder(itemView: View, onItemClickListener: OnItemClickListener):RecyclerView.ViewHolder(itemView){
        private val textViewNameBet = itemView.findViewById<TextView>(R.id.textView_name_bet)
        private val textViewSumBet = itemView.findViewById<TextView>(R.id.textView_sum_bet)
        private val textViewStatus = itemView.findViewById<TextView>(R.id.textView_status)
        private val buttonWin = itemView.findViewById<AppCompatImageButton>(R.id.button_win)
        private val buttonLoss = itemView.findViewById<AppCompatImageButton>(R.id.button_loss)
        private val buttonWait = itemView.findViewById<AppCompatImageButton>(R.id.button_wait)
        private val buttonDelete = itemView.findViewById<AppCompatImageButton>(R.id.button_delete)
        fun bind(name: String, amount: String, odd: String, capital: String, status: String){
            textViewNameBet.text = name
            textViewSumBet.text = amount
            textViewStatus.text = status
            buttonWin.setOnClickListener{
                onItemClickListener.onClick(adapterPosition, BettingModel(adapterPosition, name, odd, amount, status, capital), "win")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MainHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(list[position].name, list[position].amount, list[position].odd, list[position].capital, list[position].status)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onClick(position: Int, betModel: BettingModel, s: String) {}
    }
}