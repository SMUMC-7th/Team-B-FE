package com.example.umc_wireframe.util

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
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
    if (!isWorkerScheduled(context)) {  // 워커가 이미 등록되지 않은 경우에만
        val timeString = time.format(DateTimeFormatter.ISO_DATE_TIME)
        val inputData = workDataOf("time" to timeString)

        val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyAlarmWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "DailyAlarmWork",  // 고유한 이름
            ExistingPeriodicWorkPolicy.REPLACE,  // 기존 작업을 덮어씀
            dailyWorkRequest
        )
    }
}

fun isWorkerScheduled(context: Context): Boolean {
    val workInfo = WorkManager.getInstance(context)
        .getWorkInfosForUniqueWork("DailyAlarmWork")
        .get()
        .firstOrNull()

    return workInfo != null && workInfo.state.isFinished.not()
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

fun cancelDailyAlarmWorker(context: Context) {
    WorkManager.getInstance(context).cancelUniqueWork("DailyAlarmWork")
    cancelDailyAlarm(context)
}
