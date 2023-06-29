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

// Credits : InfixCalculator Test from David C. Harrison's CSE118 example code

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.fail

/*
 * Testing class for the PostfixCalculator.
 *
 * Here's a concise explanation of the testing order for the PostfixCalculator suite:
 *
 * 1. Malformed Expressions:
 *    - Tests for handling malformed expressions.
 *    - Covers cases like empty expressions, missing operators/operands, and excessive whitespace.
 *    - Ensures appropriate exceptions are thrown.
 *
 * 2. Basic Integer Operations:
 *    - Tests integer arithmetic operations: addition, subtraction, multiplication, division, and power.
 *    - Verifies correct integer results.
 *
 * 3. Floating-Point Calculations:
 *    - Tests floating-point calculations, considering precision issues using a delta (hardcoded precision acceptance value).
 *    - Covers addition, subtraction, multiplication, division, and power operations.
 *    - Uses `assertEquals` with delta for acceptable value range.
 *
 * 4. Edge Cases and Error Scenarios:
 *    - Tests special cases, errors, and boundary conditions.
 *    - Covers division by zero, invalid operators, non-numeric operands, large numbers, and consecutive operators.
 *    - Validates expected exceptions and correct results.
 */

class PostfixCalculatorTest {
  // Step 1: Test for malformed expressions
  @Test
  internal fun `malformed expression - empty expression`() {
    assertFails { PostfixCalculator().parse("") }
  }
  @Test
  internal fun `malformed expression - missing operator`() {
    assertThrows<IllegalArgumentException> { PostfixCalculator().parse("1 1") }
  }
  @Test
  internal fun `malformed expression - missing operand`() {
    assertThrows<IllegalArgumentException> { PostfixCalculator().parse("1 +") }
  }
  @Test
  internal fun `malformed expression - extra whitespace`() {
    assertEquals(2.0, PostfixCalculator().parse("  1   1  +   "))
  }
  @Test
  internal fun `malformed expression - excessive whitespace`() {
    assertEquals(5.0, PostfixCalculator().parse("2   3   +"))
  }

  // Step 2: Test for basic integer operations
  @Test
  internal fun `integer addition`() {
    assertEquals(2.0, PostfixCalculator().parse("1 1 +"))
  }
  @Test
  internal fun `integer subtraction`() {
    assertEquals(-1.0, PostfixCalculator().parse("1 2 -"))
  }
  @Test
  internal fun `integer multiplication`() {
    assertEquals(42.0, PostfixCalculator().parse("2 21 *"))
  }
  @Test
  internal fun `integer division`() {
    assertEquals(0.0, PostfixCalculator().parse("0 1 /"))
  }
  @Test
  internal fun `integer power operation`() {
    assertEquals(8.0, PostfixCalculator().parse("2 3 ^"))
    assertEquals(1.0, PostfixCalculator().parse("10 0 ^"))
    assertEquals(1.0, PostfixCalculator().parse("1 100000 ^"))
  }

  // Step 3: Test for floating-point calculations
  // In Kotlin, assertEquals method has a delta parameter to specify the maximum acceptable difference between expected & actual values.
  // Reference: https://kotlinlang.org/api/latest/kotlin.test/kotlin.test/assert-equals.html
  @Test
  internal fun `floating-point addition`() {
    val expected = 0.1
    val actual = PostfixCalculator().parse("0.05 0.05 +")
    val delta = 0.000001 // Specify the maximum acceptable difference (delta) here
    assertEquals(expected, actual, delta)
  }
  @Test
  internal fun `floating-point subtraction`() {
    val expected = 0.1
    val actual = PostfixCalculator().parse("0.3 0.2 -")
    val delta = 0.000001 // Specify the maximum acceptable difference (delta) here
    assertEquals(expected, actual, delta)
  }
  @Test
  internal fun `floating-point multiplication`() {
    val expected = 0.012
    val actual = PostfixCalculator().parse("0.03 0.4 *")
    val delta = 0.000001 // Specify the maximum acceptable difference (delta) here
    assertEquals(expected, actual, delta)
  }
  @Test
  internal fun `floating-point division`() {
    val expected = 0.3333333333333333
    val actual = PostfixCalculator().parse("1.0 3.0 /")
    val delta = 0.000001 // Specify the maximum acceptable difference (delta) here
    assertEquals(expected, actual, delta)
  }
  @Test
  internal fun `floating point power operation`() {
    assertEquals(8.0, PostfixCalculator().parse("2.0 3.0 ^")) // Both are floating point
    assertEquals(1.0, PostfixCalculator().parse("10.0 0 ^")) // Base is float, exponent int
    assertEquals(1.0, PostfixCalculator().parse("1 100000.0 ^")) // Base is int, exponent is a float
  }

  // Step 4: Test for edge cases and error scenarios
  @Test
  internal fun `division by zero`() {
    assertThrows<IllegalArgumentException> { PostfixCalculator().parse("1.0 0.0 /") }
  }
  @Test
  internal fun `invalid operator`() {
    assertThrows<IllegalArgumentException> { PostfixCalculator().parse("1 1 $") }
  }
  @Test
  internal fun `non-numeric operands`() {
    assertThrows<IllegalArgumentException> { PostfixCalculator().parse("1a 2 +") }
  }
  @Test
  internal fun `large numbers`() {
    assertEquals(1.0, PostfixCalculator().parse("10000000000 10000000000 /"))
  }
  @Test
  internal fun `multiple consecutive operators`() {
    assertThrows<IllegalArgumentException> { PostfixCalculator().parse("1 2 + -") }
  }
}