package com.example.umc_wireframe.presentation.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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

    private val viewModel: LoginViewModel by activityViewModels()

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

            viewModel.postJoinReq(email = email, password = password, isSuccess = {
                showToast("인증번호가 발송되었습니다. 이메일을 확인해주세요.")
                binding.getVerificationCode.visibility = View.VISIBLE
                binding.verifyCodeButton.visibility = View.VISIBLE
            })
        }

        binding.verifyCodeButton.setOnClickListener {
            val verificationCode = binding.getVerificationCode.text.toString().trim()

            if (verificationCode.isEmpty()) {
                showToast("인증번호를 입력해주세요.")
                return@setOnClickListener
            }
            viewModel.postJoinVerify(verificationCode, isSuccess = {findNavController().navigate(R.id.RegisterStep2Fragment)})
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