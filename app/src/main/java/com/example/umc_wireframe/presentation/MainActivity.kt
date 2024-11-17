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

    val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNavigation()

        requestNotificationPermission(this)
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

    fun requestNotificationPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
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
