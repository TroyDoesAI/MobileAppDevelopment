/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

// Import the necessary modules from the React and React-Native libraries.
import React, {useState} from 'react';
import {View, Text, Button, StyleSheet, FlatList} from 'react-native';
import CalendarGenerator from '../Logic/CalendarGenerator';

// The CalendarGeneratorView component displays a calendar for a specified date and allows the user to navigate between months.
const CalendarGeneratorView = () => {
  // Initialize state variable 'date' with the current date using the useState hook.
  const [date, setDate] = useState(new Date());

  // Function to format the date into a string of 'Month Year'.
  const monthYearFormat = date => {
    const monthNames = [
      'January',
      'February',
      'March',
      'April',
      'May',
      'June',
      'July',
      'August',
      'September',
      'October',
      'November',
      'December',
    ];
    return `${monthNames[date.getMonth()]} ${date.getFullYear()}`;
  };

  // Adjusting month when calling generate since javascript likes to index by 0, and we want our tests indexing from 1 for january.
  // Generate the calendar array for the current date. Adjust the month for zero indexing.
  const calendarArray = CalendarGenerator.generate(
    new Date(date.getFullYear(), date.getMonth() - 1, date.getDate()),
  );

  // Function to change to the previous month.
  const previousMonth = () => {
    setDate(
      prevDate => new Date(prevDate.getFullYear(), prevDate.getMonth() - 1, 1),
    );
  };

  // Function to change to the next month.
  const nextMonth = () => {
    setDate(
      prevDate => new Date(prevDate.getFullYear(), prevDate.getMonth() + 1, 1),
    );
  };

  // Function to return to the current month.
  const currentMonth = () => {
    const now = new Date();
    setDate(new Date(now.getFullYear(), now.getMonth(), 1));
  };

  // Render the component.
  return (
    <View style={styles.container}>
      <Text style={styles.dateText}>{monthYearFormat(date)}</Text>
      <FlatList
        data={calendarArray}
        renderItem={({item: week}) => (
          <View style={styles.week}>
            {week.map((day, index) => (
              <Text key={index} style={styles.day}>
                {day}
              </Text>
            ))}
          </View>
        )}
        keyExtractor={(item, index) => index.toString()}
      />
      <View style={styles.buttonContainer}>
        <Button title="Previous" onPress={previousMonth} />
        <Button title="Today" onPress={currentMonth} />
        <Button title="Next" onPress={nextMonth} />
      </View>
    </View>
  );
};

// Define the styles for the various elements in the component.
const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
  },
  dateText: {
    fontSize: 20,
    textAlign: 'center',
    marginVertical: 20,
  },
  week: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    marginBottom: 5,
  },
  day: {
    width: 30,
    textAlign: 'center',
  },
  buttonContainer: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    marginTop: 20,
  },
});

// Export the component for use in other parts of the app.
export default CalendarGeneratorView;
