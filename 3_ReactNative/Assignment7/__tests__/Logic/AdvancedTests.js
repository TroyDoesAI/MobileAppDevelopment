/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react-native';

import PostfixCalculatorView from '../../src/UI/PostfixCalculatorView';
import CalendarGenerator from '../../src/Logic/CalendarGenerator';

it('Evaluates expression with multiple operators', async () => {
  render(<PostfixCalculatorView />);
  fireEvent.changeText(screen.getByLabelText('expression'), '3 5 + 2 *');
  fireEvent.press(screen.getByLabelText('evaluate'));
  expect(screen.getByLabelText('result')).toHaveTextContent('16');
});

it('Evaluates expression with negative numbers', async () => {
  render(<PostfixCalculatorView />);
  fireEvent.changeText(screen.getByLabelText('expression'), '3 -5 +');
  fireEvent.press(screen.getByLabelText('evaluate'));
  expect(screen.getByLabelText('result')).toHaveTextContent('-2');
});

it('Evaluates expression with exponentiation', async () => {
  render(<PostfixCalculatorView />);
  fireEvent.changeText(screen.getByLabelText('expression'), '2 3 ^');
  fireEvent.press(screen.getByLabelText('evaluate'));
  expect(screen.getByLabelText('result')).toHaveTextContent('8');
});

it('Shows error for missing operand', async () => {
  render(<PostfixCalculatorView />);
  fireEvent.changeText(screen.getByLabelText('expression'), '1 +');
  fireEvent.press(screen.getByLabelText('evaluate'));
  expect(screen.getByLabelText('result')).toHaveTextContent(
    'Invalid Expression',
  );
});

it('Shows error for extra operand', async () => {
  render(<PostfixCalculatorView />);
  fireEvent.changeText(screen.getByLabelText('expression'), '1 2 3 +');
  fireEvent.press(screen.getByLabelText('evaluate'));
  expect(screen.getByLabelText('result')).toHaveTextContent(
    'Malformed Expression',
  );
});

it('Shows error for empty expression', async () => {
  render(<PostfixCalculatorView />);
  fireEvent.changeText(screen.getByLabelText('expression'), '');
  fireEvent.press(screen.getByLabelText('evaluate'));
  expect(screen.getByLabelText('result')).toHaveTextContent('');
});

it('Evaluates complex expressions', async () => {
  render(<PostfixCalculatorView />);
  fireEvent.changeText(screen.getByLabelText('expression'), '3 5 + 2 7 * +');
  fireEvent.press(screen.getByLabelText('evaluate'));
  expect(screen.getByLabelText('result')).toHaveTextContent('22');
});

// Calender Tests : Logic
const testMonth = (dateStr, expected) => {
  const result = CalendarGenerator.generate(new Date(dateStr));
  // Debug Console Logs
  // console.log(`Generated Calendar for ${dateStr}:`);
  // console.log(JSON.stringify(result, null, 2)); // this will pretty-print the array
  expect(result).toEqual(expected);
};

describe('CalendarGenerator', () => {

  // Example Calender From The Assignment Document
  test('Calendar for May 2023', () => {
    const expected = [
      [30, 1, 2, 3, 4, 5, 6],
      [7, 8, 9, 10, 11, 12, 13],
      [14, 15, 16, 17, 18, 19, 20],
      [21, 22, 23, 24, 25, 26, 27],
      [28, 29, 30, 31, 1, 2, 3],
      [4, 5, 6, 7, 8, 9, 10],
    ];
    testMonth('2023-05-01', expected);
  });

  // Not A Leap Year
  test('Calendar for Feb 2023', () => {
    const expected = [
      [29, 30, 31, 1, 2, 3, 4],
      [5, 6, 7, 8, 9, 10, 11],
      [12, 13, 14, 15, 16, 17, 18],
      [19, 20, 21, 22, 23, 24, 25],
      [26, 27, 28, 1, 2, 3, 4],
      [5, 6, 7, 8, 9, 10, 11],
    ];
    testMonth('2023-02-01', expected);
  });

  // Leap Year : Notice the 29th day for the leap year
  test('Calendar for Feb 2024 (Leap Year)', () => {
    const expected = [
      [28, 29, 30, 31, 1, 2, 3],
      [4, 5, 6, 7, 8, 9, 10],
      [11, 12, 13, 14, 15, 16, 17],
      [18, 19, 20, 21, 22, 23, 24],
      [25, 26, 27, 28, 29, 1, 2],
      [3, 4, 5, 6, 7, 8, 9],
    ];
    testMonth('2024-02-01', expected);
  });

  // December 2023 : Not A Leap Year
  test('Calendar for Dec 2023', () => {
    const expected = [
      [26, 27, 28, 29, 30, 1, 2],
      [3, 4, 5, 6, 7, 8, 9],
      [10, 11, 12, 13, 14, 15, 16],
      [17, 18, 19, 20, 21, 22, 23],
      [24, 25, 26, 27, 28, 29, 30],
      [31, 1, 2, 3, 4, 5, 6]
    ];
    testMonth('2023-12-01', expected);
  });

  // December 2024 : Leap Year
  test('Calendar for Dec 2024', () => {
    const expected = [
      [1, 2, 3, 4, 5, 6, 7],
      [8, 9, 10, 11, 12, 13, 14],
      [15, 16, 17, 18, 19, 20, 21],
      [22, 23, 24, 25, 26, 27, 28],
      [29, 30, 31, 1, 2, 3, 4],
      [5, 6, 7, 8, 9, 10, 11]
    ];
    testMonth('2024-12-01', expected);
  });
});
