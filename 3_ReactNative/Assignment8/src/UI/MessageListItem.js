// MessageListItem.js

// Import necessary libraries and components
import React from 'react';
import {StyleSheet, Text, View, TouchableOpacity} from 'react-native';
import {formatDate} from '../Model/DataUtil'; // Import the formatDate function

// Define the MessageListItem component
const MessageListItem = ({message, navigation}) => {
  // Define a function to handle when a message is clicked
  const handlePress = () => {
    navigation.navigate('MessageDetail', {message: message});
  };

  // Return the rendered component
  return (
    <TouchableOpacity onPress={handlePress}>
      <View style={styles.container}>
        <Text
          style={styles.posterName}
          accessibilityLabel={message.member.name}>
          {message.member.name}
        </Text>
        <Text style={styles.item} accessibilityLabel={message.content}>
          {message.content}
        </Text>
        <Text
          style={styles.date}
          accessibilityLabel={formatDate(message.posted)}>
          {formatDate(message.posted)}
        </Text>
      </View>
    </TouchableOpacity>
  );
};

// Define the styles for the component
const styles = StyleSheet.create({
  container: {
    flexDirection: 'column', // Change to column to stack name above message
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  posterName: {
    fontWeight: 'bold', // Give the poster name some weight
    marginBottom: 5, // Add some space between name and message
  },
  item: {
    paddingLeft: 10,
    fontSize: 18,
  },
  date: {
    paddingLeft: 10,
    fontSize: 14,
    color: '#888', // Make the date text a bit more muted
  },
});

// Export the component
export default MessageListItem;
