package com.example.umc_wireframe.util

import androidx.navigation.NavController
import com.example.umc_wireframe.R

internal fun NavController.navigateToHome() { navigate(resId = R.id.navi_home) }

internal fun NavController.navigateToMy() { navigate(resId = R.id.navi_my) }

internal fun NavController.navigateToCalendar() { navigate(resId = R.id.navi_calendar) }
