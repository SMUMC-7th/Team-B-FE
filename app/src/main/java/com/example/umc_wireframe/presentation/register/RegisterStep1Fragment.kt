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

            // 이메일이 비어 있는 경우 사용자에게 알림
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
            // 비밀번호가 비어 있는 경우 사용자에게 알림
            else if (password.isEmpty()) {
                Toast.makeText(requireContext(), "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
            // 이메일과 비밀번호가 모두 입력된 경우
            else {
                savePassword(password) // 비밀번호를 SharedPreferences에 저장
                findNavController().navigate(R.id.action_registerStep1Fragment_to_registerStep2Fragment) // 다음 단계로 이동
            }
        }
    }

    //입력받은 비밀번호를 SharedPreferences에 저장하는 함수
    // @param password 사용자가 입력한 비밀번호

    private fun savePassword(password: String) {
        // SharedPreferences 객체 생성 ("user_prefs" 이름으로 파일 생성)
        val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // SharedPreferences에 데이터를 저장하기 위한 에디터 객체 생성
        val editor = sharedPref.edit()
        editor.putString("password", password) // "password" 키로 비밀번호 저장
        editor.apply() // 저장 적용
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 뷰 바인딩 해제
    }
}
