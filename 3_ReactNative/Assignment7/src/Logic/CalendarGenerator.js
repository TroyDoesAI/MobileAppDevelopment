/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

// CalendarGenerator.js
export default class CalendarGenerator {
  /**
   * Generate a 2D calendar array for the month of the given date.
   * Each row in the array represents a week, and each cell in that row represents a day of the week.
   * If the month doesn't start on a Sunday or doesn't end on a Saturday,
   * the days from the previous or next month are used to fill in the gaps.
   *
   * @param {Date} date - Any date within the month you want to generate a calendar for.
   * @returns {number[][]} A 2D array representing the calendar month, with 6 rows (weeks) and 7 columns (days).
   */
  static generate(date) {
    // Get the current month from the date (0-based, so January is 0, February is 1, etc.)
    const month = date.getMonth() + 1; // Adjust to 1-based month numbering

    // Determine the first and last day of the given month.
    const firstDayOfMonth = new Date(date.getFullYear(), month, 1);
    const lastDayOfMonth = new Date(date.getFullYear(), month + 1, 0);

    // Calculate the number of days in the current month.
    const daysInMonth = lastDayOfMonth.getDate();

    // Determine the last day of the previous month.
    const prevMonthLastDay = new Date(date.getFullYear(), month, 0);
    const prevMonthDays = prevMonthLastDay.getDate();

    // Adjust for Sunday as the start of the week.
    // In this mapping, Sunday is 0, Monday is 1, ..., Saturday is 6.
    let days = [7, 1, 2, 3, 4, 5, 6];

    // Calculate which day of the previous month should be displayed first in the calendar.
    let prevMonthDay = prevMonthDays - days[firstDayOfMonth.getDay()] + 1;

    let nextMonthDay = 1; // Initialize the day counter for the next month.
    let day = 1; // Initialize the day counter for the current month.

    // Create an empty calendar array with 6 weeks and 7 days per week.
    const calendarArray = Array.from({length: 6}, () => Array(7).fill(0));

    // Fill the calendar array with the correct day numbers.
    for (let week = 0; week < 6; week++) {
      for (let dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
        if (week === 0 && dayOfWeek < firstDayOfMonth.getDay()) {
          // Fill in days from the previous month for the first week.
          calendarArray[week][dayOfWeek] = prevMonthDay;
          prevMonthDay += 1;
        } else if (day <= daysInMonth) {
          // Fill in days for the current month.
          calendarArray[week][dayOfWeek] = day;
          day += 1;
        } else {
          // Fill in days from the next month for the last week(s).
          calendarArray[week][dayOfWeek] = nextMonthDay;
          nextMonthDay += 1;
        }
      }
    }
    return calendarArray;
  }
}
