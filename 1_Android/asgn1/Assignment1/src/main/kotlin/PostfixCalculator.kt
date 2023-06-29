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

class PostfixCalculator {
  fun parse(expression: String): Double {
    val tokens = expression.split(" ")
    val stack = mutableListOf<Double>()

    for (token in tokens) {
      if (token.matches(Regex("-?\\d+(\\.\\d+)?"))) {
        stack.add(token.toDouble())
      } else {
        val secondOperand = stack.removeAt(stack.size - 1)
        val firstOperand = stack.removeAt(stack.size - 1)
        val result = when (token) {
          "+" -> firstOperand + secondOperand
          "-" -> firstOperand - secondOperand
          "*" -> firstOperand * secondOperand
          "/" -> firstOperand / secondOperand
          else -> throw IllegalArgumentException("Invalid operator: $token")
        }
        stack.add(result)
      }
    }

    return stack[0]
  }
}
