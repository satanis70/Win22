package com.example.win22.adapters

import android.content.DialogInterface.OnClickListener
import android.util.Log
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
        private val buttonDelete = itemView.findViewById<AppCompatImageButton>(R.id.button_delete)
        private val textViewWinSum = itemView.findViewById<TextView>(R.id.textView_win_sum)
        fun bind(name: String, amount: String, odd: String, status: String, capital: String){
            textViewNameBet.text = name
            textViewSumBet.text = amount
            textViewStatus.text = status
            when (status) {
                "win" -> {
                    textViewSumBet.setTextColor(itemView.resources.getColor(R.color.button_win))
                    textViewWinSum.setTextColor(itemView.resources.getColor(R.color.button_win))
                    textViewStatus.text = status
                    buttonLoss.visibility = View.INVISIBLE
                    buttonWin.visibility = View.INVISIBLE
                    val result = odd.toDouble() * amount.toDouble()
                    textViewWinSum.text = result.toString()
                    textViewSumBet.text = amount
                }
                "loss" -> {
                    textViewSumBet.setTextColor(itemView.resources.getColor(R.color.button_loss))
                    textViewStatus.text = status
                    buttonLoss.visibility = View.INVISIBLE
                    buttonWin.visibility = View.INVISIBLE
                    textViewWinSum.text = ""
                    textViewSumBet.text = "-$amount"
                }
                else -> {
                    textViewSumBet.setTextColor(itemView.resources.getColor(R.color.amount_wait))
                }
            }
            buttonWin.setOnClickListener{
                textViewSumBet.setTextColor(itemView.resources.getColor(R.color.button_win))
                onItemClickListener.onClick(adapterPosition, BettingModel(
                    adapterPosition,
                    adapterPosition,
                    name,
                    odd,
                    amount,
                    status,
                    capital),
                    "win",
                textViewStatus,
                textViewWinSum)
                buttonLoss.visibility = View.INVISIBLE
                buttonWin.visibility = View.INVISIBLE
            }
            buttonLoss.setOnClickListener {
                textViewSumBet.setTextColor(itemView.resources.getColor(R.color.button_loss))
                onItemClickListener.onClick(adapterPosition, BettingModel(
                    adapterPosition,
                    adapterPosition,
                    name,
                    odd,
                    amount,
                    status,
                    capital),
                    "loss",
                textViewStatus,
                textViewWinSum)
                buttonLoss.visibility = View.INVISIBLE
                buttonWin.visibility = View.INVISIBLE
            }
            buttonDelete.setOnClickListener {
                onItemClickListener.onClick(adapterPosition, BettingModel(
                    adapterPosition,
                    adapterPosition,
                    name,
                    odd,
                    amount,
                    status,
                    capital),
                    "delete",
                    textViewStatus,
                    textViewWinSum)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MainHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(
            list[position].name,
            list[position].amount,
            list[position].odd,
            list[position].status,
            list[position].capital
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onClick(
            position: Int,
            betModel: BettingModel,
            s: String,
            textViewStatus: TextView,
            textViewWinSum: TextView
        ) {}
    }
}