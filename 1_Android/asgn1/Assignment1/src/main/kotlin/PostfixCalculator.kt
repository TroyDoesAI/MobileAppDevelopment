import java.lang.IllegalArgumentException
import kotlin.math.pow
import java.util.*
/*
#######################################################################
#
# Copyright (C) 2022-2023 David C. Harrison. All right reserved.
#
# You may not use, distribute, publish, or modify this code without
# the express written permission of the copyright holder.
#
#######################################################################
*/
// Credits : InfixCalculator from David C. Harrison's CSE118 example code
// Auto Docstrings Credit : https://plugins.jetbrains.com/plugin/14778-kdoc-er--kotlin-doc-generator and Fixing my summary Wording and Grammar Credit : https://www.grammarly.com/
/**
 * The PostfixCalculator class provides a parse function that evaluates a postfix expression and returns the result.
 *
 * Here's a breakdown of the key components:
 * - The input expression is split into individual tokens using the regular expression "\\s+" to match one or more whitespace characters.
 * - The operandStack is used to store the operands encountered during the evaluation.
 * - The expression tokens are iterated, and if a token is numeric, it is converted to a Double and pushed onto the operandStack.
 * - If the token is an operator, the necessary operands are retrieved from the operandStack and the operation is performed, with the result being pushed back to the stack.
 * - At the end of the evaluation, it is ensured that there is only one operand left in the operandStack.
 * - The isNumeric extension function checks if a string represents a numeric value.
 * - Finally, the result is returned as the final evaluation of the postfix expression.
 *
 * @throws IllegalArgumentException if the expression is malformed or contains invalid operators.
 */
class PostfixCalculator {
  /**
   * Parses a postfix expression and evaluates it to return the result.
   *
   * @param expression The postfix expression to be evaluated.
   * @return The result of the evaluation as a Double value.
   * @throws IllegalArgumentException if the expression is empty, contains missing operands, or attempts division by zero.
   */
  fun parse(expression: String): Double {
    val expressionTokens = expression.trim().split("\\s+".toRegex()) // Splits the expression into individual tokens by matching one or more whitespace characters
    val operandStack = Stack<Double>() // Stack data structure used for storing operands during evaluation. Reference: https://www.delftstack.com/howto/kotlin/use-of-stack-data-structure-in-kotlin/

    for (token in expressionTokens) {
      if (token.isNumeric()) {
        operandStack.push(token.toDouble())
      } else {
        if (operandStack.size < 2) {
          throw IllegalArgumentException("Malformed expression - Missing operand")
        }

        val operand1 = operandStack.pop()
        val operand2 = operandStack.pop()
        val result = when (token) {
          "+" -> operand2 + operand1
          "-" -> operand2 - operand1
          "*" -> operand2 * operand1
          "/" -> {
            require(operand1 != 0.0) { "Division by zero" }
            operand2 / operand1
          }
          "^" -> operand2.pow(operand1)
          else -> throw IllegalArgumentException("Invalid operator")
        }
        operandStack.push(result)
      }
    }

    require(operandStack.size == 1) { "Malformed expression" }
    return operandStack.pop()
  }

  /**
   * Checks if a string represents a numeric value.
   *
   * @receiver The string to be checked.
   * @return `true` if the string represents a numeric value, `false` otherwise.
   */
  private fun String.isNumeric(): Boolean {
    return try {
      this.toDouble()
      true
    } catch (e: NumberFormatException) { // Reference: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-number-format-exception/
      false
    }
  }
}

