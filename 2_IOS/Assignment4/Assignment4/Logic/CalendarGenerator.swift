import Foundation

//Swift-specific components in the code:
// Resources : https://developer.apple.com/documentation/foundation/datecomponents

//DateComponents: DateComponents is a struct that represents a date or time in terms of individual components (such as year, month, day, hour, minute, etc.). In this case, yearAndMonth is a DateComponents parameter that contains the year and month for which the calendar should be generated.
//
//Calendar: Calendar is a struct that represents a specific calendar system and provides methods for working with dates, times, and their components. The Calendar.current property retrieves the current user's calendar.
//
//guard: The guard statement is used for early exit in Swift. It checks for a condition and if it evaluates to false, the code block within the guard statement is executed. It's commonly used to handle error conditions or ensure certain conditions are met before proceeding.
//
//optional binding: The optional binding syntax (if let or guard let) is used to safely unwrap optional values. In this code, it's used to safely unwrap the month and year from the yearAndMonth parameter and the resulting date and range values.
//
//?? (nil-coalescing operator): The ?? operator is used to provide a default value when an optional is nil. In the code, it's used to assign a default value of 0 if the prevMonthRange is nil, indicating that there is no previous month.



class CalendarGenerator {
    /**
     Generates a calendar month for the given year and month.
     
     - Parameters:
        - yearAndMonth: The year and month for which the calendar should be generated as DateComponents.
     
     - Returns: A 2-dimensional array representing the calendar month.
     
     The generated calendar array has 6 rows and 7 columns, representing the weeks and days of the month.
     Each element in the array represents a day of the month, with 0 indicating a day that falls outside the given month.
     */
    func generate(yearAndMonth: DateComponents) -> [[Int]] {
            let month = yearAndMonth.month!
            let year = yearAndMonth.year!
            
            var calendar = Calendar.current
            calendar.firstWeekday = 2
            
            let date = calendar.date(from: DateComponents(year: year, month: month, day: 1))!
            let daysInMonth = calendar.range(of: .day, in: .month, for: date)!.count
            
            let prevMonthDays = calendar.range(of: .day, in: .month, for: Calendar.current.date(byAdding: .month, value: -1, to: date)!)!.count
            var prevMonthDay = prevMonthDays - (calendar.component(.weekday, from: date) - 2)
            var nextMonthDay = 1
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

