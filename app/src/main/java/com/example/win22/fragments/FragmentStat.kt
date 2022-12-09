package com.example.win22.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.win22.R
import com.example.win22.model.BettingModel
import com.example.win22.room.BetDatabase
import com.github.mikephil.charting.charts.LineChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class FragmentStat : Fragment() {

    private lateinit var betDatabase: BetDatabase
    private var betList: ArrayList<BettingModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        betDatabase = BetDatabase.getDatabase(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            betList.addAll(betDatabase.bettingDao().getAllData())
            launch(Dispatchers.Main){
                val chart = requireView().findViewById<LineChart>(R.id.chartView)
                val listChart = ArrayList<Entry>()
                for (i in betList.indices){
                    listChart.add(Entry(i.toFloat(), betList[i].capital.toFloat()))
                }
                listChart.add(Entry(betList.size.toFloat(), getCapital()!!.toFloat()))
                val lineDataset = LineDataSet(listChart, "")
                lineDataset.lineWidth = 6f
                lineDataset.color = Color.GREEN
                lineDataset.setDrawCircles(true)
                lineDataset.setDrawCircleHole(false)
                val arrayIline = ArrayList<ILineDataSet>()
                arrayIline.add(lineDataset)
                val lineData = LineData(arrayIline)
                chart.setBackgroundColor(Color.DKGRAY)
                chart.axisLeft.textColor = Color.GREEN
                chart.axisLeft.textSize = 16F
                chart.axisRight.textColor = Color.GREEN
                chart.axisRight.textSize = 16F
                chart.data = lineData
                chart.invalidate()
            }
        }
        val textViewBalance = requireView().findViewById<TextView>(R.id.textView_balance)
        textViewBalance.text = getFirstCapital()
        val textViewCurrentBalance = requireView().findViewById<TextView>(R.id.textView_current_balance)
        textViewCurrentBalance.text = getCapital()
        val textViewNumberBet = requireView().findViewById<TextView>(R.id.textView_number_bet)
        textViewNumberBet.text = betList.size.toString()
    }

    private fun getCapital(): String? {
        val sharedPrefCapital =
            requireContext().getSharedPreferences("capital", Context.MODE_PRIVATE)
        sharedPrefCapital.getString("capital", "")?.let { Log.i("CAPITAL", it) }
        return sharedPrefCapital.getString("capital", "")
    }
    private fun getFirstCapital(): String?{
        val sharedPrefCapital =
            requireContext().getSharedPreferences("FirstCapital", Context.MODE_PRIVATE)
        sharedPrefCapital.getString("FirstCapital", "")?.let { Log.i("CAPITAL", it) }
        return sharedPrefCapital.getString("FirstCapital", "")
    }
}