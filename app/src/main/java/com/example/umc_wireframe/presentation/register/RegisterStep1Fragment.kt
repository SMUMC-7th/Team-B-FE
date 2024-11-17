package com.example.umc_wireframe.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentRegisterStep1Binding

class RegisterStep1Fragment : Fragment() {

    private var _binding: FragmentRegisterStep1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterStep1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "다음" 버튼 클릭 이벤트 설정
        binding.loginButton.setOnClickListener {
            val email = binding.tvEmailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(requireContext(), "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // RegisterStep2Fragment로 이동
                findNavController().navigate(R.id.action_registerStep1Fragment_to_registerStep2Fragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}