/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

/* **************************************************************************
 * Must be using Node.js Version 18 or above
 * **************************************************************************/

/* **************************************************************************
 * EDIT TO BE YOUR CREDENTIALS
 * **************************************************************************/

const cruzid = 'traschul@ucsc.edu'
const studentid = '1815098'

/* **************************************************************************
 * DO NOT MODIFY THIS FILE BELOW HERE
 * **************************************************************************/

import React from 'react';
import {
  render,
  screen,
  fireEvent,
  cleanup,
  waitFor,
  waitForElementToBeRemoved,
} from '@testing-library/react-native';

import App from '../App';

const login = (email = cruzid, password = studentid) => {
  render(<App />);
  fireEvent.changeText(screen.getByLabelText('email'), email);
  fireEvent.changeText(screen.getByLabelText('password'), password);
  fireEvent.press(screen.getByLabelText('login'));
};

const waitForTextThenClick = async text => {
  await waitFor(() => screen.getByText(text));
  fireEvent.press(screen.getByText(text));
};

const waitForLabelTextThenClick = async labelText => {
  await waitFor(() => screen.getByLabelText(labelText));
  fireEvent.press(screen.getByLabelText(labelText));
};

const waitForFirstLabelTextThenClick = async labelText => {
  await waitFor(() => screen.getAllByLabelText(labelText));
  fireEvent.press(screen.getAllByLabelText(labelText)[0]);
};

it('Accepts valid creditials', async () => {
  login();
  await waitFor(() => screen.getByLabelText('logout'));
});

it('Rejects invalid creditials', async () => {
  login('molly@cse118.com', 'wrong');
  screen.getByLabelText('login');
  cleanup();
});

it('Logs out after sucessful log in', async () => {
  login();
  await waitForLabelTextThenClick('logout');
  await waitFor(() => screen.getByLabelText('login'));
  cleanup();
});

it('Shows Workspaces', async () => {
  login();
  await waitFor(() => screen.getByText('Student Workspace'));
  cleanup();
});

it('Drills Down to Channels', async () => {
  login();
  await waitForTextThenClick('Student Workspace');
  await waitFor(() => screen.getByText('Student Channel'));
  cleanup();
});

it('Drills Down to Messages', async () => {
  login();
  await waitForTextThenClick('Student Workspace');
  await waitForTextThenClick('Student Channel');
  await waitFor(() => screen.getByText('Dr Harrison'));
  cleanup();
});

it('Drills Down to Message', async () => {
  login();
  await waitForTextThenClick('Student Workspace');
  await waitForTextThenClick('Student Channel');
  await waitForTextThenClick('Dr Harrison');
  await waitFor(() => screen.getByLabelText('back to channel'));
  cleanup();
});

it('Returns to Channel with Back Navigation', async () => {
  login();
  await waitForTextThenClick('Molly Workspace');
  await waitForTextThenClick('Molly Channel');
  await waitForLabelTextThenClick('add message');
  await waitForLabelTextThenClick('back to channel');
  cleanup();
});

it('Returns to Channel with Cancel', async () => {
  login();
  await waitForTextThenClick('Molly Workspace');
  await waitForTextThenClick('Molly Channel');
  await waitForLabelTextThenClick('add message');
  await waitForTextThenClick('Cancel');
  cleanup();
});

/*
 * Navigate to a known Channel
 * Add a message
 * Navigate back to Workspaces
 * Press Reset
 * Navigate to known Channel's Workspace
 * Check message count is blank
 */
it('Resets', async () => {
  login();
  await waitForTextThenClick('Student Workspace');
  await waitForTextThenClick('Extra Channel');
  await waitForLabelTextThenClick('add message');
  fireEvent.changeText(screen.getByLabelText('content'), 'some junk');
  fireEvent.press(screen.getByLabelText('add'));
  await waitFor(() => screen.getByLabelText('add message'));
  await waitForLabelTextThenClick('back to channels');
  await waitForLabelTextThenClick('back to workspaces');
  await waitForLabelTextThenClick('reset');
  await new Promise(resolve => setTimeout(resolve, 1000));
  await waitForTextThenClick('Student Workspace');
  await screen.findByText('Extra Channel');
  await screen.findByLabelText('count for Extra Channel');
  let count = Number(
    screen.getByLabelText('count for Extra Channel').children[0],
  );
  expect(count).toBe(NaN);
  cleanup();
});

/*
 * Navigate to a known Channel
 * Add a message
 * Check it appears in the Channel Message list
 * Navigate back up to Channel list
 * Check the Channel Message count went up by one
 * Navigate back to the known Channel
 * Delete the message by swiping left
 * Check it has been removed from the Channel Message list
 * Navigate back up to Channel list
 * Check the Channel Message count went down by one
 */
it('Adds and Deletes Messages', async () => {
  login();
  let content = new Date().toISOString();
  await waitForTextThenClick('Student Workspace');
  await screen.findByLabelText('count for Another Channel');
  let count = Number(
    screen.getByLabelText('count for Another Channel').children[0],
  );
  await waitForTextThenClick('Another Channel');
  await waitForLabelTextThenClick('add message');
  fireEvent.changeText(screen.getByLabelText('content'), content);
  fireEvent.press(screen.getByLabelText('add'));
  await waitFor(() => screen.getByLabelText('add message'));
  await waitFor(() => screen.getByText(content));
  await waitForLabelTextThenClick('back to channels');
  await waitFor(() => screen.getByLabelText('add channel'));
  await screen.findByLabelText('count for Another Channel');
  let newCount = Number(
    screen.getByLabelText('count for Another Channel').children[0],
  );
  fireEvent.press(screen.getByText('Another Channel'));
  await waitFor(() => screen.getByText(content));
  fireEvent(screen.getByText(content), 'swipeableRightOpen');
  await waitForFirstLabelTextThenClick('delete message');
  await waitForElementToBeRemoved(() => screen.getByText(content));
  await waitForLabelTextThenClick('back to channels');
  await waitFor(() => screen.getByLabelText('add channel'));
  await waitFor(() => screen.getByText('Another Channel'));
  let finalCount = Number(
    screen.getByLabelText('count for Another Channel').children[0],
  );
  cleanup();
  expect(newCount).toBe(isNaN(count) ? 1 : count + 1);
  expect(finalCount).toBe(count);
});