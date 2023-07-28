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
import {useIsFocused} from '@react-navigation/native';

const HeaderTitle = ({channelName}) => (
  <Text numberOfLines={1} ellipsizeMode="tail" style={styles.title}>
    {channelName}
  </Text>
);

const AddMessageButton = ({navigation, channelId, token}) => (
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
);

const MessageList = ({route, navigation}) => {
  const {messages, loadMessagesForChannel} = useContext(MessageContext);
  const {channelId, channelName} = route.params;
  const {token} = useContext(AuthContext);

  const isFocused = useIsFocused();

  const sortMessagesByDate = msgs => {
    return msgs.slice().sort((a, b) => new Date(b.posted) - new Date(a.posted));
  };

  useEffect(() => {
    if (isFocused) {
      loadMessagesForChannel(channelId);
    }
  }, [channelId, loadMessagesForChannel, isFocused]);

  const sortedMessages = sortMessagesByDate(messages);

  const memoizedHeaderTitle = useCallback(
    () => <HeaderTitle channelName={channelName} />,
    [channelName],
  );

  const memoizedAddMessageButton = useCallback(
    () => (
      <AddMessageButton
        navigation={navigation}
        channelId={channelId}
        token={token}
      />
    ),
    [navigation, channelId, token],
  );

  useLayoutEffect(() => {
    navigation.setOptions({
      headerTitle: memoizedHeaderTitle,
      headerTitleAlign: 'center',
      headerBackTitleVisible: false,
      headerBackAccessibilityLabel: 'back to channels',
      headerRight: memoizedAddMessageButton, // Use the memoized version here
    });
  }, [navigation, memoizedHeaderTitle, memoizedAddMessageButton]);

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
