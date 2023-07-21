/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

// Defining the PostfixCalculator class
export default class PostfixCalculator {

  static evaluate(expression) {
    const expressionTokens = expression.split(' ').filter(Boolean);

    const operandStack = [];

    for (const token of expressionTokens) {
      if (this.isNumeric(token)) {
        operandStack.push(parseFloat(token));
      } else {
        if (operandStack.length < 2) {
          throw new Error("Invalid Expression");
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
              return Infinity;
            }
            result = operand2 / operand1;
            break;
          case "^":
            result = Math.pow(operand2, operand1);
            break;
          default:
            throw new Error("Unknown Operator");
        }
        operandStack.push(result);
      }
    }

    // If there's more than one number left in the stack, it's a malformed expression.
    if (operandStack.length !== 1) {
      throw new Error("Malformed Expression");
    }

    return operandStack.pop();
  }

  static isNumeric(str) {
    return !isNaN(parseFloat(str)) && isFinite(str);
  }
}

