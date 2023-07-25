// MessageListItem.js

// Import necessary libraries and components
import React from 'react';
import {StyleSheet, Text, View, TouchableOpacity} from 'react-native';
import {formatDate} from '../Model/DataUtil'; // <-- Imported formatDate function

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
    flexDirection: 'column',
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  posterName: {
    fontWeight: 'bold',
    marginBottom: 5,
  },
  item: {
    paddingLeft: 10,
    fontSize: 18,
  },
  date: {
    width: '100%', // Stretch across container
    paddingLeft: 10,
    fontSize: 14,
    textAlign: 'right', // This aligns the text to the right
  },
});

// Export the component
export default MessageListItem;
