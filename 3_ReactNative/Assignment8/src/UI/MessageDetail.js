// MessageDetail.js

// Import necessary libraries and components
import React from 'react';
import {StyleSheet, Text, View} from 'react-native';
import {formatDate} from '../Model/DataUtil'; // Import the formatDate function

// Define the MessageDetail component
const MessageDetail = ({route}) => {
  const {message, channelName} = route.params;
  const dateFormatted = formatDate(message.posted);

  // Return the rendered component
  return (
    <View style={styles.container}>
      <Text style={styles.item}>{message.content}</Text>
      <Text style={styles.item}>{dateFormatted}</Text>
    </View>
  );
};

// Define the styles for the component
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  item: {
    fontSize: 24,
  },
});

// Export the component
export default MessageDetail;
