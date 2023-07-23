import React from 'react';
import { StyleSheet, Text, View } from 'react-native';

const MessageDetail = ({ route }) => {
    const { message } = route.params;

    return (
        <View style={styles.container}>
            <Text style={styles.item}>{message.content}</Text>
            {/* TODO Add any other details you want to display */}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    item: {
        fontSize: 24,
    },
});

export default MessageDetail;
