/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 */

// import React from 'react';
// import { View } from 'react-native';

// import CalendarGenerator from '../Logic/CalendarGenerator';

// const CalendarGeneratorView = () => {
//   return (
//     <View>
//       {/* Your JSX goes here */}
//     </View>
//   );
// };

// export default CalendarGeneratorView;




import React, { useState } from 'react';
import { View, Text, Button, StyleSheet, FlatList } from 'react-native';

import CalendarGenerator from '../Logic/CalendarGenerator';


// CalendarGeneratorView.js
const CalendarGeneratorView = () => {
    const [date, setDate] = useState(new Date());

    const monthYearFormat = (date) => {
        const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        return `${monthNames[date.getMonth()]} ${date.getFullYear()}`;
    };

    // Adjusting month when calling generate since javascript likes to index by 0, and we want our tests indexing from 1 for january.
    const calendarArray = CalendarGenerator.generate(new Date(date.getFullYear(), date.getMonth() - 1, date.getDate()));


    const previousMonth = () => {
        setDate(prevDate => new Date(prevDate.getFullYear(), prevDate.getMonth() - 1, 1));
    };

    const nextMonth = () => {
        setDate(prevDate => new Date(prevDate.getFullYear(), prevDate.getMonth() + 1, 1));
    };

    const currentMonth = () => {
      const now = new Date();
      setDate(new Date(now.getFullYear(), now.getMonth(), 1));
  };
  

    return (
        <View style={styles.container}>
            <Text style={styles.dateText}>{monthYearFormat(date)}</Text>
            <FlatList
                data={calendarArray}
                renderItem={({ item: week }) => (
                    <View style={styles.week}>
                        {week.map((day, index) => (
                            <Text key={index} style={styles.day}>{day}</Text>
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

export default CalendarGeneratorView;

