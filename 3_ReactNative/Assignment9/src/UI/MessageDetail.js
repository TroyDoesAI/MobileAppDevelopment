// MessageDetail.js

// Import necessary libraries and components
import React from 'react';
import {StyleSheet, Text, View, TouchableOpacity} from 'react-native'; // <-- Added TouchableOpacity
import {formatDate} from '../Model/DataUtil';

const MessageDetail = ({route, navigation}) => {
  const {message, channelName} = route.params;
  const dateFormatted = message.posted;

  return (
    <View style={styles.container}>
      <Text style={styles.item}>{message.content}</Text>
      <Text style={styles.item}>{dateFormatted}</Text>

      <TouchableOpacity
        onPress={() => navigation.goBack()} // <-- This makes the button go back
        accessibilityLabel="back to channel" // <-- This is what the test is looking for
        style={styles.backButton}>
        <Text style={styles.backText}>Back to {channelName}</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 10,
  },
  item: {
    fontSize: 24,
    alignSelf: 'center',
  },
  date: {
    width: '100%',
    fontSize: 24,
    textAlign: 'right',
  },
  backButton: {
    marginTop: 20,
    padding: 10,
    backgroundColor: '#ddd', // A light gray background color
    borderRadius: 5,
  },
  backText: {
    fontSize: 18,
  },
});

export default MessageDetail;
