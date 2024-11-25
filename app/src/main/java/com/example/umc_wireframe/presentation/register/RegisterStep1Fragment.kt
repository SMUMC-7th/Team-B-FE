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
            val email = binding.tvEmailInput.text.toString() // 입력된 이메일 가져오기
            val password = binding.passwordInput.text.toString() // 입력된 비밀번호 가져오기

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(requireContext(), "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                savePassword(password)
                findNavController().navigate(R.id.action_registerStep1Fragment_to_registerStep2Fragment)
            }
        }

        // 이메일 인증번호 받기 클릭 이벤트
        binding.tvGetVerificationNumber.setOnClickListener {
            if (binding.getVerificationCode.visibility == View.GONE) {
                // 인증번호 입력 필드를 표시
                binding.getVerificationCode.visibility = View.VISIBLE

                // 이메일과 비밀번호 입력 필드에 blur 효과 적용
                binding.tvEmailInput.alpha = 0.3f
                binding.passwordInput.alpha = 0.3f

                // 추가적으로 클릭 이벤트 차단 (선택사항)
                binding.tvEmailInput.isEnabled = false
                binding.passwordInput.isEnabled = false

                Toast.makeText(requireContext(), "인증번호 입력창이 활성화되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun savePassword(password: String) {
        val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("password", password)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
