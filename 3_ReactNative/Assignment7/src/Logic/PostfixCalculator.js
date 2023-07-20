/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

export default class PostfixCalculator {
  /**
   * Parses a postfix expression and evaluates it to return the result.
   * @param {String} expression Postfix expression
   * @return {Number} result of evaluating expression
   * @throws
   */
  static evaluate(expression) {
    const expressionTokens = expression.split(' ').filter(Boolean); // Filter to exclude any extra spaces
    const operandStack = [];

    for (const token of expressionTokens) {
      if (this.isNumeric(token)) {
        operandStack.push(parseFloat(token));
      } else {
        if (operandStack.length < 2) {
          throw new Error("PostfixCalculatorError.missingOperand");
        }

        const operand1 = operandStack.pop();
        const operand2 = operandStack.pop();
        let result;

        switch (token) {
          case "+":
            result = operand2 + operand1;
            break;
          case "-":
            result = operand2 - operand1;
            break;
          case "*":
            result = operand2 * operand1;
            break;
          case "/":
            if (operand1 === 0) {
              throw new Error("PostfixCalculatorError.divisionByZero");
            }
            result = operand2 / operand1;
            break;
          case "^":
            result = Math.pow(operand2, operand1);
            break;
          default:
            throw new Error("PostfixCalculatorError.invalidOperator");
        }

        operandStack.push(result);
      }
    }
    return operandStack.pop();
  }

  /**
   * Helper function to check if a string is numeric.
   * @param {String} str
   * @return {Boolean}
   */
  static isNumeric(str) {
    return !isNaN(parseFloat(str)) && isFinite(str);
  }
}

