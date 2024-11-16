package com.example.umc_wireframe.presentation.my


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.umc_wireframe.R

class NicknameChangeFragment : Fragment() {

    private lateinit var nicknameEditText: EditText
    private lateinit var btnCompleteChange: Button
    private lateinit var tvNickname: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nickname_change, container, false)

        nicknameEditText = view.findViewById(R.id.nickname_edit_text)
        btnCompleteChange = view.findViewById(R.id.btn_complete_change)
        tvNickname = view.findViewById(R.id.tv_nickname)

        btnCompleteChange.setOnClickListener {
            onCompleteChange()
        }

        return view
    }

    private fun onCompleteChange() {
        val newNickname = nicknameEditText.text.toString()
        if (newNickname.isNotBlank()) {
            // TODO: Handle the nickname change logic, e.g., update database or shared preferences
            tvNickname.text = newNickname // Update TextView to show the new nickname
            nicknameEditText.text.clear() // Clear the EditText
        } else {
            // TODO: Show error message to the user (e.g., using a Toast or Snackbar)
        }
    }
}
