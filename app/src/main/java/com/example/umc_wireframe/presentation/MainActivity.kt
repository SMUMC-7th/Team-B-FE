package com.example.umc_wireframe.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.ActivityMainBinding
import com.example.umc_wireframe.presentation.home.HomeViewModel
import com.example.umc_wireframe.presentation.home.LoginState
import com.example.umc_wireframe.util.SharedPreferencesManager
import com.example.umc_wireframe.util.cancelAlarmWorker
import com.example.umc_wireframe.util.navigateWithClear
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.kakao.sdk.common.util.Utility


class MainActivity : AppCompatActivity(), NavColor {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController

    val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var keyHash = Utility.getKeyHash(this)
        Log.d("key", keyHash)

        initNavigation()

        initViewModel()
        requestNotificationPermission()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navi_my,
                R.id.navi_home,
                R.id.navi_calendar -> binding.botNavMain.visibility = View.VISIBLE

                else -> binding.botNavMain.visibility = View.GONE
            }
        }

        // 하단 네비게이션 아이템 클릭 리스너 설정
        setBottomNavigationListeners()

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

    private fun initViewModel() = with(viewModel) {
        lifecycleScope.launch {
            viewModel.loginState.collectLatest { loginState ->
                val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
                val tokenManager = SharedPreferencesManager(this@MainActivity)
                when (loginState) {
                    LoginState.Init -> {
                        val (accessToken, refreshToken) = tokenManager.getAccessToken() to tokenManager.getRefreshToken()
                        accessToken?.let {
                            refreshToken?.let {
                                viewModel.login(
                                    accessToken = accessToken,
                                    refreshToken = refreshToken
                                )
                            }
                        }
                        if (accessToken.isNullOrBlank() && refreshToken.isNullOrBlank()) {
                            viewModel.logout()
                        }
                    }

                    is LoginState.Login -> {
                        navGraph.setStartDestination(R.id.navi_home)
                        navController.setGraph(navGraph, null)
                        viewModel.setAlarm()
                    }

                    LoginState.LoginRequire -> {
                        cancelAlarmWorker(UmcClothsOfTempApplication.context)
                        tokenManager.clearAll()
                        navGraph.setStartDestination(R.id.nav_login)
                        navController.setGraph(navGraph, null)
                    }
                }
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
