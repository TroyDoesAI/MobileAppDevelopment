import React, {
  useLayoutEffect,
  useContext,
  useEffect,
  useCallback,
} from 'react';
import {FlatList, Text, StyleSheet, TouchableOpacity} from 'react-native';
import MessageListItem from './MessageListItem';
import Icon from 'react-native-vector-icons/MaterialIcons';
import {MessageContext} from '../Model/MessageViewModel';

const HeaderTitle = ({channelName}) => (
  <Text numberOfLines={1} ellipsizeMode="tail" style={styles.title}>
    {channelName}
  </Text>
);

const HeaderLeft = ({navigation, workspaceName}) => (
  <TouchableOpacity
    onPress={() => navigation.goBack()}
    style={styles.backButton}
    accessibilityLabel="back to channels">
    <Icon name="chevron-left" size={25} color="blue" />
    <Text style={styles.backText}>{workspaceName}</Text>
  </TouchableOpacity>
);

const MessageList = ({route, navigation}) => {
  const {messages, loadMessagesForChannel} = useContext(MessageContext);
  const {channelId, channelName, workspaceName} = route.params;

  const sortMessagesByDate = msgs => {
    return msgs.slice().sort((a, b) => new Date(b.posted) - new Date(a.posted));
  };

  // useEffect(() => {
  //   console.log("\n\nFetching messages for channelId:", channelId);
  //   loadMessagesForChannel(channelId);
  // }, [channelId, loadMessagesForChannel]);

  const sortedMessages = sortMessagesByDate(messages);

  const memoizedHeaderTitle = useCallback(
    () => <HeaderTitle channelName={channelName} />,
    [channelName],
  );
  const memoizedHeaderLeft = useCallback(
    () => <HeaderLeft navigation={navigation} workspaceName={workspaceName} />,
    [navigation, workspaceName],
  );

  useLayoutEffect(() => {
    navigation.setOptions({
      headerTitle: memoizedHeaderTitle,
      headerLeft: memoizedHeaderLeft,
      headerTitleAlign: 'center',
      headerBackTitleVisible: false,
    });
  }, [navigation, memoizedHeaderTitle, memoizedHeaderLeft]);

  return (
    <FlatList
      data={sortedMessages}
      keyExtractor={item => item.id.toString()}
      renderItem={({item}) => (
        <MessageListItem
          message={item}
          navigation={navigation}
          channelName={channelName}
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
  backButton: {
    flexDirection: 'row',
    alignItems: 'center',
    marginLeft: 10,
  },
  backText: {
    fontSize: 14,
    marginLeft: 5,
    color: 'blue',
  },
});

export default MessageList;
