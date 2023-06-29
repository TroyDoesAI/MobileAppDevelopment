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

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.YearMonth
import java.time.DateTimeException

internal class CalendarArrayTest {
  @Test
  fun generateCalendarArrayForApril2023Example() {
    val calendarArray: Array<IntArray> = CalendarArray().generate(YearMonth.of(2023, 4))
    val expectedArray = arrayOf(
      intArrayOf(0, 0, 0, 0, 0, 0, 1),
      intArrayOf(2, 3, 4, 5, 6, 7, 8),
      intArrayOf(9, 10, 11, 12, 13, 14, 15),
      intArrayOf(16, 17, 18, 19, 20, 21, 22),
      intArrayOf(23, 24, 25, 26, 27, 28, 29),
      intArrayOf(30, 0, 0, 0, 0, 0, 0)
    )
    Assertions.assertArrayEquals(expectedArray, calendarArray)
  }
}