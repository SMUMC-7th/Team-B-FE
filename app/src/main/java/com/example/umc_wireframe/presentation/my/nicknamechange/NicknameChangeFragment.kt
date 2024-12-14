package com.example.umc_wireframe.presentation.my.nicknamechange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.databinding.FragmentNicknameChangeBinding

class NicknameChangeFragment : Fragment() {

    private var _binding: FragmentNicknameChangeBinding? = null
    private val binding get() = _binding!!


    private val viewModel: NicknameChangeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNicknameChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() = with(binding) {
        backButton.setOnClickListener {
            findNavController().popBackStack() // 이전 화면으로 돌아감
        }

        fun onCompleteChange() {
            btnCompleteChange.setOnClickListener {
                val newNickname = nicknameEditText.text.toString()
                if (newNickname.isNotBlank()) {
                    viewModel.changeNickname(newNickname, isSuccess = { findNavController().popBackStack()})
                } else{
                    Toast.makeText(requireContext(), "입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
        onCompleteChange()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
