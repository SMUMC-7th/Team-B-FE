package com.example.umc_wireframe.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentHomeBinding
import com.example.umc_wireframe.domain.model.ShortTermRegionObject
import com.example.umc_wireframe.domain.model.getBusanRegions
import com.example.umc_wireframe.domain.model.getChungcheongbukdoRegions
import com.example.umc_wireframe.domain.model.getChungcheongnamdoRegions
import com.example.umc_wireframe.domain.model.getDaeguRegions
import com.example.umc_wireframe.domain.model.getDaejeonRegions
import com.example.umc_wireframe.domain.model.getGwangjuRegions
import com.example.umc_wireframe.domain.model.getGyeonggiRegions
import com.example.umc_wireframe.domain.model.getGyeongsangbukdoRegions
import com.example.umc_wireframe.domain.model.getGyeongsangnamdoRegions
import com.example.umc_wireframe.domain.model.getIncheonRegions
import com.example.umc_wireframe.domain.model.getJejuRegions
import com.example.umc_wireframe.domain.model.getJeollabukdoRegions
import com.example.umc_wireframe.domain.model.getJeollanamdoRegions
import com.example.umc_wireframe.domain.model.getSeoulRegions
import com.example.umc_wireframe.domain.model.getUlsanRegions
import com.example.umc_wireframe.domain.model.toShorTermRegion
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val homeSelectLocationListAdapter: HomeSelectLocationListAdapter by lazy {
        HomeSelectLocationListAdapter(
            clickListener = { clickLocationObject ->
                homeSelectLocationListAdapter.submitList(getRegionObject(clickLocationObject.region))
            },
            selectLocationListener = { selectLocationObject ->
                viewModel.getShortTermForecast(selectLocationObject)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectLocation()
        initViewModel()
    }

    private fun selectLocation() = with(binding) {
        rvHomeLocalSelection.adapter = homeSelectLocationListAdapter
        rvHomeLocalSelection.layoutManager = GridLayoutManager(requireContext(), 2)

        tvHomeLocalSelection.setOnClickListener {
            val list = requireContext().resources.getStringArray(R.array.location_list).toList()
            homeSelectLocationListAdapter.submitList(list.toShorTermRegion())

            if (rvHomeLocalSelection.isVisible) {
                rvHomeLocalSelection.visibility = View.GONE
            } else rvHomeLocalSelection.visibility = View.VISIBLE
        }

    }

    private fun initViewModel() = with(viewModel) {
        lifecycleScope.launch {
            uiState.collectLatest { uiState ->
                bind(uiState)
            }
        }
    }

    private fun bind(uiState: HomeUiState) = with(binding) {
        uiState.selectLocation?.let {
            tvHomeLocalSelection.text = uiState.selectLocation.region
            tvHomeWeatherDescription.text = uiState.temp + ", " + uiState.pop + ", " + uiState.pcp
        }
    }


    private fun getRegionObject(region: String): List<ShortTermRegionObject> {
        return when (region) {
            "서울" -> getSeoulRegions()
            "부산" -> getBusanRegions()
            "대구" -> getDaeguRegions()
            "인천" -> getIncheonRegions()
            "광주" -> getGwangjuRegions()
            "대전" -> getDaejeonRegions()
            "울산" -> getUlsanRegions()
            "세종" -> listOf(ShortTermRegionObject.SejongCity)
            "경기도" -> getGyeonggiRegions()
            "충청북도" -> getChungcheongbukdoRegions()
            "충청남도" -> getChungcheongnamdoRegions()
            "전라북도" -> getJeollabukdoRegions()
            "전라남도" -> getJeollanamdoRegions()
            "경상북도" -> getGyeongsangbukdoRegions()
            "경상남도" -> getGyeongsangnamdoRegions()
            "제주" -> getJejuRegions()
            else -> emptyList() // 해당 지역이 없을 경우 빈 리스트 반환
        }
    }
}