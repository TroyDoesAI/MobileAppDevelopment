// ChannelListItem.js

import React from 'react';
import {Text, View, TouchableOpacity} from 'react-native';

const ChannelListItem = ({channel, navigation, workspaceName}) => {
  const handlePress = () => {
    navigation.navigate('Messages', {
      channelId: channel.id,
      channelName: channel.name,
      workspaceName: workspaceName,
    });
  };

  return (
    <TouchableOpacity onPress={handlePress} accessibilityLabel={channel.name}>
      <View>
        <Text>{channel.name}</Text>
        <View>
          <Text accessibilityLabel={`count for ${channel.name}`}>
            {`${channel.messageCount ? 1 : ''}`}
          </Text>
        </View>
      </View>
    </TouchableOpacity>
  );
};

export default ChannelListItem;
