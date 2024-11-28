package com.example.umc_wireframe.util

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.umc_wireframe.R
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.presentation.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Notification Channel 설정 (Android 8.0 이상)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "daily_alarm", "Daily Alarm", NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Coroutine을 사용하여 비동기 작업 처리
        CoroutineScope(Dispatchers.IO).launch {
            // 현재 날짜 가져오기
            val now = LocalDate.now().minusDays(1)
            val baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

            // 현재 위치 가져오기
            val location = getLastLocation(context)

            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude

                // 좌표 변환 및 날씨 데이터 가져오기
                CoordinateConverter().convertToXy(latitude, longitude).run {
                    val entity =
                        RepositoryFactory.createShortTermForecastRepository().getMaxMinTemp(
                            pageNo = 1,
                            baseDate = baseDate,
                            baseTime = "2300",  // 예시 시간 (필요시 다른 시간으로 변경)
                            nx = nx,
                            ny = ny,
                            numOfRows = 288
                        )?.body?.items

                    entity?.let { items ->
                        val tempList = items.map { "${it.fcstDate} ${it.fcstTime}" to it.value }
                        val maxTemp = tempList.maxByOrNull { it.second }
                        val minTemp = tempList.minByOrNull { it.second }

                        // UI 업데이트는 Main Thread에서 처리해야 함
                        withContext(Dispatchers.Main) {
                            // 알림 텍스트 업데이트
                            val notificationText =
                                "최고 기온: ${maxTemp?.second}, 최저 기온: ${minTemp?.second}"

                            // 알림 생성
                            val notification = NotificationCompat.Builder(context, "daily_alarm")
                                .setContentTitle("Daily Alarm")
                                .setContentText(notificationText)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                                .build()

                            notificationManager.notify(1, notification)
                        }
                    }
                }
            }
        }
    }

    // 권한 요청 함수
    private fun requestLocationPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
    }

    // 위치 가져오는 코드 (Coroutine에서 사용)
    suspend private fun getLastLocation(context: Context): Location? {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return fusedLocationClient.lastLocation.await() // await() 사용
        } else {
            // 권한이 없으면 Activity로 권한 요청
            if (context is Activity) {
                requestLocationPermission(context)
            }
            return null
        }
    }
}


// Alarm 설정 함수
fun setDailyAlarm(context: Context, time: LocalDateTime) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // 알람 수신자 인텐트 생성
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // 알람을 시간 설정
    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, time.hour)
        set(Calendar.MINUTE, time.minute)
        set(Calendar.SECOND, 0)
    }

    // 설정된 시간이 현재 시간보다 이전일 경우 다음 날로 설정
    if (calendar.timeInMillis < System.currentTimeMillis()) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    // Android 12 이상에서 권한 설정 확인
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(
                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                Uri.parse("package:${context.packageName}")
            )
            context.startActivity(intent)
        }
    }

    // AlarmManager 설정
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
        )
    } else {
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
        )
    }
}


fun cancelDailyAlarm(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // 알람 수신자 인텐트 생성
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // AlarmManager에서 알람 취소
    alarmManager.cancel(pendingIntent)
}

