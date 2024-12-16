package com.example.umc_wireframe.presentation.community.communtiy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentCommunityBinding
import com.example.umc_wireframe.presentation.NavColor
import com.example.umc_wireframe.presentation.community.communtiy.adapter.CommunityListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CommunityFragment : Fragment() {
    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        CommunityListAdapter(clickListener = { id ->
            findNavController().navigate(
                resId = R.id.navi_postDetail,
                args = bundleOf("id" to "1")
            )
        })
    }

    private val viewModel: CommunityViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is NavColor) {
            context.setNavCommunity()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        fun initRecyclerView() {
            binding.rvCommunityList.layoutManager = LinearLayoutManager(requireContext())
            binding.rvCommunityList.adapter = listAdapter
        }

        fun initFloatingBtn() {
            binding.floatingActionButtonCommunity.setOnClickListener {
                findNavController().navigate(R.id.navi_postWrite)
            }
        }

        initRecyclerView()
        initFloatingBtn()
    }

    private fun initViewModel() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest { uiState ->
                    onBind(uiState)
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            communityPagingList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest {  pagingData ->
                    listAdapter.submitData(pagingData)
                }
        }
    }

    private suspend fun onBind(uiState: CommunityUiState) = with(binding) {
        listAdapter.submitData(uiState.list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}