package com.example.win22.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.edit
import com.example.win22.MainActivity
import com.example.win22.R
import com.example.win22.room.BetDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentAccount : Fragment() {

    private lateinit var betDatabase: BetDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonClear = requireView().findViewById<Button>(R.id.button_clear_bank)
        betDatabase = BetDatabase.getDatabase(requireContext())
        buttonClear.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                betDatabase.bettingDao().deleteDatabase()
                launch(Dispatchers.Main){
                    clearCapital()
                    clearFirstCapital()
                    requireActivity().startActivity(Intent(context, MainActivity::class.java))
                }
            }
        }
    }

    private fun clearCapital() {
        val sharedPrefCapital =
            requireContext().getSharedPreferences("capital", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            remove("capital")
        }
    }

    private fun clearFirstCapital(){
        val sharedPrefCapital =
            requireContext().getSharedPreferences("FirstCapital", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            remove("FirstCapital")
        }
    }
}