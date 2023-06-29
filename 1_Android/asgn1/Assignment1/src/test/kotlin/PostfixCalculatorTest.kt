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

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PostfixCalculatorTest {
  @Test
  fun `test addition`() {
    val calculator = PostfixCalculator()
    val result = calculator.parse("5 3 +")
    assertEquals(8.0, result)
  }

  @Test
  fun `test subtraction`() {
    val calculator = PostfixCalculator()
    val result = calculator.parse("10 3 -")
    assertEquals(7.0, result)
  }

  @Test
  fun `test multiplication`() {
    val calculator = PostfixCalculator()
    val result = calculator.parse("4 5 *")
    assertEquals(20.0, result)
  }

  @Test
  fun `test division`() {
    val calculator = PostfixCalculator()
    val result = calculator.parse("12 3 /")
    assertEquals(4.0, result)
  }

  @Test
  internal fun `division by zero`() {
    assertThrows<IllegalArgumentException> { PostfixCalculator().parse("1 0 /") }
  }

  @Test
  internal fun `integer power operation`() {
    assertEquals(8.0, PostfixCalculator().parse("2 3 ^"))
    assertEquals(1.0, PostfixCalculator().parse("10 0 ^"))
    assertEquals(1.0, PostfixCalculator().parse("1 100000 ^"))
  }

  @Test
  internal fun `invalid operator`() {
    assertThrows<IllegalArgumentException> { PostfixCalculator().parse("1 1 $") }
  }

  @Test
  internal fun `non-numeric operands`() {
    assertThrows<NumberFormatException> { PostfixCalculator().parse("1a 2 +") }
  }

}