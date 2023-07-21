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
  const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
  
  const getCurrentMonth = (queryByText) => {
    return monthNames.find(month => queryByText(`${month} 2023`));
  };

  it('Changes to the next Month when the Next button is pressed', () => {
    const { getByText, queryByText } = render(<CalendarGeneratorView />);

    let currentMonth = getCurrentMonth(queryByText);
    expect(currentMonth).toBeTruthy();

    const nextMonthIndex = (monthNames.indexOf(currentMonth) + 1) % 12;
    const nextMonthName = monthNames[nextMonthIndex];

    const nextButton = getByText('Next');
    fireEvent.press(nextButton);

    expect(queryByText(`${nextMonthName} 2023`)).toBeTruthy();
    expect(queryByText(`${currentMonth} 2023`)).toBeFalsy();
  });

  it('Changes to the previous Month when the Previous button is pressed', () => {
    const { getByText, queryByText } = render(<CalendarGeneratorView />);

    let currentMonth = getCurrentMonth(queryByText);
    expect(currentMonth).toBeTruthy();

    const prevMonthIndex = (monthNames.indexOf(currentMonth) - 1 + 12) % 12; // Adding 12 to avoid negative numbers
    const prevMonthName = monthNames[prevMonthIndex];

    const prevButton = getByText('Previous');
    fireEvent.press(prevButton);

    expect(queryByText(`${prevMonthName} 2023`)).toBeTruthy();
    expect(queryByText(`${currentMonth} 2023`)).toBeFalsy();
  });

  it('Changes to the current real-world Month when the Today button is pressed', () => {
    const { getByText, queryByText } = render(<CalendarGeneratorView />);

    const realCurrentMonth = monthNames[new Date().getMonth()];

    const todayButton = getByText('Today');
    fireEvent.press(todayButton);

    expect(queryByText(`${realCurrentMonth} 2023`)).toBeTruthy(); // If the real-world date is within 2023
  });
});
