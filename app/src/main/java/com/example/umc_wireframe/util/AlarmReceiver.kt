package com.example.umc_wireframe.util

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.example.umc_wireframe.R
import com.example.umc_wireframe.presentation.MainActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // 알림 생성
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

        // Notification 설정
        val notification = NotificationCompat.Builder(context, "daily_alarm")
            .setContentTitle("Daily Alarm")
            .setContentText("오전 9시 알람입니다.")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}

// Alarm 설정 함수
fun setDailyAlarm(context: Context, time: LocalDateTime) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // 알람 수신자 인텐트 생성
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context, generateRequestCode(time), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // 알람을 시간 설정
    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, time.hour)
        set(Calendar.MINUTE, time.minute)
        set(Calendar.SECOND, time.second)
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

fun generateRequestCode(time: LocalDateTime): Int {
    val formattedTime = time.format(DateTimeFormatter.ISO_DATE_TIME)

    return formattedTime.hashCode()
}

fun cancelDailyAlarm(context: Context, time:LocalDateTime) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // 알람 수신자 인텐트 생성
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context, generateRequestCode(time), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // AlarmManager에서 알람 취소
    alarmManager.cancel(pendingIntent)
}

