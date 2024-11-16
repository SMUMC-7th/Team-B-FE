package com.example.umc_wireframe.presentation.my

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.databinding.FragmentNicknameChangeBinding

class NicknameChangeFragment : Fragment() {

    private var _binding: FragmentNicknameChangeBinding? = null
    private val binding get() = _binding!!

    private lateinit var nicknameEditText: EditText
    private lateinit var btnCompleteChange: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNicknameChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nicknameEditText = binding.nicknameEditText
        btnCompleteChange = binding.btnCompleteChange

        // 버튼 클릭 시 닉네임 변경 처리
        btnCompleteChange.setOnClickListener {
            onCompleteChange()
        }
    }

    private fun onCompleteChange() {
        val newNickname = nicknameEditText.text.toString()
        if (newNickname.isNotBlank()) {
            // 닉네임을 SharedPreferences에 저장
            val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("nickname", newNickname)
                apply()
            }

            nicknameEditText.text.clear()
            findNavController().navigateUp()
        } else {
            // TODO: 사용자에게 오류 메시지 표시
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
