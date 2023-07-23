// MessageDetail.js

import React from 'react';
import {StyleSheet, Text, View} from 'react-native';

const MessageDetail = ({route}) => {
  const {message, channelName} = route.params;
  const postedDate = new Date(message.posted);
  const options = {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
  };

  const dateFormatted = `${postedDate.toLocaleString(
    'en-US',
    options,
  )} at ${postedDate.toLocaleTimeString(['en-US'], {
    hour: 'numeric',
    minute: 'numeric',
  })}`;

  return (
    <View style={styles.container}>
      <Text style={styles.item}>{message.content}</Text>
      <Text style={styles.item}>{dateFormatted}</Text>
    </View>
  );
};

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

export default MessageDetail;
