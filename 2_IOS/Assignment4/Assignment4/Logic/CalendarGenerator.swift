import Foundation

class CalendarGenerator {
    // Do not change the signature of this function,
    // replace the empty return with your implementation
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
        
        let daysInMonth = range.count
        let firstDayOfWeek = calendar.component(.weekday, from: date)
        
        var calendarArray = [[Int]](repeating: [Int](repeating: 0, count: 7), count: 6)
        var day = 1
        for week in 0..<6 {
            for dayOfWeek in 0..<7 {
                if week == 0 && dayOfWeek < firstDayOfWeek - 1 {
                    calendarArray[week][dayOfWeek] = 0
                } else if day <= daysInMonth {
                    calendarArray[week][dayOfWeek] = day
                    day += 1
                } else {
                    calendarArray[week][dayOfWeek] = 0
                }
            }
        }
        return calendarArray
    }
}

