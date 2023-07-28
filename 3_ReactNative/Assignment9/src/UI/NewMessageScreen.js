// NewMessageScreen.js

import React, {useState, useLayoutEffect} from 'react';
import {View, TextInput, Button, StyleSheet} from 'react-native';

const NewMessageScreen = ({route, navigation}) => {
  const [messageContent, setMessageContent] = useState('');
  const channelId = route.params.channelId;
  const accessToken = route.params.token;

  // Use the useLayoutEffect hook to set navigation options for the component
  useLayoutEffect(() => {
    navigation.setOptions({
      // This will set the accessibility label for the default back navigation
      headerBackAccessibilityLabel: 'back to channel',
      headerBackTitle: 'Cancel',
    });
  }, [navigation]);

  const handleAddMessage = async () => {
    const response = await fetch(
      `https://cse118.com/api/v2/channel/${channelId}/message`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${accessToken}`,
        },
        body: JSON.stringify({
          content: messageContent,
        }),
      },
    );
    const responseData = await response.json();
    navigation.goBack();
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        value={messageContent}
        onChangeText={setMessageContent}
        accessibilityLabel="content"
        placeholder="Enter your message"
      />
      <Button title="Add" onPress={handleAddMessage} accessibilityLabel="add" />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    padding: 10,
  },
  input: {
    borderWidth: 1,
    borderColor: 'gray',
    padding: 10,
    marginBottom: 20,
  },
});

export default NewMessageScreen;
