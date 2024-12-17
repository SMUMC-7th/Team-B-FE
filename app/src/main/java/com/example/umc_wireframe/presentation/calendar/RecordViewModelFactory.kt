package com.example.umc_wireframe.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber

class RecordViewModelFactory(private val repository: RecordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyRecordViewModel::class.java)) {
            Timber.tag("RecordViewModelFactory").d("Creating MyRecordViewModel instance")
            @Suppress("UNCHECKED_CAST")
            return MyRecordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}