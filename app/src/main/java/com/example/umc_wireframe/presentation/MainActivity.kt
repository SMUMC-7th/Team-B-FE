package com.example.umc_wireframe.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
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
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNavigation()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocationPermission()
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        val navController = navHostFragment.navController

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
            when (item.itemId) {
                R.id.menu_botNav_home -> {
                    navController.navigateWithClear(R.id.navi_home)
                    true
                }

                R.id.menu_botNav_calendar -> {
                    navController.navigateWithClear(R.id.navi_calendar)
                    true
                }

                R.id.menu_botNav_my -> {
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
                    Log.d("MainActivity", "Current Location: Latitude=${it.latitude}, Longitude=${it.longitude}")
                } ?: Log.d("MainActivity", "Location is null")
            }
        }
    }
}
