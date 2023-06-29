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

  @Test
  fun generateCalendarArrayForLeapYearFeb2024() {
    val calendarArray: Array<IntArray> = CalendarArray().generate(YearMonth.of(2024, 2))
    val expectedArray = arrayOf(
      intArrayOf(0, 0, 0, 0, 1, 2, 3),
      intArrayOf(4, 5, 6, 7, 8, 9, 10),
      intArrayOf(11, 12, 13, 14, 15, 16, 17),
      intArrayOf(18, 19, 20, 21, 22, 23, 24),
      intArrayOf(25, 26, 27, 28, 29, 0, 0),
      intArrayOf(0, 0, 0, 0, 0, 0, 0)
    )
    Assertions.assertArrayEquals(expectedArray, calendarArray)
  }

  @Test
  fun generateCalendarArrayForNonLeapYear() {
    val calendarArray: Array<IntArray> = CalendarArray().generate(YearMonth.of(2023, 2))
    val expectedArray = arrayOf(
      intArrayOf(0, 0, 0, 1, 2, 3, 4),
      intArrayOf(5, 6, 7, 8, 9, 10, 11),
      intArrayOf(12, 13, 14, 15, 16, 17, 18),
      intArrayOf(19, 20, 21, 22, 23, 24, 25),
      intArrayOf(26, 27, 28, 0, 0, 0, 0),
      intArrayOf(0, 0, 0, 0, 0, 0, 0)
    )
    Assertions.assertArrayEquals(expectedArray, calendarArray)
  }

  @Test
  fun generateCalendarArrayForDecember() {
    val calendarArray: Array<IntArray> = CalendarArray().generate(YearMonth.of(2023, 12))
    val expectedArray = arrayOf(
      intArrayOf(0, 0, 0, 0, 0, 1, 2),
      intArrayOf(3, 4, 5, 6, 7, 8, 9),
      intArrayOf(10, 11, 12, 13, 14, 15, 16),
      intArrayOf(17, 18, 19, 20, 21, 22, 23),
      intArrayOf(24, 25, 26, 27, 28, 29, 30),
      intArrayOf(31, 0, 0, 0, 0, 0, 0)
    )
    Assertions.assertArrayEquals(expectedArray, calendarArray)
  }
}