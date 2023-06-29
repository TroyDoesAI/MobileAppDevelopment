/*
#######################################################################
#
# Copyright (C) 2022-2023 David C. Harrison. All right reserved.
#
# You may not use, distribute, publish, or modify this code without
# the express written permission of the copyright holder.
#
#######################################################################
*/

import java.time.YearMonth
import kotlin.math.ceil

class CalendarArray {
  fun generate(yearMonth: YearMonth): Array<IntArray> {
    val daysInMonth = yearMonth.lengthOfMonth()
    var firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value

    firstDayOfWeek = if (firstDayOfWeek == 7) 0 else firstDayOfWeek
    val weeks = ceil((daysInMonth + firstDayOfWeek - 1) / 7.0).toInt().coerceAtLeast(6)
    val calendarArray = Array(weeks) { IntArray(7) }

    var day = 1
    for (week in 0 until weeks) {
      for (dayOfWeek in 0 until 7) {
        if (week == 0 && dayOfWeek < firstDayOfWeek) {
          calendarArray[week][dayOfWeek] = 0
        } else if (day <= daysInMonth) {
          calendarArray[week][dayOfWeek] = day++
        } else {
          calendarArray[week][dayOfWeek] = 0
        }
      }
    }

    return calendarArray
  }
}



