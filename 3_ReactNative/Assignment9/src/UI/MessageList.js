// MessageList.js

import React, {
  useLayoutEffect,
  useContext,
  useEffect,
  useCallback,
} from 'react';
import {FlatList, Text, StyleSheet} from 'react-native';
import MessageListItem from './MessageListItem';
import {MessageContext} from '../Model/MessageViewModel';
import AuthContext from '../Model/AuthContext';

const HeaderTitle = ({channelName}) => (
  <Text numberOfLines={1} ellipsizeMode="tail" style={styles.title}>
    {channelName}
  </Text>
);

const MessageList = ({route, navigation}) => {
  const {messages, loadMessagesForChannel} = useContext(MessageContext);
  const {channelId, channelName} = route.params;
  const {token} = useContext(AuthContext); // Extract the token from AuthContext

  const sortMessagesByDate = msgs => {
    return msgs.slice().sort((a, b) => new Date(b.posted) - new Date(a.posted));
  };

  useEffect(() => {
    loadMessagesForChannel(channelId);
  }, [channelId, loadMessagesForChannel]);

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
      headerBackAccessibilityLabel: 'back to channels', // Setting accessibilityLabel for the default back button
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
          accessToken={token} // Pass the token as accessToken prop
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
