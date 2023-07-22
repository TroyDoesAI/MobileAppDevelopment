/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

/* **************************************************************************
 * DO NOT MODIFY THIS FILE
 * **************************************************************************/

import React from 'react';
import {render, screen, fireEvent} from '@testing-library/react-native';

import PostfixCalculatorView from '../../src/UI/PostfixCalculatorView';

it('Evaluates a simple expression', async () => {
  render(<PostfixCalculatorView />);
  fireEvent.changeText(screen.getByLabelText('expression'), '1 3.2 +');
  fireEvent.press(screen.getByLabelText('evaluate'));
  expect(screen.getByLabelText('result')).toHaveTextContent('4.2');
});

it('Shows error on invalid expression', async () => {
  render(<PostfixCalculatorView />);
  fireEvent.changeText(screen.getByLabelText('expression'), '1 + 3.2');
  fireEvent.press(screen.getByLabelText('evaluate'));
  expect(screen.getByLabelText('result')).toHaveTextContent(
    'Invalid Expression',
  );
});

it('Shows error on unknown operator', async () => {
  render(<PostfixCalculatorView />);
  fireEvent.changeText(screen.getByLabelText('expression'), '1 3.2 !');
  fireEvent.press(screen.getByLabelText('evaluate'));
  expect(screen.getByLabelText('result')).toHaveTextContent('Unknown Operator');
});

it('Clears both fields', async () => {
  render(<PostfixCalculatorView />);
  fireEvent.changeText(screen.getByLabelText('expression'), '1 3.2 +');
  fireEvent.press(screen.getByLabelText('evaluate'));
  fireEvent.press(screen.getByLabelText('clear'));
  expect(screen.getByLabelText('expression')).toHaveTextContent('');
  expect(screen.getByLabelText('result')).toHaveTextContent('');
});
