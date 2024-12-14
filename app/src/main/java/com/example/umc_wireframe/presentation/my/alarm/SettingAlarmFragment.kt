package com.example.umc_wireframe.presentation.my.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentSettingAlarmBinding
import com.example.umc_wireframe.presentation.my.MyUiState
import com.example.umc_wireframe.util.scheduleDailyAlarmWorker
import java.time.LocalDate
import java.time.LocalDateTime

class SettingAlarmFragment : Fragment() {
    private var _binding: FragmentSettingAlarmBinding? = null
    private val binding: FragmentSettingAlarmBinding get() = _binding!!

    private val viewModel: SettingAlarmViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {
        arguments?.getSerializable(getString(R.string.AlarmState))?.let { serializableState ->
            val alarmState = serializableState as? MyUiState.AlarmState
            alarmState?.let {
                scAlarm.isChecked = it.isSet

                if (it.isSet) {
                    clAlarmContent.visibility = View.VISIBLE
                } else {
                    clAlarmContent.visibility = View.GONE
                }

                timePickerAlarm.hour = it.hour
                timePickerAlarm.minute = it.min
            }
        }

        scAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // ON 상태일 때 실행할 로직
                clAlarmContent.visibility = View.VISIBLE
            } else {
                // OFF 상태일 때 실행할 로직
                clAlarmContent.visibility = View.GONE
            }
        }

        btnAlarmComplete.setOnClickListener {
            if (scAlarm.isChecked) {
                val hour = binding.timePickerAlarm.currentHour
                val minute = binding.timePickerAlarm.currentMinute

                val currentDate = LocalDate.now()

                val selectedDateTime = LocalDateTime.of(
                    currentDate.year,
                    currentDate.monthValue,
                    currentDate.dayOfMonth,
                    hour,
                    minute
                )

                scheduleDailyAlarmWorker(requireContext(), selectedDateTime)
            }

            viewModel.setAlarm(
                isSet = scAlarm.isChecked,
                hour = binding.timePickerAlarm.currentHour,
                min = binding.timePickerAlarm.currentMinute,
                isSuccess = {
                    findNavController().popBackStack()
                }
            )
        }

        backButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}