package com.example.umc_wireframe.presentation.community.write

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentPostWriteBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostWriteFragment : Fragment() {
    private var _binding: FragmentPostWriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostWriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        fun btnActivation() {
            val list = listOf(
                etPostWriteTitle,
                etPostWriteContent
            )
            list.forEach {
                it.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        charSequence: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        charSequence: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        val isTitleValid = etPostWriteTitle.text.isNotBlank()
                        val isContentValid = etPostWriteContent.text.isNotBlank()

                        btnPostWriteRegist.isEnabled = isTitleValid && isContentValid
                    }

                    override fun afterTextChanged(editable: Editable?) {
                    }
                })
            }

            btnPostWriteRegist.setOnClickListener {
                viewModel.postPost(
                    title = etPostWriteTitle.text.toString(),
                    content = etPostWriteContent.text.toString()
                )
            }
        }


        btnActivation()
    }

    private fun initViewModel() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest { uiState ->
                    when (uiState) {
                        is PostWriteUiStates.init -> {}
                        is PostWriteUiStates.success -> {
                            findNavController().popBackStack()
                        }

                        is PostWriteUiStates.fail -> {
                            Toast.makeText(requireContext(), "오류", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}