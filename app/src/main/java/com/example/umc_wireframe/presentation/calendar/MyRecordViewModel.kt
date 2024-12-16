package com.example.umc_wireframe.presentation.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MyRecordViewModel(private val repository: RecordRepository) : ViewModel() {
    private val _diaryCards = MutableLiveData<List<Ootd>>()
    val diaryCards: LiveData<List<Ootd>> get() = _diaryCards

    fun loadDiaryCards(year: Int, month: Int) {
        Timber.tag("MyRecordViewModel").d("loadDiaryCards called with year: $year, month: $month")

        viewModelScope.launch {
            val response = repository.getMyRecord(year, month)
            response.enqueue(object : Callback<OOTDResponse> {
                override fun onResponse(
                    call: Call<OOTDResponse>,
                    response: Response<OOTDResponse>
                ) {
                    if (response.isSuccessful) {
                        val cardList = response.body()?.result?.ootds ?: emptyList()
                        _diaryCards.value = cardList
                        Timber.tag("MyRecordViewModel").d("API Success: Received ${cardList.size} diary cards")
                        Timber.tag("MyRecordViewModel").d("API Success: Received $cardList")

                    } else {
                        // 오류 처리
                        val errorMessage = response.message()
                        handleError(errorMessage)
                        Timber.tag("MyRecordViewModel").d("API Error: $errorMessage")
                    }
                }

                override fun onFailure(call: Call<OOTDResponse>, t: Throwable) {
                    // 네트워크 오류 처리
                    val errorMessage = t.message ?: "Unknown error"
                    handleError(errorMessage)
                    Timber.tag("MyRecordViewModel").d("Network Failure: $errorMessage")
                }
            })
        }
    }

    private fun handleError(message: String) {
        Timber.tag("MyRecordViewModel").d("Error: $message")
        // 오류 메시지 처리 로직 (예: 사용자에게 알림, UI 업데이트 등)
    }
}