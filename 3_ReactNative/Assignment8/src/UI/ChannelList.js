// ChannelList.js

import React, { useLayoutEffect } from 'react';
import { FlatList, Text, StyleSheet, TouchableOpacity } from 'react-native';
import ChannelListItem from './ChannelListItem';
import Icon from 'react-native-vector-icons/MaterialIcons';

const ChannelList = ({ navigation, route }) => {
    const { channels, workspaceName } = route.params;

    useLayoutEffect(() => {
        navigation.setOptions({
            headerTitle: () => (
                <Text numberOfLines={1} ellipsizeMode="tail" style={styles.title}>
                    {workspaceName}
                </Text>
            ),
            headerLeft: () => (
                <TouchableOpacity onPress={() => navigation.goBack()} style={styles.backButton}>
                    <Icon name="chevron-left" size={25} color="blue" />
                    <Text style={styles.backText}>Workspaces</Text>
                </TouchableOpacity>
            ),
            headerTitleAlign: 'center',
            headerBackTitleVisible: false,
        });
    }, [navigation, workspaceName]);

    return (
        <FlatList
            data={channels}
            keyExtractor={(item) => item.id.toString()}
            renderItem={({ item }) => (
                <ChannelListItem
                    channel={item}
                    navigation={navigation}
                    workspaceName={workspaceName}  // Pass the workspace name to ChannelListItem
                />
            )}
        />
    );
};

const styles = StyleSheet.create({
    title: {
        fontSize: 10,
        fontWeight: 'bold',

    },
    backButton: {
        flexDirection: 'row',
        alignItems: 'center',
        marginLeft: 10
    },
    backText: {
        fontSize: 10,
        marginLeft: 5, 
        color: 'blue'
    }
});

export default ChannelList;
