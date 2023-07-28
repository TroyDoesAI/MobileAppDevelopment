// MessageList.js

import React, {
  useLayoutEffect,
  useContext,
  useEffect,
  useCallback,
} from 'react';
import {FlatList, Text, StyleSheet, Button} from 'react-native';
import MessageListItem from './MessageListItem';
import {MessageContext} from '../Model/MessageViewModel';
import AuthContext from '../Model/AuthContext';
import {useIsFocused} from '@react-navigation/native'; // <-- Import the useIsFocused hook

const HeaderTitle = ({channelName}) => (
  <Text numberOfLines={1} ellipsizeMode="tail" style={styles.title}>
    {channelName}
  </Text>
);

const MessageList = ({route, navigation}) => {
  const {messages, loadMessagesForChannel} = useContext(MessageContext);
  const {channelId, channelName} = route.params;
  const {token} = useContext(AuthContext);

  const isFocused = useIsFocused(); // <-- Use the useIsFocused hook here

  const sortMessagesByDate = msgs => {
    return msgs.slice().sort((a, b) => new Date(b.posted) - new Date(a.posted));
  };

  useEffect(() => {
    if (isFocused) {
      // <-- Check if the screen is focused
      loadMessagesForChannel(channelId); // If so, fetch the messages
    }
  }, [channelId, loadMessagesForChannel, isFocused]); // <-- Add isFocused as a dependency

  const sortedMessages = sortMessagesByDate(messages);

  const memoizedHeaderTitle = useCallback(
    () => <HeaderTitle channelName={channelName} />,
    [channelName],
  );

  useLayoutEffect(() => {
    navigation.setOptions({
      headerTitle: memoizedHeaderTitle,
      headerTitleAlign: 'center',
      headerBackTitleVisible: false,
      headerBackAccessibilityLabel: 'back to channels',
      headerRight: () => (
        <Button
          title="add"
          onPress={() =>
            navigation.navigate('NewMessage', {
              channelId: channelId,
              token: token,
            })
          }
          accessibilityLabel="add message"
        />
      ),
    });
  }, [navigation, memoizedHeaderTitle]);

  return (
    <FlatList
      data={sortedMessages}
      keyExtractor={item => item.id.toString()}
      renderItem={({item}) => (
        <MessageListItem
          message={item}
          navigation={navigation}
          channelName={channelName}
          accessToken={token}
        />
      )}
      initialNumToRender={20}
    />
  );
};

const styles = StyleSheet.create({
  title: {
    fontSize: 16,
  },
});

export default MessageList;
