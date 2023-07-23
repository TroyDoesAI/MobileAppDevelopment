import React from 'react';
import { FlatList } from 'react-native';
import ChannelListItem from './ChannelListItem';

const ChannelList = ({ navigation, route }) => {
    const { channels } = route.params;

    return (
        <FlatList
            data={channels}
            keyExtractor={(item) => item.id}
            renderItem={({ item }) => (
                <ChannelListItem
                    channel={item}
                    navigation={navigation}
                />
            )}
        />
    );
};

export default ChannelList;
