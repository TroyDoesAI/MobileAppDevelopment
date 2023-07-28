// MessageDetail.js

import React, {useEffect} from 'react'; // <-- Import useEffect
import {StyleSheet, Text, View} from 'react-native';

const MessageDetail = ({route, navigation}) => {
  const {message, channelName} = route.params;
  const dateFormatted = message.posted;

  useEffect(() => {
    // Set the accessibilityLabel for the default navigation back button
    navigation.setOptions({
      headerBackAccessibilityLabel: 'back to channel',
    });
  }, [navigation]);

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
});

export default MessageDetail;
