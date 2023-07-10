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
    func generate(yearAndMonth: DateComponents) -> [[Int]]  {
        // Extract the month and year from the `yearAndMonth` parameter.
        guard let month = yearAndMonth.month, let year = yearAndMonth.year else {
            return [[Int]]()
        }
        
        // Create a Calendar instance to perform date calculations.
        var calendar = Calendar.current
        calendar.firstWeekday = 2 // Make Monday the first day of the week.
        
        // Calculate the number of days in the month and the weekday of the first day of the month.
        // The `range(of:in:for:)` method returns an optional `Range<Int>` representing the valid range of values for a specific calendar component.
        // In this case, it calculates the range of days in the given month and assigns it to the `range` constant.
        // If the calculation fails, indicating an invalid date, the method returns `nil`, and the code block in the `guard` statement is executed.
        guard let date = calendar.date(from: DateComponents(year: year, month: month, day: 1)),
              let range = calendar.range(of: .day, in: .month, for: date) else {
            return [[Int]]()
        }
        
        // Compute the previous and next month
        // The `date(byAdding:value:to:wrappingComponents:)` method is used to calculate a new date by adding or subtracting a value from a specific component of a given date.
        // In this case, it calculates the previous month's range of days and assigns it to the `prevMonthRange` constant.
        // The `component(_:from:)` method retrieves the value of a specific calendar component from a given date.
        // Here, it retrieves the weekday of the first day of the month and computes the `prevMonthDay` value accordingly.
        let prevMonthRange = Calendar.current.range(of: .day, in: .month, for: Calendar.current.date(byAdding: .month, value: -1, to: date)!)
        var nextMonthDay = 1
        var prevMonthDay = (prevMonthRange?.count ?? 0) - (calendar.component(.weekday, from: date) - 2)
        
        // Determine the number of days in the current month.
        let daysInMonth = range.count
        var day = 1
        var calendarArray = [[Int]](repeating: [Int](repeating: 0, count: 7), count: 6)
        
        // Populate the calendar array with the days of the month and neighboring days from the previous and next months.
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

