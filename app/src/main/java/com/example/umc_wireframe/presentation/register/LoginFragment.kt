package com.example.umc_wireframe.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentLoginBinding
import com.example.umc_wireframe.presentation.home.HomeViewModel
import com.example.umc_wireframe.util.SharedPreferencesManager

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvError.visibility = View.GONE

        binding.loginButton.setOnClickListener {
            val email = binding.tvEmailInput.text.toString().trim()
            val password = binding.tvPasswordInput.text.toString().trim()

            validateLogin(email, password)
        }

        binding.tvLoginJoin.setOnClickListener {
            findNavController().navigate(R.id.registerStep1Fragment)
        }
    }

    private fun validateLogin(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            binding.tvError.text = "이메일 또는 비밀번호를 입력해주세요."
            binding.tvError.visibility = View.VISIBLE
            return
        }

        // 유효한 입력일 경우 HomeFragment로 이동
        loginViewModel.postLogin(
           email = binding.tvEmailInput.text.toString(),
            password = binding.tvPasswordInput.text.toString(),
            isSuccess = {
                val tokenManager = SharedPreferencesManager(requireContext())
                val (accessToken, refreshToken) = tokenManager.getAccessToken() to tokenManager.getRefreshToken()

                accessToken?.let {
                    refreshToken?.let {
                        homeViewModel.login(
                            accessToken = accessToken,
                            refreshToken = refreshToken
                        )
                    }
                }
            }
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
