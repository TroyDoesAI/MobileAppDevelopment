/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

// CalendarGenerator.js
export default class CalendarGenerator {
  static generate(date) {
    const month = date.getMonth() + 1; // Adjust for 1-based month numbering
    const firstDayOfMonth = new Date(date.getFullYear(), month, 1);
    const lastDayOfMonth = new Date(date.getFullYear(), month + 1, 0);
    const daysInMonth = lastDayOfMonth.getDate();
    const prevMonthLastDay = new Date(date.getFullYear(), month, 0);
    const prevMonthDays = prevMonthLastDay.getDate();

    //let prevMonthDay = prevMonthDays - (firstDayOfMonth.getDay() || 7) + 1; // adjust for Sunday as start of the week
    let days = [7, 1, 2, 3, 4, 5, 6];
    let prevMonthDay = prevMonthDays - days[firstDayOfMonth.getDay()] + 1;

    
    let nextMonthDay = 1;
    let day = 1;

    const calendarArray = Array.from({ length: 6 }, () => Array(7).fill(0));

    for (let week = 0; week < 6; week++) {
      for (let dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
        if (week === 0 && dayOfWeek < firstDayOfMonth.getDay()) {
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
