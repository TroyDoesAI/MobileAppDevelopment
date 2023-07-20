/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

/* **************************************************************************
 * DO NOT MODIFY THIS FILE
 * **************************************************************************/

import PostfixCalculator from '../../src/Logic/PostfixCalculator';

const evaluate = (expression, expected) => {
  const raw = PostfixCalculator.evaluate(expression);
  let result = Math.round(raw * 10000.0) / 10000.0;
  expect(result).toBe(expected);
};

test('Simple addition', () => {
  evaluate('1 2 +', 3.0);
});

test('Simple subtraction', () => {
  evaluate('4.5 2.2 -', 2.3);
});

test('Simple multiplication', () => {
  evaluate('3.2 2 *', 6.4);
});

test('Simple division', () => {
  evaluate('3.2 2 /', 1.6);
});

test('Simple power', () => {
  evaluate('3 2 ^', 9.0);
});

test('Rounded addition', () => {
  evaluate('1.1 2.2 +', 3.3);
});

test('Rounded power', () => {
  evaluate('3.2 2 ^', 10.24);
});

test('Divide by zero', () => {
  evaluate('3 0 /', Infinity);
});

test('Unknown operator', () => {
  expect(() => {
    PostfixCalculator.evaluate('1 2 !');
  }).toThrow('Unknown Operator');
});

test('Invalid expression', () => {
  expect(() => {
    PostfixCalculator.evaluate('1 + 2');
  }).toThrow('Invalid Expression');
});
