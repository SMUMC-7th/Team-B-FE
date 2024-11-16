package com.example.umc_wireframe.presentation.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentRegisterStep2Binding

class RegisterStep2Fragment : Fragment() {

    private var _binding: FragmentRegisterStep2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "다음" 버튼 클릭 이벤트 설정
        binding.loginButton.setOnClickListener {
            val name = binding.tvNameInput.text.toString()
            val nickname = binding.tvNicknameInput.text.toString()
            val gender = when {
                binding.maleRadioButton.isChecked -> "남자"
                binding.femaleRadioButton.isChecked -> "여자"
                else -> ""
            }

            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else if (nickname.isEmpty()) {
                Toast.makeText(requireContext(), "닉네임을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else if (gender.isEmpty()) {
                Toast.makeText(requireContext(), "성별을 선택해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // SharedPreferences에 닉네임과 성별 저장
                val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                sharedPref.edit().apply {
                    putString("nickname", nickname)
                    putString("gender", gender)
                    apply()
                }

                // 다음 단계로 이동
                findNavController().navigate(R.id.action_registerStep2Fragment_to_registerStep3Fragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
