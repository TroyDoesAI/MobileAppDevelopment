import React, {useLayoutEffect, useContext, useEffect} from 'react';
import {FlatList, Text, StyleSheet, TouchableOpacity} from 'react-native';
import MessageListItem from './MessageListItem';
import Icon from 'react-native-vector-icons/MaterialIcons';
import {MessageContext} from '../Model/MessageViewModel';
import {GET_MESSAGES_FOR_CHANNEL} from '../Repo/MessageRepo';

const MessageList = ({route, navigation}) => {
  const {messages, setMessages} = useContext(MessageContext);
  const {channelId, channelName, workspaceName} = route.params;

  const sortMessagesByDate = messages => {
    return messages
      .slice()
      .sort((a, b) => new Date(b.posted) - new Date(a.posted));
  };

  const sortedMessages = sortMessagesByDate(messages);

  useEffect(() => {
    const fetchMessagesForChannel = async channelId => {
      const fetchedMessages = await GET_MESSAGES_FOR_CHANNEL(channelId);
      setMessages(fetchedMessages);
    };
    fetchMessagesForChannel(route.params.channelId);
  }, [route.params]);

  useLayoutEffect(() => {
    navigation.setOptions({
      headerTitle: () => (
        <Text numberOfLines={1} ellipsizeMode="tail" style={styles.title}>
          {channelName}
        </Text>
      ),
      headerLeft: () => (
        <TouchableOpacity
          onPress={() => navigation.goBack()}
          style={styles.backButton}
          accessibilityLabel="back to channels">
          <Icon name="chevron-left" size={25} color="blue" />
          <Text style={styles.backText}>{workspaceName}</Text>
        </TouchableOpacity>
      ),
      headerTitleAlign: 'center',
      headerBackTitleVisible: false,
    });
  }, [navigation, channelName, workspaceName]);

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
