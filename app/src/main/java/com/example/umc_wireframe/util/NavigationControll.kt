package com.example.umc_wireframe.util

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions


internal fun NavController.navigateWithClear(@IdRes resId: Int) {
    val navigationOptions = NavOptions.Builder()
        .setPopUpTo(destinationId = resId, inclusive = true, saveState = true)
        .setLaunchSingleTop(true)
        .build()

    navigate(resId, null, navigationOptions)
}
