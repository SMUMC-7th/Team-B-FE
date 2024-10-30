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
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.ActivityMainBinding
import com.example.umc_wireframe.util.navigateToCalendar
import com.example.umc_wireframe.util.navigateToHome
import com.example.umc_wireframe.util.navigateToMy
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentContainerViewMain.id) as NavHostFragment
        val navController = navHostFragment.navController

        binding.botNavMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_botNav_home -> {
                    navController.navigateToHome()
                    true
                }

                R.id.menu_botNav_calendar -> {
                    navController.navigateToCalendar()
                    true
                }

                R.id.menu_botNav_my -> {
                    navController.navigateToMy()
                    true
                }

                else -> false

            }
        }

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
                val coordinatesXy =
                    CoordinateConverter().convertToXy(location.latitude, location.longitude)
                viewModel.getShortTermForecast(coordinatesXy.nx, coordinatesXy.ny) // 위치 정보 전달
            } ?: run {
            }
        }
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                Log.d("result", uiState.toString())
            }
        }
    }
}