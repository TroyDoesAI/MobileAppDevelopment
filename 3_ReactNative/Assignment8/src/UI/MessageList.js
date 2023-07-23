import React, { useLayoutEffect } from 'react';
import { FlatList, Text, StyleSheet, TouchableOpacity } from 'react-native';
import MessageListItem from './MessageListItem';
import Icon from 'react-native-vector-icons/MaterialIcons';

const MessageList = ({ route, navigation }) => {
    const { messages, channelName, workspaceName } = route.params; // Assuming you'll pass both channelName and workspaceName as params

    useLayoutEffect(() => {
        navigation.setOptions({
            headerTitle: () => (
                <Text numberOfLines={1} ellipsizeMode="tail" style={styles.title}>
                    {channelName}
                </Text>
            ),
            headerLeft: () => (
                <TouchableOpacity onPress={() => navigation.goBack()} style={styles.backButton}>
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
            data={messages}
            keyExtractor={(item) => item.id.toString()}
            renderItem={({ item }) => (
                <MessageListItem 
                    message={item} 
                    navigation={navigation}
                    channelName={channelName}
                />
            )}
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
        marginLeft: 10
    },
    backText: {
        fontSize: 14,
        marginLeft: 5, 
        color: 'blue'
    }
});

export default MessageList;