package com.example.umc_wireframe.presentation.my.alarm

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ListenableWorker.Result.Success
import com.example.umc_wireframe.domain.model.SetAlarm
import com.example.umc_wireframe.domain.repository.RepositoryFactory
import com.example.umc_wireframe.presentation.UmcClothsOfTempApplication
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SettingAlarmViewModel : ViewModel() {
    private val memberRepository = RepositoryFactory.createMemberRepository()

    fun setAlarm(isSet: Boolean, hour: Int, min: Int, isSuccess: () -> Unit) = viewModelScope.launch {
        val alarmStatus = if (isSet) SetAlarm.POST else SetAlarm.DELETE
        val alarmTime = LocalDateTime.now().withHour(hour).withMinute(min)

        try {
            memberRepository.patchAlarmSet(
                alarmTime = alarmTime,
                alarmStatus = alarmStatus
            )
            isSuccess()
        }catch (e:Exception){
            Toast.makeText(UmcClothsOfTempApplication.context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}