/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

// PostfixCalculator class: It evaluates mathematical expressions given in postfix notation.
export default class PostfixCalculator {
  // This function evaluates a postfix mathematical expression.
  static evaluate(expression) {
    // Split the input expression into tokens (operands and operators) and filter out any empty strings.
    const expressionTokens = expression.split(' ').filter(Boolean);

    // Initialize a stack to hold the operands during evaluation.
    const operandStack = [];

    // Iterate over each token in the expression.
    for (const token of expressionTokens) {
      // If the token is a numeric value (operand), push it onto the stack.
      if (this.isNumeric(token)) {
        operandStack.push(parseFloat(token));
      } else {
        // If there are less than two operands on the stack, the expression is invalid.
        if (operandStack.length < 2) {
          throw new Error('Invalid Expression');
        }

        // Pop two operands from the stack. The second operand popped will be the left operand.
        const operand1 = operandStack.pop();
        const operand2 = operandStack.pop();
        let result;

        // Determine the operator and perform the corresponding arithmetic operation.
        switch (token) {
          case '+':
            result = operand2 + operand1;
            break;
          case '-':
            result = operand2 - operand1;
            break;
          case '*':
            result = operand2 * operand1;
            break;
          case '/':
            // Ensure we're not dividing by zero.
            if (operand1 === 0) {
              return Infinity; // Return Infinity for division by zero.
            }
            result = operand2 / operand1;
            break;
          case '^':
            result = Math.pow(operand2, operand1); // Raise operand2 to the power of operand1.
            break;
          default:
            // If the token is not a recognized operator, throw an error.
            throw new Error('Unknown Operator');
        }
        // Push the result of the operation back onto the stack.
        operandStack.push(result);
      }
    }

    // If the final stack contains more than one number, the expression wasn't fully evaluated, indicating it's malformed.
    if (operandStack.length !== 1) {
      throw new Error('Malformed Expression');
    }

    // Pop the final result from the stack and return it.
    return operandStack.pop();
  }

  // Utility function to check if a string represents a numeric value.
  static isNumeric(str) {
    return !isNaN(parseFloat(str)) && isFinite(str);
  }
}
