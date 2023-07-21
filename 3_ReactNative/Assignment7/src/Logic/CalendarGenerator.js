/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

export default class {
  /**
   * @param {Date} date
   * @return {[[Number]]} 6x7 array of integers
   */
  // static generate(date) {
  //   throw "Not implemented"
  // }
  static generate(date) {
    const firstDayOfMonth = startOfMonth(date);
    const daysInMonth = getDaysInMonth(date);
    const prevMonthDays = getDaysInMonth(subMonths(date, 1));

    let prevMonthDay = prevMonthDays - ((getDay(firstDayOfMonth) || 7) - 2); // adjust for Monday as start of the week
    let nextMonthDay = 1;
    let day = 1;

    const calendarArray = Array.from({ length: 6 }, () => Array(7).fill(0));

    for (let week = 0; week < 6; week++) {
      for (let dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
        if (week === 0 && dayOfWeek < (getDay(firstDayOfMonth) || 7) - 1) {
          calendarArray[week][dayOfWeek] = prevMonthDay;
          prevMonthDay += 1;
        } else if (day <= daysInMonth) {
          calendarArray[week][dayOfWeek] = day;
          day += 1;
        } else {
          calendarArray[week][dayOfWeek] = nextMonthDay;
          nextMonthDay += 1;
        }
      }
    }
    return calendarArray;
  }
}
