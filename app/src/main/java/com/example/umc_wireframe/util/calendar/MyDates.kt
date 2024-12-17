package com.example.umc_wireframe.util.calendar

import java.util.Calendar

object MyDates {

    fun generateDates(calendar: Calendar, imageMap: Map<Pair<Int, Int>, Map<Int, String?>>): List<MyDate> {
        val dates = mutableListOf<MyDate>()
        val cal = calendar.clone() as Calendar
        cal.set(Calendar.DAY_OF_MONTH, 1)

        val targetImageUrl3 = "https://i.pinimg.com/736x/41/4a/2d/414a2dcffdafbb06315e026de5d77074.jpg"
        val targetImageUrl7 = "https://i.pinimg.com/736x/c5/e4/81/c5e481327b05a918994a05aac1eec0a8.jpg"
        val targetImageUrl10 = "https://i.pinimg.com/474x/9d/58/96/9d5896d04f314727852a80724e9f9160.jpg"
        val targetImageUrl11 = "https://i.pinimg.com/474x/5b/c9/d7/5bc9d7294ba7c636c8a95cc072d77932.jpg"
        val targetImageUrl14 = "https://i.pinimg.com/236x/e6/d8/c2/e6d8c2b5499f291a43e1a50029288e6b.jpg"
        val targetImageUrl16 = "https://i.pinimg.com/736x/1b/35/8c/1b358ccc087f179cb8dfc3a4fd11ba7d.jpg"
        val targetImageUrl17 = "https://i.pinimg.com/736x/80/51/4a/80514adcc5d2a5d72b8189dc423918fe.jpg"

        // 달의 첫 번째 날의 요일 계산 (월요일이 시작일인 경우)
        val firstDayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7

        // 전월의 마지막 일로 채우기
        cal.add(Calendar.DAY_OF_MONTH, -firstDayOfWeek)
        for (i in 0 until firstDayOfWeek) {
            dates.add(MyDate(cal.time, null))
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }

        // 현재 달의 일자 추가
        val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 0 until daysInMonth) {
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            // 특정 날짜에 이미지를 강제로 설정 (16일과 17일)
            val imageResId = when (day) {
                3 -> targetImageUrl3
                7 -> targetImageUrl7
                10 -> targetImageUrl10
                11 -> targetImageUrl11
                14 -> targetImageUrl14
                16 -> targetImageUrl16
                17 -> targetImageUrl17
                else -> imageMap[Pair(year, month)]?.get(day)?.first()
            }

            // 현재 달의 일자를 MyDate에 추가
            dates.add(MyDate(cal.time, imageResId.toString()))
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }

        // 다음 달의 처음 일로 채우기
        val lastDayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY + 6) % 7
        val remainingDays = 6 - lastDayOfWeek

        // 마지막 날이 일요일이 아니거나, 마지막 날이 일요일이지만 다음 달 1일이 월요일이 아닌 경우에만 채우기
        if (lastDayOfWeek != 6 || (remainingDays == 0 && cal.get(Calendar.DAY_OF_MONTH) != 1)) {
            for (i in 0 until remainingDays) {
                dates.add(MyDate(cal.time, null))
                cal.add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        return dates
    }
}
