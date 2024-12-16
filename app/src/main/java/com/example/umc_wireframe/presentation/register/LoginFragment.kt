package com.example.umc_wireframe.presentation.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentLoginBinding
import com.example.umc_wireframe.presentation.home.HomeViewModel
import com.example.umc_wireframe.presentation.my.MyViewModel
import com.example.umc_wireframe.util.SharedPreferencesManager
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val myViewModel: MyViewModel by activityViewModels()


    // 카카오계정 로그인 공통 callback
    private val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("LoginFragment", "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i("LoginFragment", "카카오계정으로 로그인 성공 ${token.accessToken}")
            goToHomeFragment()
        }
    }

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

        // 기존 이메일 로그인 버튼
        binding.loginButton.setOnClickListener {
            val email = binding.tvEmailInput.text.toString().trim()
            val password = binding.tvPasswordInput.text.toString().trim()

            validateLogin(email, password)
        }

        // 카카오 로그인 버튼
        binding.kakaoLoginButton.setOnClickListener {
            performKakaoLogin()
        }

        binding.tvLoginJoin.setOnClickListener {
            findNavController().navigate(R.id.registerStep1Fragment)
        }
    }

    // 카카오톡으로 로그인 시도함
    private fun performKakaoLogin() {
        // 카카오톡이 설치되어 있는 경우
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) {
                    Log.e("LoginFragment", "카카오톡으로 로그인 실패", error)

                    // 사용자가 로그인 취소한 경우
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡 로그인 실패 시, 카카오계정으로 로그인
                    fetchKakaoAccessToken()
                } else if (token != null) {
                    Log.i("LoginFragment", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    fetchKakaoUserInfo()
                }
            }
        } else {
            // 카카오톡이 설치되어 있지 않은 경우, 카카오계정으로 로그인
            fetchKakaoAccessToken()
        }
    }

    // 카카오톡으로 로그인 시도 실패 시 여기로 옴 (카카오 계정으로 로그인 전환)
    private fun fetchKakaoAccessToken() {
        UserApiClient.instance.loginWithKakaoAccount(requireContext()) { token, error ->
            if (error != null) {
                Log.e("LoginFragment", "카카오 로그인 실패: ${error.message}")
            } else if (token != null) {
                val accessToken = token.accessToken
                Log.d("AccessToken", "Token: $accessToken")

                Log.i("LoginFragment", "카카오 로그인 성공. AccessToken: $accessToken")

                // ViewModel을 통해 서버에 Access Token 전달
                myViewModel.sendKakaoAccessToken(
                    accessToken = accessToken,
                    onSuccess = {
                        Log.i("LoginFragment", "서버 인증 성공")
                        fetchKakaoUserInfo()
                    },
                    onFailure = { errorMessage ->
                        Log.e("LoginFragment", errorMessage)
                    }
                )
            }
        }
    }

    // 서버 인증 성공 시 아래 함수 호출
    private fun fetchKakaoUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("LoginFragment", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                val nickname = user.kakaoAccount?.profile?.nickname ?: "사용자"
                val profileImageUrl = user.kakaoAccount?.profile?.thumbnailImageUrl ?: ""

                // MyViewModel에 사용자 정보 업데이트
                val myViewModel: MyViewModel by activityViewModels()
                myViewModel.updateKakaoUserInfo(nickname, profileImageUrl)

                Log.i("LoginFragment", "사용자 정보 요청 성공")
                goToHomeFragment()
            }
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

    private fun goToHomeFragment() {
        Log.i("LoginFragment", "로그인 성공, HomeFragment로 이동")
        findNavController().navigate(R.id.navi_home)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
