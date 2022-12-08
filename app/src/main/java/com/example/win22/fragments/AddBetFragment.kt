package com.example.win22.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.win22.MainActivity
import com.example.win22.R
import com.example.win22.model.BettingModel
import com.example.win22.room.BetDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddBetFragment : DialogFragment() {

    private lateinit var betDatabase: BetDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_bet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        betDatabase = BetDatabase.getDatabase(requireContext())
        val buttonValidate = requireView().findViewById<Button>(R.id.button_bet_validate)
        buttonValidate.setOnClickListener {
            val editTextName = requireView().findViewById<EditText>(R.id.edit_text_name_of_bet)
            val editTextOdd = requireView().findViewById<EditText>(R.id.edit_text_odd)
            val editTextAmount = requireView().findViewById<EditText>(R.id.edit_text_amount)
            if (editTextName.text.isEmpty()||editTextOdd.text.isEmpty()||editTextAmount.text.isEmpty()){
                Toast.makeText(requireContext(), R.string.toast_empty, Toast.LENGTH_SHORT).show()
            } else {
                val name = editTextName.text.toString()
                val odd = editTextOdd.text.toString()
                val amount = editTextAmount.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    betDatabase.bettingDao().insertData(BettingModel(null, name, odd, amount, "wait", getCapital()!!))
                    launch(Dispatchers.Main){
                        requireActivity().startActivity(Intent(context, MainActivity::class.java))
                    }
                }
            }
        }

    }
    private fun getCapital(): String? {
        val sharedPrefBankroll =
            requireContext().getSharedPreferences("capital", Context.MODE_PRIVATE)
        return sharedPrefBankroll.getString("capital", "")
    }
}