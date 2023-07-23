// MessageListItem.js
import React from 'react';
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native';

const MessageListItem = ({ message, navigation }) => {
    const handlePress = () => {
        navigation.navigate('MessageDetail', { message: message });
    };

    return (
        <TouchableOpacity onPress={handlePress}>
            <View style={styles.container}>
                <Text style={styles.posterName} accessibilityLabel={message.member.name}>
                    {message.member.name}
                </Text>
                <Text style={styles.item} accessibilityLabel={message.content}>
                    {message.content}
                </Text>
            </View>
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    container: {
        flexDirection: 'column', // Change to column to stack name above message
        padding: 10,
        borderBottomWidth: 1,
        borderBottomColor: '#ddd',
    },
    posterName: {
        fontWeight: 'bold',   // Give the poster name some weight
        marginBottom: 5,      // Add some space between name and message
    },
    item: {
        paddingLeft: 10,
        fontSize: 18,
    },
});
export default MessageListItem;
