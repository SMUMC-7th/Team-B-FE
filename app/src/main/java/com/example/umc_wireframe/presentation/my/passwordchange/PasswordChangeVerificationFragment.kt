package com.example.umc_wireframe.presentation.my.passwordchange

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentPasswordChangeVerificationBinding
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.util.SharedPreferencesManager
import kotlinx.coroutines.launch

class PasswordChangeVerificationFragment : Fragment() {

    private var _binding: FragmentPasswordChangeVerificationBinding? = null
    private val binding get() = _binding!!

    private val memberRepository = RepositoryFactory.createMemberRepository()

    private val viewModel: PasswordChangeVerificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordChangeVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.verifyPasswordChangeReq()

        // 뒤로가기 버튼 클릭 시
        binding.backButton.setOnClickListener {
            findNavController().popBackStack() // 이전 화면으로 돌아감
        }

        // 인증번호 요청 API 호출
        requestVerificationCode()

        // 완료 버튼 클릭 시 인증번호 검증
        binding.btnEmailcodeComplete.setOnClickListener {
            verifyCode()
        }
    }

    private fun requestVerificationCode() {
        lifecycleScope.launch {
            try {
                val token =
                    SharedPreferencesManager(requireContext()).getAccessToken() // SharedPreferences에서 토큰 가져옴
                val response = memberRepository.postPasswordChange()
                if (response.isSuccess == true) {
                    Toast.makeText(requireContext(), "인증번호가 이메일로 전송되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "인증번호 요청에 실패했습니다: ${response.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyCode() {
        val enteredCode = binding.verifyEmailCode.text.toString()
        if (enteredCode.isEmpty()) {
            binding.tvEmailcodeErrorMessage.text = "인증번호를 입력해주세요."
            binding.tvEmailcodeErrorMessage.visibility = View.VISIBLE
            return
        }

        viewModel.verifyPasswordChange(
            enteredCode,
            isSuccess = {
                Toast.makeText(requireContext(), "인증번호가 확인되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_passwordChangeVerificationFragment_to_passwordChangeInputFragment)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
