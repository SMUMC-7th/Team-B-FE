package com.example.umc_wireframe.util

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class DailyAlarmWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val timeString = inputData.getString("time")
        val time = LocalDateTime.parse(timeString, DateTimeFormatter.ISO_DATE_TIME)

        setDailyAlarm(applicationContext, time)
        return Result.success()
    }
}

fun scheduleDailyAlarmWorker(context: Context, time: LocalDateTime) {
    val timeString = time.format(DateTimeFormatter.ISO_DATE_TIME)
    val inputData = workDataOf("time" to timeString)

    val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyAlarmWorker>(1, TimeUnit.DAYS)
        .setInitialDelay(calculateInitialDelay(time), TimeUnit.MILLISECONDS)
        .setInputData(inputData)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "DailyAlarmWork",  // 고유한 이름
        ExistingPeriodicWorkPolicy.REPLACE,  // 기존 작업을 덮어씀
        dailyWorkRequest
    )
}


fun calculateInitialDelay(time: LocalDateTime): Long {
    val now = LocalDateTime.now()
    return Duration.between(now, time).toMillis().coerceAtLeast(0)
}


fun cancelAlarmWorker(context: Context) {
    WorkManager.getInstance(context).cancelUniqueWork("DailyAlarmWork")
    cancelDailyAlarm(context)
}
