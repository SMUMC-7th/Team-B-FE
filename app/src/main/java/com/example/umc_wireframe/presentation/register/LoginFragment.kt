package com.example.umc_wireframe.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
        findNavController().navigate(R.id.navi_home)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
