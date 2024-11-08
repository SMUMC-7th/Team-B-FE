package com.example.umc_wireframe.util

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.Calendar
import java.util.concurrent.TimeUnit

class DailyAlarmWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        setDailyAlarm(applicationContext)
        return Result.success()
    }
}

fun scheduleDailyAlarmWorker(context: Context) {
    val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyAlarmWorker>(1, TimeUnit.DAYS)
        .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "DailyAlarmWork",
        ExistingPeriodicWorkPolicy.REPLACE,
        dailyWorkRequest
    )
}

// 초기 지연 시간 계산 함수
fun calculateInitialDelay(): Long {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }
    if (calendar.timeInMillis < System.currentTimeMillis()) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    return calendar.timeInMillis - System.currentTimeMillis()
}
