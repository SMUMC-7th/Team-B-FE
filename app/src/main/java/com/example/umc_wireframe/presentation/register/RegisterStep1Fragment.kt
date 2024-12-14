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
import com.example.umc_wireframe.data.remote.AccountRequest
import com.example.umc_wireframe.data.remote.JoinVerify
import com.example.umc_wireframe.databinding.FragmentRegisterStep1Binding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterStep1Fragment : Fragment() {

    private var _binding: FragmentRegisterStep1Binding? = null
    private val binding get() = _binding!!
    private lateinit var serverDatasource: ServerDatasource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterStep1Binding.inflate(inflater, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://43.202.248.120:8080/") // 서버 URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        serverDatasource = retrofit.create(ServerDatasource::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvGetVerificationNumber.setOnClickListener {
            val email = binding.tvEmailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showToast("이메일과 비밀번호를 모두 입력해주세요.")
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val requestBody = AccountRequest(email, password)
                    val response = serverDatasource.postJoinResquest(requestBody)
                    if (response.isSuccess == true) {
                        showToast("인증번호가 발송되었습니다. 이메일을 확인해주세요.")
                        binding.getVerificationCode.visibility = View.VISIBLE
                        binding.verifyCodeButton.visibility = View.VISIBLE
                    } else {
                        showToast(response.message ?: "인증번호 발송 실패. 다시 시도해주세요.")
                    }
                } catch (e: Exception) {
                    showToast("오류 발생: ${e.message}")
                }
            }
        }

        binding.verifyCodeButton.setOnClickListener {
            val email = binding.tvEmailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            val verificationCode = binding.getVerificationCode.text.toString().trim()

            if (verificationCode.isEmpty()) {
                showToast("인증번호를 입력해주세요.")
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val requestBody = JoinVerify(email, verificationCode)
                    val response = serverDatasource.postJoinVerify(requestBody)
                    if (response.isSuccess == true) {
                        showToast("인증이 완료되었습니다.")
                        saveEmailAndPassword(email, password)
                        findNavController().navigate(R.id.action_registerStep1Fragment_to_registerStep2Fragment)
                    } else {
                        showToast(response.message ?: "인증 실패. 다시 시도해주세요.")
                    }
                } catch (e: Exception) {
                    showToast("오류 발생: ${e.message}")
                }
            }
        }
    }

    private fun saveEmailAndPassword(email: String, password: String) {
        val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("email", email)
            putString("password", password)
            apply()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
