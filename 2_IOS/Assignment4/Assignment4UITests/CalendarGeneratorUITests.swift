import XCTest

final class CalendarGeneratorUITests: XCTestCase {
    var app: XCUIApplication!

    override func setUpWithError() throws {
        continueAfterFailure = false
        app = XCUIApplication()
        app.launch()
    }

    func testCalendarNavigation() throws {
        // Fetch the initial month
        let monthLabel = app.staticTexts["monthYearLabel"]
        let initialMonthYear = monthLabel.label

        // Click on the "Next" button
        app.buttons["nextMonthButton"].tap()

        // Confirm that the month displayed is now the next month
        XCTAssertNotEqual(monthLabel.label, initialMonthYear)

        let nextMonthYear = monthLabel.label

        // Click on the "Today" button
        app.buttons["currentMonthButton"].tap()

        // Confirm that the month displayed is the initial month
        XCTAssertEqual(monthLabel.label, initialMonthYear)

        // Click on the "Previous" button
        app.buttons["previousMonthButton"].tap()

        // Confirm that the month displayed is not the next month
        XCTAssertNotEqual(monthLabel.label, nextMonthYear)

        // As mentioned before, for better accuracy, you can compute the expected previous
        // and next months based on the `initialMonthYear` and compare them with the displayed month/year.
    }
}
