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
import {
  render,
  screen,
  fireEvent,
  cleanup,
} from '@testing-library/react-native';

import App from '../../App';

it('Shows Workspaces', async () => {
  render(<App />);
  await screen.findByText('Workspaces');
  await screen.findByText('Elevator');
  let items = await screen.findAllByText('EIFS');
  expect(items.length).toBe(2);
});

it('Drills Down to Channels', async () => {
  render(<App />);
  fireEvent.press(await screen.findByText('Elevator'));
  await screen.findByText('Painter');
  await screen.findByText('Refridgeration');
});

it('Drills Down to Messages', async () => {
  render(<App />);
  fireEvent.press(await screen.findByText('Elevator'));
  fireEvent.press(await screen.findByText('Painter'));
  await screen.findByText('Gideon Huckle');
  await screen.findByText('Gabbi Bilsland');
  await screen.findByText('Apr 18, 2023 at 7:06 PM');
  await screen.findByText('Apr 14, 2022 at 8:06 AM');
});

it('Drills Down to Message', async () => {
  render(<App />);
  fireEvent.press(await screen.findByText('Elevator'));
  fireEvent.press(await screen.findByText('Painter'));
  fireEvent.press(await screen.findByText('Gideon Huckle'));
  await screen.findAllByText('Gideon Huckle');
  await screen.findAllByText('Aug 26, 2022 at 9:29 AM');
});

it('Navigates One Level', async () => {
  render(<App />);
  fireEvent.press(await screen.findByText('Elevator'));
  await screen.findByText('Painter');
  fireEvent.press(await screen.findByLabelText('back to workspaces'));
  await screen.findAllByText('Elevator');
  cleanup();
});

it('Navigates Two Levels', async () => {
  render(<App />);
  fireEvent.press(await screen.findByText('Elevator'));
  fireEvent.press(await screen.findByText('Painter'));
  fireEvent.press(await screen.findByLabelText('back to channels'));
  await screen.findAllByText('Painter');
  fireEvent.press(await screen.findByLabelText('back to workspaces'));
  await screen.findAllByText('Elevator');
  cleanup();
});

it('Navigates Three Levels', async () => {
  render(<App />);
  fireEvent.press(await screen.findByText('Elevator'));
  fireEvent.press(await screen.findByText('Painter'));
  fireEvent.press(await screen.findByText('Gideon Huckle'));
  fireEvent.press(await screen.findByLabelText('back to channel'));
  await screen.findAllByText('Gideon Huckle');
  fireEvent.press(await screen.findByLabelText('back to channels'));
  await screen.findAllByText('Painter');
  fireEvent.press(await screen.findByLabelText('back to workspaces'));
  await screen.findAllByText('Elevator');
  cleanup();
});
