// ChanneListItem.js
import React from 'react';
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native';

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

const ChannelListItem = ({ channel, navigation, workspaceName }) => {
    const handlePress = () => {
        navigation.navigate('Messages', {  
            messages: channel.messages,  
            channelName: channel.name,
            workspaceName: workspaceName
        });
    };
    return (
        <TouchableOpacity onPress={handlePress}>
            <View style={styles.container}>
                <Text style={styles.item}>{channel.name}</Text>
            </View>
        </TouchableOpacity>
    );
};
export default ChannelListItem;
