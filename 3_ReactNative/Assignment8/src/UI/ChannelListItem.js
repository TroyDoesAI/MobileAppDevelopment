// ChannelListItem.js
import React from 'react';
import {StyleSheet, Text, View, TouchableOpacity} from 'react-native';
import {formatElapsedTime} from '../Utils/ElapsedTimeFormatter';

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  item: {
    paddingLeft: 10,
    fontSize: 18,
  },
  details: {
    flexDirection: 'column',
    alignItems: 'flex-end',
  },
  detailText: {
    fontSize: 14,
    color: '#888',
  },
});

const ChannelListItem = ({channel, navigation, workspaceName}) => {
  const handlePress = () => {
    navigation.navigate('Messages', {
      channelId: channel.id,
      channelName: channel.name,
      workspaceName: workspaceName,
    });
  };

  const elapsedTime = formatElapsedTime(new Date(channel.mostRecentMessage));

  return (
    <TouchableOpacity onPress={handlePress} accessibilityLabel={channel.name}>
      <View style={styles.container}>
        <Text style={styles.item}>{channel.name}</Text>
        <View style={styles.details}>
          <Text
            style={styles.detailText}
            accessibilityLabel={`count for ${channel.name}`}>
            Messages: {channel.messages.length}
          </Text>
          <Text
            style={styles.detailText}
            accessibilityLabel={`members active in ${channel.name}`}>
            Members: {channel.uniquePosters}
          </Text>
          <Text
            style={styles.detailText}
            accessibilityLabel={`latest message in ${channel.name}`}>
            Latest: {elapsedTime} ago
          </Text>
        </View>
      </View>
    </TouchableOpacity>
  );
};

export default ChannelListItem;

/*
ChannelListItem.js: This component represents a single channel in the channel list within a specific workspace.

For each channel, it shows the channel name, the number of messages, the number of unique members, and the time of the latest message.
This component also contains a TouchableOpacity wrapper that enables navigation to the Messages page when pressed.
The ChannelListItem component receives the channel data, navigation object, and workspace name as props.
When a channel is pressed, the user is navigated to the Messages screen, with the channel ID, channel name, and workspace name passed as parameters.
Key elements of the channel information are assigned accessibility labels based on the channel's name to support automated UI testing.
*/
