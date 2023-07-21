import Foundation

extension String {
    func isNumeric() -> Bool {
        return Double(self) != nil
    }
}

class PostfixCalculator {
    // Parses a postfix expression and evaluates it to return the result.
    func parse(expression: String) throws -> Double {
        let expressionTokens = expression.split(separator: " ").map { String($0) }
        var operandStack = [Double]()

        for token in expressionTokens {
            if token.isNumeric() {
                operandStack.append(Double(token)!)
            } else {
                guard operandStack.count >= 2 else {
                    throw PostfixCalculatorError.missingOperand
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
                        throw PostfixCalculatorError.divisionByZero
                    }
                    result = operand2 / operand1
                case "^": result = pow(operand2, operand1)
                default: throw PostfixCalculatorError.invalidOperator
                }

                // Check if the result is -0.0 and if so, convert it to 0.0
                operandStack.append(result == -0.0 ? 0.0 : result)
            }
        }

        guard operandStack.count == 1 else {
            throw PostfixCalculatorError.malformedExpression
        }
        return operandStack.popLast()!
    }
}

