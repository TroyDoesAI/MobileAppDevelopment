/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

// Defining the PostfixCalculator class
export default class PostfixCalculator {

  /**
   * Parses a postfix expression and evaluates it to return the result.
   *
   * @param {String} expression - The postfix expression provided by the user
   * @return {Number} - The result of evaluating the expression
   * @throws - Will throw an error if the expression is invalid
   */
  static evaluate(expression) {
    // Split the given expression into tokens (numbers or operators)
    // The filter(Boolean) ensures any extra spaces are excluded
    const expressionTokens = expression.split(' ').filter(Boolean); // Cool Filter Trick Ref : https://michaeluloth.com/javascript-filter-boolean/

    // Create an empty operand stack for the postfix evaluation
    const operandStack = [];

    // Loop over each token to evaluate the expression
    for (const token of expressionTokens) {
      // Check if the token is a number
      if (this.isNumeric(token)) {
        operandStack.push(parseFloat(token)); // Push the number to the operand stack
      } else {
        // Ensure there are enough operands for the operator
        if (operandStack.length < 2) {
          throw new Error("PostfixCalculatorError.missingOperand");
        }

        // Pop the last two operands from the stack
        const operand1 = operandStack.pop();
        const operand2 = operandStack.pop();
        let result;

        // Evaluate the two operands based on the operator
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
            // Check for division by zero
            if (operand1 === 0) {
              throw new Error("PostfixCalculatorError.divisionByZero");
            }
            result = operand2 / operand1;
            break;
          case "^":
            result = Math.pow(operand2, operand1); // Use Math.pow() for exponentiation
            break;
          default:
            // If the operator is not recognized, throw an error
            throw new Error("PostfixCalculatorError.invalidOperator");
        }

        // Push the result of the operation back to the stack
        operandStack.push(result);
      }
    }
    // Return the final result from the stack
    return operandStack.pop();
  }

  /**
   * Helper function to check if a given string represents a number.
   *
   * @param {String} str - The string to check
   * @return {Boolean} - True if the string is numeric, otherwise false
   */
  static isNumeric(str) {
    // Check if the parsed value is a number and finite
    return !isNaN(parseFloat(str)) && isFinite(str);
  }
}
