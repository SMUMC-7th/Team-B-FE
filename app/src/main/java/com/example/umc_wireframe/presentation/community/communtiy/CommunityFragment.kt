package com.example.umc_wireframe.presentation.community.communtiy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentCommunityBinding
import com.example.umc_wireframe.presentation.NavColor

class CommunityFragment : Fragment() {
    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        CommunityListAdapter(clickListener = { id ->
            findNavController().navigate(
                resId = R.id.navi_postDetail,
                args = bundleOf("id" to id)
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
    }

    private fun initView() {
        fun initRecyclerView() {
            binding.rvCommunityList.layoutManager = LinearLayoutManager(requireContext())
            binding.rvCommunityList.adapter = listAdapter
        }

        fun initFloatingBtn() {

        }

        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}