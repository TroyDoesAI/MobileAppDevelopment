import React from 'react';
import { FlatList, StyleSheet, Text, View } from 'react-native';

const MessageList = ({ route }) => {
    const { messages } = route.params;

    return (
        <FlatList
            data={messages}
            keyExtractor={(item) => item.id}
            renderItem={({ item }) => (
                <View style={styles.container}>
                    <Text style={styles.item}>{item.content}</Text>
                </View>
            )}
        />
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

export default MessageList;
