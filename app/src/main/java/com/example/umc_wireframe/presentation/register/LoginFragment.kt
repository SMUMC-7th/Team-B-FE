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
import com.example.umc_wireframe.data.remote.AccountRequest
import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var serverDatasource: ServerDatasource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://43.202.248.120:8080/") // 서버 URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        serverDatasource = retrofit.create(ServerDatasource::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvError.visibility = View.GONE

        // SharedPreferences에서 저장된 이메일과 비밀번호 가져오기
        val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val registeredEmail = sharedPref.getString("email", null)
        val registeredPassword = sharedPref.getString("password", null)

        binding.loginButton.setOnClickListener {
            val email = binding.tvEmailInput.text.toString().trim()
            val password = binding.tvPasswordInput.text.toString().trim()

            // 입력한 정보와 저장된 정보 비교
            if (email == registeredEmail && password == registeredPassword) {
                // 정보가 일치하면 로그인 요청
                validateAndLogin(email, password)
            } else {
                binding.tvError.text = "이메일 또는 비밀번호가 일치하지 않습니다."
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }

    private fun validateAndLogin(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            binding.tvError.text = "이메일 또는 비밀번호를 입력해주세요."
            binding.tvError.visibility = View.VISIBLE
            return
        }

        // 서버에 로그인 요청
        lifecycleScope.launch {
            try {
                val requestBody = AccountRequest(email, password)
                val response = serverDatasource.postLogin(requestBody)

                if (response.isSuccess == true) {
                    // 로그인 성공
                    saveTokens(response.result?.accessToken, response.result?.refreshToken)
                    Toast.makeText(requireContext(), "로그인 성공!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.navi_home)
                } else {
                    // 로그인 실패 메시지 표시
                    binding.tvError.text = response.message ?: "로그인에 실패했습니다."
                    binding.tvError.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                // 네트워크 또는 기타 오류 처리
                binding.tvError.text = "오류 발생: ${e.message}"
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }

    private fun saveTokens(accessToken: String?, refreshToken: String?) {
        if (!accessToken.isNullOrEmpty() && !refreshToken.isNullOrEmpty()) {
            val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("accessToken", accessToken)
                putString("refreshToken", refreshToken)
                apply()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
