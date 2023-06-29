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
import java.time.YearMonth // Ref : https://www.geeksforgeeks.org/java-time-yearmonth-class-in-java/#
import kotlin.math.ceil

class CalendarArray { // Auto Docstrings Credit : https://plugins.jetbrains.com/plugin/14778-kdoc-er--kotlin-doc-generator, Fixing my summary Wording and Grammar Ref: https://www.grammarly.com/, UNDERSTANDING LEAP YEARS Ref : https://www.almanac.com/content/when-next-leap-year
  /**
   * The `generate` function creates a two-dimensional array (6x7) representing a calendar month for a specific YearMonth.
   * Each element of the array corresponds to a day of the month, with zeroes representing days that fall outside the
   * given month (either from the previous month or the next month).
   *
   * @param yearMonth: The year and month for which the calendar should be generated.
   *
   * Understanding Leap Years:
   * A leap year is a year, occurring once every four years, which has 366 days including 29 February as an intercalary day.
   * This is necessary because the actual length of a year is not 365 days, as we usually consider, but approximately
   * 365.2425 days. To align the calendar year with the solar year (the length of time it takes the Earth to orbit around
   * the Sun), we add an extra day, February 29, almost every four years.
   *
   * The function automatically handles leap years when generating the calendar. For instance, when you pass
   * YearMonth.of(2024, 2) as an argument, it will correctly generate a February calendar with 29 days because 2024 is a leap year.
   *
   * @return calendarArray: A two-dimensional array representing the calendar for the provided YearMonth.
   * The rows represent weeks (up to six, enough to cover any possible month), and the columns represent days of the week
   * from Monday (column 0) to Sunday (column 6).
   * The array is filled with day numbers for the current month, starting with 1, and zeros for the days that fall outside
   * of the current month.
   *
   */
  fun generate(yearMonth: YearMonth): Array<IntArray> {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = (yearMonth.atDay(1).dayOfWeek.value % 7) // 0-6
    val weeks = ceil((daysInMonth + firstDayOfWeek - 1) / 7.0).toInt().coerceAtLeast(6) // coerceAtLeast : Ensures that this value is not less than the specified minimumValue. Reference: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/coerce-at-least.html
    val calendarArray = Array(weeks) { IntArray(7) }
    var day = 1
    for (week in 0 until weeks)
      for (dayOfWeek in 0 until 7) {
        calendarArray[week][dayOfWeek] = when {
          week == 0 && dayOfWeek < firstDayOfWeek -> 0
          day <= daysInMonth -> day++
          else -> 0
        }
      }
    return calendarArray
  }
}
