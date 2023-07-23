import React from 'react';
import { FlatList } from 'react-native';
import MessageListItem from './MessageListItem';

const MessageList = ({ route, navigation }) => { // Destructure both route and navigation
    const { messages } = route.params;

    return (
        <FlatList
            data={messages}
            keyExtractor={(item) => item.id}
            renderItem={({ item }) => <MessageListItem message={item} navigation={navigation} />}
        />
    );
};

export default MessageList;
