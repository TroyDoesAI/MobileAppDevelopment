import Foundation

class CalendarGenerator {
    func generate(yearAndMonth: DateComponents) -> [[Int]]  {
        guard let month = yearAndMonth.month, let year = yearAndMonth.year else {
            return [[Int]]()
        }
        
        var calendar = Calendar.current
        calendar.firstWeekday = 2 // Make Monday the first day of the week.
        
        // Calculate the number of days in the month and the weekday of the first day of the month.
        guard let date = calendar.date(from: DateComponents(year: year, month: month, day: 1)),
              let range = calendar.range(of: .day, in: .month, for: date) else {
            return [[Int]]()
        }
        
        // Compute the previous and next month
        let prevMonthRange = Calendar.current.range(of: .day, in: .month, for: Calendar.current.date(byAdding: .month, value: -1, to: date)!)
        var nextMonthDay = 1
        var prevMonthDay = (prevMonthRange?.count ?? 0) - (calendar.component(.weekday, from: date) - 2)
        
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

