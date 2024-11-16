package com.example.umc_wireframe.presentation.ootd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.umc_wireframe.databinding.FragmentHashtagBinding

class HashtagFragment : Fragment() {

    private var _binding: FragmentHashtagBinding? = null
    private val binding get() = _binding!!

    private val selectedHashtags = ArrayList<String>() // 선택된 해시태그 리스트

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHashtagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 해시태그 버튼 목록
        val buttons = listOf(
            binding.topButton1, binding.topButton2, binding.topButton3,
            binding.topButton4, binding.topButton5, binding.topButton6,
            binding.bottomButton1, binding.bottomButton2,binding.bottomButton3,
            binding.bottomButton4, binding.bottomButton5, binding.bottomButton6,
            binding.outerButton1, binding.outerButton2, binding.outerButton3,
            binding.outerButton4, binding.outerButton5, binding.outerButton6,
            binding.shoesButton1, binding.shoesButton2, binding.shoesButton3,
            binding.shoesButton4, binding.shoesButton5, binding.shoesButton6,
            binding.accessoryButton1, binding.accessoryButton2, binding.accessoryButton3,
            binding.accessoryButton4, binding.accessoryButton5, binding.accessoryButton6
        )

        // 각 버튼 클릭 리스너 설정
        buttons.forEach { button ->
            button.setOnClickListener {
                val hashtag = button.text.toString()

                if (selectedHashtags.contains(hashtag)) {
                    selectedHashtags.remove(hashtag) // 이미 선택된 해시태그는 제거
                } else {
                    if (selectedHashtags.size < 3) {
                        selectedHashtags.add(hashtag) // 최대 3개까지만 추가
                    } else {
                        Toast.makeText(context, "최대 3개의 해시태그만 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // 저장 버튼 클릭 리스너
        binding.saveButton.setOnClickListener {
            val result = Bundle()
            result.putStringArrayList("selectedHashtags", selectedHashtags) // 해시태그 전달
            parentFragmentManager.setFragmentResult("hashtagRequestKey", result)
            parentFragmentManager.popBackStack() // 이전 화면으로 돌아가기
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

