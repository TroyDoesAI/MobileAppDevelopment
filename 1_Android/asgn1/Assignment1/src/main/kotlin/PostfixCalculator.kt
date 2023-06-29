import java.lang.IllegalArgumentException
import kotlin.math.pow

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
    val (operand1, operand2, operator) = expression.split(" ").map { it.trim() } // splits the expression string by whitespace, trims each resulting element, and assigns them respectively to the variables operand1, operand2, and operator. When using a lambda expression with only a single parameter, you can use the implicit "it" keyword https://kotlinlang.org/docs/lambdas.html#it-implicit-name-of-a-single-parameter
    require(operator.isNotEmpty() && operand1.isNotEmpty() && operand2.isNotEmpty()) {
      "Malformed expression"
    }
    return when (operator) {
      "+" -> operand1.toDouble() + operand2.toDouble()
      "-" -> operand1.toDouble() - operand2.toDouble()
      "*" -> operand1.toDouble() * operand2.toDouble()
      "/" -> {
        val divisor = operand2.toDouble()
        require(divisor != 0.0) { "Division by zero" }
        operand1.toDouble() / divisor
      }
      "^" -> operand1.toDouble().pow(operand2.toDouble())
      else -> throw IllegalArgumentException("Invalid operator")
    }
  }
}
