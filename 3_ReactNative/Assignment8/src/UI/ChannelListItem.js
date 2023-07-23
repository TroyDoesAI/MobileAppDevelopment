import React from 'react';
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native';

const ChannelListItem = ({ channel, navigation }) => {

    const handlePress = () => {
        navigation.push('Messages', { messages: channel.messages });
    };

    return (
        <TouchableOpacity onPress={handlePress}>
            <View style={styles.container}>
                <Text style={styles.item}>{channel.name}</Text>
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

export default ChannelListItem;
