package com.example.win22.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.DialogFragment
import com.example.win22.MainActivity
import com.example.win22.R


class WriteSumFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_write_sum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextSum = requireView().findViewById<EditText>(R.id.edit_text_sum)
        val buttonValidate = requireView().findViewById<Button>(R.id.button_sum_validate)
        buttonValidate.setOnClickListener {
            if (editTextSum.text.isEmpty()){
                Toast.makeText(requireContext(), R.string.toast_empty, Toast.LENGTH_SHORT).show()
            } else {
                val sharedPrefBankroll = requireContext().getSharedPreferences("capital", Context.MODE_PRIVATE)
                sharedPrefBankroll.edit {
                    putString("capital", editTextSum.text.toString())
                }
                requireActivity().startActivity(Intent(context, MainActivity::class.java))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        requireActivity().startActivity(Intent(context, MainActivity::class.java))
    }
}