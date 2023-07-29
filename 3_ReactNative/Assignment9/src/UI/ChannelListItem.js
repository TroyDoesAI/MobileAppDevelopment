// ChannelListItem.js
import React from 'react';
import {StyleSheet, Text, View, TouchableOpacity} from 'react-native';

const styles = StyleSheet.create({
  // Your styles here
});

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
      <View style={styles.container}>
        <Text style={styles.item}>{channel.name}</Text>
        <View style={styles.details}>
          <Text
            style={styles.detailText}
            accessibilityLabel={`count for ${channel.name}`}>
            {`${channel.messageCount ? 1 : ''}`}
          </Text>
        </View>
      </View>
    </TouchableOpacity>
  );
};

export default ChannelListItem;
