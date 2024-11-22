package com.example.umc_wireframe.presentation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.ActivityMainBinding
import com.example.umc_wireframe.util.navigateWithClear

class MainActivity : AppCompatActivity(), NavColor {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController

    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNavigation()
        requestNotificationPermission()
    }

    private fun initNavigation() {
        // 네비게이션 호스트 설정
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        navController = navHostFragment.navController

        // 화면 전환에 따른 하단 네비게이션 바 가시성 설정
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.botNavMain.visibility = when (destination.id) {
                R.id.registerStep1Fragment,
                R.id.RegisterStep2Fragment,
                R.id.RegisterStep3Fragment,
                R.id.loginFragment -> View.GONE
                else -> View.VISIBLE
            }
        }

        // 하단 네비게이션 아이템 클릭 리스너 설정
        setBottomNavigationListeners()

        // 회원가입 토큰 기반 네비게이션 설정
        val token = getRegistrationToken()
        setNavGraph(token)
    }

    private fun setBottomNavigationListeners() {
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
    }

    private fun setNavGraph(token: String?) {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        if (!token.isNullOrEmpty()) {
            navGraph.setStartDestination(R.id.navi_home)
        } else {
            navGraph.setStartDestination(R.id.registerStep1Fragment)
        }
        navController.setGraph(navGraph, null)
    }

    private fun getRegistrationToken(): String? {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("registration_token", null)
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
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
