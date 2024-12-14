/*
package com.example.umc_wireframe.presentation.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.data.remote.JoinInfo
import com.example.umc_wireframe.databinding.FragmentRegisterStep2Binding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterStep2Fragment : Fragment() {

    private var _binding: FragmentRegisterStep2Binding? = null
    private val binding get() = _binding!!
    private var isFragmentActive = true
    private var isNavigated = false
    private lateinit var serverDatasource: ServerDatasource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterStep2Binding.inflate(inflater, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://43.202.248.120:8080/") // 서버 URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        serverDatasource = retrofit.create(ServerDatasource::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val name = binding.tvNameInput.text.toString().trim()
            val nickname = binding.tvNicknameInput.text.toString().trim()
            val gender = when {
                binding.maleRadioButton.isChecked -> "MALE"
                binding.femaleRadioButton.isChecked -> "FEMALE"
                else -> ""
            }

            if (name.isEmpty() || nickname.isEmpty() || gender.isEmpty()) {
                showToast("모든 정보를 입력해주세요.")
                return@setOnClickListener
            }

            val email = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                .getString("email", "") ?: ""

            if (email.isEmpty()) {
                showToast("이메일 정보가 누락되었습니다. 이전 단계를 확인해주세요.")
                return@setOnClickListener
            }

            val joinInfo = JoinInfo(email, name, nickname, gender)

            // 서버에 요청 보내기
            lifecycleScope.launch {
                try {
                    val response = serverDatasource.postJoinSuccess(joinInfo)
                    if (response.isSuccess == true && !isNavigated) {
                        isNavigated = true
                        // 성공 메시지 표시
                        showToast("회원가입이 완료되었습니다.")
                        // 다음 단계로 이동
                        findNavController().navigate(R.id.action_registerStep2Fragment_to_registerStep3Fragment)
                    } else {
                        showToast(response.message ?: "회원가입에 실패했습니다.")
                    }
                } catch (e: Exception) {
                    // 예외 처리
                    showToast("오류 발생: ${e.message}")
                }
            }
        }
    }

    private fun showToast(message: String) {
        if (isFragmentActive) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFragmentActive = false
        _binding = null
    }
}
*/
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
