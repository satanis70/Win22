package com.example.win22.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.win22.R
import com.example.win22.room.BetDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FragmentMain : Fragment() {

    private lateinit var betDatabase: BetDatabase



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textViewEmptyBet = requireView().findViewById<TextView>(R.id.textView_bet_empty)
        betDatabase = BetDatabase.getDatabase(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            if (betDatabase.bettingDao().getAllData().isEmpty()){
                launch(Dispatchers.Main){
                    textViewEmptyBet.visibility = View.VISIBLE
                }
            }
        }
        if (getCapital()!!.isEmpty()){
            val showAddDialog = WriteSumFragment()
            showAddDialog.show((activity as AppCompatActivity).supportFragmentManager, "")
        }
        val floatingButton = requireView().findViewById<FloatingActionButton>(R.id.floating_button)
        floatingButton.setOnClickListener {
            val showAddDialog = AddBetFragment()
            showAddDialog.show((activity as AppCompatActivity).supportFragmentManager, "")
        }
    }

    private fun getCapital(): String? {
        val sharedPrefBankroll =
            requireContext().getSharedPreferences("capital", Context.MODE_PRIVATE)
        return sharedPrefBankroll.getString("capital", "")
    }

    private fun showBet(){

    }
}