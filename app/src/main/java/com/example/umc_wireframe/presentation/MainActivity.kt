package com.example.umc_wireframe.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.ActivityMainBinding
import com.example.umc_wireframe.util.navigateWithClear
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity(), NavColor {
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

        val navItems = listOf(
            binding.navHome,
            binding.navCalendar,
            binding.navMypage
        )

        navItems.forEach { itemLayout ->
            itemLayout.setOnClickListener {
                val currentDestinationId = navController.currentDestination?.id

                when (itemLayout.id) {
                    binding.navHome.id -> {
                        if (currentDestinationId != R.id.menu_botNav_home)
                            navController.navigateWithClear(R.id.navi_home)
                    }

                    binding.navCalendar.id -> {
                        if (currentDestinationId != R.id.menu_botNav_calendar)
                            navController.navigateWithClear(R.id.navi_calendar)
                    }

                    binding.navMypage.id -> {
                        if (currentDestinationId != R.id.menu_botNav_my)
                            navController.navigateWithClear(R.id.navi_my)
                    }

                }
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



    private fun updateNavIconTint(selected: ImageView) {
        listOf(binding.ivNavHome, binding.ivNavMypage, binding.ivNavCalendar).forEach {
            it.setImageTintList(ColorStateList.valueOf(Color.GRAY))
        }
        selected.setImageTintList(ColorStateList.valueOf(Color.BLACK))
    }

    override fun setNavHome() {
        updateNavIconTint(binding.ivNavHome)
    }

    override fun seNavCalendar() {
        updateNavIconTint(binding.ivNavCalendar)
    }

    override fun setNavMy() {
        updateNavIconTint(binding.ivNavMypage)
    }

}
