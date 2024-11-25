package com.example.umc_wireframe.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentPasswordChangeVerificationBinding

class PasswordChangeVerificationFragment : Fragment() {

    private var _binding: FragmentPasswordChangeVerificationBinding? = null
    private val binding get() = _binding!!

    private val sentVerificationCode = "123456" // 서버에서 가져온 인증번호 (예시)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordChangeVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 클릭 시
        binding.backButton.setOnClickListener {
            findNavController().popBackStack() // 이전 화면으로 돌아감
        }

        // 완료 버튼 클릭 시
        binding.btnEmailcodeComplete.setOnClickListener {
            handleEmailVerification()
        }
    }

    private fun handleEmailVerification() {
        val enteredCode = binding.verifyEmailCode.text.toString()

        // 이메일 인증번호 검증
        if (enteredCode != sentVerificationCode) {
            binding.tvEmailcodeErrorMessage.visibility = View.VISIBLE
            binding.tvEmailcodeErrorMessage.text = "인증번호가 일치하지 않습니다"
        } else {
            binding.tvEmailcodeErrorMessage.visibility = View.GONE
            Toast.makeText(requireContext(), "인증번호가 확인되었습니다", Toast.LENGTH_SHORT).show()

            // 인증번호가 일치하면 다음 화면으로 이동
            findNavController().navigate(R.id.action_passwordChangeVerificationFragment_to_passwordChangeInputFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
