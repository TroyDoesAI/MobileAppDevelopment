import XCTest

final class PostfixCalculatorUITests: XCTestCase {

    var app: XCUIApplication!

    override func setUp() {
        super.setUp()
        continueAfterFailure = false

        app = XCUIApplication()
        app.launch()
    }

    func testCalculatorEvaluatesSimpleExpression() {
        // Enter a simple postfix expression
        let expressionField = app.textFields["Expression"]
        expressionField.tap()
        expressionField.typeText("2 3 +")

        // Tap on the "Evaluate" button
        app.buttons["Evaluate"].tap()

        // Check the result
        let resultField = app.textFields["Result"]
        XCTAssertEqual(resultField.value as? String, "5.0")
    }

    func testCalculatorClearsFields() {
        // Enter a simple postfix expression
        let expressionField = app.textFields["Expression"]
        expressionField.tap()
        expressionField.typeText("2 3 +")

        // Tap on the "Clear" button
        app.buttons["Clear"].tap()

        // Check that the fields are empty
        XCTAssertEqual(expressionField.value as? String, "Expression")
        XCTAssertEqual(app.textFields["Result"].value as? String, "Result")
    }

    func testCalculatorShowsErrorForInvalidExpression() {
        // Enter an invalid postfix expression
        let expressionField = app.textFields["Expression"]
        expressionField.tap()
        expressionField.typeText("2 +")

        // Tap on the "Evaluate" button
        app.buttons["Evaluate"].tap()

        // Check the error message
        let resultField = app.textFields["Result"]
        XCTAssertEqual(resultField.value as? String, "Error: missingOperand")
    }
    
    func testCalculatorShowsErrorForMalformedExpression() {
        // Enter a malformed postfix expression
        let expressionField = app.textFields["Expression"]
        expressionField.tap()
        expressionField.typeText("2 3 + 4")

        // Tap on the "Evaluate" button
        app.buttons["Evaluate"].tap()

        // Check the error message
        let resultField = app.textFields["Result"]
        XCTAssertEqual(resultField.value as? String, "Error: malformedExpression")
    }

    func testCalculatorShowsErrorForDivisionByZero() {
        // Enter a division by zero postfix expression
        let expressionField = app.textFields["Expression"]
        expressionField.tap()
        expressionField.typeText("2 0 /")

        // Tap on the "Evaluate" button
        app.buttons["Evaluate"].tap()

        // Check the error message
        let resultField = app.textFields["Result"]
        XCTAssertEqual(resultField.value as? String, "Error: divisionByZero")
    }

    func testCalculatorShowsErrorForInvalidOperator() {
        // Enter an invalid operator postfix expression
        let expressionField = app.textFields["Expression"]
        expressionField.tap()
        expressionField.typeText("2 3 &")

        // Tap on the "Evaluate" button
        app.buttons["Evaluate"].tap()

        // Check the error message
        let resultField = app.textFields["Result"]
        XCTAssertEqual(resultField.value as? String, "Error: invalidOperator")
    }

    func testCalculatorShowsErrorForMissingOperand() {
        // Enter a missing operand postfix expression
        let expressionField = app.textFields["Expression"]
        expressionField.tap()
        expressionField.typeText("2 +")

        // Tap on the "Evaluate" button
        app.buttons["Evaluate"].tap()

        // Check the error message
        let resultField = app.textFields["Result"]
        XCTAssertEqual(resultField.value as? String, "Error: missingOperand")
    }

}

