import React, {useState} from 'react';
import {View, TextInput, Button, StyleSheet, Alert} from 'react-native';

const NewMessageScreen = ({route, navigation}) => {
  const [messageContent, setMessageContent] = useState('');
  const channelId = route.params.channelId;
  const accessToken = route.params.token; // Retrieve the token from route.params

  const handleAddMessage = async () => {
    try {
      const response = await fetch(
        `https://cse118.com/api/v2/channel/${channelId}/message`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${accessToken}`, // Use the actual token here
          },
          body: JSON.stringify({
            content: messageContent,
          }),
        },
      );

      if (response.status !== 201) {
        throw new Error('Failed to add message');
      }

      const responseData = await response.json();
      console.log('New message added:', responseData);

      navigation.goBack();
    } catch (error) {
      Alert.alert('Error', 'Failed to add the message. Please try again.', [
        {text: 'OK'},
      ]);
    }
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
      <Button title="Add" onPress={handleAddMessage} />
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
