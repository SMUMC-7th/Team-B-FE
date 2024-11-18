package com.example.umc_wireframe.presentation.my

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.databinding.FragmentPasswordChangeBinding

class PasswordChangeFragment : Fragment() {

    private var _binding: FragmentPasswordChangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 클릭 시
        binding.backButton.setOnClickListener {
            findNavController().popBackStack() // 이전 화면으로 돌아감
        }

        // 변경 완료 버튼 클릭 시
        binding.btnPwCompleteChange.setOnClickListener {
            handlePasswordChange()
        }
    }

    private fun handlePasswordChange() {
        val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val savedPassword = sharedPref.getString("password", "") // SharedPreferences에서 저장된 비밀번호 가져오기

        val currentPassword = binding.currentPwEditText.text.toString()
        val newPassword = binding.inputChangePassword.text.toString()
        val confirmPassword = binding.inputChangePasswordCheck.text.toString()

        // 현재 비밀번호 확인
        if (currentPassword != savedPassword) {
            binding.tvCurrentPwErrorMessage.visibility = View.VISIBLE
            binding.tvCurrentPwErrorMessage.text = "현재 비밀번호가 일치하지 않습니다"
            return
        } else {
            binding.tvCurrentPwErrorMessage.visibility = View.GONE
        }

        // 새 비밀번호와 확인 비밀번호 일치 여부 확인
        if (newPassword != confirmPassword) {
            binding.tvChangePwErrorMessage.visibility = View.VISIBLE
            binding.tvChangePwErrorMessage.text = "새 비밀번호가 일치하지 않습니다"
            return
        } else {
            binding.tvChangePwErrorMessage.visibility = View.GONE
        }

        // 모든 조건 만족 시 비밀번호 변경
        if (currentPassword == savedPassword && newPassword == confirmPassword) {
            val editor = sharedPref.edit()
            editor.putString("password", newPassword) // 새로운 비밀번호 저장
            editor.apply()

            Toast.makeText(requireContext(), "비밀번호 변경이 완료되었습니다", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack() // 이전 화면으로 돌아감
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
