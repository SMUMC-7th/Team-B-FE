package com.example.umc_wireframe.presentation.community.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_wireframe.databinding.FragmentPostDetailBinding

class PostDetailFragment : Fragment() {
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        PostDetailCommentListAdapter()
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}