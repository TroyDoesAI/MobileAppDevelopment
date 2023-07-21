/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

import React from 'react';
import { render, fireEvent } from '@testing-library/react-native';
import CalendarGeneratorView from '../../src/UI/CalendarGeneratorView';

import App from '../../App';

it('Renders', async () => {
  render(<App />);
});

describe('CalendarGeneratorView interactions', () => {
  it('Changes to the next Month when the Next button is pressed', () => {
    const { getByText, queryByText } = render(<CalendarGeneratorView />);

    const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

    // Identify the currently displayed month
    let currentMonth = monthNames.find(month => queryByText(`${month} 2023`));

    expect(currentMonth).toBeTruthy();

    // Determine the next month
    const nextMonthIndex = (monthNames.indexOf(currentMonth) + 1) % 12; // Use modulo to wrap around to January
    const nextMonthName = monthNames[nextMonthIndex];

    // Find the "Next" button and tap it
    const nextButton = getByText('Next');
    fireEvent.press(nextButton);

    // Verify the next month is displayed
    expect(queryByText(`${nextMonthName} 2023`)).toBeTruthy();

    // Optionally, ensure previous month is no longer visible
    expect(queryByText(`${currentMonth} 2023`)).toBeFalsy();
  });
});