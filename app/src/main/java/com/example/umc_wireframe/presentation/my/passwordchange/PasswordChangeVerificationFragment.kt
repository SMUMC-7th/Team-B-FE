package com.example.umc_wireframe.presentation.my.passwordchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentPasswordChangeVerificationBinding
import com.example.umc_wireframe.domain.repository.RepositoryFactory

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

        // 완료 버튼 클릭 시 인증번호 검증
        binding.btnEmailcodeComplete.setOnClickListener {
            verifyCode()
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
