import Foundation

class CalendarGenerator {
    /**
     Generates a calendar month for the given year and month.
     
     - Parameters:
        - yearAndMonth: The year and month for which the calendar should be generated as DateComponents.
     
     - Returns: A 2-dimensional array representing the calendar month.
     
     The generated calendar array has 6 rows and 7 columns, representing the weeks and days of the month.
     Each element in the array represents a day of the month, with 0 indicating a day that falls outside the given month.
     */
    func generate(yearAndMonth: DateComponents) -> [[Int]]  {
        let month = yearAndMonth.month!
        let year = yearAndMonth.year!
        
        var calendar = Calendar.current
        calendar.firstWeekday = 2 // Make Monday the first day of the week.
        
        let date = calendar.date(from: DateComponents(year: year, month: month, day: 1))!
        let range = calendar.range(of: .day, in: .month, for: date)!
        
        let prevMonthRange = Calendar.current.range(of: .day, in: .month, for: Calendar.current.date(byAdding: .month, value: -1, to: date)!)
        var nextMonthDay = 1
        var prevMonthDay = prevMonthRange!.count - (calendar.component(.weekday, from: date) - 2)
        
        let daysInMonth = range.count
        var day = 1
        var calendarArray = [[Int]](repeating: [Int](repeating: 0, count: 7), count: 6)
        
        for week in 0..<6 {
            for dayOfWeek in 0..<7 {
                if week == 0 && dayOfWeek < calendar.component(.weekday, from: date) - 1 {
                    calendarArray[week][dayOfWeek] = prevMonthDay
                    prevMonthDay += 1
                } else if day <= daysInMonth {
                    calendarArray[week][dayOfWeek] = day
                    day += 1
                } else {
                    calendarArray[week][dayOfWeek] = nextMonthDay
                    nextMonthDay += 1
                }
            }
        }
        return calendarArray
    }
}
