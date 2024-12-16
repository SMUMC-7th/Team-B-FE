package com.example.umc_wireframe.presentation.community.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_wireframe.databinding.FragmentPostDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostDetailFragment : Fragment() {
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        PostDetailCommentListAdapter()
    }

    private val viewModel: PostDetailViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("id")?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        fun initRv() {
            binding.rvPostDetailComment.run {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = listAdapter
            }
        }

        initRv()
    }

    private fun initViewModel() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest { uiState ->
                    onBind(uiState)
                }
        }
    }

    private suspend fun onBind(uiState: PostDetailUiState) = with(binding) {
        when (uiState) {
            PostDetailUiState.fail -> {
                tvPostTitle.text = "게시글을 찾을 수 없습니다."
                tvPostDescription.text = "게시글을 찾을 수 없습니다."
            }

            PostDetailUiState.init -> {}
            is PostDetailUiState.success -> {
                uiState.let {
                    tvPostTitle.text = it.title
                    tvPostDescription.text = it.content
                    tvPostPosterNickname.text = it.wirter
                    listAdapter.submitData(it.commentList)
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}