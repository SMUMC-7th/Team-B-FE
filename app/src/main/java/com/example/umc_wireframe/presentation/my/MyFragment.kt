package com.example.umc_wireframe.presentation.my

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentMyBinding
import com.example.umc_wireframe.presentation.NavColor
import com.example.umc_wireframe.presentation.home.HomeViewModel
import com.example.umc_wireframe.util.SharedPreferencesManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyFragment : Fragment() {
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: MyViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavColor) {
            context.setNavMy()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 닉네임 변경 화면으로 이동
        binding.btnChangeNickname.setOnClickListener {
            findNavController().navigate(R.id.action_myFragment_to_nicknameChangeFragment)
        }

        // 비밀번호 변경 화면으로 이동
        binding.btnChangePassword.setOnClickListener {
            findNavController().navigate(R.id.action_myFragment_to_passwordChangeFragment)
        }

        // 알림 설정 화면으로 이동
        binding.btnNotificationSettings.setOnClickListener {
            val alarmState = viewModel.getMyAlarmState()
            val bundle = Bundle().apply {
                putSerializable(getString(R.string.AlarmState), alarmState)
            }
            findNavController().navigate(R.id.navi_alarm, bundle)
        }

        binding.logoutText.setOnClickListener {
            homeViewModel.logout()
        }

        binding.deleteAccountText.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("탈퇴하기")
                .setMessage("이 작업을 진행하시겠습니까?")
                .setPositiveButton("예") { dialog, _ ->
                    homeViewModel.withdraw()
                    SharedPreferencesManager(requireContext()).clearAll()
                    dialog.dismiss()
                }
                .setNegativeButton("아니오") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        // 사용자 프로필 정보 불러오기
        getMyProfile()
        initViewModel()
    }

    private fun getMyProfile() {
        viewModel.getMyProfile(
            isFailed = { homeViewModel.failedToken() }
        )
    }

    private fun initViewModel() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            uiState.collectLatest { uiState ->
                // 닉네임 업데이트
                binding.tvNickname.text = uiState.nickName

                // Glide를 사용해 프로필 이미지 표시
                Glide.with(this@MyFragment)
                    .load(uiState.profileImageUrl)
                    .placeholder(R.drawable.ic_profile_man) // 기본 이미지 설정
                    .circleCrop()
                    .into(binding.ivProfileMan)

                // 성별 아이콘 대신 Glide 이미지 표시 후 숨김 처리
                binding.ivProfileWoman.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
