import React from 'react';
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native';
import MessageDetail from './MessageDetail';

const MessageListItem = ({ message, navigation }) => {
    const handlePress = () => {
        navigation.navigate('MessageDetail', { message: message });
    };

    return (
        <TouchableOpacity onPress={handlePress}>
            <View style={styles.container}>
                <Text style={styles.item} accessibilityLabel={message.content}>
                    {message.content}
                </Text>
            </View>
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        padding: 10,
        borderBottomWidth: 1,
        borderBottomColor: '#ddd',
    },
    item: {
        paddingLeft: 10,
        fontSize: 18,
    },
});

export default MessageListItem;
