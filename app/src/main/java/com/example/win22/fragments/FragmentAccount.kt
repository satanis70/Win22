package com.example.win22.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.edit
import com.example.win22.MainActivity
import com.example.win22.R

class FragmentAccount : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonClear = requireView().findViewById<Button>(R.id.button_clear_bank)
        buttonClear.setOnClickListener {
            clearCapital()
            requireActivity().startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private fun clearCapital() {
        val sharedPrefBankroll =
            requireContext().getSharedPreferences("capital", Context.MODE_PRIVATE)
        sharedPrefBankroll.edit {
            remove("capital")
        }
    }
}