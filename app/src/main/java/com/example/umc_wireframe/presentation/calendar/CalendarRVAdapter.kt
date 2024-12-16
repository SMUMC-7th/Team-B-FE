package com.example.umc_wireframe.presentation.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_wireframe.databinding.CalendarItemBinding
import com.example.umc_wireframe.util.calendar.MyDate
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarRVAdapter(
    private val dates: List<MyDate>,
    currentMonth: Int
) : RecyclerView.Adapter<CalendarRVAdapter.ViewHolder>() {

    private val thisMonth = currentMonth

    inner class ViewHolder(private val binding: CalendarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myDate: MyDate) {
            val date = myDate.date
            val imageResId = myDate.imageResId
            val calendar = Calendar.getInstance()
            calendar.time = date
            val month = calendar.get(Calendar.MONTH)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

            if (month != thisMonth) {
                binding.dayText.setTextColor(Color.LTGRAY)
            } else {
                when (dayOfWeek) {
                    Calendar.SATURDAY -> {
                        binding.dayText.setTextColor(Color.BLACK)
                    }
                    Calendar.SUNDAY -> {
                        binding.dayText.setTextColor(Color.RED)
                    }
                    else -> {
                        binding.dayText.setTextColor(Color.BLACK)
                    }
                }
            }

            // 이미지 설정
            if (!imageResId.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(imageResId)  // 이미지 URL을 Glide로 로드
                    .into(binding.myrecordImageView)  // ImageView에 이미지 설정
                binding.myrecordImageView.visibility = View.VISIBLE
            } else {
                binding.myrecordImageView.visibility = View.GONE
            }

            binding.dayText.text = SimpleDateFormat("d", Locale.getDefault()).format(date)
            binding.root.setOnClickListener {
                Timber.tag("CalendarRVAdapter").d("Date clicked: $date")

                // 날짜 클릭했을 때 기록탭 날짜 변경로직
//                onDateClickListener.onDateClick(date)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dates[position])
    }

    override fun getItemCount(): Int {
        return dates.size
    }
}