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
import com.example.umc_wireframe.databinding.FragmentPasswordChangeInputBinding
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.util.SharedPreferencesManager
import com.example.umc_wireframe.util.navigateWithClear
import kotlinx.coroutines.launch

class PasswordChangeInputFragment : Fragment() {

    private var _binding: FragmentPasswordChangeInputBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PasswordChangeInputViewModel by viewModels()

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
            findNavController().navigateWithClear(R.id.nav_mypage)
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
            binding.tvChangePwErrorMessage.visibility = View.GONE

            viewModel.patchPasswordSuccess(newPassword = newPassword, isSuccess = {
                Toast.makeText(requireContext(), "비밀번호 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigateWithClear(R.id.navi_my)
            })
        }

    }

    private fun sendPasswordChangeRequest(newPassword: String) {
        lifecycleScope.launch {
            try {
                val token =
                    SharedPreferencesManager(requireContext()).getAccessToken() // SharedPreferences에서 토큰 가져옴
                val response = RepositoryFactory.createMemberRepository().patchPasswordSuccess(
                    newPassword = newPassword
                )
                Log.d("API_RESPONSE", "Response: $response")

                if (response.isSuccess == true) {
                    Toast.makeText(requireContext(), "비밀번호 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_navi_password_change_input_to_navi_my) // 마이페이지로 이동
                } else {
                    Toast.makeText(
                        requireContext(),
                        "비밀번호 변경 실패: ${response.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
