package com.example.umc_wireframe.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.umc_wireframe.databinding.ActivityMainBinding
import com.example.umc_wireframe.domain.model.MidTermWideRegion
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val fusedLocationClient: FusedLocationProviderClient by lazy{
        LocationServices.getFusedLocationProviderClient(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        getCurrentLocation()

        viewModel.getMidTermForecast(MidTermWideRegion.SEOUL_INCHEON_GYEONGGI)
        initViewModel()
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val coordinatesXy = CoordinateConverter().convertToXy(location.latitude,location.longitude)
                viewModel.getShortTermForecast(coordinatesXy.nx, coordinatesXy.ny) // 위치 정보 전달
            } ?: run {
                binding.tvMainTest.text = "location fetch error"
            }
        }
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                Log.d("dd", uiState.toString())
                binding.tvMainTest.text = uiState.toString()
            }
        }
    }
}