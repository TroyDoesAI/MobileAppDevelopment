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

fun generateExpectedCalendarArray(yearMonth: YearMonth): Array<IntArray> {
  val firstDayOfMonth = yearMonth.atDay(1)
  val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // Adjust the first day of the week to match your calendar
  val daysInMonth = yearMonth.lengthOfMonth()
  val expectedArray = Array(6) { IntArray(7) { 0 } } // Initialize the calendar with 0's
  var day = 1

  for (i in 0 until 6) { // Range Loops until Ref: https://kotlinlang.org/docs/ranges.html
    for (j in 0 until 7) {
      if (i == 0 && j < firstDayOfWeek) {
        // In the original function, these slots are 0
        expectedArray[i][j] = 0
      } else if (day <= daysInMonth) {
        expectedArray[i][j] = day++
      } else {
        // Once we run out of days in the month, fill in with 0's
        expectedArray[i][j] = 0
      }
    }
  }
  return expectedArray
}

internal class CalendarArrayTest {
  @Test
  fun generateCalendarArrayForApril2023_GivenAssignmentExample() {
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
  fun generateCalendarArrayForApril2023_GivenAssignmentExample_Using_generateExpectedCalendarArrayFunction() {
    val calendarArray: Array<IntArray> = CalendarArray().generate(YearMonth.of(2023, 4))
    val expectedArray = generateExpectedCalendarArray(YearMonth.of(2023, 4))
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
  fun generateCalendarArrayForLeapYearFeb2028() {
    val calendarArray: Array<IntArray> = CalendarArray().generate(YearMonth.of(2028, 2))
    val expectedArray = generateExpectedCalendarArray(YearMonth.of(2028, 2))
    Assertions.assertArrayEquals(expectedArray, calendarArray)
  }

  @Test
  fun generateCalendarArrayForNonLeapYearFeb2023() {
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
  fun generateCalendarArrayForDecember2023() {
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

  @Test
  fun generateCalendarArrayForLeapYears() {
    val leapYears = listOf(2024, 2028, 2032, 2036) // Add more leap years as needed
    val months = (1..12).toList() // All months

    for (year in leapYears) {
      for (month in months) {
        val yearMonth = YearMonth.of(year, month)
        val calendarArray: Array<IntArray> = CalendarArray().generate(yearMonth)
        val expectedArray = generateExpectedCalendarArray(yearMonth)
        Assertions.assertArrayEquals(expectedArray, calendarArray)
      }
    }
  }

  @Test
  fun generateCalendarArrayForNonLeapYears() {
    val nonLeapYears = listOf(2023, 2025, 2026, 2027) // Add more non-leap years as needed
    val months = (1..12).toList() // All months

    for (year in nonLeapYears) {
      for (month in months) {
        val yearMonth = YearMonth.of(year, month)
        val calendarArray: Array<IntArray> = CalendarArray().generate(yearMonth)
        val expectedArray = generateExpectedCalendarArray(yearMonth)
        Assertions.assertArrayEquals(expectedArray, calendarArray)
      }
    }
  }

  @Test
  fun generateCalendarArrayForInvalidMonth() {
    Assertions.assertThrows(DateTimeException::class.java) {
      CalendarArray().generate(YearMonth.of(2023, 13))
    }
  }
}
