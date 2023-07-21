import Foundation

struct StringError: Error {
    enum ErrorType {
        case missingOperand
        case divisionByZero
        case invalidOperator
        case malformedExpression
    }
    
    let type: ErrorType
    
    var localizedDescription: String {
        switch type {
        case .missingOperand: return "Error: missingOperand"
        case .divisionByZero: return "Error: divisionByZero"
        case .invalidOperator: return "Error: invalidOperator"
        case .malformedExpression: return "Error: malformedExpression"
        }
    }
}

extension String {
    func isNumeric() -> Bool {
        return Double(self) != nil
    }
}

class PostfixCalculator {
    // Parses a postfix expression and evaluates it to return the result.
    func parse(expression: String) throws -> Double {
        let expressionTokens = expression.split(separator: " ").map { String($0) }.filter { !$0.isEmpty }
        var operandStack = [Double]()

        for token in expressionTokens {
            if token.isNumeric() {
                operandStack.append(Double(token)!)
            } else {
                guard operandStack.count >= 2 else {
                    throw StringError(type: .missingOperand)
                }

                let operand1 = operandStack.removeLast()
                let operand2 = operandStack.removeLast()
                let result: Double
                switch token {
                case "+": result = operand2 + operand1
                case "-": result = operand2 - operand1
                case "*": result = operand2 * operand1
                case "/":
                    guard operand1 != 0 else {
                        throw StringError(type: .divisionByZero)
                    }
                    result = operand2 / operand1
                case "^": result = pow(operand2, operand1)
                default: throw StringError(type: .invalidOperator)
                }

                // Check if the result is -0.0 and if so, convert it to 0.0
                operandStack.append(result == -0.0 ? 0.0 : result)
            }
        }

        // If there's more than one number left in the stack, it's a malformed expression.
        guard operandStack.count == 1 else {
            throw StringError(type: .malformedExpression)
        }
        
        return operandStack.popLast()!
    }
}

