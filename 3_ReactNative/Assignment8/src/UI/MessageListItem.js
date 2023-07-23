// MessageListItem.js

// Import necessary libraries and components
import React from 'react';
import {StyleSheet, Text, View, TouchableOpacity} from 'react-native';
import {formatDate} from '../Model/DataUtil'; // Import the formatDate function

// Define the MessageListItem component
const MessageListItem = ({message, navigation}) => {
  const handlePress = () => {
    navigation.navigate('MessageDetail', {message: message});
  };

  return (
    <TouchableOpacity onPress={handlePress}>
      <View style={styles.container}>
        <Text style={styles.posterName}>{message.member.name}</Text>
        <Text style={styles.item}>{message.content}</Text>
        <Text style={styles.date}>{formatDate(message.posted)}</Text>
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
  },
});

// Export the component
export default MessageListItem;
