/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react-native';

import PostfixCalculatorView from '../../src/UI/PostfixCalculatorView';

//it('should throw an error for division by zero', () => {
//  // This is a postfix expression for "2 0 /", which tries to divide 2 by 0
//  const expression = "2 0 /";
//
//  // Expect an error to be thrown when evaluating this expression
//  expect(() => PostfixCalculator.evaluate(expression)).toThrow("Division By Zero");
//});

it('Evaluates expression with multiple operators', async () => {
 render(<PostfixCalculatorView />);
 fireEvent.changeText(screen.getByLabelText('expression'), '3 5 + 2 *');
 fireEvent.press(screen.getByLabelText('evaluate'));
 expect(screen.getByLabelText('result')).toHaveTextContent('16');
});

//it('Evaluates expression with negative numbers', async () => {
//  render(<PostfixCalculatorView />);
//  fireEvent.changeText(screen.getByLabelText('expression'), '3 -5 +');
//  fireEvent.press(screen.getByLabelText('evaluate'));
//  expect(screen.getByLabelText('result')).toHaveTextContent('-2');
//});
//
//it('Evaluates expression with exponentiation', async () => {
//  render(<PostfixCalculatorView />);
//  fireEvent.changeText(screen.getByLabelText('expression'), '2 3 ^');
//  fireEvent.press(screen.getByLabelText('evaluate'));
//  expect(screen.getByLabelText('result')).toHaveTextContent('8');
//});
//
//it('Shows error for missing operand', async () => {
//  render(<PostfixCalculatorView />);
//  fireEvent.changeText(screen.getByLabelText('expression'), '1 +');
//  fireEvent.press(screen.getByLabelText('evaluate'));
//  expect(screen.getByLabelText('result')).toHaveTextContent('PostfixCalculatorError.missingOperand');
//});
//
//it('Shows error for extra operand', async () => {
//  render(<PostfixCalculatorView />);
//  fireEvent.changeText(screen.getByLabelText('expression'), '1 2 3 +');
//  fireEvent.press(screen.getByLabelText('evaluate'));
//  expect(screen.getByLabelText('result')).toHaveTextContent('PostfixCalculatorError.malformedExpression');
//});
//
//it('Shows error for empty expression', async () => {
//  render(<PostfixCalculatorView />);
//  fireEvent.changeText(screen.getByLabelText('expression'), '');
//  fireEvent.press(screen.getByLabelText('evaluate'));
//  expect(screen.getByLabelText('result')).toHaveTextContent('Invalid Expression');
//});
//
//it('Evaluates complex expressions', async () => {
//  render(<PostfixCalculatorView />);
//  fireEvent.changeText(screen.getByLabelText('expression'), '3 5 + 2 7 * +');
//  fireEvent.press(screen.getByLabelText('evaluate'));
//  expect(screen.getByLabelText('result')).toHaveTextContent('24');
//});
//
//
