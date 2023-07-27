import Foundation

enum PostfixCalculatorError: Error, LocalizedError {
    case malformedExpression
    case divisionByZero
    case missingOperand
    case invalidOperator
    case notImplemented(String)
}

