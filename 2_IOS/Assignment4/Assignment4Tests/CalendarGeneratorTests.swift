import XCTest
@testable import Assignment4

final class CalendarGeneratorTests: XCTestCase {

    var calendarGenerator: CalendarGenerator!

    // Set up the test case by initializing the CalendarGenerator instance.
    override func setUp() {
        super.setUp()
        calendarGenerator = CalendarGenerator()
    }

    // Utility function to convert the calendar array to a formatted string for easier comparison.
    private func calendarToString(_ calendar: [[Int]]) -> String {
        return calendar.map { week in week.map { day in String(format: "%02d", day) }.joined(separator: " ") }
                        .joined(separator: "\n")
    }

    // Test case for generating the calendar of February in a non-leap year.
    func testFebruaryNonLeapYear() {
        let february2023 = DateComponents(year: 2023, month: 2)
        let february2023Calendar = calendarGenerator.generate(yearAndMonth: february2023)
        let calendarStr = calendarToString(february2023Calendar)
        let expectedStr = """
        29 30 31 01 02 03 04
        05 06 07 08 09 10 11
        12 13 14 15 16 17 18
        19 20 21 22 23 24 25
        26 27 28 01 02 03 04
        05 06 07 08 09 10 11
        """
        XCTAssertEqual(calendarStr, expectedStr)
    }

    // Test case for generating the calendar of February in a leap year.
    func testFebruaryLeapYear() {
        let february2024 = DateComponents(year: 2024, month: 2)
        let february2024Calendar = calendarGenerator.generate(yearAndMonth: february2024)
        let calendarStr = calendarToString(february2024Calendar)
        let expectedStr = """
        28 29 30 31 01 02 03
        04 05 06 07 08 09 10
        11 12 13 14 15 16 17
        18 19 20 21 22 23 24
        25 26 27 28 29 01 02
        03 04 05 06 07 08 09
        """
        XCTAssertEqual(calendarStr, expectedStr)
    }

    // Test case for generating the calendar of December in a non-leap year.
    func testDecemberNonLeapYear() {
        let december2023 = DateComponents(year: 2023, month: 12)
        let december2023Calendar = calendarGenerator.generate(yearAndMonth: december2023)
        let calendarStr = calendarToString(december2023Calendar)
        let expectedStr = """
        26 27 28 29 30 01 02
        03 04 05 06 07 08 09
        10 11 12 13 14 15 16
        17 18 19 20 21 22 23
        24 25 26 27 28 29 30
        31 01 02 03 04 05 06
        """
        XCTAssertEqual(calendarStr, expectedStr)
    }
    
    // Test case for generating the calendar of December in a leap year.
    func testDecemberLeapYear() {
        let december2024 = DateComponents(year: 2024, month: 12)
        let december2024Calendar = calendarGenerator.generate(yearAndMonth: december2024)
        let calendarStr = calendarToString(december2024Calendar)
        let expectedStr = """
        01 02 03 04 05 06 07
        08 09 10 11 12 13 14
        15 16 17 18 19 20 21
        22 23 24 25 26 27 28
        29 30 31 01 02 03 04
        05 06 07 08 09 10 11
        """
        XCTAssertEqual(calendarStr, expectedStr)
    }
}

