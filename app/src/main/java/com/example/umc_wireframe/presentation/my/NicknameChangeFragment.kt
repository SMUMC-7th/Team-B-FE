package com.example.umc_wireframe.presentation.my

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.data.remote.NewNickname
import com.example.umc_wireframe.databinding.FragmentNicknameChangeBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NicknameChangeFragment : Fragment() {

    private var _binding: FragmentNicknameChangeBinding? = null
    private val binding get() = _binding!!

    private lateinit var serverDatasource: ServerDatasource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNicknameChangeBinding.inflate(inflater, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://43.202.248.120:8080/") // 서버 URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        serverDatasource = retrofit.create(ServerDatasource::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        // 닉네임 변경 버튼
        binding.btnCompleteChange.setOnClickListener {
            val newNickname = binding.nicknameEditText.text.toString().trim()
            if (newNickname.isNotBlank()) {
                changeNickname(newNickname)
            } else {
                showToast("닉네임을 입력해 주세요.")
            }
        }
    }

    private fun changeNickname(newNickname: String) {
        lifecycleScope.launch {
            try {
                // Authorization 헤더 가져오기 (예: SharedPreferences에서 저장된 토큰)
                val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val token = sharedPref.getString("token", null)

                if (token.isNullOrEmpty()) {
                    showToast("인증 토큰이 없습니다. 다시 로그인해 주세요.")
                    return@launch
                }

                // 서버 요청
                val response = serverDatasource.patchNicknameChange(
                    newNickname = NewNickname(newNickname)
                )

                if (response.isSuccess==true) {
                    showToast("닉네임이 성공적으로 변경되었습니다.")
                    binding.nicknameEditText.text.clear()
                    findNavController().navigateUp()
                } else {
                    showToast(response.message ?: "닉네임 변경에 실패했습니다.")
                }
            } catch (e: Exception) {
                showToast("오류 발생: ${e.message}")
            }
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
