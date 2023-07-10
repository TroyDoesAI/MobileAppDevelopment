import Foundation

enum PostfixCalculatorError: Error, LocalizedError {
    case malformedExpression
    case divisionByZero
    case missingOperand
    case invalidOperator
    case notImplemented(String)

    var errorDescription: String? {
        switch self {
        case .malformedExpression: return "Malformed expression"
        case .divisionByZero: return "Division by zero"
        case .missingOperand: return "Missing operand"
        case .invalidOperator: return "Invalid operator"
        case .notImplemented(let msg): return msg
        }
    }
}
