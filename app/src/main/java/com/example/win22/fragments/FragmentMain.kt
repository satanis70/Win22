package com.example.win22.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.win22.R
import com.example.win22.adapters.RecyclerAdapter
import com.example.win22.model.BettingModel
import com.example.win22.room.BetDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FragmentMain : Fragment(), RecyclerAdapter.OnItemClickListener {

    private lateinit var betDatabase: BetDatabase
    private var betList: ArrayList<BettingModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        val floatingButton = requireView().findViewById<FloatingActionButton>(R.id.floating_button)
        floatingButton.setOnClickListener {
            val showAddDialog = AddBetFragment()
            showAddDialog.show((activity as AppCompatActivity).supportFragmentManager, "")
        }
    }

    private fun getCapital(): String? {
        val sharedPrefBankroll =
            requireContext().getSharedPreferences("capital", Context.MODE_PRIVATE)
        sharedPrefBankroll.getString("capital", "")?.let { Log.i("CAPITAL", it) }
        return sharedPrefBankroll.getString("capital", "")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData(){
        betList.clear()
        val textViewEmptyBet = requireView().findViewById<TextView>(R.id.textView_bet_empty)
        val textViewCapital = requireView().findViewById<TextView>(R.id.textView_capital)
        betDatabase = BetDatabase.getDatabase(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            if (betDatabase.bettingDao().getAllData().isEmpty()){
                launch(Dispatchers.Main){
                    textViewEmptyBet.visibility = View.VISIBLE
                }
            } else {
                betList.addAll(betDatabase.bettingDao().getAllData())
                Log.i("DATAB", betList.size.toString())
                launch(Dispatchers.Main){
                    val adapter = RecyclerAdapter(betList, this@FragmentMain)
                    val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view)
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
        }
        if (getCapital()!!.isEmpty()){
            val showAddDialog = WriteSumFragment()
            showAddDialog.show((activity as AppCompatActivity).supportFragmentManager, "")
        } else {
            textViewCapital.text = getCapital()
        }
    }

    override fun onClick(position: Int, betModel: BettingModel, s: String, textViewStatus: TextView, textViewWinSum: TextView) {
        val odd = betList[position].odd
        val amount = betList[position].amount
        val textViewSum = requireView().findViewById<TextView>(R.id.textView_sum_bet)
        val textViewCapital = requireView().findViewById<TextView>(R.id.textView_capital)
        if (s=="win"){
            val result = odd.toDouble() * amount.toDouble()
            setCapital(getCapital()!!.toDouble() + result)
            textViewCapital.text = getCapital()
            textViewSum.text = amount
            textViewWinSum.text = result.toString()
            textViewStatus.text = s
            CoroutineScope(Dispatchers.IO).launch {
                betDatabase.bettingDao().update("win", position)
            }
        } else if(s=="loss"){
            val amount = betList[position].amount.toDouble()
            textViewStatus.text = s
            textViewSum.text = (amount * -1).toString()
            CoroutineScope(Dispatchers.IO).launch{
                betDatabase.bettingDao().update("loss", position)
            }
        } else if(s=="delete"){
            CoroutineScope(Dispatchers.IO).launch {
                betList = betDatabase.bettingDao().getAllData() as ArrayList<BettingModel>
                launch(Dispatchers.Main){
                    if (betList[position].status == "loss"){
                    var capWinLoss = getCapital()!!.toDouble()
                    capWinLoss += betList[position].amount.toDouble()
                    setCapital(capWinLoss)
                    textViewCapital.text = "${capWinLoss}€"
                    launch(Dispatchers.IO){
                        betDatabase.bettingDao().deleteData(betList[position])
                        betList = betDatabase.bettingDao().getAllData() as ArrayList<BettingModel>
                        launch(Dispatchers.Main) {
                            val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view)
                            var adapter = RecyclerAdapter(betList, this@FragmentMain)
                            adapter.notifyItemRemoved(position)
                            adapter = RecyclerAdapter(betList, this@FragmentMain)
                            recyclerView.adapter = adapter
                        }
                    }
                } else if(betList[position].status == "win") {
                        var capWinDel = getCapital()!!.toDouble()
                        capWinDel -= betList[position].amount.toDouble()
                        setCapital(capWinDel)
                        textViewSum.text = "${capWinDel}€"
                        CoroutineScope(Dispatchers.IO).launch {
                            betDatabase.bettingDao().deleteData(betList[position])
                            betList = betDatabase.bettingDao().getAllData() as ArrayList<BettingModel>
                            launch(Dispatchers.Main) {
                                val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view)
                                var adapter = RecyclerAdapter(betList, this@FragmentMain)
                                adapter.notifyItemRemoved(position)
                                adapter = RecyclerAdapter(betList, this@FragmentMain)
                                recyclerView.adapter = adapter
                            }
                        }
                    } else {
                        var capWinLoss = getCapital()!!.toDouble()
                        capWinLoss += betList[position].amount.toDouble()
                        setCapital(capWinLoss)
                        textViewCapital.text = "${capWinLoss}€"
                        launch(Dispatchers.IO){
                            betDatabase.bettingDao().deleteData(betList[position])
                            betList = betDatabase.bettingDao().getAllData() as ArrayList<BettingModel>
                            launch(Dispatchers.Main) {
                                val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view)
                                var adapter = RecyclerAdapter(betList, this@FragmentMain)
                                adapter.notifyItemRemoved(position)
                                adapter = RecyclerAdapter(betList, this@FragmentMain)
                                recyclerView.adapter = adapter
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setCapital(capital: Double) {
        val sharedPrefCapital =
            requireContext().getSharedPreferences("capital", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            putString("capital", capital.toString())
        }
    }

}