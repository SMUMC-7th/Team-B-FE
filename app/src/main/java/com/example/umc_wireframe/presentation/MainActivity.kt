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
import androidx.navigation.fragment.NavHostFragment
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.ActivityMainBinding
import com.example.umc_wireframe.util.navigateWithClear
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNavigation()
        getLocationPermission()
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        val navController = navHostFragment.navController

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
            // 권한 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }
    }

}