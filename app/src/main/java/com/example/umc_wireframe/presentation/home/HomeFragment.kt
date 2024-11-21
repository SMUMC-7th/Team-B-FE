package com.example.umc_wireframe.presentation.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.location.Location
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentHomeBinding
import com.example.umc_wireframe.domain.model.ShortTermRegionObject
import com.example.umc_wireframe.domain.model.findRegionByCoordinates
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
import com.example.umc_wireframe.presentation.NavColor
import com.example.umc_wireframe.util.CoordinateConverter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private val viewModel: HomeViewModel by activityViewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLastLocation()
            } else {
                Toast.makeText(requireContext(), "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    private val homeSelectLocationListAdapter: HomeSelectLocationListAdapter by lazy {
        HomeSelectLocationListAdapter(
            clickListener = { clickLocationObject ->
                homeSelectLocationListAdapter.submitList(getRegionObject(clickLocationObject.region))
            },
            selectLocationListener = { selectLocationObject ->
                viewModel.getDailyShortTermForecast(selectLocationObject)
                binding.rvHomeLocalSelection.visibility = View.GONE
            }
        )
    }


    private val homeRecommendedClothesListAdapter: HomeRecommendedClothesListAdapter by lazy {
        HomeRecommendedClothesListAdapter()
    }

    private val homeTagListAdapter: HomeTagListAdapter by lazy {
        HomeTagListAdapter()
    }

    private val homeHistoryTagListAdapterList = listOf(
        HomeHistoryTagListAdapter(),
        HomeHistoryTagListAdapter()
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavColor) {
            context.setNavHome()
        }
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

        getCurrentLocation()
        initView()
        selectLocation()
        initViewModel()

    }


    private fun initView() = with(binding) {
        fun setClothyString() {
            val fullText = "그래서 클로디는"

            val spannableString = SpannableString(fullText)

            val targetStart = 4 // "클로디"의 시작 인덱스
            val targetEnd = 7   // "클로디"의 끝 인덱스

            // 오렌지색으로 설정
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#FFA500")),
                targetStart,
                targetEnd,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // 크기 24sp로 설정
            spannableString.setSpan(
                AbsoluteSizeSpan(24, true),
                targetStart,
                targetEnd,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            binding.tvHomeClothy1.text = spannableString
        }

        fun initRecommendedClothesRv() = with(binding.rvHomeRecommendedClothes) {
            adapter = homeRecommendedClothesListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        fun initRecommendedTagRv() = with(binding.rvHomeRecommendedTags) {
            adapter = homeTagListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        fun initHistoryTagRv() {
            binding.rvHomeMyHitory1.run {
                adapter = homeHistoryTagListAdapterList[0]
                layoutManager = LinearLayoutManager(requireContext())
            }

            binding.rvHomeMyHitory2.run {
                adapter = homeHistoryTagListAdapterList[1]
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        btnHomeOotd.setOnClickListener {
            findNavController().navigate(R.id.navi_uploadOOTD)
        }

        setClothyString()
        initRecommendedClothesRv()
        initRecommendedTagRv()
        initHistoryTagRv()
    }

    private fun selectLocation() = with(binding) {
        rvHomeLocalSelection.adapter = homeSelectLocationListAdapter
        rvHomeLocalSelection.layoutManager = GridLayoutManager(requireContext(), 2)

        clHomeLocalSelection.setOnClickListener {
            val list = requireContext().resources.getStringArray(R.array.location_list).toList()
            homeSelectLocationListAdapter.submitList(list.toShorTermRegion())

            if (rvHomeLocalSelection.isVisible) {
                rvHomeLocalSelection.visibility = View.GONE
                ivHomeSelectionArrow.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_arrowdown
                    )
                )
            } else {
                rvHomeLocalSelection.visibility = View.VISIBLE
                ivHomeSelectionArrow.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_arrow_up
                    )
                )
            }
        }

    }

    private fun initViewModel() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest { state ->
                    onBind(state)
                }
        }
    }

    private fun onBind(uiState: HomeUiState) = with(binding) {
        uiState.selectLocation?.let {
            tvHomeLocalSelection.text = uiState.selectLocation.region
        }

        uiState.let {
            val biggest = it.maxTemp.second
            val smallest = it.minTemp.second

            val spannableString = SpannableString("최저 $smallest°C 최고 $biggest°C의")

            // "최저"와 "최고" 텍스트에 대해 스타일 적용
            spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, 2, 0) // "최저"에 볼드 적용
            spannableString.setSpan(AbsoluteSizeSpan(12, true), 0, 2, 0) // "최저" 글자 크기 12sp 적용

            val startIndex = spannableString.indexOf("최고")
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                startIndex,
                startIndex + 2,
                0
            ) // "최고"에 볼드 적용
            spannableString.setSpan(
                AbsoluteSizeSpan(12, true),
                startIndex,
                startIndex + 2,
                0
            ) // "최고" 글자 크기 12sp 적용

            // "$smallest°C"에 대해 스타일 적용
            val smallestStart = 3
            val smallestEnd = smallestStart + smallest.length + 2  // "°C" 추가
            spannableString.setSpan(
                AbsoluteSizeSpan(24, true),
                smallestStart,
                smallestEnd,
                0
            ) // "$smallest°C" 글자 크기 24sp 적용
            spannableString.setSpan(
                ForegroundColorSpan(Color.BLUE),
                smallestStart,
                smallestEnd,
                0
            ) // "$smallest°C" 파란색 적용

            // "$biggest°C"에 대해 스타일 적용
            val biggestStart = smallestEnd + 4
            val biggestEnd = biggestStart + biggest.length + 2 // "°C" 추가
            spannableString.setSpan(
                AbsoluteSizeSpan(24, true),
                biggestStart,
                biggestEnd,
                0
            ) // "$biggest°C" 글자 크기 24sp 적용
            spannableString.setSpan(
                ForegroundColorSpan(Color.RED),
                biggestStart,
                biggestEnd,
                0
            ) // "$biggest°C" 빨간색 적용

            tvHomeWeatherDescriptionLine2.text = spannableString

            it.recommendedClothes.let { list ->
                val hashtag = list.map { it.hashtag }
                val clothesImgUrl = list.map { it.image }

                homeTagListAdapter.submitList(hashtag)
                homeRecommendedClothesListAdapter.submitList(clothesImgUrl)
            }




            it.historyList.let {
                if (it.size < 2) {
                    tvHomeNoHistory.visibility = View.VISIBLE
                    clHomeHistory.visibility = View.INVISIBLE
                } else {
                    tvHomeNoHistory.visibility = View.INVISIBLE
                    clHomeHistory.visibility = View.VISIBLE

                    Glide.with(requireContext())
                        .load(it[0].image) // 로드할 이미지 URL
                        .into(ivHomeMyHistory1)
                    tvHomeMyHistory1.text = it[0].date
                    homeHistoryTagListAdapterList[0].submitList(it[0].hashtags)

                    Glide.with(requireContext())
                        .load(it[1].image) // 로드할 이미지 URL
                        .into(ivHomeMyHistory2)
                    tvHomeMyHistory2.text = it[1].date
                    homeHistoryTagListAdapterList[1].submitList(it[1].hashtags)
                }
            }
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLastLocation()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnCompleteListener { task: Task<Location> ->
            val location: Location? = task.result
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                CoordinateConverter().convertToXy(latitude, longitude).run {
                    findRegionByCoordinates(nx, ny)?.let {
                        viewModel.getDailyShortTermForecast(it)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "위치를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
