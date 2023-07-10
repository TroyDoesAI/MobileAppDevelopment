import XCTest
@testable import Assignment4

final class PostfixCalculatorTests: XCTestCase {
    let calculator = PostfixCalculator()
    // Step 1: Test for malformed expressions
    func testEmptyExpression() {
        XCTAssertThrowsError(try calculator.parse(expression: ""))
    }

    func testMissingOperator() {
        XCTAssertThrowsError(try calculator.parse(expression: "1 1"))
    }

    func testMissingOperand() {
        XCTAssertThrowsError(try calculator.parse(expression: "1 +"))
    }

    func testExtraWhitespace() {
        XCTAssertEqual(try calculator.parse(expression: "  1   1  +   "), 2)
    }

    func testExcessiveWhitespace() {
        XCTAssertEqual(try calculator.parse(expression: "2   3   +"), 5)
    }

    // Step 2: Test for basic integer operations
    func testIntegerAddition() {
        XCTAssertEqual(try calculator.parse(expression: "1 1 +"), 2)
    }

    func testIntegerSubtraction() {
        XCTAssertEqual(try calculator.parse(expression: "1 2 -"), -1)
    }

    func testIntegerMultiplication() {
        XCTAssertEqual(try calculator.parse(expression: "2 21 *"), 42)
    }

    func testIntegerDivision() {
        XCTAssertEqual(try calculator.parse(expression: "0 1 /"), 0)
    }

    func testIntegerPowerOperation() {
        XCTAssertEqual(try calculator.parse(expression: "2 3 ^"), 8)
        XCTAssertEqual(try calculator.parse(expression: "10 0 ^"), 1)
        XCTAssertEqual(try calculator.parse(expression: "1 100000 ^"), 1)
    }

    // Step 3: Test for floating-point calculations
    func testFloatingPointAddition() {
        XCTAssertEqual(try calculator.parse(expression: "0.05 0.05 +"), 0.1, accuracy: 0.000001)
    }

    func testFloatingPointSubtraction() {
        XCTAssertEqual(try calculator.parse(expression: "0.3 0.2 -"), 0.1, accuracy: 0.000001)
    }

    func testFloatingPointMultiplication() {
        XCTAssertEqual(try calculator.parse(expression: "0.03 0.4 *"), 0.012, accuracy: 0.000001)
    }

    func testFloatingPointDivision() {
        XCTAssertEqual(try calculator.parse(expression: "1.0 3.0 /"), 0.3333333333333333, accuracy: 0.000001)
    }

    func testFloatingPointPowerOperation() {
        XCTAssertEqual(try calculator.parse(expression: "2.0 3.0 ^"), 8)
        XCTAssertEqual(try calculator.parse(expression: "10.0 0 ^"), 1)
        XCTAssertEqual(try calculator.parse(expression: "1 100000.0 ^"), 1)
    }

    // Step 4: Test for edge cases and error scenarios
    func testDivisionByZero() {
        XCTAssertThrowsError(try calculator.parse(expression: "1.0 0.0 /"))
    }

    func testInvalidOperator() {
        XCTAssertThrowsError(try calculator.parse(expression: "1 1 $"))
    }

    func testNonNumericOperands() {
        XCTAssertThrowsError(try calculator.parse(expression: "1a 2 +"))
    }

    func testLargeNumbers() {
        XCTAssertEqual(try calculator.parse(expression: "10000000000 10000000000 /"), 1)
    }

    func testMultipleConsecutiveOperators() {
        XCTAssertThrowsError(try calculator.parse(expression: "1 2 + -"))
    }
}
