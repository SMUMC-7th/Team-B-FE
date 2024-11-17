package com.example.umc_wireframe.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.ActivityMainBinding
import com.example.umc_wireframe.util.navigateWithClear
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNavigation()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocationPermission()

        // "오늘의 OOTD 저장하기" 버튼 클릭 리스너 설정
        binding.saveOOTDButton.setOnClickListener {
            navigateToPhotoFragment()
        }

        // NavController를 사용해 버튼 가시성 조정
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        val navController = navHostFragment.navController

        // 특정 프래그먼트에서 버튼 가시성 변경
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navi_home -> binding.saveOOTDButton.visibility =
                    View.VISIBLE // 메인 페이지에서 버튼 보이기
                else -> binding.saveOOTDButton.visibility = View.GONE // 다른 페이지에서는 버튼 숨기기
            }
        }
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.registerStep1Fragment,
                R.id.RegisterStep2Fragment,
                R.id.RegisterStep3Fragment,
                R.id.loginFragment -> binding.botNavMain.visibility = View.GONE

                else -> binding.botNavMain.visibility = View.VISIBLE
            }
        }

        binding.botNavMain.setOnItemSelectedListener { item ->
            val currentDestinationId = navController.currentDestination?.id

            when (item.itemId) {
                R.id.menu_botNav_home -> {
                    if (currentDestinationId != R.id.menu_botNav_home)
                        navController.navigateWithClear(R.id.navi_home)
                    true
                }

                R.id.menu_botNav_calendar -> {
                    if (currentDestinationId != R.id.menu_botNav_calendar)
                        navController.navigateWithClear(R.id.navi_calendar)
                    true
                }

                R.id.menu_botNav_my -> {
                    if (currentDestinationId != R.id.menu_botNav_my)
                        navController.navigateWithClear(R.id.navi_my)
                    true
                }

                else -> false
            }
        }

        fun setNavGraph(isAlreadyLogin: Boolean) {
            val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
            if (isAlreadyLogin) {
                navGraph.setStartDestination(R.id.navi_home)
            } else {
                navGraph.setStartDestination(R.id.loginFragment)
            }
            navController.setGraph(navGraph, null)
        }

        setNavGraph(true)
    }

    private fun getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        } else {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    Log.d(
                        "MainActivity",
                        "Current Location: Latitude=${it.latitude}, Longitude=${it.longitude}"
                    )
                } ?: Log.d("MainActivity", "Location is null")
            }
        }
    }

    private fun navigateToPhotoFragment() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.navi_uploadOOTD) // photoFragment로 이동
    }

}
