package com.example.umc_wireframe.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentRegisterStep2Binding
import com.example.umc_wireframe.domain.model.Gender

class RegisterStep2Fragment : Fragment() {

    private var _binding: FragmentRegisterStep2Binding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var gender: Gender? = null

        binding.genderRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            gender = when (checkedId) {
                R.id.maleRadioButton -> Gender.MALE
                R.id.femaleRadioButton -> Gender.FEMALE
                else -> null
            }
        }

        // "다음" 버튼 클릭 이벤트 설정
        binding.loginButton.setOnClickListener {
            val name = binding.tvNameInput.text.toString()
            val nickname = binding.tvNicknameInput.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else if (nickname.isEmpty()) {
                Toast.makeText(requireContext(), "닉네임을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else if (gender == null) {
                Toast.makeText(requireContext(), "성별을 선택해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.postJoinSuccess(
                    name = name,
                    nickname = nickname,
                    gender = gender!!,
                    isSuccess = { findNavController().navigate(R.id.action_registerStep2Fragment_to_registerStep3Fragment) }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
