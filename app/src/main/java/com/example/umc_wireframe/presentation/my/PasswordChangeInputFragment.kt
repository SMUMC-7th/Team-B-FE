package com.example.umc_wireframe.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentPasswordChangeInputBinding

class PasswordChangeInputFragment : Fragment() {

    private var _binding: FragmentPasswordChangeInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordChangeInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로 가기 버튼 클릭 시 이전 화면으로 이동
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        // 변경 완료 버튼 클릭 이벤트
        binding.btnPwCompleteChange.setOnClickListener {
            handlePasswordChange()
        }
    }

    private fun handlePasswordChange() {
        val newPassword = binding.inputChangePassword.text.toString()
        val confirmPassword = binding.inputChangePasswordCheck.text.toString()

        // 비밀번호 일치 여부 확인
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(requireContext(), "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmPassword) {
            // 일치하지 않을 경우 에러 메시지 표시
            binding.tvChangePwErrorMessage.visibility = View.VISIBLE
            binding.tvChangePwErrorMessage.text = "비밀번호가 일치하지 않습니다"
            return
        } else {
            // 에러 메시지 숨김
            binding.tvChangePwErrorMessage.visibility = View.GONE
        }

        // 비밀번호 변경 성공 시
        Toast.makeText(requireContext(), "비밀번호 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show()

        // 마이페이지로 돌아가기
        findNavController().navigate(R.id.action_navi_password_change_input_to_navi_my)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
