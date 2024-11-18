package com.example.umc_wireframe.presentation.my

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentMyBinding
import com.example.umc_wireframe.presentation.NavColor

class MyFragment : Fragment() {

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavColor) {
            context.setNavMy()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SharedPreferences에서 저장된 정보 불러오기
        val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val savedNickname = sharedPref.getString("nickname", "기본 닉네임")
        val savedGender = sharedPref.getString("gender", "남자") // 성별 정보 포함

        // 닉네임 설정
        binding.tvNickname.text = savedNickname

        // 성별에 따른 프로필 이미지 설정
        if (savedGender == "남자") {
            binding.ivProfileMan.visibility = View.VISIBLE
            binding.ivProfileWoman.visibility = View.INVISIBLE
        } else {
            binding.ivProfileMan.visibility = View.INVISIBLE
            binding.ivProfileWoman.visibility = View.VISIBLE
        }

        // 닉네임 변경 화면으로 이동
        binding.btnChangeNickname.setOnClickListener {
            findNavController().navigate(R.id.action_myFragment_to_nicknameChangeFragment)
        }
        // 비밀번호 변경 화면으로 이동
        binding.btnChangePassword.setOnClickListener {
            findNavController().navigate(R.id.action_myFragment_to_passwordChangeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
